# Changelog

All notable changes to the library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## 0.4.11

### Added
- `pilot-swift-extensions-plugin` Gradle plugin to generate Pilot's Swift extension files at consumer build time instead of requiring manual vendoring

## 0.4.10

### Added
- `Decimal` and `NumberPunctuation` keyboard types added to `PilotKeyboardType`

## 0.4.9

### Fixed
- iOS `StateObservable` no longer races on its cached state: the flow/state-flow collection now mutates `lastState` on the main actor instead of the background collection context, preventing a crash (`objc_opt_isKindOfClass` via SKIE `onEnum`) when the value is read during SwiftUI body evaluation

## 0.4.8

### Fixed
- iOS `PilotTextFieldView` now correctly handles `PilotTextObfuscationMode` by using conditional rendering instead of overlapping fields

## 0.4.7

### Added
- `isEnabled` property added to `PilotSwitch`, `PilotPicker`, and `PilotTextField`

## 0.4.6

### Updates

### Breaking Changes

## 0.4.5

### Added
- Android PilotTextField now uses contentType for password obscuring and autofill hints

## 0.4.0

### Added
- Coil3 module

### Updates
- Kotlin to 2.2.0
- AGP to 8.11.1
- Gradle to 8.14.3
- Several other dependencies

## 0.3.6

### Updates
- Update compose Picker to expose offset and properties of inner drop down menu

### Breaking Changes
- Renamed compose Picker `itemColors` field to `labelView`.
