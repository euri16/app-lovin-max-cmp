package dev.euryperez.kmp.applovinmax.ads

import dev.euryperez.kmp.applovinmax.ads.models.AppLovinSdkSettings
import dev.euryperez.kmp.applovinmax.ads.models.MaxAudienceSegment

interface AppLovinSdkManager {
    /**
     * Initializes the AppLovin SDK with the provided SDK key.
     *
     * @param sdkKey The SDK key for AppLovin.
     * @param segments Optional list of audience segments to be used for targeting.
     * @param androidApplication Optional Android application instance for initialization.
     * @param sdkSettings Optional settings for the AppLovin SDK.
     * @param onCompleted Callback to be invoked when the SDK initialization is complete.
     *
     */
    fun initializeSdk(
        sdkKey: String,
        segments: List<MaxAudienceSegment> = emptyList(),
        androidApplication: Any? = null,
        sdkSettings: AppLovinSdkSettings? = null,
        onCompleted: () -> Unit,
    )
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppLovinSdkManagerBuilder {
    fun build(): AppLovinSdkManager
}