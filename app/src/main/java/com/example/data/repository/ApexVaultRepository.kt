package com.example.data.repository

import com.example.data.dao.TransactionDao
import com.example.data.dao.VaultDao
import com.example.data.model.TransactionEntity
import com.example.data.model.VaultGoalEntity
import kotlinx.coroutines.flow.Flow

class ApexVaultRepository(
  private val transactionDao: TransactionDao,
  private val vaultDao: VaultDao
) {
  val allTransactions: Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()
  val recentTransactions: Flow<List<TransactionEntity>> = transactionDao.getRecentTransactions()
  val allVaultGoals: Flow<List<VaultGoalEntity>> = vaultDao.getAllVaultGoals()

  suspend fun ensureInitialData() {
    if (transactionDao.getCount() == 0) {
      val now = System.currentTimeMillis()
      val initialTransactions = listOf(
        TransactionEntity(
          title = "Stripe Payout",
          category = "Income",
          subtitle = "Freelance Consulting",
          amount = 3200.00,
          type = "INCOME",
          dateStr = "Today, 2:45 PM",
          timestamp = now
        ),
        TransactionEntity(
          title = "Apple Store",
          category = "Hardware",
          subtitle = "Mac Studio Display",
          amount = -1199.00,
          type = "EXPENSE",
          dateStr = "Yesterday",
          timestamp = now - 86400000L
        ),
        TransactionEntity(
          title = "Whole Foods Market",
          category = "Groceries",
          subtitle = "Organic Provisions",
          amount = -84.20,
          type = "EXPENSE",
          dateStr = "Oct 24",
          timestamp = now - 172800000L,
          location = "Union Square • New York, NY",
          tags = "#reimbursable, #vacation"
        ),
        TransactionEntity(
          title = "Blue Bottle Coffee",
          category = "Dining",
          subtitle = "Hayes Valley Espresso",
          amount = -6.50,
          type = "EXPENSE",
          dateStr = "Oct 23",
          timestamp = now - 259200000L
        ),
        TransactionEntity(
          title = "Equinox Fitness",
          category = "Health",
          subtitle = "Executive Tier All-Access",
          amount = -280.00,
          type = "EXPENSE",
          dateStr = "Oct 20",
          timestamp = now - 345600000L
        ),
        TransactionEntity(
          title = "Delta Air Lines",
          category = "Travel",
          subtitle = "JFK to HND Tokyo Haneda",
          amount = -1450.00,
          type = "EXPENSE",
          dateStr = "Oct 18",
          timestamp = now - 432000000L
        ),
        TransactionEntity(
          title = "Vanguard S&P 500",
          category = "Income",
          subtitle = "Quarterly Dividend Distribution",
          amount = 485.50,
          type = "INCOME",
          dateStr = "Oct 15",
          timestamp = now - 518400000L
        )
      )
      transactionDao.insertAll(initialTransactions)
    }

    if (vaultDao.getCount() == 0) {
      val initialGoals = listOf(
        VaultGoalEntity(
          title = "Emergency Fund (6 Mos)",
          priorityTag = "Priority 1 • High Yield",
          currentAmount = 24500.0,
          targetAmount = 30000.0,
          note = "Auto-deposit: $500/mo",
          targetDate = "Jan 2025",
          accentColorHex = 0xFF4EDEA3,
          progressType = "CIRCLE"
        ),
        VaultGoalEntity(
          title = "Japan Cherry Blossom",
          priorityTag = "Lifestyle Vault",
          currentAmount = 4800.0,
          targetAmount = 6500.0,
          note = "Round-ups enabled • 2x Multiplier",
          targetDate = "Mar 2025",
          accentColorHex = 0xFFCEBDFF,
          progressType = "MILESTONE"
        ),
        VaultGoalEntity(
          title = "Mac Studio & Display",
          priorityTag = "Hardware Upgrade",
          currentAmount = 1850.0,
          targetAmount = 4000.0,
          note = "Status: Active Compounding",
          targetDate = "Dec 2024",
          accentColorHex = 0xFFC0C1FF,
          progressType = "BAR"
        )
      )
      vaultDao.insertAll(initialGoals)
    }
  }

  suspend fun insertTransaction(transaction: TransactionEntity): Long {
    return transactionDao.insertTransaction(transaction)
  }

  suspend fun deleteTransaction(id: Long) {
    transactionDao.deleteById(id)
  }

  suspend fun boostVaultGoal(id: Long, amount: Double = 50.0) {
    vaultDao.boostVaultGoal(id, amount)
  }

  suspend fun insertVaultGoal(goal: VaultGoalEntity): Long {
    return vaultDao.insertVaultGoal(goal)
  }
}
