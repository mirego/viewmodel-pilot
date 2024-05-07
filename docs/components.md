# Components
This library provides a set of re-usable components that can be used in your application. We provide integration with material3 and coil for android and kingfisher for iOS.

## Usage
There are several components available in this library. Refer to the API docs for more extensive information.

### Images and style resources
Image and style resources (for rich text) is something you need to setup in your application theme.

#### Android
Start by creating a `PilotTextStyleResourceProvider`.
```kotlin
class YourTextStyleResourceProvider : PilotTextStyleResourceProvider {
    @Composable
    override fun textStyleForResource(resource: PilotTextStyleResource) =
        when (resource) {
            is YourTextStyleResource -> mapYourStyleResource(resource)
            else -> null
        }

    @Composable
    private fun mapYourStyleResource(resource: YourTextStyleResource): TextStyle =
        when (resource) {
            YourTextStyleResource.YOUR_TITLE_STYLE -> TextStyle(fontWeight = FontWeight.Bold)
        }
}
```

And then create a `PilotImageResourceProvider`.
```kotlin
private val resourceMap = enumMapBuilder<YourImageResource, @Composable () -> Painter> {
    when (this) {
        YourImageResource.CLOSE_ICON -> imageVector(Icons.Filled.Close)
        YourImageResource.BRAND_TOPBAR_LOGO -> resource(R.drawable.ic_brand_logo)
    }
}

private fun resource(@DrawableRes id: Int): @Composable () -> Painter =
    {
        painterResource(id)
    }

private fun imageVector(image: ImageVector): @Composable () -> Painter =
    {
        rememberVectorPainter(image)
    }

class YourImageResourceProvider : PilotImageResourceProvider {
    @Composable
    override fun painterForResource(resource: PilotImageResource): Painter? =
        when (resource) {
            is YourImageResource -> resourceMap.getValue(resource).invoke()
            else -> throw IllegalArgumentException("Unsupported image resource: $resource")
        }
}
```
And pass it to the `PilotResources` @Composable function.
```kotlin
PilotResources(
    textStyleResourceProvider = YourTextStyleResourceProvider(),
    imageResourceProvider = YourImageResourceProvider(),
)

```

Underneath, the `PilotResources` composable will provide the resources to the `PilotRichText` and `PilotRemoteImage` components. It is done via a composition local. It is also possible to nest multiple `PilotResources` composable to provide different resources to different parts of your application.

#### iOS
First create a `PilotTextStyleProvider`.
```swift
final class YourTextStyleProvider: PilotTextStyleProvider {
    func spanStyle(from resource: PilotTextStyleResource) -> PilotSpanStyle {
        guard let resource = resource as? YourTextStyleResource else { fatalError("Invalid PilotTextStyleResource type") }
        return resource.spanStyle(fontsProvider: fontsProvider, colorsProvider: colorsProvider)
    }
}

extension YourTextStyleResource {
    func spanStyle() -> PilotSpanStyle {
        switch self {
        case .yourTitleStyle:
            [.font: UIFont(name: "FontAwesome", size: 30)]
        }
    }
}
```

And then create a `PilotImageProvider`.
```swift
final class YourImageProvider: PilotImageProvider {
    func image(from resource: PilotImageResource) -> Image {
        resource.image
    }
}

extension PilotImageResource {
    var image: Image {
        guard let self = self as? YourImageResource else { fatalError("Unsupported image type")}
        return self.image
    }
}

extension YourImageResource {
    var image: Image {
        switch self {
        case .yourImageResource:
            Image(.imageResource)
        }
    }
}
```

You can create a custom View Modifier to provide the resources to the components. 
```swift
private struct YourStyleModifier: ViewModifier {
    func body(content: Content) -> some View {
        content
            .environment(\.pilotImageProvider, YourImageProvider())
            .environment(\.pilotTextStyleProvider, YourTextStyleProvider())
    }
}

extension View {
    func yourStyle() -> some View {
        modifier(YourStyleModifier())
    }
}
```
Underneath, the `PilotResources` view modifier will provide the resources to the `PilotRichTextView` and `PilotRemoteImageView` components. It is done via an environment variable.

#### View lifecycle events
`PilotAppearanceLifecycle` allows you to listen to the lifecycle events of your views. This can be used by inheriting from `PilotAppearanceLifecycleViewModel` or delegating your component class to `PilotAppearanceLifecycleImpl` such as:

```kotlin
class YourComponent: PilotAppearanceLifecycle by PilotAppearanceLifecycleImpl()
```

And then on Android using the `@Composable`:
```kotlin
PilotLifecycleView(yourComponent) {
    // Your view here
}
```

And on iOS using the view modifier:
```swift
YourView {
    // Your view here
}
.pilotAppearanceLifecycle(yourComponent)
```

## Components
| Common                      | Android                     | iOS                             |
|-----------------------------|-----------------------------|---------------------------------|
| `PilotButton`               | `PilotButton`               | `PilotButtonView`               |
| `PilotRichText`             | `PilotRichText`             | `PilotRichTextView`             |
| `PilotSwitch`               | `PilotSwitch`               | `PilotSwitchView`               |
| `PilotTextField`            | `PilotTextField`            | `PilotTextFieldView`            |
| `PilotRemoteImage`          | `PilotRemoteImage`          | `PilotRemoteImageView`          |
| `PilotRemoteImage`          | `PilotRemoteImage`          | `PilotRemoteImageView`          | 
| `PilotResizableRemoteImage` | `PilotResizableRemoteImage` | `PilotResizableRemoteImageView` |
| `PilotPicker`               | `PilotPicker`               | `PilotPickerView`               |

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
#### Android
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

#### iOS
`Podfile`
```ruby
pod 'Pilot/Components', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
pod 'Pilot/Components.Kingfisher', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
```