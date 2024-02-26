import Foundation

@objc public class NativeCompass: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
