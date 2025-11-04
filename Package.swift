// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorNativeCompass",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorNativeCompass",
            targets: ["NativeCompassPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "NativeCompassPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/NativeCompassPlugin"),
        .testTarget(
            name: "NativeCompassPluginTests",
            dependencies: ["NativeCompassPlugin"],
            path: "ios/Tests/NativeCompassPluginTests")
    ]
)