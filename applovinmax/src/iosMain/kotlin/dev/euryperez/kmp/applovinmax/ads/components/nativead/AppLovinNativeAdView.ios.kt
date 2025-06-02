package dev.euryperez.kmp.applovinmax.ads.components.nativead

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.AppLovinSDK.MAAd
import cocoapods.AppLovinSDK.MANativeAdView
import dev.euryperez.kmp.applovinmax.ads.AppLovinAdLoader
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun AppLovinNativeAdView(
    adUnitId: String,
    modifier: Modifier,
    onAdLoaded: (adIdentifier: String) -> Unit,
    onAdClick: (adIdentifier: String) -> Unit,
    onAdFailedToLoad: (String) -> Unit
) {
    var nativeAd by remember { mutableStateOf<MAAd?>(null) }
    var nativeAdView by remember { mutableStateOf<MANativeAdView?>(null) }

    val adLoader = remember(adUnitId) { AppLovinAdLoader(adUnitId) }

    DisposableEffect(adUnitId) {
        adLoader.loadAd(
            onAdLoad = { ad, adView ->
                nativeAd = ad
                nativeAdView = adView

                onAdLoaded(ad.adUnitIdentifier)
            },
            onAdClick = { onAdClick(it.adUnitIdentifier) },
            onAdFailToLoad = onAdFailedToLoad
        )

        onDispose { adLoader.onClear() }
    }

    nativeAdView?.let { adView ->
        Box(modifier = modifier) {
            UIKitView(factory = { adView })
        }
    }
}