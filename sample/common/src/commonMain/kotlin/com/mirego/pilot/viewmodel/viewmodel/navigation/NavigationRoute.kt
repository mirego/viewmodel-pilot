package com.mirego.pilot.viewmodel.viewmodel.navigation

import com.mirego.pilot.navigation.EnumPilotNavigationRoute

public enum class NavigationRouteName {
    RICH_TEXT_PAGE,
    TEXT_FIELD_PAGE,
    OTHER_COMPONENTS_PAGE,
}

public sealed class NavigationRoute(routeName: NavigationRouteName) : EnumPilotNavigationRoute(routeName) {
    public data object RichTextPage: NavigationRoute(NavigationRouteName.RICH_TEXT_PAGE)

    public data object TextFieldPage: NavigationRoute(NavigationRouteName.TEXT_FIELD_PAGE)

    public data object OtherComponentsPage: NavigationRoute(NavigationRouteName.OTHER_COMPONENTS_PAGE)
}
