package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.ApexAmber
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexCyan
import com.example.ui.theme.ApexIndigo
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexPurple
import com.example.ui.theme.ApexSecondary
import com.example.ui.theme.ApexSurfaceBright
import com.example.ui.theme.ApexSurfaceContainer
import com.example.ui.theme.ApexSurfaceContainerHigh
import com.example.ui.theme.ApexSurfaceContainerHighest
import com.example.ui.theme.ApexSurfaceContainerLow
import com.example.ui.theme.ApexSurfaceContainerLowest
import com.example.ui.viewmodel.ApexUiState

const val VAULT_MECHANISM_IMAGE_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDJ1VdFzQRViXBANVfvP-QYvYrR4abiJG50ZxEYwogbTAy-2hmVngazIifWtt-eyjkFmOOupqj_zMG7o9LS1MWmLZ9kek75oMnMNs4xDMoUOAf-SjcXKxsbGau7B9wHj0vIVBJSFxrjtu7mRwsVMw886CDTgPR9m5SLSwKLxoi3S4QRbpb8xSy91v0QUXt5USDRuTgNfshvcuzrRkc0J_M9kFjysIJBuoWoxLGp6i034ds0MF5PKqWpUQ"

data class CategoryShare(
  val name: String,
  val sharePercent: Int,
  val amount: String,
  val color: Color
)

@Composable
fun AnalyticsScreen(
  uiState: ApexUiState,
  onSelectTimeframe: (String) -> Unit,
  onSelectCategory: (amount: String, label: String) -> Unit,
  onAdjustSweep: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "live_calc")
  val pulseDotAlpha by infiniteTransition.animateFloat(
    initialValue = 0.3f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(900),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot_pulse"
  )

  val categories = listOf(
    CategoryShare("Housing & Utilities", 38, "$1,450.00", ApexIndigo),
    CategoryShare("Food & Dining", 24, "$916.00", ApexPrimary),
    CategoryShare("Tech & Subscriptions", 16, "$610.00", ApexPurple),
    CategoryShare("Transit & Auto", 12, "$458.00", ApexCyan),
    CategoryShare("Misc & Fun", 10, "$386.00", ApexAmber)
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ApexBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Header Horizon & Timeframe Filter Bar
    item {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "LIQUIDITY VELOCITY",
              style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.outline
              )
            )
            Text(
              text = "Capital & Outflow",
              style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(ApexSurfaceContainerHigh)
              .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(ApexPrimary.copy(alpha = pulseDotAlpha))
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "LIVE CALC",
              style = MaterialTheme.typography.labelSmall.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
          }
        }

        // Timeframe Segmented Switcher
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ApexSurfaceContainerLowest)
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          listOf("WEEKLY", "MONTHLY", "QUARTERLY", "YEARLY").forEach { tf ->
            val isActive = uiState.activeTimeframe == tf
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isActive) ApexSurfaceContainerHigh else Color.Transparent)
                .clickable { onSelectTimeframe(tf) }
                .padding(vertical = 7.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = tf,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                  color = if (isActive) ApexPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }
        }
      }
    }

    // 2. Safe-to-Spend Daily Pace Hero Card
    item {
      Box(
        modifier = Modifier
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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(ApexPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Shield,
                  contentDescription = null,
                  tint = ApexPrimary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "SAFE-TO-SPEND PACE",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(ApexSurfaceContainerHigh)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "14 Days Left",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }

          Row(verticalAlignment = Alignment.Bottom) {
            Text(
              text = "$42.80",
              style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 32.sp
              )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "/ day",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              ),
              modifier = Modifier.padding(bottom = 4.dp)
            )
          }

          Text(
            text = "Disciplined pace for the next 14 days of October. You are trending under safe buffer limits by $184.20.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 16.sp
            )
          )

          // 3 Micro Metric Cards
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MicroMetric(label = "ALLOCATED", value = "$1,200.00", valueColor = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            MicroMetric(label = "SPENT", value = "$600.80", valueColor = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            MicroMetric(label = "RESERVED", value = "$599.20", valueColor = ApexPrimary, modifier = Modifier.weight(1f))
          }
        }
      }
    }

    // 3. Category Proportions & Interactive Donut Chart
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(16.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "ALLOCATION MATRIX",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  color = MaterialTheme.colorScheme.outline
                )
              )
              Text(
                text = "Category Proportions",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }

            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(ApexSurfaceContainerHigh)
                .clickable { onSelectCategory("$3,820", "Total Out") },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = "Reset filter",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          // Custom Donut Chart + Center Text
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.size(170.dp)) {
              val strokeWidth = 24.dp.toPx()
              val radius = (size.minDimension - strokeWidth) / 2
              val center = Offset(size.width / 2, size.height / 2)

              // Background track
              drawCircle(
                color = ApexSurfaceContainerHighest,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
              )

              // 5 Segments: Housing 38%, Food 24%, Tech 16%, Transit 12%, Misc 10%
              val segments = listOf(
                Pair(ApexIndigo, 0.38f * 360f),
                Pair(ApexPrimary, 0.24f * 360f),
                Pair(ApexPurple, 0.16f * 360f),
                Pair(ApexCyan, 0.12f * 360f),
                Pair(ApexAmber, 0.10f * 360f)
              )

              var startAngle = -90f
              for ((color, sweepAngle) in segments) {
                drawArc(
                  color = color,
                  startAngle = startAngle,
                  sweepAngle = sweepAngle - 2f,
                  useCenter = false,
                  topLeft = Offset(center.x - radius, center.y - radius),
                  size = Size(radius * 2, radius * 2),
                  style = Stroke(width = strokeWidth)
                )
                startAngle += sweepAngle
              }
            }

            // Center Readout
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Text(
                text = "OUTFLOW",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 9.sp,
                  letterSpacing = 1.sp,
                  color = MaterialTheme.colorScheme.outline
                )
              )
              Text(
                text = uiState.focusedCategoryAmount,
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 20.sp
                )
              )
              Text(
                text = uiState.focusedCategoryLabel,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          // Category Detailed Legend Rows
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            categories.forEach { cat ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(ApexSurfaceContainer)
                  .clickable {
                    onSelectCategory(cat.amount.replace(".00", ""), "${cat.name.split(" ").first()} ${cat.sharePercent}%")
                  }
                  .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(10.dp)
                      .clip(CircleShape)
                      .background(cat.color)
                  )
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = cat.name,
                      style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                      )
                    )
                    Text(
                      text = "${cat.sharePercent}% share",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.outline
                      )
                    )
                  }
                }

                Text(
                  text = cat.amount,
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }
            }
          }
        }
      }
    }

    // 4. Monthly Budget Status & Progress Gauges
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
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "OVERALL CEILING",
              style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.outline
              )
            )
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(ApexPrimary.copy(alpha = 0.15f))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "ON TARGET",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            Row(verticalAlignment = Alignment.Bottom) {
              Text(
                text = "$3,820.50",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 22.sp
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "of $4,800.00",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.padding(bottom = 2.dp)
              )
            }

            Text(
              text = "79% Spent",
              style = MaterialTheme.typography.labelMedium.copy(
                color = ApexPrimary,
                fontWeight = FontWeight.Bold
              )
            )
          }

          // Master Macro Progress Bar
          LinearProgressIndicator(
            progress = { 0.79f },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(CircleShape),
            color = ApexPrimary,
            trackColor = ApexSurfaceContainerHighest
          )

          Spacer(modifier = Modifier.height(4.dp))

          // Category Micro Bars
          BudgetMicroBar("Housing & Utilities", "$1,450 / $1,500", "NEAR LIMIT", 0.96f, ApexAmber)
          BudgetMicroBar("Food & Dining", "$916 / $1,200", "ON TRACK", 0.76f, ApexPrimary)
          BudgetMicroBar("Tech & Subscriptions", "$610 / $800", "ON TRACK", 0.76f, ApexPrimary)
          BudgetMicroBar("Transit & Auto", "$458 / $750", "UNDER BUDGET", 0.61f, ApexSecondary)
        }
      }
    }

    // 5. Predictive Modeling / Cash Flow Trajectory
    item {
      Box(
        modifier = Modifier
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
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "PREDICTIVE MODELING",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  color = MaterialTheme.colorScheme.outline
                )
              )
              Text(
                text = "Cash Flow Trajectory",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = ApexPrimary,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "+8.4% Surplus",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = ApexPrimary,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Text(
            text = "Month-end forecast projects net retained balance of $979.50 based on current velocity.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              lineHeight = 16.sp
            )
          )

          // Interactive Line Graph Canvas
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(130.dp)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              // Horizontal guide lines
              val lineCount = 3
              for (i in 0..lineCount) {
                val y = h * (i.toFloat() / lineCount)
                drawLine(
                  color = Color.White.copy(alpha = 0.08f),
                  start = Offset(0f, y),
                  end = Offset(w, y),
                  strokeWidth = 1f
                )
              }

              // Points: Oct 01 (w*0.05, h*0.80), Oct 09 (w*0.28, h*0.65), Today (w*0.55, h*0.42)
              val p0 = Offset(w * 0.05f, h * 0.80f)
              val p1 = Offset(w * 0.28f, h * 0.65f)
              val pToday = Offset(w * 0.55f, h * 0.42f)
              val p3 = Offset(w * 0.78f, h * 0.32f)
              val pEnd = Offset(w * 0.95f, h * 0.20f)

              // Gradient area fill under historical line
              val fillPath = Path().apply {
                moveTo(p0.x, p0.y)
                lineTo(p1.x, p1.y)
                lineTo(pToday.x, pToday.y)
                lineTo(pToday.x, h)
                lineTo(p0.x, h)
                close()
              }

              drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                  colors = listOf(ApexPrimary.copy(alpha = 0.25f), Color.Transparent),
                  startY = pToday.y,
                  endY = h
                )
              )

              // Solid actual line
              val solidPath = Path().apply {
                moveTo(p0.x, p0.y)
                lineTo(p1.x, p1.y)
                lineTo(pToday.x, pToday.y)
              }
              drawPath(
                path = solidPath,
                color = ApexPrimary,
                style = Stroke(width = 6f)
              )

              // Dashed projected line
              val dashedPath = Path().apply {
                moveTo(pToday.x, pToday.y)
                lineTo(p3.x, p3.y)
                lineTo(pEnd.x, pEnd.y)
              }
              drawPath(
                path = dashedPath,
                color = ApexIndigo,
                style = Stroke(
                  width = 5f,
                  pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                )
              )

              // Pulsing Today circle node
              drawCircle(
                color = ApexPrimary.copy(alpha = 0.4f),
                radius = 16f,
                center = pToday
              )
              drawCircle(
                color = ApexPrimary,
                radius = 10f,
                center = pToday
              )

              // End projected node
              drawCircle(
                color = ApexIndigo,
                radius = 8f,
                center = pEnd
              )
            }
          }

          // X-Axis Labels
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Oct 01", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline))
            Text("Oct 09", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline))
            Text("Today (17)", style = MaterialTheme.typography.labelSmall.copy(color = ApexPrimary, fontWeight = FontWeight.Bold))
            Text("Oct 24", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline))
            Text("Oct 31", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline))
          }

          // Legend Footnote
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
              .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(ApexPrimary))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Actual Cumulative",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .width(16.dp)
                  .height(2.dp)
                  .background(ApexSecondary)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "AI Projected Run",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = ApexSecondary
                )
              )
            }
          }
        }
      }
    }

    // 6. Contextual Visual Note: Vault Liquidity Anchor
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(ApexSurfaceContainer)
          .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        AsyncImage(
          model = VAULT_MECHANISM_IMAGE_URL,
          contentDescription = "Vault Anchor",
          modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ApexSurfaceContainerHigh),
          contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Automated Rebalancing Enabled",
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1
          )
          Text(
            text = "Next sweeps at 23:59 UTC to Apex Core",
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            maxLines = 1
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        TextButton(
          onClick = onAdjustSweep,
          colors = ButtonDefaults.textButtonColors(
            containerColor = ApexSurfaceContainerHighest,
            contentColor = ApexPrimary
          ),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = "ADJUST",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
          )
        }
      }
    }

    item { Spacer(modifier = Modifier.height(16.dp)) }
  }
}

@Composable
private fun MicroMetric(
  label: String,
  value: String,
  valueColor: Color,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(ApexSurfaceContainer)
      .padding(8.dp)
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 9.sp,
        color = MaterialTheme.colorScheme.outline
      )
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = value,
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = valueColor,
        fontSize = 12.sp
      )
    )
  }
}

@Composable
private fun BudgetMicroBar(
  title: String,
  ratioText: String,
  statusLabel: String,
  progress: Float,
  color: Color
) {
  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.Medium,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = ratioText,
          style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 5.dp, vertical = 2.dp)
        ) {
          Text(
            text = statusLabel,
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 9.sp,
              color = color,
              fontWeight = FontWeight.Bold
            )
          )
        }
      }
    }
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(5.dp)
        .clip(CircleShape),
      color = color,
      trackColor = ApexSurfaceContainerHighest
    )
  }
}
