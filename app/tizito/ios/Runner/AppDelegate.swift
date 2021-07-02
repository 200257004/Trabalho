import UIKit
import Flutter

@UIApplicationMain
@objc class AppDelegate: FlutterAppDelegate {
  override func application(
    _ application: UIApplication,
    didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
  ) -> Bool {
    GeneratedPluginRegistrant.register(with: self)

    // TODO: Add your API key
    GMSServices.provideAPIKey("AIzaSyC5YWNR98HZnQ3XrTOtd4nFmqgpvozmxXQ")

    return super.application(application, didFinishLaunchingWithOptions: launchOptions)
  }
}
