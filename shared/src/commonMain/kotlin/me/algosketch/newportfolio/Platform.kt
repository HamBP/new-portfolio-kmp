package me.algosketch.newportfolio

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform