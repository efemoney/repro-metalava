package com.repro.metalava.liba

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.repro.metalava.libb.BApi
import com.repro.metalava.libb.BDeprecatedApi
import org.jetbrains.compose.resources.stringResource

internal val LocalBApi = staticCompositionLocalOf<BApi> { BDeprecatedApi("default") }

@Composable
internal fun ProvideBApi(content: @Composable () -> Unit) {
  CompositionLocalProvider(
    LocalBApi provides BDeprecatedApi(stringResource(Res.string.test_string)),
    content = content,
  )
}
