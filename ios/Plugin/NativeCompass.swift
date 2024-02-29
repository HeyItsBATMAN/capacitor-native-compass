import Foundation
import CoreMotion
import UIKit
import os.log

@objc public class NativeCompass: NSObject, CLLocationManagerDelegate {
    private let locationManager = CLLocationManager()
    private let log = OSLog(subsystem: "capacitor-native-compass", category: "NativeCompass")

    private var lastTrueHeading: Double = -1.0

    @objc override public init() {
        super.init()
        locationManager.delegate = self
        if CLLocationManager.authorizationStatus() == .notDetermined {
            locationManager.requestWhenInUseAuthorization()
        }
    }

    @objc public func locationManager(_ manager: CLLocationManager, didUpdateHeading newHeading: CLHeading) {
        self.lastTrueHeading = newHeading.trueHeading
    }

    @objc public func startListeners() {
        if CLLocationManager.headingAvailable() {
            locationManager.startUpdatingLocation()
            locationManager.startUpdatingHeading()
        } else {
            os_log("CLLocationManager heading not available", log: log, type: .error)
        }
    }

    @objc public func stopListeners() {
        locationManager.stopUpdatingLocation()
        locationManager.stopUpdatingHeading()
    }

    @objc public func getCurrentHeading() -> Double {
        return self.lastTrueHeading
    }
}
