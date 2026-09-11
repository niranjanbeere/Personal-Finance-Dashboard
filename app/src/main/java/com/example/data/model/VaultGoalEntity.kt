package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault_goals")
data class VaultGoalEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val title: String,
  val priorityTag: String,
  val currentAmount: Double,
  val targetAmount: Double,
  val note: String,
  val targetDate: String,
  val accentColorHex: Long = 0xFF4EDEA3,
  val progressType: String = "CIRCLE" // CIRCLE, MILESTONE, BAR
)
