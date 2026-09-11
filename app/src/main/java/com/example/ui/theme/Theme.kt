package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  primary = ApexPrimary,
  onPrimary = ApexOnPrimary,
  primaryContainer = ApexPrimaryContainer,
  onPrimaryContainer = ApexOnPrimaryContainer,
  secondary = ApexSecondary,
  onSecondary = ApexOnSecondary,
  secondaryContainer = ApexSecondaryContainer,
  onSecondaryContainer = ApexOnSecondaryContainer,
  tertiary = ApexTertiary,
  onTertiary = ApexOnTertiary,
  background = ApexBackground,
  onBackground = ApexOnSurface,
  surface = ApexSurface,
  onSurface = ApexOnSurface,
  surfaceVariant = ApexSurfaceContainerHighest,
  onSurfaceVariant = ApexOnSurfaceVariant,
  outline = ApexOutline,
  outlineVariant = ApexOutlineVariant,
  error = ApexError,
  onError = ApexOnError
)

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}
