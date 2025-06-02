package dev.euryperez.kmp.applovinmax.ads.models

data class AppLovinSdkSettings(
    val userId: String,
    val extraParameterForKey: Pair<String, String>? = null,
    val termsAndPrivacyPolicyFlowSettings: TermsAndPrivacyPolicyFlowSettings? = null,
) {
    companion object
}

data class TermsAndPrivacyPolicyFlowSettings(
    val isEnabled: Boolean = false,
    val termsUrl: String? = null,
    val privacyPolicyUrl: String? = null,
)
