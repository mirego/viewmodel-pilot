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
  
  spec.platform = :ios, "13.0"

  spec.dependency "Shared"

  spec.subspec "Navigation" do |subspec|
    subspec.platform = :ios, "14.0"
    subspec.source_files  = "navigation/ios/**/*.swift"
  end
  
  spec.subspec "ViewModel" do |subspec|
    subspec.source_files  = "viewmodel/ios/**/*.swift"
  end

  spec.subspec "Components" do |subspec|
    subspec.source_files  = "components/ios/base/**/*.swift"
    subspec.dependency 'Pilot/ViewModel'
  end

  spec.subspec "Components.Kingfisher" do |subspec|
    subspec.platform = :ios, "14.0"
    subspec.source_files  = "components/ios/kingfisher/**/*.swift"
    subspec.dependency 'Pilot/Components'
    subspec.dependency 'Kingfisher', '~> 7.10.1'
  end

end
