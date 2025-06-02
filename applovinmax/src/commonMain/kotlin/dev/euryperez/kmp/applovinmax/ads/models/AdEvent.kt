package dev.euryperez.kmp.applovinmax.ads.models

sealed interface AdEvent {
    data class OnAdLoaded(val adIdentifier: String) : AdEvent
    data class OnAdClick(val adIdentifier: String) : AdEvent
    data class OnAdDisplay(val adIdentifier: String) : AdEvent
    data class OnAdHidden(val adIdentifier: String) : AdEvent
    data class OnAdExpand(val adIdentifier: String) : AdEvent
    data class OnAdCollapse(val adIdentifier: String) : AdEvent
    data class OnAdLoadFail(
        val adIdentifier: String,
        val errorCode: Int,
        val mediatedNetworkErrorCode: Int,
        val errorMessage: String
    ) : AdEvent

    data class OnAdDisplayFail(
        val adIdentifier: String,
        val errorCode: Int,
        val mediatedNetworkErrorCode: Int,
        val errorMessage: String
    ) : AdEvent
}