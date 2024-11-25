package com.mirego.pilot.navigation

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultPilotNavigationManagerTest {

    private val testScope = CoroutineScope(UnconfinedTestDispatcher())
    private val parentListener = TestNavigationListener()
    private val childListener = TestNavigationListener()
    private val parentNavigationManager = DefaultPilotNavigationManager<TestNavigationRoute, Any>(testScope).apply {
        listener = parentListener
    }
    private val childNavigationManager = DefaultPilotNavigationManager(testScope, parentNavigationManager = parentNavigationManager).apply {
        listener = childListener
    }

    @Test
    fun `when pushing and popping on the child not locally it should push and pop to the parent`() {
        childNavigationManager.push(TestNavigationRoute.Route1, locally = false)
        childNavigationManager.push(TestNavigationRoute.Route2, locally = false)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1, TestNavigationRoute.Route2)
        assertThat(childListener.routes).isEmpty()

        childNavigationManager.pop(locally = false)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1)
        assertThat(childListener.routes).isEmpty()

        childNavigationManager.pop(locally = false)
        assertThat(parentListener.routes).isEmpty()
        assertThat(childListener.routes).isEmpty()
    }

    @Test
    fun `when popping locally and there is nothing to pop it should pop the parent`() {
        parentNavigationManager.push(TestNavigationRoute.Route1, locally = true)
        childNavigationManager.push(TestNavigationRoute.Route2, locally = true)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1)
        assertThat(childListener.routes).containsExactly(TestNavigationRoute.Route2)

        childNavigationManager.pop(locally = true)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1)
        assertThat(childListener.routes).isEmpty()

        childNavigationManager.pop(locally = true)
        assertThat(parentListener.routes).isEmpty()
        assertThat(childListener.routes).isEmpty()
    }

    @Test
    fun `when pop inclusive it should pop the given route`() {
        parentNavigationManager.push(TestNavigationRoute.Route1)
        parentNavigationManager.push(TestNavigationRoute.Route2)
        parentNavigationManager.push(TestNavigationRoute.Route3)

        parentNavigationManager.popToId(TestNavigationRoute.Route2.uniqueId, inclusive = true)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1)

        parentNavigationManager.popToName(TestNavigationRouteName.ROUTE1.name, inclusive = true)
        assertThat(parentListener.routes).isEmpty()
    }

    @Test
    fun `when pop exclusive it should not pop the given route`() {
        parentNavigationManager.push(TestNavigationRoute.Route1)
        parentNavigationManager.push(TestNavigationRoute.Route2)
        parentNavigationManager.push(TestNavigationRoute.Route3)

        parentNavigationManager.popToId(TestNavigationRoute.Route2.uniqueId, inclusive = false)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1, TestNavigationRoute.Route2)

        parentNavigationManager.popToName(TestNavigationRoute.Route1.name, inclusive = false)
        assertThat(parentListener.routes).containsExactly(TestNavigationRoute.Route1)
    }

    private enum class TestNavigationRouteName {
        ROUTE1,
        ROUTE2,
        ROUTE3,
    }

    private sealed class TestNavigationRoute(routeName: TestNavigationRouteName) : EnumPilotNavigationRoute(routeName) {
        data object Route1 : TestNavigationRoute(TestNavigationRouteName.ROUTE1)
        data object Route2 : TestNavigationRoute(TestNavigationRouteName.ROUTE2)
        data object Route3 : TestNavigationRoute(TestNavigationRouteName.ROUTE3)
    }

    private class TestNavigationListener : PilotNavigationListener<TestNavigationRoute>() {
        val routes = mutableListOf<TestNavigationRoute>()

        override fun canNavigate(route: TestNavigationRoute): Boolean {
            return true
        }

        override fun push(route: TestNavigationRoute) {
            routes.add(route)
        }

        override fun pop() {
            routes.removeLastOrNull()
        }

        override fun popTo(route: TestNavigationRoute, inclusive: Boolean) {
            while (routes.last() != route) {
                routes.removeLastOrNull()
            }
            if (inclusive) {
                routes.removeLastOrNull()
            }
        }
    }
}
