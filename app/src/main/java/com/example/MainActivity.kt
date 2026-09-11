package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.data.model.VaultGoalEntity
import com.example.ui.components.APEX_AVATAR_URL
import com.example.ui.components.ApexBottomBar
import com.example.ui.components.ApexToastBanner
import com.example.ui.components.ApexTopBar
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.LedgerScreen
import com.example.ui.screens.LogTransactionScreen
import com.example.ui.screens.OverviewScreen
import com.example.ui.screens.VaultGoalsScreen
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexOnPrimary
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexSecondary
import com.example.ui.theme.ApexSurfaceContainerHigh
import com.example.ui.theme.ApexSurfaceContainerHighest
import com.example.ui.theme.ApexSurfaceContainerLow
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.ApexScreen
import com.example.ui.viewmodel.ApexVaultViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        ApexVaultApp()
      }
    }
  }
}

@Composable
fun ApexVaultApp(
  viewModel: ApexVaultViewModel = viewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val allTransactions by viewModel.allTransactions.collectAsStateWithLifecycle()
  val recentTransactions by viewModel.recentTransactions.collectAsStateWithLifecycle()
  val vaultGoals by viewModel.vaultGoals.collectAsStateWithLifecycle()

  var showProfileDialog by remember { mutableStateOf(false) }
  var showNotificationDialog by remember { mutableStateOf(false) }

  val topBarSubtitle = when (uiState.currentScreen) {
    ApexScreen.OVERVIEW -> "INSTITUTIONAL OVERVIEW"
    ApexScreen.ANALYTICS -> "LIQUIDITY & VELOCITY"
    ApexScreen.QUICK_ACTION -> "SMART LEDGER ENTRY"
    ApexScreen.VAULT -> "PROTECTED VAULT GOALS"
    ApexScreen.LEDGER -> "TRANSACTION AUDIT LOG"
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(ApexBackground)
  ) {
    Scaffold(
      modifier = Modifier.fillMaxSize(),
      containerColor = ApexBackground,
      contentWindowInsets = WindowInsets(0, 0, 0, 0),
      topBar = {
        if (uiState.currentScreen != ApexScreen.QUICK_ACTION) {
          ApexTopBar(
            subtitle = topBarSubtitle,
            unreadNotifications = uiState.unreadNotifications,
            onNotificationClick = { showNotificationDialog = true },
            onProfileClick = { showProfileDialog = true }
          )
        }
      },
      bottomBar = {
        if (uiState.currentScreen != ApexScreen.QUICK_ACTION) {
          ApexBottomBar(
            currentScreen = uiState.currentScreen,
            onNavigate = { screen -> viewModel.navigateTo(screen) }
          )
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        AnimatedContent(
          targetState = uiState.currentScreen,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "screen_transition"
        ) { screen ->
          when (screen) {
            ApexScreen.OVERVIEW -> {
              OverviewScreen(
                uiState = uiState,
                transactions = recentTransactions,
                onToggleNetWorth = { viewModel.toggleNetWorthVisibility() },
                onQuickAction = { action ->
                  when (action) {
                    "Send" -> viewModel.showToast("Initiating Wire Transfer Protocol...")
                    "Request" -> viewModel.showToast("Payment Request Link Generated")
                    "Add Cash" -> viewModel.showToast("Connecting to Apex Prime Liquidity Pool...")
                    "Scan Bill" -> viewModel.navigateTo(ApexScreen.QUICK_ACTION)
                  }
                },
                onSeeAllTransactions = { viewModel.navigateTo(ApexScreen.LEDGER) }
              )
            }

            ApexScreen.ANALYTICS -> {
              AnalyticsScreen(
                uiState = uiState,
                onSelectTimeframe = { tf -> viewModel.setTimeframe(tf) },
                onSelectCategory = { amt, lbl -> viewModel.setFocusedCategory(amt, lbl) },
                onAdjustSweep = { viewModel.showToast("Automated sweep window set to 23:59 UTC") }
              )
            }

            ApexScreen.QUICK_ACTION -> {
              LogTransactionScreen(
                uiState = uiState,
                onCancel = { viewModel.navigateTo(ApexScreen.OVERVIEW) },
                onDone = {
                  viewModel.saveTransaction {
                    viewModel.navigateTo(ApexScreen.LEDGER)
                  }
                },
                onSetFlowType = { flow -> viewModel.setLogFlowType(flow) },
                onSetCategory = { cat -> viewModel.setLogCategory(cat) },
                onKeypadInput = { key -> viewModel.inputKeypadKey(key) },
                onToggleRecurring = { viewModel.toggleRecurring() },
                onAddTag = { tag -> viewModel.addTag(tag) },
                onRemoveTag = { tag -> viewModel.removeTag(tag) },
                onSnapCamera = { viewModel.showToast("AI OCR scanning camera feed...") },
                onUploadPdf = { viewModel.showToast("Parsing itemized tax statements from PDF...") },
                onSendSplitLinks = { viewModel.showToast("4 Payment Request links sent via SMS & AirDrop") }
              )
            }

            ApexScreen.VAULT -> {
              VaultGoalsScreen(
                uiState = uiState,
                vaultGoals = vaultGoals,
                onBoostGoal = { id, title -> viewModel.boostVaultGoal(id, title) },
                onToggleRoundUp = { viewModel.toggleRoundUp() },
                onTogglePaycheckSplitter = { viewModel.togglePaycheckSplitter() },
                onQuickDeposit = { viewModel.showToast("Quick Deposit of $500 initiated to Sovereign Vault") },
                onAddGoal = { title, initial, target ->
                  viewModel.showToast("Created target bucket: $title")
                }
              )
            }

            ApexScreen.LEDGER -> {
              LedgerScreen(
                transactions = allTransactions,
                onDeleteTransaction = { id -> viewModel.deleteTransaction(id) },
                onLogNewTransaction = { viewModel.navigateTo(ApexScreen.QUICK_ACTION) }
              )
            }
          }
        }

        // Floating Toast Banner
        ApexToastBanner(
          message = uiState.toastMessage,
          onDismiss = { viewModel.clearToast() },
          modifier = Modifier.align(Alignment.BottomCenter)
        )
      }
    }
  }

  // Profile Dialog
  if (showProfileDialog) {
    AlertDialog(
      onDismissRequest = { showProfileDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          AsyncImage(
            model = APEX_AVATAR_URL,
            contentDescription = null,
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape),
            contentScale = ContentScale.Crop
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text("Alexander Vance", fontWeight = FontWeight.Bold)
            Text(
              "Institutional Member • Tier 1",
              style = MaterialTheme.typography.labelSmall.copy(color = ApexPrimary)
            )
          }
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(ApexSurfaceContainerLow)
              .padding(10.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("CUSTODY CONTRACT", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
              Text("0x89A...43FE91B", style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface))
              Text("Multi-Sig Ledger • 3 of 5 Keys", style = MaterialTheme.typography.bodySmall.copy(color = ApexSecondary))
            }
          }
          Text(
            "Account status is in compliance with FDIC and cold-storage custodial protocols. Zero counterparty risk guarantee active.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      },
      confirmButton = {
        Button(
          onClick = { showProfileDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimary, contentColor = ApexOnPrimary)
        ) {
          Text("Done")
        }
      },
      containerColor = ApexSurfaceContainerHigh
    )
  }

  // Notification Dialog
  if (showNotificationDialog) {
    AlertDialog(
      onDismissRequest = { showNotificationDialog = false },
      title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Notifications, contentDescription = null, tint = ApexPrimary)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Notifications", fontWeight = FontWeight.Bold)
        }
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(ApexSurfaceContainerLow)
              .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = ApexPrimary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text("Yield Compounded", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
              Text("+$18.42 accrued today at zero counterparty risk", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.dismissNotification()
            showNotificationDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimary, contentColor = ApexOnPrimary)
        ) {
          Text("Mark as Read")
        }
      },
      dismissButton = {
        TextButton(onClick = { showNotificationDialog = false }) {
          Text("Close", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      },
      containerColor = ApexSurfaceContainerHigh
    )
  }
}
