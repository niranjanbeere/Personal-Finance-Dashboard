package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.TransactionEntity
import com.example.data.model.VaultGoalEntity
import com.example.data.repository.ApexVaultRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat

enum class ApexScreen {
  OVERVIEW,
  ANALYTICS,
  QUICK_ACTION,
  VAULT,
  LEDGER
}

data class ApexUiState(
  val currentScreen: ApexScreen = ApexScreen.OVERVIEW,
  val isNetWorthHidden: Boolean = false,
  val activeTimeframe: String = "MONTHLY",
  val focusedCategoryAmount: String = "$3,820",
  val focusedCategoryLabel: String = "Total Out",
  val isRoundUpEnabled: Boolean = true,
  val isPaycheckSplitterEnabled: Boolean = true,
  val unreadNotifications: Int = 1,
  val toastMessage: String? = null,
  // Quick Action / Log Transaction State
  val logFlowType: String = "EXPENSE",
  val logAmount: String = "84.50",
  val logCategory: String = "Groceries",
  val logMerchant: String = "Whole Foods Market",
  val logSubtitle: String = "Organic Provisions",
  val logLocation: String = "Union Square • New York, NY",
  val logTags: List<String> = listOf("reimbursable", "vacation"),
  val logIsRecurring: Boolean = false,
  val logSplitCount: Int = 4,
  val isSavedSuccess: Boolean = false
)

class ApexVaultViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: ApexVaultRepository

  private val _uiState = MutableStateFlow(ApexUiState())
  val uiState: StateFlow<ApexUiState> = _uiState.asStateFlow()

  val allTransactions: StateFlow<List<TransactionEntity>>
  val recentTransactions: StateFlow<List<TransactionEntity>>
  val vaultGoals: StateFlow<List<VaultGoalEntity>>

  init {
    val db = AppDatabase.getDatabase(application)
    repository = ApexVaultRepository(db.transactionDao(), db.vaultDao())

    allTransactions = repository.allTransactions
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    recentTransactions = repository.recentTransactions
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    vaultGoals = repository.allVaultGoals
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    viewModelScope.launch {
      repository.ensureInitialData()
    }
  }

  fun navigateTo(screen: ApexScreen) {
    _uiState.value = _uiState.value.copy(currentScreen = screen)
  }

  fun toggleNetWorthVisibility() {
    _uiState.value = _uiState.value.copy(isNetWorthHidden = !_uiState.value.isNetWorthHidden)
  }

  fun setTimeframe(timeframe: String) {
    _uiState.value = _uiState.value.copy(activeTimeframe = timeframe)
  }

  fun setFocusedCategory(amount: String, label: String) {
    _uiState.value = _uiState.value.copy(
      focusedCategoryAmount = amount,
      focusedCategoryLabel = label
    )
  }

  fun toggleRoundUp() {
    val newState = !_uiState.value.isRoundUpEnabled
    _uiState.value = _uiState.value.copy(isRoundUpEnabled = newState)
    showToast(if (newState) "Round-Up Multiplier Activated" else "Round-Up Multiplier Paused")
  }

  fun togglePaycheckSplitter() {
    val newState = !_uiState.value.isPaycheckSplitterEnabled
    _uiState.value = _uiState.value.copy(isPaycheckSplitterEnabled = newState)
    showToast(if (newState) "Paycheck Splitter Activated" else "Paycheck Splitter Paused")
  }

  fun boostVaultGoal(goalId: Long, goalTitle: String) {
    viewModelScope.launch {
      repository.boostVaultGoal(goalId, 50.0)
      showToast("Deposited $50.00 into $goalTitle")
    }
  }

  fun setLogFlowType(type: String) {
    _uiState.value = _uiState.value.copy(logFlowType = type)
  }

  fun setLogCategory(category: String) {
    _uiState.value = _uiState.value.copy(logCategory = category)
  }

  fun inputKeypadKey(key: String) {
    var current = _uiState.value.logAmount
    if (key == "backspace") {
      current = if (current.isNotEmpty()) current.dropLast(1) else "0"
      if (current.isEmpty()) current = "0"
    } else if (key == ".") {
      if (!current.contains(".")) {
        current += "."
      }
    } else {
      if (current == "0") {
        current = key
      } else {
        val parts = current.split(".")
        if (parts.size > 1 && parts[1].length >= 2) {
          return // max 2 decimals
        }
        if (current.length < 8) {
          current += key
        }
      }
    }
    _uiState.value = _uiState.value.copy(logAmount = current)
  }

  fun addTag(tag: String) {
    val clean = tag.trim().removePrefix("#")
    if (clean.isNotEmpty() && clean !in _uiState.value.logTags) {
      _uiState.value = _uiState.value.copy(logTags = _uiState.value.logTags + clean)
    }
  }

  fun removeTag(tag: String) {
    _uiState.value = _uiState.value.copy(logTags = _uiState.value.logTags.filter { it != tag })
  }

  fun toggleRecurring() {
    _uiState.value = _uiState.value.copy(logIsRecurring = !_uiState.value.logIsRecurring)
  }

  fun saveTransaction(onSaved: () -> Unit = {}) {
    val numericAmount = _uiState.value.logAmount.toDoubleOrNull() ?: 0.0
    if (numericAmount <= 0.0) {
      showToast("Please enter a valid amount")
      return
    }

    val finalAmount = if (_uiState.value.logFlowType == "EXPENSE") -numericAmount else numericAmount
    val entity = TransactionEntity(
      title = _uiState.value.logMerchant.ifBlank { "Whole Foods Market" },
      category = _uiState.value.logCategory,
      subtitle = _uiState.value.logSubtitle.ifBlank { "Smart Ledger Allocation" },
      amount = finalAmount,
      type = _uiState.value.logFlowType,
      dateStr = "Just now",
      timestamp = System.currentTimeMillis(),
      tags = _uiState.value.logTags.joinToString(", ") { "#$it" },
      location = _uiState.value.logLocation,
      isRecurring = _uiState.value.logIsRecurring,
      splitCount = _uiState.value.logSplitCount
    )

    viewModelScope.launch {
      repository.insertTransaction(entity)
      _uiState.value = _uiState.value.copy(isSavedSuccess = true)
      showToast("Transaction Logged Successfully")
      onSaved()
    }
  }

  fun deleteTransaction(id: Long) {
    viewModelScope.launch {
      repository.deleteTransaction(id)
      showToast("Transaction removed")
    }
  }

  fun showToast(message: String) {
    _uiState.value = _uiState.value.copy(toastMessage = message)
  }

  fun clearToast() {
    _uiState.value = _uiState.value.copy(toastMessage = null)
  }

  fun dismissNotification() {
    _uiState.value = _uiState.value.copy(unreadNotifications = 0)
    showToast("Notifications cleared")
  }
}
