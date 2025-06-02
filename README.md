# AppLovin MAX Integration in Compose Multiplatform

This repository demonstrates how to integrate AppLovin MAX ads into a Compose Multiplatform (CMP) project, supporting both Android and iOS platforms.

## 🚀 Features

- **Cross-Platform Ad Integration**: Utilize AppLovin MAX ads seamlessly across Android and iOS using shared Kotlin code.
- **Composable Ad Components**: Implement ads as composable functions for easy integration into your UI.
- **SDK Initialization**: Platform-specific initialization methods to set up the AppLovin SDK.

## 📦 Module Structure

- `applovinmax`: A Kotlin Multiplatform module containing shared code for displaying ads.

## 🛠️ SDK Initialization

### Android

Initialize the AppLovin SDK in your Android `Application` class:

```kotlin
AppLovinSdkManagerBuilder.build().initializeSdk(
    "YOUR_SDK_KEY",
    androidApplication = this,
    onCompleted = {
        // SDK initialization completed
    }
)
```

### iOS

Initialize the AppLovin SDK in your iOS application's entry point:

```kotlin
AppLovinSdkManagerBuilder.shared.build().initializeSdk(
    sdkKey = "YOUR_SDK_KEY",
    segments = [],
    androidApplication = nil,
    sdkSettings = nil,
    onCompleted = {}
)
```

## 🎨 Ad Components

### Banner Ads

Display banner ads using the `AppLovinBanner` composable function:

```kotlin
@Composable
expect fun AppLovinBanner(
    adUnitId: String,
    onAdEvent: (AdEvent) -> Unit,
    modifier: Modifier = Modifier,
)
```

### Native Ads

Display native ads using the `AppLovinNativeAdView` composable function:

```kotlin
@Composable
expect fun AppLovinNativeAdView(
    adUnitId: String,
    modifier: Modifier,
    onAdLoaded: (adIdentifier: String) -> Unit,
    onAdClick: (adIdentifier: String) -> Unit,
    onAdFailedToLoad: (String) -> Unit
)
```

## 📱 Platform Support

- **Android**: Fully supported with Jetpack Compose.
- **iOS**: Supported via Compose Multiplatform with integration into SwiftUI.

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
