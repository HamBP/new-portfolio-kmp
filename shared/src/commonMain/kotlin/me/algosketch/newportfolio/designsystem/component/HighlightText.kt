package me.algosketch.newportfolio.designsystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.model.Highlight

/** 목록 한 줄. 링크 부분은 제목색·굵게·밑줄로 보여주고 누르면 [onLinkClick] 에 주소를 넘긴다. */
@Composable
fun HighlightText(
    highlight: Highlight,
    style: TextStyle,
    onLinkClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val link = highlight.link
    if (link == null) {
        Text(text = highlight.text, modifier = modifier, style = style)
        return
    }
    val text = remember(highlight) {
        val start = highlight.text.indexOf(link.text)
        buildAnnotatedString {
            if (start < 0) {
                append(highlight.text)
                return@buildAnnotatedString
            }
            append(highlight.text.substring(0, start))
            withLink(
                LinkAnnotation.Clickable(
                    tag = link.url,
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = PortfolioColors.Heading,
                            fontWeight = FontWeight.Medium,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ),
                    linkInteractionListener = { onLinkClick(link.url) },
                ),
            ) {
                append(link.text)
            }
            append(highlight.text.substring(start + link.text.length))
        }
    }
    Text(text = text, modifier = modifier, style = style)
}
