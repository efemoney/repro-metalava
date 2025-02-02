package com.repro.metalava.liba

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ComponentText(
  string: String,
  modifier: Modifier = Modifier,
) {
  BasicText(
    text = string,
    modifier = modifier,
  )
}

@Composable
fun ComponentTextField(
  state: TextFieldState,
  modifier: Modifier = Modifier,
) {
  BasicTextField(
    state = state,
    modifier = modifier,
  )
}

@Composable
@Deprecated("Use ComponentTextField with TextFieldState instead", level = DeprecationLevel.HIDDEN)
fun ComponentTextField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
  )
}
