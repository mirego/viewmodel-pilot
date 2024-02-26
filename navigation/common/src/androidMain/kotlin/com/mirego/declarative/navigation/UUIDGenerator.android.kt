package com.mirego.declarative.navigation

import java.util.UUID

internal actual object UUIDGenerator {
    actual fun uuid(): String =
        UUID.randomUUID().toString()
}
