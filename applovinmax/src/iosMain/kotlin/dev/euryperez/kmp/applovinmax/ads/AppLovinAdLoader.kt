package dev.euryperez.kmp.applovinmax.ads

import cocoapods.AppLovinSDK.MAAd
import cocoapods.AppLovinSDK.MAError
import cocoapods.AppLovinSDK.MANativeAdDelegateProtocol
import cocoapods.AppLovinSDK.MANativeAdLoader
import cocoapods.AppLovinSDK.MANativeAdView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class AppLovinAdLoader(adUnitId: String) {
    private var nativeAdLoader: MANativeAdLoader = MANativeAdLoader(adUnitId)
    private var currentAd: MAAd? = null

    fun loadAd(
        onAdLoad: (ad: MAAd, adView: MANativeAdView) -> Unit,
        onAdClick: (MAAd) -> Unit,
        onAdFailToLoad: (String) -> Unit
    ) {
        nativeAdLoader
            .also { it.setNativeAdDelegate(onAdLoad, onAdFailToLoad, onAdClick) }
            .loadAd()
    }

    private fun MANativeAdLoader.setNativeAdDelegate(
        onAdLoad: (ad: MAAd, adView: MANativeAdView) -> Unit,
        onAdFailToLoad: (String) -> Unit,
        onAdClick: (MAAd) -> Unit
    ) {
        setNativeAdDelegate(object : NSObject(), MANativeAdDelegateProtocol {
            override fun didLoadNativeAd(nativeAdView: MANativeAdView?, forAd: MAAd) {
                currentAd?.let(nativeAdLoader::destroyAd)
                currentAd = forAd

                if (nativeAdView != null) {
                    onAdLoad(forAd, nativeAdView)
                } else {
                    onAdFailToLoad("Native ad view is null")
                }
            }

            override fun didFailToLoadNativeAdForAdUnitIdentifier(
                adUnitIdentifier: String,
                withError: MAError
            ) {
                onAdFailToLoad(withError.message)
            }

            override fun didClickNativeAd(ad: MAAd) {
                onAdClick(ad)
            }

            override fun didExpireNativeAd(ad: MAAd) {
                // Handle ad expiration - clean up expired ad
                if (currentAd == ad) {
                    currentAd = null
                }
            }
        })
    }

    fun onClear() {
        currentAd?.let(nativeAdLoader::destroyAd)
        nativeAdLoader.setNativeAdDelegate(null)
    }
}