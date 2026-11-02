package me.algosketch.newportfolio.designsystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role

/** CSS `transition: ... 0.15s` 에 해당하는 기본 전환 시간 */
const val HoverTransitionMillis = 150

/**
 * 링크·버튼처럼 동작하게 한다. 손가락 커서, 호버 감지, 클릭. 물결(ripple) 효과는 쓰지 않는다.
 *
 * 앱 전체가 `SelectionContainer` 안에 있어 안쪽 텍스트가 I-beam 커서를 내므로,
 * `overrideDescendants` 로 자식 커서를 덮어 요소 전체에서 손가락 커서가 보이게 한다.
 */
fun Modifier.webClickable(
    interactionSource: MutableInteractionSource,
    role: Role = Role.Button,
    onClick: () -> Unit,
): Modifier = this
    .pointerHoverIcon(PointerIcon.Hand, overrideDescendants = true)
    .hoverable(interactionSource)
    .clickable(
        interactionSource = interactionSource,
        indication = null,
        role = role,
        onClick = onClick,
    )

/** 호버 상태에 따라 [default] 와 [hovered] 사이를 부드럽게 바꾼다. */
@Composable
fun MutableInteractionSource.hoverColor(default: Color, hovered: Color): State<Color> {
    val isHovered by collectIsHoveredAsState()
    return animateColorAsState(
        targetValue = if (isHovered) hovered else default,
        animationSpec = tween(HoverTransitionMillis),
        label = "hoverColor",
    )
}
