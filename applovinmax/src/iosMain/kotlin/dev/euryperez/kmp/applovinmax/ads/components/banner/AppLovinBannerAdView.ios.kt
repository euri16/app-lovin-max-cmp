package dev.euryperez.kmp.applovinmax.ads.components.banner

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.AppLovinSDK.MAAd
import cocoapods.AppLovinSDK.MAAdView
import cocoapods.AppLovinSDK.MAAdViewAdDelegateProtocol
import cocoapods.AppLovinSDK.MAError
import dev.euryperez.kmp.applovinmax.ads.models.AdEvent
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIColor
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun AppLovinBanner(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit,
    modifier: Modifier,
) {
    val maxAdView = rememberMaxAdView(adUnitId, onAdEvent)

    Box(modifier = modifier) {
        UIKitView(factory = { maxAdView })
    }
}


@OptIn(ExperimentalForeignApi::class)
@Composable
private fun rememberMaxAdView(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit
): MAAdView {
    val maxAdView = remember(adUnitId) { MAAdView(adUnitId) }

    DisposableEffect(maxAdView) {
        maxAdView.setBackgroundColor(UIColor.whiteColor)

        val height: CGFloat =
            if (UIDevice.currentDevice.userInterfaceIdiom == UIUserInterfaceIdiomPad) {
                90.0
            } else {
                50.0
            }

        val width = UIScreen.mainScreen.bounds.useContents { size.width }

        maxAdView.setFrame(CGRectMake(0.0, 0.0, width, height))

        maxAdView.setDelegate(object : NSObject(), MAAdViewAdDelegateProtocol {
            override fun didCollapseAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdCollapse(ad.adUnitIdentifier))
            }

            override fun didClickAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdClick(ad.adUnitIdentifier))
            }

            override fun didDisplayAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdDisplay(ad.adUnitIdentifier))
            }

            override fun didExpandAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdExpand(ad.adUnitIdentifier))
            }

            override fun didFailToDisplayAd(ad: MAAd, withError: MAError) {
                onAdEvent(
                    AdEvent.OnAdDisplayFail(
                        ad.adUnitIdentifier,
                        withError.code.toInt(),
                        withError.mediatedNetworkErrorCode.toInt(),
                        withError.message ?: "Unknown error"
                    )
                )
            }

            override fun didFailToLoadAdForAdUnitIdentifier(
                adUnitIdentifier: String,
                withError: MAError
            ) {
                onAdEvent(
                    AdEvent.OnAdLoadFail(
                        adUnitIdentifier,
                        withError.code.toInt(),
                        withError.mediatedNetworkErrorCode.toInt(),
                        withError.message ?: "Unknown error"
                    )
                )
            }

            override fun didHideAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdHidden(ad.adUnitIdentifier))
            }

            override fun didLoadAd(ad: MAAd) {
                onAdEvent(AdEvent.OnAdLoaded(ad.adUnitIdentifier))
            }
        })

        onDispose {
            maxAdView.removeFromSuperview()
            maxAdView.delegate = null
        }
    }

    return maxAdView
}