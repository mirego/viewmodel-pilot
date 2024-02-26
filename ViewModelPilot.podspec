Pod::Spec.new do |spec|
  spec.name                     = "ViewModel"
  spec.version                  = "0.1.0"
  spec.homepage                 = "https://github.com/mirego/viewmodel-pilot"
  spec.source                   = { :git => "https://github.com/mirego/viewmodel-pilot", :tag => "#{spec.version}" }
  spec.authors                  = { "Nicolas Presseault" => "npresseault@mirego.com" }
  spec.license                  =  { :type => "MIT", :file => "LICENSE.md" }
  spec.summary                  = "ViewModel Pilot"
  spec.static_framework         = true
  spec.source_files             = "ios/**/*.swift"
  
  spec.platform = :ios, "15.0"

  spec.subspec "Navigation" do |subspec|
    subspec.source_files  = "navigation/ios/**/*.swift"
  end
  
  spec.subspec "ViewModel" do |subspec|
    subspec.source_files  = "viewmodel/ios/**/*.swift"
  end
end
