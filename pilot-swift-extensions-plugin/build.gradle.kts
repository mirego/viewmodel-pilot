plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("mirego.publish")
}

group = "com.mirego.pilot"

gradlePlugin {
    plugins {
        create("pilotSwiftExtensions") {
            id = "com.mirego.pilot.swift-extensions"
            implementationClass = "com.mirego.pilot.swiftextensions.PilotSwiftExtensionsPlugin"
        }
    }
}

// The checked-in Swift sources use a literal `import Shared` — Pilot.podspec's CocoaPods
// consumers read these files as-is and need that to stay valid Swift. The
// PILOT_FRAMEWORK_NAME placeholder substitution below is applied only while packaging
// this plugin's own resources, not to the source files themselves.
fun CopySpec.substituteFrameworkImport() {
    filter { line -> if (line == "import Shared") "import PILOT_FRAMEWORK_NAME" else line }
}

tasks.named<Copy>("processResources") {
    from(rootProject.file("viewmodel/ios")) {
        include("*.swift")
        into("swift-extensions/viewmodel")
        substituteFrameworkImport()
    }
    from(rootProject.file("components/ios/base")) {
        include("**/*.swift")
        into("swift-extensions/components")
        substituteFrameworkImport()
    }
    from(rootProject.file("components/ios/kingfisher")) {
        include("*.swift")
        into("swift-extensions/components-kingfisher")
        substituteFrameworkImport()
    }
}
