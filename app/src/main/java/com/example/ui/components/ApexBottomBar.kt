package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexOnPrimary
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexSurfaceContainerLowest
import com.example.ui.viewmodel.ApexScreen

@Composable
fun ApexBottomBar(
  currentScreen: ApexScreen,
  onNavigate: (ApexScreen) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    color = ApexSurfaceContainerLowest.copy(alpha = 0.95f),
    shadowElevation = 16.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()
        .height(64.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // 1. Overview
      NavItem(
        icon = Icons.Default.GridView,
        label = "Overview",
        selected = currentScreen == ApexScreen.OVERVIEW,
        onClick = { onNavigate(ApexScreen.OVERVIEW) },
        testTag = "nav_overview"
      )

      // 2. Analytics
      NavItem(
        icon = Icons.Default.Timeline,
        label = "Analytics",
        selected = currentScreen == ApexScreen.ANALYTICS,
        onClick = { onNavigate(ApexScreen.ANALYTICS) },
        testTag = "nav_analytics"
      )

      // 3. Quick Action (+) Center Button
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(ApexPrimary)
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(color = ApexOnPrimary),
            onClick = { onNavigate(ApexScreen.QUICK_ACTION) }
          )
          .testTag("nav_quick_action"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Log Transaction",
          tint = ApexOnPrimary,
          modifier = Modifier.size(28.dp)
        )
      }

      // 4. Vault
      NavItem(
        icon = Icons.Default.Shield,
        label = "Vault",
        selected = currentScreen == ApexScreen.VAULT,
        onClick = { onNavigate(ApexScreen.VAULT) },
        testTag = "nav_vault"
      )

      // 5. Ledger
      NavItem(
        icon = Icons.Default.ReceiptLong,
        label = "Ledger",
        selected = currentScreen == ApexScreen.LEDGER,
        onClick = { onNavigate(ApexScreen.LEDGER) },
        testTag = "nav_ledger"
      )
    }
  }
}

@Composable
private fun NavItem(
  icon: ImageVector,
  label: String,
  selected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  val tintColor by animateColorAsState(
    targetValue = if (selected) ApexPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
    label = "nav_tint"
  )

  Column(
    modifier = Modifier
      .clip(CircleShape)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(bounded = false, radius = 24.dp),
        onClick = onClick
      )
      .padding(horizontal = 12.dp, vertical = 6.dp)
      .testTag(testTag),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = tintColor,
      modifier = Modifier.size(22.dp)
    )
    Text(
      text = label.uppercase(),
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
        color = tintColor,
        letterSpacing = 0.5.sp
      )
    )
  }
}
