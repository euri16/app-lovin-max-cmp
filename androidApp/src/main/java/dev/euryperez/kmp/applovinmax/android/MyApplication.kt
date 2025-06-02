package dev.euryperez.kmp.applovinmax.android

import android.app.Application
import dev.euryperez.kmp.applovinmax.ads.AppLovinSdkManagerBuilder

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppLovinSdkManagerBuilder.build().initializeSdk(
            "YOUR_SDK_KEY",
            androidApplication = this,
            onCompleted = {
                // SDK initialization completed
            }
        )
    }
}