package dev.euryperez.kmp.applovinmax.ads.components.nativead

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.nativeAds.MaxNativeAdView

@Composable
actual fun AppLovinNativeAdView(
    adUnitId: String,
    modifier: Modifier,
    onAdLoaded: (adIdentifier: String) -> Unit,
    onAdClick: (adIdentifier: String) -> Unit,
    onAdFailedToLoad: (String) -> Unit
) {
    var nativeAdView by remember { mutableStateOf<MaxNativeAdView?>(null) }

    val adLoader = remember(adUnitId) { AppLovinAdLoader(adUnitId) }

    DisposableEffect(adUnitId) {
        adLoader.loadAd(
            onAdLoad = { ad, adView ->
                nativeAdView = adView

                onAdLoaded(ad.adUnitId)
            },
            onAdClick = { onAdClick(it.adUnitId) },
            onAdFailToLoad = onAdFailedToLoad
        )

        onDispose { adLoader.onClear() }
    }

    nativeAdView?.let { adView ->
        Box(modifier = modifier) {
            AndroidView(factory = { adView })
        }
    }
}