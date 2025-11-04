import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NativeCompassPlugin)
public class NativeCompassPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NativeCompassPlugin" 
    public let jsName = "NativeCompass" 
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getCurrentHeading", returnType: CAPPluginReturnPromise),
    ] 
    private let implementation = NativeCompass()

    override public func load() {
        implementation.startListeners()

        NotificationCenter.default.addObserver(forName: UIApplication.didBecomeActiveNotification, object: nil, queue: OperationQueue.main) {
            [weak self] (_) in self?.implementation.startListeners()
        }

        NotificationCenter.default.addObserver(forName: UIApplication.willResignActiveNotification, object: nil, queue: OperationQueue.main) {
            [weak self] (_) in self?.implementation.stopListeners()
        }
    }

    @objc func getCurrentHeading(_ call: CAPPluginCall) {
        let heading = implementation.getCurrentHeading()
        if heading < 0 {
            call.reject("Failed to get heading. Did the user permit location access?")
            return
        }
        call.resolve([
            "value": heading
        ])
    }
}
