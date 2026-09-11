package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TransactionEntity
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexError
import com.example.ui.theme.ApexOnError
import com.example.ui.theme.ApexOnPrimary
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexSecondary
import com.example.ui.theme.ApexSurfaceContainer
import com.example.ui.theme.ApexSurfaceContainerHigh
import com.example.ui.theme.ApexSurfaceContainerHighest
import com.example.ui.theme.ApexSurfaceContainerLow
import com.example.ui.theme.ApexSurfaceContainerLowest
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerScreen(
  transactions: List<TransactionEntity>,
  onDeleteTransaction: (Long) -> Unit,
  onLogNewTransaction: () -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("ALL") }
  var selectedTransactionForDetail by remember { mutableStateOf<TransactionEntity?>(null) }
  var showDeleteConfirmation by remember { mutableStateOf(false) }

  // Filter list
  val filteredTransactions = transactions.filter { tx ->
    val matchesFilter = when (selectedFilter) {
      "EXPENSES" -> tx.amount < 0
      "INCOME" -> tx.amount > 0
      else -> true
    }
    val matchesSearch = searchQuery.isBlank() ||
      tx.title.contains(searchQuery, ignoreCase = true) ||
      tx.category.contains(searchQuery, ignoreCase = true) ||
      tx.tags.contains(searchQuery, ignoreCase = true)

    matchesFilter && matchesSearch
  }

  // Calculate totals
  val totalInflow = transactions.filter { it.amount > 0 }.sumOf { it.amount }
  val totalOutflow = transactions.filter { it.amount < 0 }.sumOf { Math.abs(it.amount) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ApexBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Header & Summary Stats
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "IMMUTABLE AUDIT TRAIL",
              style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
            Text(
              text = "Smart Ledger",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(ApexSurfaceContainerHigh)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "${transactions.size} ENTRIES",
              style = MaterialTheme.typography.labelSmall.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }

        // Summary Bar (Inflow vs Outflow)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ApexSurfaceContainerLow)
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "TOTAL INFLOW",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
            Text(
              text = "+$${DecimalFormat("#,##0.00").format(totalInflow)}",
              style = MaterialTheme.typography.titleLarge.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "TOTAL OUTFLOW",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
            Text(
              text = "-$${DecimalFormat("#,##0.00").format(totalOutflow)}",
              style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }
      }
    }

    // 2. Search Field
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search transactions, tags, categories...") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("ledger_search_input"),
        shape = RoundedCornerShape(12.dp)
      )
    }

    // 3. Filter Chips
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf("ALL", "EXPENSES", "INCOME").forEach { f ->
          val isSelected = selectedFilter == f
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) ApexSurfaceContainerHighest else ApexSurfaceContainerLow)
              .clickable { selectedFilter = f }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = f,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ApexPrimary else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }
    }

    // 4. Transactions List
    if (filteredTransactions.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = Icons.Default.ReceiptLong,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No transactions found",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }
    } else {
      items(filteredTransactions, key = { it.id }) { tx ->
        TransactionRow(
          transaction = tx,
          onItemClick = { selectedTransactionForDetail = tx }
        )
      }
    }

    item { Spacer(modifier = Modifier.height(16.dp)) }
  }

  // Detail Modal Bottom Sheet
  selectedTransactionForDetail?.let { tx ->
    ModalBottomSheet(
      onDismissRequest = { selectedTransactionForDetail = null },
      containerColor = ApexSurfaceContainerHigh,
      shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = tx.category.uppercase(),
              style = MaterialTheme.typography.labelSmall.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
            Text(
              text = tx.title,
              style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          val amountFormatted = if (tx.amount >= 0) {
            "+$${DecimalFormat("#,##0.00").format(tx.amount)}"
          } else {
            "-$${DecimalFormat("#,##0.00").format(Math.abs(tx.amount))}"
          }
          val amountColor = if (tx.amount >= 0) ApexPrimary else MaterialTheme.colorScheme.onSurface
          Text(
            text = amountFormatted,
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.Bold,
              color = amountColor
            )
          )
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ApexSurfaceContainerLow)
            .padding(14.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DetailRow(label = "Date & Time", value = tx.dateStr)
            DetailRow(label = "Subtitle", value = tx.subtitle)
            if (tx.location.isNotBlank()) {
              DetailRow(label = "Location", value = tx.location)
            }
            if (tx.tags.isNotBlank()) {
              DetailRow(label = "Tags", value = tx.tags)
            }
            DetailRow(label = "Recurring", value = if (tx.isRecurring) "Monthly Repeat" else "One-Time")
            if (tx.splitCount > 1) {
              DetailRow(label = "Split", value = "${tx.splitCount} participants")
            }
          }
        }

        Button(
          onClick = {
            showDeleteConfirmation = true
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = ApexError.copy(alpha = 0.2f),
            contentColor = ApexError
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth().testTag("delete_tx_btn")
        ) {
          Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Delete Record", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }

  // Delete Confirmation Dialog
  if (showDeleteConfirmation && selectedTransactionForDetail != null) {
    val tx = selectedTransactionForDetail!!
    AlertDialog(
      onDismissRequest = { showDeleteConfirmation = false },
      title = { Text("Delete Transaction") },
      text = { Text("Are you sure you want to remove \"${tx.title}\" from the ledger?") },
      confirmButton = {
        Button(
          onClick = {
            onDeleteTransaction(tx.id)
            showDeleteConfirmation = false
            selectedTransactionForDetail = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexError, contentColor = ApexOnError)
        ) {
          Text("Delete")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDeleteConfirmation = false }) {
          Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      },
      containerColor = ApexSurfaceContainerHigh
    )
  }
}

@Composable
private fun DetailRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface
      )
    )
  }
}
