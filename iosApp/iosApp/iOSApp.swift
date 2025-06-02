import SwiftUI
import applovinmax

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
	var body: some Scene {
		WindowGroup {
            
			ContentView()
		}
	}
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions:
                     [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        AppLovinSdkManagerBuilder.shared.build().initializeSdk(
            sdkKey: "YOUR SDK KEY",
            segments: [],
            androidApplication: nil,
            sdkSettings: nil,
            onCompleted: {}
        )
        
        return true
    }
}
