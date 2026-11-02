package me.algosketch.newportfolio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * URL 해시와 현재 화면을 맞춘다.
 * [navigate] 는 해시만 바꾸고, 실제 화면 전환은 `hashchange` 이벤트로 일어난다.
 * 그래서 브라우저 뒤로·앞으로 가기와 주소창 직접 입력도 같은 경로로 처리된다.
 */
@Stable
class PortfolioNavigator internal constructor(initialRoute: Route) {
    var currentRoute: Route by mutableStateOf(initialRoute)
        private set

    fun navigate(route: Route) {
        if (route == currentRoute) return
        setLocationHash(route.hash)
    }

    internal fun syncWithLocation() {
        currentRoute = Route.fromHash(currentLocationHash())
    }
}

@Composable
fun rememberPortfolioNavigator(): PortfolioNavigator {
    val navigator = remember { PortfolioNavigator(Route.fromHash(currentLocationHash())) }
    DisposableEffect(navigator) {
        val removeListener = addHashChangeListener(navigator::syncWithLocation)
        onDispose(removeListener)
    }
    return navigator
}
