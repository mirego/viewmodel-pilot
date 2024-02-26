package com.mirego.declarative.navigation

import platform.Foundation.NSUUID

internal actual object UUIDGenerator {
    actual fun uuid(): String =
        NSUUID().UUIDString()
}
