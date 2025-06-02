package dev.euryperez.kmp.applovinmax.ads

import cocoapods.AppLovinSDK.ALMediationProviderMAX
import cocoapods.AppLovinSDK.ALSdk
import cocoapods.AppLovinSDK.ALSdkInitializationConfiguration
import cocoapods.AppLovinSDK.ALSdkSettings
import cocoapods.AppLovinSDK.MASegment
import cocoapods.AppLovinSDK.MASegmentCollection.Companion.segmentCollectionWithBuilderBlock
import dev.euryperez.kmp.applovinmax.ads.models.AppLovinSdkSettings
import dev.euryperez.kmp.applovinmax.ads.models.MaxAudienceSegment
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSNumber
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
class AppLovinSdkManagerImpl : AppLovinSdkManager {

    @OptIn(ExperimentalForeignApi::class)
    override fun initializeSdk(
        sdkKey: String,
        segments: List<MaxAudienceSegment>,
        androidApplication: Any?,
        sdkSettings: AppLovinSdkSettings?,
        onCompleted: () -> Unit,
    ) {
        val configuration = ALSdkInitializationConfiguration.builderWithSdkKey(sdkKey)
            .apply {
                mediationProvider = ALMediationProviderMAX
                segmentCollection = segmentCollectionWithBuilderBlock { segmentCollectionBuilder ->
                    segments.forEach { segment ->
                        segmentCollectionBuilder?.addSegment(
                            MASegment(NSNumber(segment.key), segment.values)
                        )
                    }
                }
            }
            .build()

        sdkSettings?.let { ALSdk.shared().settings.configureSettings(it) }

        ALSdk.shared().initializeWithConfiguration(
            configuration,
            completionHandler = { onCompleted() }
        )
    }

    private fun ALSdkSettings.configureSettings(it: AppLovinSdkSettings) {
        userIdentifier = it.userId

        it.extraParameterForKey?.let { extraParam ->
            setExtraParameterForKey(extraParam.first, extraParam.second)
        }
        it.termsAndPrivacyPolicyFlowSettings?.let { termsSettings ->
            termsAndPrivacyPolicyFlowSettings.enabled = termsSettings.isEnabled
            termsSettings.termsUrl?.let { url ->
                termsAndPrivacyPolicyFlowSettings.termsOfServiceURL = NSURL.URLWithString(url)
            }
            termsSettings.privacyPolicyUrl?.let { url ->
                termsAndPrivacyPolicyFlowSettings.privacyPolicyURL = NSURL.URLWithString(url)
            }
        }
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppLovinSdkManagerBuilder {
    actual fun build(): AppLovinSdkManager = AppLovinSdkManagerImpl()
}