package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val category: String,
  val subtitle: String,
  val amount: Double,
  val type: String, // EXPENSE, INCOME, TRANSFER
  val dateStr: String,
  val timestamp: Long = System.currentTimeMillis(),
  val tags: String = "",
  val location: String = "",
  val isRecurring: Boolean = false,
  val splitCount: Int = 1
)
