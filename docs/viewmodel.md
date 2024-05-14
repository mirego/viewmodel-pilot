# ViewModel

## Usage
#### Common
```kotlin
class YourViewModel : ViewModel() {
    val someData: StateFlow<String>  = MutableStateFlow("")
    val someNullableData: StateFlow<String?> = MutableStateFlow(null)
    
    fun yourFunction() {
        viewModelScope.launch {
            // Your code here
        }
    }
}
```

#### Android
Use the view model as you would use a regular android view model.
```kotlin
@Composable
fun YourView(viewModel: YourViewModel) {
    val someData by viewModel.someData.collectAsState()
    val someNullableData by viewModel.someNullableData.collectAsState()
}
```

#### iOS
```swift
struct YourView: View {
    @StateObject private var viewModelLifecycle: ViewModelLifecycleHandler<YourViewModel>
    @ObservedObject private var someData: StateObservable<String>
    @ObservedObject private var someNullableData: NullableStateObservable<String>
    
    private var viewModel: YourViewModel {
        viewModelLifecycle.viewModel
    }
    
    init(viewModel: YourViewModel) {
        _viewModelLifecycle = StateObject(wrappedValue: ViewModelLifecycleHandler(viewModel: viewModel))
        _someData = ObservedObject(wrappedValue: StateObservable(viewModel.someData))
        _someNullableData = ObservedObject(wrappedValue: NullableStateObservable(viewModel.someNullableData))
   }

    var body: some View {
        // Your code here where you can use viewModel, someData.value and someNullableData.value
    }
}
```

## Installation
#### Common / Android
`build.gradle.kts`
```kotlin
repositories {
    maven(url = "https://s3.amazonaws.com/mirego-maven/public")
}

dependencies {
    implementation("com.mirego.pilot:viewmodel:<version>")
}
```

#### iOS
`Podfile`
```ruby
pod 'Pilot/ViewModel', :git => 'git@github.com:mirego/viewmodel-pilot.git', :tag => '<version>', :inhibit_warnings => true
```