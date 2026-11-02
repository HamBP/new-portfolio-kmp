package me.algosketch.newportfolio.app

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.data.Profile
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.hoverColor
import me.algosketch.newportfolio.designsystem.component.webClickable
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.navigation.Route

private const val ScrollTransitionMillis = 300
private val TabShape = RoundedCornerShape(6.dp)

/** 이 폭보다 좁으면 이메일 글자 대신 메일 아이콘을 보여준다. */
private val EmailTextMinWidth = 480.dp

/**
 * 상단 고정 Navigation. 스크롤이 20px 를 넘으면([scrolled]) 반투명 흰 배경과 아래 구분선이 생긴다.
 */
@Composable
fun NavigationBar(
    currentRoute: Route,
    scrolled: Boolean,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    // Color.Transparent 는 투명한 "검정"이라 흰색과 보간하면 중간에 어두운 색이 비친다.
    // 같은 색의 알파 0 에서 시작해 투명도만 바뀌게 한다.
    val background by animateColorAsState(
        targetValue = PortfolioColors.White.copy(alpha = if (scrolled) 0.92f else 0f),
        animationSpec = tween(ScrollTransitionMillis),
        label = "navBackground",
    )
    val borderColor by animateColorAsState(
        targetValue = PortfolioColors.Border.copy(alpha = if (scrolled) 1f else 0f),
        animationSpec = tween(ScrollTransitionMillis),
        label = "navBorder",
    )

    ContentContainer(
        modifier = modifier
            .background(background)
            .drawBehind {
                val stroke = 1.dp.toPx()
                val y = size.height - stroke / 2
                drawLine(borderColor, Offset(0f, y), Offset(size.width, y), strokeWidth = stroke)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(PortfolioDimens.NavigationHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                NavigationTab(label = "홈", selected = currentRoute == Route.Home) { onNavigate(Route.Home) }
                NavigationTab(label = "프로젝트", selected = currentRoute == Route.Projects) { onNavigate(Route.Projects) }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavigationIconLink(LucideIcons.Github, "GitHub") { uriHandler.openUri(Profile.GITHUB_URL) }
                NavigationIconLink(LucideIcons.Linkedin, "LinkedIn") { uriHandler.openUri(Profile.LINKEDIN_URL) }
                NavigationIconLink(LucideIcons.BookOpen, "Blog") { uriHandler.openUri(Profile.BLOG_URL) }
                if (PortfolioTheme.windowSize.width >= EmailTextMinWidth) {
                    EmailLink(modifier = Modifier.padding(start = 8.dp)) { uriHandler.openUri(Profile.EMAIL_URL) }
                } else {
                    NavigationIconLink(LucideIcons.Mail, Profile.EMAIL) { uriHandler.openUri(Profile.EMAIL_URL) }
                }
            }
        }
    }
}

@Composable
private fun NavigationTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = label,
        modifier = Modifier
            .webClickable(interactionSource, role = Role.Tab, onClick = onClick)
            .background(if (selected) PortfolioColors.TagBackground else Color.Transparent, TabShape)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        style = PortfolioTextStyle.sans(
            size = 14.sp,
            color = if (selected) PortfolioColors.Heading else PortfolioColors.Muted,
            weight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
        ),
    )
}

@Composable
private fun NavigationIconLink(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val tint by interactionSource.hoverColor(default = PortfolioColors.Subtle, hovered = PortfolioColors.Heading)
    Box(
        modifier = Modifier
            .webClickable(interactionSource, role = Role.Button, onClick = onClick)
            .padding(7.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(18.dp),
            tint = tint,
        )
    }
}

@Composable
private fun EmailLink(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val color by interactionSource.hoverColor(default = PortfolioColors.Muted, hovered = PortfolioColors.Heading)
    Text(
        text = Profile.EMAIL,
        modifier = modifier.webClickable(interactionSource, role = Role.Button, onClick = onClick),
        style = PortfolioTextStyle.sans(size = 13.sp, color = color),
    )
}
