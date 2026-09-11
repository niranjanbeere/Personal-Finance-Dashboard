package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.VaultGoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDao {
  @Query("SELECT * FROM vault_goals ORDER BY id ASC")
  fun getAllVaultGoals(): Flow<List<VaultGoalEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertVaultGoal(goal: VaultGoalEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(goals: List<VaultGoalEntity>)

  @Update
  suspend fun updateVaultGoal(goal: VaultGoalEntity)

  @Query("UPDATE vault_goals SET currentAmount = currentAmount + :boostAmount WHERE id = :id")
  suspend fun boostVaultGoal(id: Long, boostAmount: Double)

  @Query("SELECT COUNT(*) FROM vault_goals")
  suspend fun getCount(): Int
}
