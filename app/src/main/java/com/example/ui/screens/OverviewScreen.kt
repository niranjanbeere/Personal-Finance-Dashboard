package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.TransactionEntity
import com.example.ui.theme.ApexBackground
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
import com.example.ui.viewmodel.ApexScreen
import com.example.ui.viewmodel.ApexUiState
import java.text.DecimalFormat

const val VAULT_IMAGE_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCzGebTbWccow-qU4ANDXpOX6HeI3VJdVq-ZXP1NfvEkAGDy4fxouIWIj22IVKllMySim7BUqQ4aPrcIuClFMKm6QkuIOG5CDMM9jRzDIqjZVCtqA53sW9LXg2xpbOS69_yzO2TR5wJLxKKJxmc6us-OXaKHy_YxFMCFktdabQ05juAnxQAqLFZmMbISyxQbBZgA9dLIg2Q_H13WyyLW3sRjB6zGxtCexsMdSPiDC1iqelZGE5ZKRnoVw"

@Composable
fun OverviewScreen(
  uiState: ApexUiState,
  transactions: List<TransactionEntity>,
  onToggleNetWorth: () -> Unit,
  onQuickAction: (String) -> Unit,
  onSeeAllTransactions: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot_alpha"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ApexBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Total Net Worth Hero Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(18.dp)
          .testTag("net_worth_card")
      ) {
        Column {
          // Top Row: Title + Pulse + Privacy Eye
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "TOTAL NET WORTH",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.2.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .clip(CircleShape)
                  .background(ApexPrimary.copy(alpha = pulseAlpha))
              )
            }
            IconButton(
              onClick = onToggleNetWorth,
              modifier = Modifier
                .size(32.dp)
                .testTag("toggle_privacy_btn")
            ) {
              Icon(
                imageVector = if (uiState.isNetWorthHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = "Toggle privacy",
                tint = if (uiState.isNetWorthHidden) ApexPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          // Balance Display
          Text(
            text = if (uiState.isNetWorthHidden) "••••••••••" else "$124,850.40",
            style = MaterialTheme.typography.displayLarge.copy(
              color = MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Bold,
              fontSize = 32.sp
            ),
            modifier = Modifier.testTag("balance_text")
          )

          Spacer(modifier = Modifier.height(6.dp))

          // Monthly Growth Badge
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(ApexSurfaceContainerHighest)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.TrendingUp,
              contentDescription = null,
              tint = ApexPrimary,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "+8.4% (+$9,620)",
              style = MaterialTheme.typography.labelMedium.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "this month",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Custody Status & APY
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.VerifiedUser,
                contentDescription = null,
                tint = ApexPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Apex Vault Custody • Active",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "APY ",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              Text(
                text = "5.12%",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }
        }
      }
    }

    // 2. Quick Action Grid (4 Buttons)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        QuickActionButton(
          title = "SEND",
          icon = Icons.Default.ArrowUpward,
          iconTint = ApexPrimary,
          onClick = { onQuickAction("Send") },
          modifier = Modifier.weight(1f)
        )
        QuickActionButton(
          title = "REQUEST",
          icon = Icons.Default.ArrowDownward,
          iconTint = ApexSecondary,
          onClick = { onQuickAction("Request") },
          modifier = Modifier.weight(1f)
        )
        QuickActionButton(
          title = "ADD CASH",
          icon = Icons.Default.AddCard,
          iconTint = ApexPrimary,
          onClick = { onQuickAction("Add Cash") },
          modifier = Modifier.weight(1f)
        )
        QuickActionButton(
          title = "SCAN BILL",
          icon = Icons.Default.DocumentScanner,
          iconTint = ApexTertiary,
          onClick = { onQuickAction("Scan Bill") },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // 3. Cash Flow Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(16.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          // Card Header
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.PieChart,
                contentDescription = null,
                tint = ApexPrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Cash Flow",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ApexSurfaceContainerHighest)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "OCTOBER CYCLE",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 0.8.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          // Two Sub-cards: Income & Spent
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            // Income
            Column(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(ApexSurfaceContainerLowest)
                .padding(12.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "INCOME",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
                Text(
                  text = "+12%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = ApexPrimary,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "$8,450.00",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Spacer(modifier = Modifier.height(8.dp))
              LinearProgressIndicator(
                progress = { 0.78f },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(5.dp)
                  .clip(CircleShape),
                color = ApexPrimary,
                trackColor = ApexSurfaceContainerHighest
              )
            }

            // Spent
            Column(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(ApexSurfaceContainerLowest)
                .padding(12.dp)
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = "SPENT",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
                Text(
                  text = "-4%",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = ApexSecondary,
                    fontWeight = FontWeight.Bold
                  )
                )
              }
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "$3,820.50",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Spacer(modifier = Modifier.height(8.dp))
              LinearProgressIndicator(
                progress = { 0.45f },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(5.dp)
                  .clip(CircleShape),
                color = ApexSecondary,
                trackColor = ApexSurfaceContainerHighest
              )
            }
          }

          // Monthly Surplus Reserve
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(ApexSurfaceContainerHigh)
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Icon(
                imageVector = Icons.Default.Savings,
                contentDescription = null,
                tint = ApexPrimary,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "Monthly Surplus Reserve",
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
                Text(
                  text = "54.8% retained",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
              }
            }

            Text(
              text = "+$4,629.50",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = ApexPrimary
              )
            )
          }
        }
      }
    }

    // 4. Yield Protocol Banner Card
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        AsyncImage(
          model = VAULT_IMAGE_URL,
          contentDescription = "Yield Protocol Vault",
          modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(ApexSurfaceContainerHighest),
          contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "YIELD PROTOCOL",
              style = MaterialTheme.typography.labelSmall.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
            Text(
              text = " • Auto-Compounding",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "High-Yield Treasury Reserve",
            style = MaterialTheme.typography.titleLarge.copy(
              color = MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.SemiBold
            )
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "+$18.42 accrued today at zero counterparty risk",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }
    }

    // 5. Recent Activity Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSeeAllTransactions() }
            .padding(4.dp)
            .testTag("see_all_activity")
        ) {
          Text(
            text = "SEE ALL",
            style = MaterialTheme.typography.labelSmall.copy(
              letterSpacing = 1.sp,
              color = ApexPrimary,
              fontWeight = FontWeight.Bold
            )
          )
          Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "See All",
            tint = ApexPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }

    // 6. Recent Activity List (Top 4 from Room)
    val displayTransactions = transactions.take(4)
    items(displayTransactions, key = { it.id }) { tx ->
      TransactionRow(transaction = tx)
    }

    item { Spacer(modifier = Modifier.height(16.dp)) }
  }
}

@Composable
fun TransactionRow(
  transaction: TransactionEntity,
  modifier: Modifier = Modifier,
  onItemClick: (() -> Unit)? = null
) {
  val icon: ImageVector = when (transaction.category) {
    "Income" -> Icons.Default.Payments
    "Hardware" -> Icons.Default.Devices
    "Groceries" -> Icons.Default.ShoppingCart
    "Dining" -> Icons.Default.Coffee
    else -> Icons.Default.Payments
  }

  val iconColor = when (transaction.category) {
    "Income" -> ApexPrimary
    "Hardware" -> MaterialTheme.colorScheme.onSurfaceVariant
    "Groceries" -> ApexSecondary
    "Dining" -> ApexTertiary
    else -> ApexPrimary
  }

  val amountColor = if (transaction.amount >= 0) ApexPrimary else MaterialTheme.colorScheme.onSurface
  val amountFormatted = if (transaction.amount >= 0) {
    "+$${DecimalFormat("#,##0.00").format(transaction.amount)}"
  } else {
    "-$${DecimalFormat("#,##0.00").format(Math.abs(transaction.amount))}"
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(ApexSurfaceContainerLow)
      .clickable(enabled = onItemClick != null) { onItemClick?.invoke() }
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(ApexSurfaceContainerHighest),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = transaction.category,
          tint = iconColor,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = transaction.title,
          style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
          ),
          maxLines = 1
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(ApexSurfaceContainerHighest)
              .padding(horizontal = 5.dp, vertical = 2.dp)
          ) {
            Text(
              text = transaction.category,
              style = MaterialTheme.typography.labelSmall.copy(
                color = iconColor,
                fontSize = 10.sp
              )
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = transaction.subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1
          )
        }
      }
    }

    Column(horizontalAlignment = Alignment.End) {
      Text(
        text = amountFormatted,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = amountColor,
          fontSize = 14.sp
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = transaction.dateStr,
        style = MaterialTheme.typography.labelSmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      )
    }
  }
}

@Composable
private fun QuickActionButton(
  title: String,
  icon: ImageVector,
  iconTint: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .background(ApexSurfaceContainerLow)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(),
        onClick = onClick
      )
      .padding(vertical = 12.dp, horizontal = 6.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .background(ApexSurfaceContainerHighest),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = iconTint,
        modifier = Modifier.size(20.dp)
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colorScheme.onSurface
      )
    )
  }
}
