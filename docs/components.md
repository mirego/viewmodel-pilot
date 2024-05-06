# Components

## Usage
### Common


### Android


### iOS

## Installation
### Common
`build.gradle.kts`
```kotlin
repositories {
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation("com.mirego.pilot:components:<version>")
}
```
### Android
`build.gradle.kts`
```kotlin
repositories {
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation("com.mirego.pilot:components:<version>")
    implementation("com.mirego.pilot:components-material3:<version>")
    implementation("com.mirego.pilot:components-coil:<version>")
}
```

### iOS
`Podfile`
```ruby
pod 'Pilot/Components', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
pod 'Pilot/Components.Kingfisher', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
```