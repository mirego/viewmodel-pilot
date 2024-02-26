package com.mirego.declarative.navigation

public abstract class PilotNavigationManager<ROUTE : PilotNavigationRoute, ACTION : Any> {
    public abstract fun currentRoutes(): List<ROUTE>

    public abstract fun <T : ROUTE> findRoute(uniqueId: String): T?

    public abstract fun push(route: ROUTE, locally: Boolean = false)
    public abstract fun pop()
    public abstract fun popToId(uniqueId: String, inclusive: Boolean)
    public abstract fun popToName(name: String, inclusive: Boolean)
    public abstract fun popToRoot()

    public var listener: PilotNavigationListener<ROUTE>? = null
    public var actionListener: PilotActionNavigationListener<ACTION>? = null

    public abstract fun popped()
    public abstract fun poppedFrom(route: ROUTE)

    public abstract fun handleAction(action: ACTION)
}

public abstract class PilotNavigationListener<ROUTE : PilotNavigationRoute> {
    public abstract fun push(route: ROUTE)
    public abstract fun pop()
    public abstract fun popTo(route: ROUTE, inclusive: Boolean)
}

public abstract class PilotActionNavigationListener<ACTION> {
    public abstract fun handleAction(action: ACTION)
}
