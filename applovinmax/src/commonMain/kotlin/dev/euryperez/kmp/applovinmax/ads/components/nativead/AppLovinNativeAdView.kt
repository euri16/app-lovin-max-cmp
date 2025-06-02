package dev.euryperez.kmp.applovinmax.ads.components.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun AppLovinNativeAdView(
    adUnitId: String,
    modifier: Modifier,
    onAdLoaded: (adIdentifier: String) -> Unit,
    onAdClick: (adIdentifier: String) -> Unit,
    onAdFailedToLoad: (String) -> Unit
)