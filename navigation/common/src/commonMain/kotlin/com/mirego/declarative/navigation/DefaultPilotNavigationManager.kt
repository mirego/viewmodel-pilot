package com.mirego.declarative.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

public open class DefaultPilotNavigationManager<ROUTE : PilotNavigationRoute, ACTION : Any>(
    private val coroutineScope: CoroutineScope,
    private val parentNavigationManager: PilotNavigationManager<ROUTE, ACTION>? = null,
) : PilotNavigationManager<ROUTE, ACTION>() {

    private val internalRouteList: MutableList<ROUTE> = mutableListOf()

    override fun currentRoutes(): List<ROUTE> =
        internalRouteList

    @Suppress("UNCHECKED_CAST")
    override fun <T : ROUTE> findRoute(uniqueId: String): T? =
        internalRouteList.firstOrNull { it.uniqueId == uniqueId } as? T

    override fun push(route: ROUTE, locally: Boolean) {
        coroutineScope.launch {
            if (!locally && parentNavigationManager != null) {
                parentNavigationManager.push(route, locally = locally)
                return@launch
            }

            internalRouteList.add(route)
            listener?.push(route)
        }
    }

    override fun pop() {
        internalPop(callListener = true)
    }

    private fun internalPop(callListener: Boolean) {
        coroutineScope.launch {
            internalRouteList.removeLastOrNull()

            if (callListener) {
                listener?.pop()
            }
        }
    }

    override fun popToId(uniqueId: String, inclusive: Boolean) {
        internalPopTo(popType = PopType.ById(uniqueId), inclusive = inclusive, callListener = true)
    }

    override fun popToName(name: String, inclusive: Boolean) {
        internalPopTo(popType = PopType.ByName(name), inclusive = inclusive, callListener = true)
    }

    override fun popToRoot() {
        coroutineScope.launch {
            val firstItem = internalRouteList.firstOrNull() ?: return@launch
            internalPopTo(popType = PopType.ById(firstItem.uniqueId), inclusive = true, callListener = true)
        }
    }

    private fun internalPopTo(popType: PopType, inclusive: Boolean, callListener: Boolean) {
        coroutineScope.launch {
            val navigationItem = internalRouteList
                .lastOrNull { item ->
                    when (popType) {
                        is PopType.ById -> item.uniqueId == popType.id
                        is PopType.ByName -> item.name == popType.name
                    }
                } ?: return@launch
            val index = internalRouteList.indexOf(navigationItem)

            if (index != -1 && internalRouteList.isNotEmpty()) {
                val effectiveIndex = index + if (inclusive) 0 else 1
                internalRouteList.removeAll(internalRouteList.takeLast(internalRouteList.size - effectiveIndex))
                if (callListener) {
                    listener?.popTo(navigationItem, inclusive = inclusive)
                }
            }
        }
    }

    override fun popped() {
        internalPop(callListener = false)
    }

    override fun handleAction(action: ACTION) {
        parentNavigationManager?.handleAction(action) ?: actionListener?.handleAction(action)
    }

    override fun poppedFrom(route: ROUTE) {
        coroutineScope.launch {
            val routeIndex = internalRouteList.indexOfFirst {
                it.uniqueId == route.uniqueId
            }
            if (routeIndex != -1) {
                internalPopTo(popType = PopType.ById(route.uniqueId), inclusive = true, callListener = false)
            }
        }
    }
}

private sealed interface PopType {
    data class ById(val id: String) : PopType
    data class ByName(val name: String) : PopType
}
