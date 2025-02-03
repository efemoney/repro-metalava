package com.repro.metalava.libb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmInline

sealed interface Token {
  val value: Color
}

@Deprecated("", level = DeprecationLevel.HIDDEN)
sealed interface FillColor : Token {

  @Deprecated("", level = DeprecationLevel.HIDDEN)
  val contentColor: Color @Composable @ReadOnlyComposable get() = Color.Unspecified
}

@JvmInline
@Suppress("DEPRECATION_ERROR")
value class BackgroundColor(
  @Deprecated("", level = DeprecationLevel.ERROR)
  override val value: Color
) : FillColor {
  @Deprecated("", level = DeprecationLevel.HIDDEN)
  override val contentColor: Color @Composable @ReadOnlyComposable get() = value
}
