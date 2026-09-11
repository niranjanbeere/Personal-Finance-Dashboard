package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Splitscreen
import androidx.compose.material.icons.filled.Toll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.VaultGoalEntity
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexOnPrimary
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPrimaryContainer
import com.example.ui.theme.ApexSecondary
import com.example.ui.theme.ApexSurfaceBright
import com.example.ui.theme.ApexSurfaceContainer
import com.example.ui.theme.ApexSurfaceContainerHigh
import com.example.ui.theme.ApexSurfaceContainerHighest
import com.example.ui.theme.ApexSurfaceContainerLow
import com.example.ui.theme.ApexSurfaceContainerLowest
import com.example.ui.theme.ApexTertiary
import com.example.ui.viewmodel.ApexUiState
import java.text.DecimalFormat

@Composable
fun VaultGoalsScreen(
  uiState: ApexUiState,
  vaultGoals: List<VaultGoalEntity>,
  onBoostGoal: (Long, String) -> Unit,
  onToggleRoundUp: () -> Unit,
  onTogglePaycheckSplitter: () -> Unit,
  onQuickDeposit: () -> Unit,
  onAddGoal: (String, Double, Double) -> Unit,
  modifier: Modifier = Modifier
) {
  var showNewBucketDialog by remember { mutableStateOf(false) }
  var newBucketTitle by remember { mutableStateOf("") }
  var newBucketTarget by remember { mutableStateOf("5000") }
  var newBucketInitial by remember { mutableStateOf("500") }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ApexBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Locked Apex Vault Hero Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(16.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = ApexPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "LOCKED APEX VAULT",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(ApexSurfaceContainerHighest)
                .padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(6.dp)
                  .clip(CircleShape)
                  .background(ApexPrimary)
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = "+4.85% APY Earned",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "$48,320.00",
                style = MaterialTheme.typography.displayLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 30.sp
                )
              )
              Text(
                text = "Total Protected Balance Across 3 Buckets",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            IconButton(
              onClick = onQuickDeposit,
              modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ApexPrimary)
                .testTag("quick_deposit_btn")
            ) {
              Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = "Deposit",
                tint = ApexOnPrimary,
                modifier = Modifier.size(20.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Growth Projection Mini Ticker
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(ApexSurfaceContainerLowest)
              .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(
                text = "MONTHLY ACCRUAL",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 9.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              Text(
                text = "+$195.29/mo",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = "3-GOAL TRAJECTORY",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 9.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              Text(
                text = "92% On Track",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = ApexSecondary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }
        }
      }
    }

    // 2. Savings Goals Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Target Vaults",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(ApexSurfaceContainerHigh)
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "${vaultGoals.size} Active",
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        TextButton(
          onClick = { showNewBucketDialog = true },
          modifier = Modifier.testTag("add_bucket_btn")
        ) {
          Text(
            text = "+ New Bucket",
            style = MaterialTheme.typography.bodySmall.copy(
              color = ApexPrimary,
              fontWeight = FontWeight.SemiBold
            )
          )
        }
      }
    }

    // 3. Vault Goals List
    items(vaultGoals, key = { it.id }) { goal ->
      VaultGoalCard(
        goal = goal,
        onBoost = { onBoostGoal(goal.id, goal.title) }
      )
    }

    // 4. Automation Protocol Section
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Automation Protocol",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = "AI REBALANCED",
            style = MaterialTheme.typography.labelSmall.copy(
              color = ApexPrimary,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.8.sp
            )
          )
        }

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ApexSurfaceContainerLow)
            .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
            .padding(16.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Rule 1: Round-up
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ApexSurfaceContainerHigh),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Toll,
                    contentDescription = null,
                    tint = ApexPrimary,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "Round-Up Multiplier",
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurface
                    )
                  )
                  Text(
                    text = "2x spare change routed to Emergency Fund",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                  )
                }
              }

              Switch(
                checked = uiState.isRoundUpEnabled,
                onCheckedChange = { onToggleRoundUp() },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = ApexOnPrimary,
                  checkedTrackColor = ApexPrimary,
                  uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                  uncheckedTrackColor = ApexSurfaceContainerHighest
                ),
                modifier = Modifier.testTag("toggle_roundup_switch")
              )
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ApexSurfaceContainerHighest.copy(alpha = 0.5f))
            )

            // Rule 2: Paycheck Splitter
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ApexSurfaceContainerHigh),
                  contentAlignment = Alignment.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Splitscreen,
                    contentDescription = null,
                    tint = ApexTertiary,
                    modifier = Modifier.size(20.dp)
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "Paycheck Splitter",
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurface
                    )
                  )
                  Text(
                    text = "15% auto-allocated to High-Yield Vault",
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                  )
                }
              }

              Switch(
                checked = uiState.isPaycheckSplitterEnabled,
                onCheckedChange = { onTogglePaycheckSplitter() },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = ApexOnPrimary,
                  checkedTrackColor = ApexPrimary,
                  uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                  uncheckedTrackColor = ApexSurfaceContainerHighest
                ),
                modifier = Modifier.testTag("toggle_paycheck_switch")
              )
            }
          }
        }
      }
    }

    // 5. Portfolio Allocation Preview / Asset Reserves
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Asset Reserves",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = "Real-Time Yield",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          AssetReserveCard(
            title = "HY CASH",
            badge = "4.85% APY",
            amount = "$32.0k",
            color = ApexPrimary,
            modifier = Modifier.weight(1f)
          )
          AssetReserveCard(
            title = "S&P 500 ETF",
            badge = "+1.2% 24h",
            amount = "$12.5k",
            color = ApexSecondary,
            modifier = Modifier.weight(1f)
          )
          AssetReserveCard(
            title = "CRYPTO VAULT",
            badge = "Staked ETH",
            amount = "$3.8k",
            color = ApexTertiary,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    item { Spacer(modifier = Modifier.height(16.dp)) }
  }

  // Dialog for Adding a New Bucket
  if (showNewBucketDialog) {
    AlertDialog(
      onDismissRequest = { showNewBucketDialog = false },
      title = {
        Text("Create Target Vault", fontWeight = FontWeight.Bold)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = newBucketTitle,
            onValueChange = { newBucketTitle = it },
            label = { Text("Vault Name (e.g. Tesla Model 3)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newBucketTarget,
            onValueChange = { newBucketTarget = it },
            label = { Text("Target Goal ($)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = newBucketInitial,
            onValueChange = { newBucketInitial = it },
            label = { Text("Initial Deposit ($)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val target = newBucketTarget.toDoubleOrNull() ?: 5000.0
            val initial = newBucketInitial.toDoubleOrNull() ?: 500.0
            if (newBucketTitle.isNotBlank()) {
              onAddGoal(newBucketTitle, initial, target)
              showNewBucketDialog = false
              newBucketTitle = ""
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimary, contentColor = ApexOnPrimary)
        ) {
          Text("Create")
        }
      },
      dismissButton = {
        TextButton(onClick = { showNewBucketDialog = false }) {
          Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      },
      containerColor = ApexSurfaceContainerHigh
    )
  }
}

@Composable
fun VaultGoalCard(
  goal: VaultGoalEntity,
  onBoost: () -> Unit,
  modifier: Modifier = Modifier
) {
  val percent = if (goal.targetAmount > 0) {
    ((goal.currentAmount / goal.targetAmount) * 100).toInt().coerceIn(0, 100)
  } else 0

  val accentColor = Color(goal.accentColorHex)

  val icon: ImageVector = when {
    goal.title.contains("Emergency", ignoreCase = true) -> Icons.Default.HealthAndSafety
    goal.title.contains("Japan", ignoreCase = true) || goal.title.contains("Travel", ignoreCase = true) -> Icons.Default.FlightTakeoff
    else -> Icons.Default.Devices
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(ApexSurfaceContainerLow)
      .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
      .padding(16.dp)
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(26.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(ApexSurfaceContainerHigh),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = goal.priorityTag.uppercase(),
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = accentColor,
                fontWeight = FontWeight.Bold
              )
            )
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = goal.title,
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = goal.note,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        // Circular or Linear percentage indicator
        if (goal.progressType == "BAR") {
          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "$percent%",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = accentColor
              )
            )
            Text(
              text = "FUNDED",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        } else {
          // Circular Progress Ring
          Box(
            modifier = Modifier.size(54.dp),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val strokeWidth = 10f
              val radius = (size.minDimension - strokeWidth) / 2
              val center = Offset(size.width / 2, size.height / 2)

              drawCircle(
                color = ApexSurfaceContainerHighest,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
              )

              drawArc(
                color = accentColor,
                startAngle = -90f,
                sweepAngle = (percent / 100f) * 360f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "$percent",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 15.sp,
                  lineHeight = 16.sp
                )
              )
              Text(
                text = "%",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 8.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  lineHeight = 9.sp
                )
              )
            }
          }
        }
      }

      // If milestone progress type (Japan Cherry Blossom)
      if (goal.progressType == "MILESTONE") {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Box(modifier = Modifier.weight(1f).height(5.dp).clip(CircleShape).background(accentColor))
          Box(modifier = Modifier.weight(1f).height(5.dp).clip(CircleShape).background(accentColor))
          Box(modifier = Modifier.weight(1f).height(5.dp).clip(CircleShape).background(accentColor))
          Box(modifier = Modifier.weight(1f).height(5.dp).clip(CircleShape).background(ApexSurfaceContainerHighest))
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("Flights Booked", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
          Text("Hotel Reserved", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
          Text("Experience Pass", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
          Text("Final $1.7k", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
        }
      } else if (goal.progressType == "BAR") {
        LinearProgressIndicator(
          progress = { percent / 100f },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(CircleShape),
          color = accentColor,
          trackColor = ApexSurfaceContainerHighest
        )
      }

      // Current vs Target Amount
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.Bottom) {
          Text(
            text = "$${DecimalFormat("#,##0").format(goal.currentAmount)}",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = " / $${DecimalFormat("#,##0").format(goal.targetAmount)}",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.padding(bottom = 1.dp)
          )
        }

        if (goal.targetDate.isNotBlank()) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.EventAvailable,
              contentDescription = null,
              tint = accentColor,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = goal.targetDate,
              style = MaterialTheme.typography.bodySmall.copy(
                color = accentColor,
                fontWeight = FontWeight.Medium
              )
            )
          }
        } else {
          val remaining = (goal.targetAmount - goal.currentAmount).coerceAtLeast(0.0)
          Text(
            text = "$${DecimalFormat("#,##0").format(remaining)} remaining",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      // Boost Action Footer Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(ApexSurfaceContainerLowest.copy(alpha = 0.6f))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Next $500 auto-deposit in 4 days",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(ApexSurfaceContainerHighest)
            .clickable { onBoost() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .testTag("boost_btn_${goal.id}")
        ) {
          Text(
            text = "+ Boost $50",
            style = MaterialTheme.typography.labelSmall.copy(
              color = ApexPrimary,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
  }
}

@Composable
private fun AssetReserveCard(
  title: String,
  badge: String,
  amount: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(ApexSurfaceContainerLow)
      .padding(10.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 9.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    )
    Text(
      text = badge,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        color = color,
        fontWeight = FontWeight.Bold
      )
    )
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = amount,
      style = MaterialTheme.typography.titleLarge.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 16.sp
      )
    )
    Spacer(modifier = Modifier.height(4.dp))
    // Mini Sparkline Canvas
    Canvas(
      modifier = Modifier
        .fillMaxWidth()
        .height(14.dp)
    ) {
      val w = size.width
      val h = size.height
      val path = Path().apply {
        moveTo(0f, h * 0.8f)
        lineTo(w * 0.25f, h * 0.6f)
        lineTo(w * 0.5f, h * 0.7f)
        lineTo(w * 0.75f, h * 0.3f)
        lineTo(w, h * 0.15f)
      }
      drawPath(
        path = path,
        color = color,
        style = Stroke(width = 4f, cap = StrokeCap.Round)
      )
    }
  }
}
