package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.ApexBackground
import com.example.ui.theme.ApexPrimary
import com.example.ui.theme.ApexSurfaceContainerHigh

const val APEX_LOGO_URL = "https://lh3.googleusercontent.com/aida/AEtjO1UxtgKZFNEE9TGczVEaaiKAJLNUoA-LpHtLs4IQzWQ31KKx-va9r0P7xupC6Agq8tOSOl5WHKRg6zMKN78BwNvBdfbzRdA-K_ysT3-JL9APVw3nC5OqGTNLMK_RFPpGgZKXCzPCGnw2DXBrArkHIviVZMd1NCX2FlX3Q0sycIVJ46waBtnzSOoxD3jyxbDzVEvmG6sLQJhK4U0kt-RwRs1kT3grfrcfjJslk6uAqiRfL3G0yTS_HqUWkFUO"
const val APEX_AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuARI_LCaon3rBvh1jwukwoNkyvIGgq1bp5OTmK3JVTeQdMlLW-cc-_wVEmbqI2ZSP0VNBVF3WdjcotBg67_klcNq9zuWj2a1EXN8Oee_ilWEmEbOTUPJoP3hhaU0XBzKJDh9mksCHJIZRPjDUyJ76Xc658ignR8IxujkBxK_APT_DqNl4LI8h8CI3Bk3sehPNPst66BZc3CXAN2DDPikM0u2tUGqwlsr2vPm2RZwi5BXE4iHXjKRVldfQ"

@Composable
fun ApexTopBar(
  subtitle: String,
  unreadNotifications: Int,
  onNotificationClick: () -> Unit,
  onProfileClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(ApexBackground.copy(alpha = 0.95f))
      .statusBarsPadding()
      .height(60.dp)
      .padding(horizontal = 16.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Logo + Name
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        AsyncImage(
          model = APEX_LOGO_URL,
          contentDescription = "ApexVault Logo",
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp)),
          contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "ApexVault",
            style = MaterialTheme.typography.headlineMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = subtitle.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
              letterSpacing = 1.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      // Notification button with badge
      Box(contentAlignment = Alignment.TopEnd) {
        IconButton(
          onClick = onNotificationClick,
          modifier = Modifier
            .size(44.dp)
            .testTag("notifications_button")
        ) {
          Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
        if (unreadNotifications > 0) {
          Box(
            modifier = Modifier
              .padding(top = 8.dp, end = 8.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(ApexPrimary)
              .border(1.5.dp, ApexBackground, CircleShape)
          )
        }
      }

      Spacer(modifier = Modifier.width(4.dp))

      // User Avatar
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .border(1.dp, ApexSurfaceContainerHigh, CircleShape)
          .clickable { onProfileClick() }
          .testTag("profile_button")
      ) {
        AsyncImage(
          model = APEX_AVATAR_URL,
          contentDescription = "User Profile",
          modifier = Modifier.size(36.dp),
          contentScale = ContentScale.Crop
        )
      }
    }
  }
}
