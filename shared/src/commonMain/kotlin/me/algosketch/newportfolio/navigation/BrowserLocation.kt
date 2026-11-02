package me.algosketch.newportfolio.navigation

/** 현재 `window.location.hash` */
internal expect fun currentLocationHash(): String

/** `window.location.hash` 를 바꾼다. 브라우저 방문 기록에 남아 뒤로 가기가 동작한다. */
internal expect fun setLocationHash(hash: String)

/** `hashchange` 이벤트를 구독한다. 돌려받은 함수를 호출하면 구독을 끊는다. */
internal expect fun addHashChangeListener(listener: () -> Unit): () -> Unit
