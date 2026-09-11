package com.example.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EventRepeat
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexOnPrimary
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexSecondary
import com.example.ui.theme.ApexSecondaryContainer
import com.example.ui.theme.ApexSurfaceContainer
import com.example.ui.theme.ApexSurfaceContainerHigh
import com.example.ui.theme.ApexSurfaceContainerHighest
import com.example.ui.theme.ApexSurfaceContainerLow
import com.example.ui.theme.ApexSurfaceContainerLowest
import com.example.ui.viewmodel.ApexUiState
import java.text.DecimalFormat

const val GROCER_LOGO_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCAxGGslHTYXWMfCRvRK-MvD4qDFUDVoxFxQ2EDJbI8MLAjWwg50HSa_4L_peY64wWeah-YdlVvQwhaNvoJTpxQcjzJAwd05Qzd-MsGU1R2TY7f-pk1TS28-d_Wz9PfnJqFezwd7RFsS38fhBFct-ieHi7DhdtDAP30E6EdPZT7gXy-dI13_3dGm42rH-TvinCSFmHOK3162ImzS5M09yYXWOTW1WBDMaBojE7CVdR6vET-LTLfuQFUQQ"
const val MAP_PREVIEW_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuAXN4Cr-FTPI7gWxP0LLw33k3r_Rkmv9WrzNjLKXRUj-ShWfl79KVkYjlpN0QQmDQqZEDoDvxE2YkbU8JX5bv8aE87J7MXq6OW73AI-3deBoSSs2MeIgzvAMkYKYIyc5kQZacmezk1aWCOxiBsq8wylDxyZIl_bEzASU8cDT8lgNhXEB-5nmrHAR541UvYqLMwK3n5THUEvIHBV2ICgY6kmy-56Hgz2-gjhhCj0QgK8uEJ8wQaLFkqKuQ"
const val AVATAR_1 = "https://lh3.googleusercontent.com/aida-public/AB6AXuAbB3EZyBf-h4zYYOtV_eFl7Hv6ODjg0lNs9mNUP4cs-rz76IaJkFLRLLdYlsL53BAP_FnJaebKPLY8-1l069D3dInK9cxTVrdJDqMMfGQiqws2tF3TYMGL6I0X0nVvBhImq_k3nq-h4YuVWJ0OrQ6uHHl28UCOgXNOYZBRbq751ZQbw-CMB6oN0gx1kYrWDTOgFMBCeLhNhE0Yg34YhCjG93GijeVsN51TvEjK7c6V_okJfBm_QTLu-w"
const val AVATAR_2 = "https://lh3.googleusercontent.com/aida-public/AB6AXuD4-Z3nGFGy1-xKp9uoDJLmVqVKkXXZQ27ZWU2ML_oCWcKgZa6EKo42Rw0Z-8yz5OjGMrtwA7SedvG9hlgqVrQ7D8_ReiB3IBqcD64tStAhRcYmoS2ih02ewOaSobzUaMnv891ua_sqznCtQFJf61htXQ3q8ry1RNhN72rGKkeWsGERkspAGBaCjDnIywq6yGTE5ASSNzd8Vjj-ctG3VCjYgr25wpJRpRLtMNWZjOU9kzRh3F4rShQ4og"
const val AVATAR_3 = "https://lh3.googleusercontent.com/aida-public/AB6AXuAEY-gRD8l-FQkhLVg6O0Cr9ogTp7v7HCY0Ct8g9_Ix7vlDyBzziIh-90uo6mHs5CWT7rgIaHlozU74c6Ni9N0PKjHW1VvmAOqH6d6MbiflE3oYvrRp9-zxAsY7Bz2yi56H80ly-M_yP5oe-J06ucZYAmUsJluYqJZQ0V1uTQVNDbUEWlKGV1qDQsDaPnlWfPZ4Wo71avYvofrfKoELDMwxknZsgarRWUvndnYntKTDz15f-yhsJGgFwQ"

@Composable
fun LogTransactionScreen(
  uiState: ApexUiState,
  onCancel: () -> Unit,
  onDone: () -> Unit,
  onSetFlowType: (String) -> Unit,
  onSetCategory: (String) -> Unit,
  onKeypadInput: (String) -> Unit,
  onToggleRecurring: () -> Unit,
  onAddTag: (String) -> Unit,
  onRemoveTag: (String) -> Unit,
  onSnapCamera: () -> Unit,
  onUploadPdf: () -> Unit,
  onSendSplitLinks: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddTagDialog by remember { mutableStateOf(false) }
  var newTagText by remember { mutableStateOf("") }

  val infiniteTransition = rememberInfiniteTransition(label = "cursor")
  val cursorAlpha by infiniteTransition.animateFloat(
    initialValue = 0.2f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(600),
      repeatMode = RepeatMode.Reverse
    ),
    label = "cursor_blink"
  )

  // Calculate live split amount
  val numericAmount = uiState.logAmount.toDoubleOrNull() ?: 0.0
  val splitAmount = if (uiState.logSplitCount > 0) numericAmount / uiState.logSplitCount else 0.0

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ApexBackground)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Modal Navigation Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        TextButton(
          onClick = onCancel,
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(ApexSurfaceContainer)
            .testTag("cancel_log_btn")
        ) {
          Text(
            text = "Cancel",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "Log Transaction",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 17.sp
            )
          )
          Text(
            text = "APEX SMART LEDGER",
            style = MaterialTheme.typography.labelSmall.copy(
              letterSpacing = 1.sp,
              color = ApexPrimary,
              fontWeight = FontWeight.Bold
            )
          )
        }

        Button(
          onClick = onDone,
          colors = ButtonDefaults.buttonColors(
            containerColor = ApexPrimary,
            contentColor = ApexOnPrimary
          ),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("done_log_btn")
        ) {
          Text(
            text = if (uiState.isSavedSuccess) "Done" else "Done",
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // 2. Segmented Flow Switcher
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(ApexSurfaceContainerLow)
          .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        listOf("EXPENSE", "INCOME", "TRANSFER").forEach { flow ->
          val isSelected = uiState.logFlowType == flow
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) ApexSurfaceContainerHighest else Color.Transparent)
              .clickable { onSetFlowType(flow) }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = flow,
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) ApexPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
              )
            )
          }
        }
      }
    }

    // 3. Numeric Input Hero
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(vertical = 20.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "LIVE ALLOCATION VALUE",
            style = MaterialTheme.typography.labelSmall.copy(
              letterSpacing = 1.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = "$",
              style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = ApexPrimary,
                fontSize = 32.sp
              )
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = uiState.logAmount.ifEmpty { "0" },
              style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 38.sp
              ),
              modifier = Modifier.testTag("keypad_display_text")
            )
            Spacer(modifier = Modifier.width(4.dp))
            // Blinking cursor
            Box(
              modifier = Modifier
                .width(2.5.dp)
                .height(34.dp)
                .background(ApexPrimary.copy(alpha = cursorAlpha))
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(ApexPrimary)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "USD • SOVEREIGN MULTI-VAULT RESERVE",
              style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.5.sp
              )
            )
          }
        }
      }
    }

    // 4. Quick Receipt Scanner AI OCR Banner
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainer)
          .border(1.dp, ApexSurfaceContainerHigh, RoundedCornerShape(20.dp))
          .padding(14.dp)
      ) {
        Row(verticalAlignment = Alignment.Top) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(ApexSurfaceContainerHighest),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.ReceiptLong,
              contentDescription = null,
              tint = ApexPrimary,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Scan Receipt or Invoice",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 15.sp
                )
              )
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(ApexSurfaceContainerHighest)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "Auto-AI",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = ApexPrimary,
                    fontSize = 10.sp
                  )
                )
              }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "Instant OCR parses itemized taxes, vendor nodes & merchant",
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              ),
              maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Button(
                onClick = onSnapCamera,
                colors = ButtonDefaults.buttonColors(
                  containerColor = ApexPrimary,
                  contentColor = ApexOnPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                modifier = Modifier.testTag("snap_camera_btn")
              ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Snap Camera", fontSize = 11.sp, fontWeight = FontWeight.Bold)
              }

              Button(
                onClick = onUploadPdf,
                colors = ButtonDefaults.buttonColors(
                  containerColor = ApexSurfaceContainerHigh,
                  contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                modifier = Modifier.testTag("upload_pdf_btn")
              ) {
                Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Upload PDF", fontSize = 11.sp)
              }
            }
          }
        }
      }
    }

    // 5. Category Selector Chips
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "CATEGORY VECTOR",
            style = MaterialTheme.typography.labelSmall.copy(
              letterSpacing = 1.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
          Text(
            text = "Smart AI Match: 98.4%",
            style = MaterialTheme.typography.labelSmall.copy(
              color = ApexPrimary
            )
          )
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          val categoryItems = listOf(
            Pair("Groceries", Icons.Default.ShoppingCart),
            Pair("Dining", Icons.Default.Restaurant),
            Pair("Travel", Icons.Default.FlightTakeoff),
            Pair("Shopping", Icons.Default.ShoppingBag),
            Pair("Bills", Icons.Default.Bolt),
            Pair("Health", Icons.Default.HealthAndSafety)
          )

          categoryItems.forEach { (cat, icon) ->
            val isSelected = uiState.logCategory == cat
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) ApexPrimary else ApexSurfaceContainer)
                .clickable { onSetCategory(cat) }
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .testTag("category_chip_$cat"),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = icon,
                contentDescription = cat,
                tint = if (isSelected) ApexOnPrimary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = cat.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) ApexOnPrimary else MaterialTheme.colorScheme.onSurface,
                  letterSpacing = 0.5.sp
                )
              )
            }
          }
        }
      }
    }

    // 6. Merchant & Contextual Metadata
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainer)
          .padding(14.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          // Merchant Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              AsyncImage(
                model = GROCER_LOGO_URL,
                contentDescription = "Merchant Logo",
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(ApexSurfaceContainerHighest),
                contentScale = ContentScale.Crop
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = uiState.logMerchant,
                    style = MaterialTheme.typography.titleLarge.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurface,
                      fontSize = 15.sp
                    )
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified",
                    tint = ApexPrimary,
                    modifier = Modifier.size(16.dp)
                  )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = ApexSecondary,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = uiState.logLocation,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1
                  )
                }
              }
            }

            IconButton(
              onClick = { /* edit merchant */ },
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(ApexSurfaceContainerHigh)
            ) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Merchant",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          // Mini Map Preview Banner
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(90.dp)
              .clip(RoundedCornerShape(12.dp))
          ) {
            AsyncImage(
              model = MAP_PREVIEW_URL,
              contentDescription = "Map Location",
              modifier = Modifier.fillMaxSize(),
              contentScale = ContentScale.Crop
            )
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.verticalGradient(
                    colors = listOf(Color.Transparent, ApexBackground.copy(alpha = 0.85f))
                  )
                )
                .padding(8.dp),
              contentAlignment = Alignment.BottomStart
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(6.dp)
                      .clip(CircleShape)
                      .background(ApexPrimary)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = "POS Terminal #0492 • 40.7359° N",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = MaterialTheme.colorScheme.onSurface,
                      fontSize = 10.sp
                    )
                  )
                }

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(ApexSurfaceContainerHighest.copy(alpha = 0.8f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "VERIFIED GEO",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontSize = 9.sp,
                      color = ApexSecondary,
                      fontWeight = FontWeight.Bold
                    )
                  )
                }
              }
            }
          }

          // Vault Meta Tags
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "VAULT META TAGS",
              style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              uiState.logTags.forEach { tag ->
                Row(
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(ApexSurfaceContainerHighest)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "#$tag",
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = MaterialTheme.colorScheme.onSurface
                    )
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove tag",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                      .size(12.dp)
                      .clickable { onRemoveTag(tag) }
                  )
                }
              }

              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(ApexSurfaceContainerHigh)
                  .clickable { showAddTagDialog = true }
                  .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Add,
                  contentDescription = "Add Tag",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                  text = "Add Tag",
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
              }
            }
          }

          // Recurring Payment Toggle
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(ApexSurfaceContainerLow)
              .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(RoundedCornerShape(8.dp))
                .background(ApexSurfaceContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.EventRepeat,
                  contentDescription = null,
                  tint = ApexSecondary,
                  modifier = Modifier.size(16.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Repeat Monthly",
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
                Text(
                  text = "Auto-record recurring baseline expense",
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                )
              }
            }

            Switch(
              checked = uiState.logIsRecurring,
              onCheckedChange = { onToggleRecurring() },
              colors = SwitchDefaults.colors(
                checkedThumbColor = ApexOnPrimary,
                checkedTrackColor = ApexPrimary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = ApexSurfaceContainerHighest
              )
            )
          }
        }
      }
    }

    // 7. Split with Friends Utility
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainer)
          .padding(14.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Group,
                contentDescription = null,
                tint = ApexSecondary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Split with Friends",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurface,
                  fontSize = 15.sp
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ApexSecondaryContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "${uiState.logSplitCount} People Total",
                style = MaterialTheme.typography.labelSmall.copy(
                  color = MaterialTheme.colorScheme.onSurface,
                  fontWeight = FontWeight.Bold
                )
              )
            }
          }

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(ApexSurfaceContainerLow)
              .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "PER PARTICIPANT",
                style = MaterialTheme.typography.labelSmall.copy(
                  letterSpacing = 1.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
              Text(
                text = "$${DecimalFormat("#,##0.00").format(splitAmount)}",
                style = MaterialTheme.typography.headlineMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = ApexPrimary
                )
              )
            }

            // Contact Avatar Circles + "You"
            Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
              AsyncImage(
                model = AVATAR_1,
                contentDescription = "Contact 1",
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .border(1.5.dp, ApexSurfaceContainer, CircleShape),
                contentScale = ContentScale.Crop
              )
              AsyncImage(
                model = AVATAR_2,
                contentDescription = "Contact 2",
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .border(1.5.dp, ApexSurfaceContainer, CircleShape),
                contentScale = ContentScale.Crop
              )
              AsyncImage(
                model = AVATAR_3,
                contentDescription = "Contact 3",
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .border(1.5.dp, ApexSurfaceContainer, CircleShape),
                contentScale = ContentScale.Crop
              )
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(ApexPrimary)
                  .border(1.5.dp, ApexSurfaceContainer, CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "You",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = ApexOnPrimary
                  )
                )
              }
            }
          }

          Button(
            onClick = onSendSplitLinks,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("send_split_btn"),
            colors = ButtonDefaults.buttonColors(
              containerColor = ApexSurfaceContainerHigh,
              contentColor = ApexSecondary
            ),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Send Payment Request Links",
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }

    // 8. Custom Numeric Keypad
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(ApexSurfaceContainerLow)
          .padding(8.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            KeypadButton("1", onClick = { onKeypadInput("1") }, modifier = Modifier.weight(1f))
            KeypadButton("2", onClick = { onKeypadInput("2") }, modifier = Modifier.weight(1f))
            KeypadButton("3", onClick = { onKeypadInput("3") }, modifier = Modifier.weight(1f))
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            KeypadButton("4", onClick = { onKeypadInput("4") }, modifier = Modifier.weight(1f))
            KeypadButton("5", onClick = { onKeypadInput("5") }, modifier = Modifier.weight(1f))
            KeypadButton("6", onClick = { onKeypadInput("6") }, modifier = Modifier.weight(1f))
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            KeypadButton("7", onClick = { onKeypadInput("7") }, modifier = Modifier.weight(1f))
            KeypadButton("8", onClick = { onKeypadInput("8") }, modifier = Modifier.weight(1f))
            KeypadButton("9", onClick = { onKeypadInput("9") }, modifier = Modifier.weight(1f))
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            KeypadButton(".", onClick = { onKeypadInput(".") }, modifier = Modifier.weight(1f))
            KeypadButton("0", onClick = { onKeypadInput("0") }, modifier = Modifier.weight(1f))
            KeypadButton("backspace", isIcon = true, onClick = { onKeypadInput("backspace") }, modifier = Modifier.weight(1f))
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(16.dp)) }
  }

  // Add Tag Dialog
  if (showAddTagDialog) {
    AlertDialog(
      onDismissRequest = { showAddTagDialog = false },
      title = { Text("Add Vault Tag") },
      text = {
        OutlinedTextField(
          value = newTagText,
          onValueChange = { newTagText = it },
          label = { Text("Tag Name (e.g. tax-deductible)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      },
      confirmButton = {
        Button(
          onClick = {
            if (newTagText.isNotBlank()) {
              onAddTag(newTagText)
              newTagText = ""
              showAddTagDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = ApexPrimary, contentColor = ApexOnPrimary)
        ) {
          Text("Add")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddTagDialog = false }) {
          Text("Cancel", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      },
      containerColor = ApexSurfaceContainerHigh
    )
  }
}

@Composable
private fun KeypadButton(
  text: String,
  isIcon: Boolean = false,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .height(48.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(ApexSurfaceContainer)
      .clickable { onClick() }
      .testTag("keypad_btn_$text"),
    contentAlignment = Alignment.Center
  ) {
    if (isIcon) {
      Icon(
        imageVector = Icons.Default.Backspace,
        contentDescription = "Backspace",
        tint = ApexPrimary,
        modifier = Modifier.size(22.dp)
      )
    } else {
      Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface,
          fontSize = 20.sp
        )
      )
    }
  }
}
