package me.algosketch.newportfolio.model

import androidx.compose.runtime.Immutable

/**
 * 목록의 한 줄. [link] 가 있으면 [text] 안의 [Link.text] 부분을 링크로 보여준다.
 */
@Immutable
data class Highlight(
    val text: String,
    val link: Link? = null,
) {
    @Immutable
    data class Link(val text: String, val url: String)
}
