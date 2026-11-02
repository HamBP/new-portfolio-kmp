package me.algosketch.newportfolio.navigation

import kotlinx.browser.window
import org.w3c.dom.events.Event

internal actual fun currentLocationHash(): String = window.location.hash

internal actual fun setLocationHash(hash: String) {
    window.location.hash = hash
}

internal actual fun addHashChangeListener(listener: () -> Unit): () -> Unit {
    val callback: (Event) -> Unit = { listener() }
    window.addEventListener("hashchange", callback)
    return { window.removeEventListener("hashchange", callback) }
}
