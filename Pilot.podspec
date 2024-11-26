require_relative 'podspec_versions.rb'

properties = load_properties('gradle.properties')

Pod::Spec.new do |spec|
  spec.name                     = "Pilot"
  spec.version                  = "#{properties['version']}"
  spec.homepage                 = "https://github.com/mirego/viewmodel-pilot"
  spec.source                   = { :git => "https://github.com/mirego/viewmodel-pilot", :tag => "#{spec.version}" }
  spec.authors                  = { "Nicolas Presseault" => "npresseault@mirego.com" }
  spec.license                  =  { :type => "MIT", :file => "LICENSE.md" }
  spec.summary                  = "ViewModel Pilot"
  spec.static_framework         = true
  spec.source_files             = "ios/**/*.swift"
  
  spec.ios.deployment_target = "15.0"
  spec.tvos.deployment_target = "15.0"
  spec.osx.deployment_target = "12.0"

  spec.dependency "Shared"

  spec.subspec "Navigation" do |subspec|
    subspec.source_files  = "navigation/ios/**/*.swift"
    subspec.osx.exclude_files  = "navigation/ios/FullScreenNotAnimatedPresenter.swift"
  end
  
  spec.subspec "ViewModel" do |subspec|
    subspec.source_files  = "viewmodel/ios/**/*.swift"
  end

  spec.subspec "Components" do |subspec|
    subspec.source_files  = "components/ios/base/**/*.swift"
    subspec.osx.exclude_files = [
      "components/ios/base/Types/PilotKeyboardType.swift",
      "components/ios/base/Types/PilotKeyboardAutoCapitalization.swift",
      "components/ios/base/Types/PilotTextContentType.swift"
    ]
    subspec.dependency 'Pilot/ViewModel'
  end

  spec.subspec "Components.Kingfisher" do |subspec|
    subspec.source_files  = "components/ios/kingfisher/**/*.swift"
    subspec.dependency 'Pilot/Components'
    subspec.dependency 'Kingfisher', '~> 7.10.1'
  end

end
