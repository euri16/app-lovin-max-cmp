package dev.euryperez.kmp.applovinmax.ads

import android.app.Application
import com.applovin.mediation.MaxSegment
import com.applovin.mediation.MaxSegmentCollection
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import androidx.core.net.toUri
import dev.euryperez.kmp.applovinmax.ads.models.AppLovinSdkSettings
import dev.euryperez.kmp.applovinmax.ads.models.MaxAudienceSegment

internal class AppLovinSdkManagerImpl : AppLovinSdkManager {
    override fun initializeSdk(
        sdkKey: String,
        segments: List<MaxAudienceSegment>,
        androidApplication: Any?,
        sdkSettings: AppLovinSdkSettings?,
        onCompleted: () -> Unit
    ) {
        require(androidApplication is Application) {
            "Application must be of type android.app.Application"
        }

        val initConfig = AppLovinSdkInitializationConfiguration.builder(sdkKey)
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .setSegmentCollection(
                MaxSegmentCollection.builder()
                    .apply {
                        segments.forEach { segment ->
                            addSegment(MaxSegment(segment.key, segment.values))
                        }
                    }
                    .build()
            )
            .build()


        sdkSettings?.let {
            AppLovinSdk.getInstance(androidApplication).settings.configureSettings(it)
        }

        AppLovinSdk.getInstance(androidApplication).initialize(initConfig) { _ ->
            onCompleted()
        }
    }

    private fun com.applovin.sdk.AppLovinSdkSettings.configureSettings(it: AppLovinSdkSettings) {
        userIdentifier = it.userId
        it.extraParameterForKey?.let { extraParam ->
            setExtraParameter(extraParam.first, extraParam.second)
        }
        it.termsAndPrivacyPolicyFlowSettings?.let { termsSettings ->
            termsAndPrivacyPolicyFlowSettings.isEnabled = termsSettings.isEnabled
            termsSettings.termsUrl?.let { url ->
                termsAndPrivacyPolicyFlowSettings.termsOfServiceUri = url.toUri()
            }
            termsSettings.privacyPolicyUrl?.let { url ->
                termsAndPrivacyPolicyFlowSettings.privacyPolicyUri = url.toUri()
            }
        }
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppLovinSdkManagerBuilder {
    actual fun build(): AppLovinSdkManager = AppLovinSdkManagerImpl()
}