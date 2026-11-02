package me.algosketch.newportfolio.screen.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.data.Profile
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.HoverTransitionMillis
import me.algosketch.newportfolio.designsystem.component.SectionEyebrow
import me.algosketch.newportfolio.designsystem.component.SectionTitle
import me.algosketch.newportfolio.designsystem.component.webClickable
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.model.Contribution

private val CardShape = RoundedCornerShape(10.dp)
private val TagShape = RoundedCornerShape(3.dp)

@Composable
internal fun OtherActivitiesSection(
    blogPosts: List<Contribution>,
    openSourceContributions: List<Contribution>,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentContainer(
        modifier = modifier
            .background(PortfolioColors.SectionBackground)
            .padding(vertical = PortfolioDimens.SectionVerticalPadding),
    ) {
        SectionEyebrow("More", modifier = Modifier.padding(bottom = 8.dp))
        SectionTitle("그 외 활동", modifier = Modifier.padding(bottom = 12.dp))
        Text(
            text = buildAnnotatedString {
                append("블로그 글 작성, 오픈소스 기여, 기술 서적 오류 보고 등 커뮤니티에 기여합니다. ")
                withLink(
                    LinkAnnotation.Clickable(
                        tag = Profile.BLOG_URL,
                        styles = TextLinkStyles(
                            style = SpanStyle(color = PortfolioColors.Heading, fontWeight = FontWeight.Medium),
                        ),
                        linkInteractionListener = { onAction(HomeAction.ClickLink(Profile.BLOG_URL)) },
                    ),
                ) {
                    append("${Profile.BLOG_DISPLAY} →")
                }
            },
            modifier = Modifier.padding(bottom = 48.dp),
            style = PortfolioTextStyle.sans(size = 14.sp, color = PortfolioColors.Muted),
        )

        val columns = listOf(blogPosts, openSourceContributions)
        if (PortfolioTheme.windowSize.isCompact) {
            Column(verticalArrangement = Arrangement.spacedBy(40.dp)) {
                columns.forEach { ContributionColumn(it, onAction) }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                columns.forEach { ContributionColumn(it, onAction, Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun ContributionColumn(
    contributions: List<Contribution>,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        contributions.forEach { contribution ->
            ContributionCard(
                contribution = contribution,
                onClick = { onAction(HomeAction.ClickLink(contribution.url)) },
            )
        }
    }
}

/** 호버하면 테두리가 진해지고 오른쪽으로 4px 이동하며 그림자가 생긴다. */
@Composable
private fun ContributionCard(
    contribution: Contribution,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val border by animateColorAsState(
        targetValue = if (isHovered) PortfolioColors.Faint else PortfolioColors.Border,
        animationSpec = tween(HoverTransitionMillis),
        label = "cardBorder",
    )
    val offsetX by animateDpAsState(
        targetValue = if (isHovered) 4.dp else 0.dp,
        animationSpec = tween(HoverTransitionMillis),
        label = "cardOffset",
    )
    val shadowAlpha by animateFloatAsState(
        targetValue = if (isHovered) 0.04f else 0f,
        animationSpec = tween(HoverTransitionMillis),
        label = "cardShadow",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX)
            .dropShadow(CardShape, Shadow(radius = 8.dp, color = Color.Black.copy(alpha = shadowAlpha), offset = DpOffset(0.dp, 2.dp)))
            .webClickable(interactionSource, role = Role.Button, onClick = onClick)
            .background(PortfolioColors.White, CardShape)
            .border(1.dp, border, CardShape)
            .padding(horizontal = 20.dp, vertical = 18.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val tag = contribution.tag
            Text(
                text = tag.label,
                modifier = Modifier
                    .background(tag.background, TagShape)
                    .border(1.dp, tag.border, TagShape)
                    .padding(horizontal = 7.dp, vertical = 1.dp),
                style = PortfolioTextStyle.mono(size = 10.sp, color = tag.text),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = contribution.date,
                    style = PortfolioTextStyle.mono(size = 11.sp, color = PortfolioColors.Faint),
                )
                Icon(
                    imageVector = LucideIcons.ExternalLink,
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = PortfolioColors.Faint,
                )
            }
        }
        Text(
            text = contribution.title,
            modifier = Modifier.padding(bottom = 4.dp),
            style = PortfolioTextStyle.sans(
                size = 13.sp,
                color = PortfolioColors.Heading,
                weight = FontWeight.SemiBold,
                lineHeight = 1.4f,
            ),
        )
        Text(
            text = contribution.subtitle,
            style = PortfolioTextStyle.sans(size = 12.sp, color = PortfolioColors.Subtle),
        )
    }
}
