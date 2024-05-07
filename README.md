<div align="center">
  <p>Pilot is a library that allows you to share viewmodels, navigation and components on android and iOS</p>
  <a href="http://kotlinlang.org"><img alt="kotlin 1.9.23" src="https://img.shields.io/badge/kotlin-1.9.23-blue.svg?logo=kotlin" /></a>
  <a href="https://opensource.org/licenses/BSD-3-Clause"><img alt="BSD-3 Clause" src="https://img.shields.io/badge/License-BSD_3--Clause-blue.svg" /></a>
  <a href="https://github.com/mirego/viewmodel-pilot/tags"><img alt="Latest"  src="https://img.shields.io/github/tag/mirego/viewmodel-pilot.svg?label=latest"></a>
  <a href="https://github.com/mirego/viewmodel-pilot/actions/workflows/ci.yml"><img alt="CI" src="https://github.com/mirego/viewmodel-pilot/actions/workflows/ci.yml/badge.svg" /></a>
  <a href="https://github.com/mirego/viewmodel-pilot/actions/workflows/release.yml"><img alt="Release" src="https://github.com/mirego/viewmodel-pilot/actions/workflows/release.yml/badge.svg" /></a>
</div>

# Pilot

## Kotlin versions

Each [release](https://github.com/mirego/viewmodel-pilot/releases) outlines what version of the Kotlin compiler is used.

<table>
 <tr>
  <td><img alt="kotlin 1.9.22" src="https://img.shields.io/badge/kotlin-1.9.22-blue.svg?logo=kotlin" /></td><td><img alt="0.1.0" src="https://img.shields.io/maven-metadata/v?label=mirego-maven&versionPrefix=0.1.0&metadataUrl=https%3A%2F%2Fmirego-maven.s3.amazonaws.com%2Fpublic%2Fcom%2Fmirego%2Fpilot%2Fviewmodel%2Fmaven-metadata.xml"></td>
 </tr>
 <tr>
  <td><img alt="kotlin 1.9.23" src="https://img.shields.io/badge/kotlin-1.9.23-blue.svg?logo=kotlin" /></td><td><img alt="0.2.10" src="https://img.shields.io/maven-metadata/v?label=mirego-maven&versionPrefix=0.2.10&metadataUrl=https%3A%2F%2Fmirego-maven.s3.amazonaws.com%2Fpublic%2Fcom%2Fmirego%2Fpilot%2Fviewmodel%2Fmaven-metadata.xml"></td>
 </tr>
</table>

## Libraries
### [Viewmodel](docs/viewmodel.md)
A tiny library that allows you to share view models between android and iOS.

### [Navigation](docs/navigation.md)
A library for Compose and SwiftUI that allows you to share navigation between android and iOS.

### [Components](docs/components.md)
A library that contains re-usable components that can be used in your view models.

## Pre-requisites
All libraries require:

- [SKIE](https://skie.touchlab.co/) to be configured on your main project.
- Your kotlin multiplatform iOS Framework to be called `Shared`.

## License

Pilot is © 2024 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
