package com.mirego.pilot.navigation

public interface PilotNavigationRoute {
    public val name: String
    public val uniqueId: String
}

public abstract class EnumPilotNavigationRoute(routeName: Enum<*>) : PilotNavigationRoute {
    override val name: String = routeName.name
    override val uniqueId: String = UUIDGenerator.uuid()
}
