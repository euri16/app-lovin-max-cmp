package dev.euryperez.kmp.applovinmax.ads.components.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.euryperez.kmp.applovinmax.ads.models.AdEvent

@Composable
expect fun AppLovinBanner(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit,
    modifier: Modifier = Modifier,
)