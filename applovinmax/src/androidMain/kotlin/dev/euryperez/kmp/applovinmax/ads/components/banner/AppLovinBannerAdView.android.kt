package dev.euryperez.kmp.applovinmax.ads.components.banner

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import dev.euryperez.kmp.applovinmax.ads.models.AdEvent

@Composable
actual fun AppLovinBanner(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit,
    modifier: Modifier,
) {
    val maxAdView = rememberMaxAdView(adUnitId, onAdEvent)

    Box(modifier = modifier) {
        AndroidView(factory = { maxAdView })
    }
}

@Composable
private fun rememberMaxAdView(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit
): MaxAdView {
    val context = LocalContext.current
    val maxAdView = remember(adUnitId) { MaxAdView(adUnitId) }

    DisposableEffect(maxAdView) {
        maxAdView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT // TODO: Make it flexible to allow a fixed height
        )

        maxAdView.setBackgroundColor(context.getColor(android.R.color.white))

        maxAdView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                onAdEvent(AdEvent.OnAdLoaded(ad.adUnitId))
            }

            override fun onAdDisplayed(ad: MaxAd) {
                onAdEvent(AdEvent.OnAdDisplay(ad.adUnitId))
            }

            override fun onAdHidden(ad: MaxAd) {
                onAdEvent(AdEvent.OnAdHidden(ad.adUnitId))
            }

            override fun onAdClicked(ad: MaxAd) {
                onAdEvent(AdEvent.OnAdClick(ad.adUnitId))
            }

            override fun onAdLoadFailed(adUnitId: String, maxError: MaxError) {
                onAdEvent(
                    AdEvent.OnAdLoadFail(
                        adIdentifier = adUnitId,
                        errorCode = maxError.code,
                        mediatedNetworkErrorCode = maxError.mediatedNetworkErrorCode,
                        errorMessage = maxError.message.orEmpty()
                    )
                )
            }

            override fun onAdDisplayFailed(ad: MaxAd, maxError: MaxError) {
                onAdEvent(
                    AdEvent.OnAdDisplayFail(
                        adIdentifier = ad.adUnitId,
                        errorCode = maxError.code,
                        mediatedNetworkErrorCode = maxError.mediatedNetworkErrorCode,
                        errorMessage = maxError.message.orEmpty()
                    )
                )
            }

            override fun onAdExpanded(ad: MaxAd) {
                onAdEvent(AdEvent.OnAdExpand(ad.adUnitId))
            }

            override fun onAdCollapsed(p0: MaxAd) {
                onAdEvent(AdEvent.OnAdCollapse(p0.adUnitId))
            }
        })

        maxAdView.loadAd()

        onDispose {
            maxAdView.setListener(null)
            maxAdView.destroy()
        }
    }

    return maxAdView
}