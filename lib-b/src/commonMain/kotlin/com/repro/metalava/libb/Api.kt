package com.repro.metalava.libb

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface BApi {

  val name: String

  @Composable
  fun Content(modifier: Modifier = Modifier)
}

enum class BEnum {
  Berlin,
  London,
  Paris,
}

class BDeprecatedApi(override val name: String) : BApi {

  @Composable
  override fun Content(modifier: Modifier) = Unit

  @Deprecated("Use new api", level = DeprecationLevel.HIDDEN)
  @Composable
  fun Content(name: String, modifier: Modifier = Modifier) {
    if (this.name == name) {
      Content(modifier)
    }
  }
}
