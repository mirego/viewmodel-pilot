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

tasks.named<Copy>("processResources") {
    from(rootProject.file("viewmodel/ios")) {
        include("*.swift")
        into("swift-extensions/viewmodel")
    }
    from(rootProject.file("components/ios/base")) {
        include("**/*.swift")
        into("swift-extensions/components")
    }
    from(rootProject.file("components/ios/kingfisher")) {
        include("*.swift")
        into("swift-extensions/components-kingfisher")
    }
}
