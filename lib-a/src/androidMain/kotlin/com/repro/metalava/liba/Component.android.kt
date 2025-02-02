package com.repro.metalava.liba

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ComponentText(
  string: String,
  context: Context,
  modifier: Modifier = Modifier,
) {
  Text(
    text = string,
    modifier = modifier,
  )
}
