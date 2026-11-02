package me.algosketch.newportfolio.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle

/**
 * 아이콘 + 글자 링크 (예: `GitHub`, `Play 스토어`). 호버하면 [hoverColor] 로 바뀐다.
 */
@Composable
fun IconTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    color: Color = PortfolioColors.Subtle,
    hoverColor: Color = PortfolioColors.Heading,
    fontSize: TextUnit = 13.sp,
    iconSize: Dp = 14.dp,
    gap: Dp = 5.dp,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor by interactionSource.hoverColor(default = color, hovered = hoverColor)

    Row(
        modifier = modifier.webClickable(interactionSource, role = Role.Button, onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = contentColor,
            )
        }
        Text(
            text = text,
            style = PortfolioTextStyle.sans(size = fontSize, color = contentColor),
        )
    }
}
