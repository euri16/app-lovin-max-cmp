package dev.euryperez.kmp.applovinmax.ads.components.nativead

import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView

internal class AppLovinAdLoader(adUnitId: String) {
    private val nativeAdLoader = MaxNativeAdLoader(adUnitId)
    private var currentAd: MaxAd? = null
    private var currentNativeAdView: MaxNativeAdView? = null

    fun loadAd(
        onAdLoad: (ad: MaxAd, adView: MaxNativeAdView) -> Unit,
        onAdClick: (MaxAd) -> Unit,
        onAdFailToLoad: (String) -> Unit
    ) {
        nativeAdLoader
            .also { it.setNativeAddListener(onAdLoad, onAdFailToLoad, onAdClick) }
            .loadAd()
    }

    private fun MaxNativeAdLoader.setNativeAddListener(
        onAdLoad: (ad: MaxAd, adView: MaxNativeAdView) -> Unit,
        onAdFailToLoad: (String) -> Unit,
        onAdClick: (MaxAd) -> Unit
    ) {
        setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                currentAd?.let {
                    nativeAdLoader.destroy(it)
                    currentNativeAdView?.let { adView ->
                        adView.removeAllViews()
                        adView.addView(nativeAdView)
                    }
                }

                currentAd = ad

                if (nativeAdView != null) {
                    this@AppLovinAdLoader.currentNativeAdView = nativeAdView
                    onAdLoad(ad, nativeAdView)
                } else {
                    onAdFailToLoad("Native ad view is null")
                }
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                onAdFailToLoad("Failed to load ad: ${error.message}")
            }

            override fun onNativeAdClicked(ad: MaxAd) {
                onAdClick(ad)
            }

            override fun onNativeAdExpired(ad: MaxAd) {
                // Handle ad expiration
            }
        })
    }

    fun onClear() {
        currentAd?.let(nativeAdLoader::destroy) ?: run { nativeAdLoader.destroy() }
        nativeAdLoader.setNativeAdListener(null)
    }
}