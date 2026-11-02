package me.algosketch.newportfolio.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

class RouteTest {

    @Test
    fun emptyOrRootHashIsHome() {
        assertEquals(Route.Home, Route.fromHash(""))
        assertEquals(Route.Home, Route.fromHash("#"))
        assertEquals(Route.Home, Route.fromHash("#/"))
    }

    @Test
    fun projectsHashIsProjects() {
        assertEquals(Route.Projects, Route.fromHash("#/projects"))
        assertEquals(Route.Projects, Route.fromHash("#/projects/"))
        assertEquals(Route.Projects, Route.fromHash("#/projects?from=home"))
    }

    @Test
    fun unknownHashFallsBackToHome() {
        assertEquals(Route.Home, Route.fromHash("#/unknown"))
    }

    @Test
    fun hashRoundTrips() {
        Route.entries.forEach { assertEquals(it, Route.fromHash(it.hash)) }
    }
}
