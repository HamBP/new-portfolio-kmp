package me.algosketch.newportfolio.screen.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.HoverTransitionMillis
import me.algosketch.newportfolio.designsystem.component.SectionEyebrow
import me.algosketch.newportfolio.designsystem.component.SectionTitle
import me.algosketch.newportfolio.designsystem.component.TechTag
import me.algosketch.newportfolio.designsystem.component.webClickable
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.model.TimelineFilter
import me.algosketch.newportfolio.model.TimelineItem
import me.algosketch.newportfolio.model.TimelineType

private const val FilterTransitionMillis = 200
private val ChipShape = RoundedCornerShape(20.dp)
private val BadgeShape = RoundedCornerShape(4.dp)

/** 세로선과 점의 x 위치 (항목 왼쪽 끝 기준) */
private val LineX = 0.5.dp
private val DotTop = 4.dp
private val DotSize = 11.dp
private val DotBorder = 2.dp

private fun TimelineType.dotColor(): Color = when (this) {
    TimelineType.Career -> PortfolioColors.Heading
    TimelineType.Activity -> PortfolioColors.Subtle
    TimelineType.Education -> PortfolioColors.Faint
}

@Composable
internal fun CareerTimelineSection(
    timeline: List<TimelineItem>,
    selectedFilter: TimelineFilter,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentContainer(
        modifier = modifier
            .background(PortfolioColors.White)
            .padding(vertical = PortfolioDimens.SectionVerticalPadding),
    ) {
        SectionEyebrow("Timeline", modifier = Modifier.padding(bottom = 8.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            itemVerticalAlignment = Alignment.Bottom,
        ) {
            SectionTitle("경력 & 활동")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                TimelineFilter.entries.forEach { filter ->
                    FilterChip(
                        label = filter.label,
                        selected = filter == selectedFilter,
                        onClick = { onAction(HomeAction.SelectTimelineFilter(filter)) },
                    )
                }
            }
        }

        Column(
            modifier = Modifier.drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(PortfolioColors.Heading, PortfolioColors.Border),
                        startY = 12.dp.toPx(),
                        endY = size.height,
                    ),
                    topLeft = Offset(0f, 12.dp.toPx()),
                    size = androidx.compose.ui.geometry.Size(1.dp.toPx(), size.height - 12.dp.toPx()),
                )
            },
        ) {
            timeline.forEach { item ->
                TimelineEntry(item = item, matched = selectedFilter.matches(item))
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val spec = tween<Color>(HoverTransitionMillis)
    val background by animateColorAsState(if (selected) PortfolioColors.Heading else PortfolioColors.White, spec)
    val border by animateColorAsState(
        when {
            selected -> PortfolioColors.Heading
            isHovered -> PortfolioColors.Subtle
            else -> PortfolioColors.Border
        },
        spec,
    )
    val textColor by animateColorAsState(
        when {
            selected -> PortfolioColors.White
            isHovered -> PortfolioColors.Heading
            else -> PortfolioColors.Muted
        },
        spec,
    )
    Text(
        text = label,
        modifier = Modifier
            .webClickable(interactionSource, role = Role.Tab, onClick = onClick)
            .background(background, ChipShape)
            .border(1.dp, border, ChipShape)
            .padding(horizontal = 14.dp, vertical = 5.dp),
        style = PortfolioTextStyle.sans(
            size = 13.sp,
            color = textColor,
            weight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
        ),
    )
}

@Composable
private fun TimelineEntry(
    item: TimelineItem,
    matched: Boolean,
) {
    val alpha by animateFloatAsState(
        targetValue = if (matched) 1f else 0.25f,
        animationSpec = tween(FilterTransitionMillis),
        label = "timelineAlpha",
    )
    val dotColor by animateColorAsState(
        targetValue = if (matched) item.type.dotColor() else PortfolioColors.Border,
        animationSpec = tween(FilterTransitionMillis),
        label = "timelineDot",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { this.alpha = alpha }
            .drawBehind {
                val radius = DotSize.toPx() / 2
                val center = Offset(LineX.toPx(), DotTop.toPx() + radius)
                val stroke = DotBorder.toPx()
                drawCircle(PortfolioColors.White, radius = radius, center = center)
                drawCircle(dotColor, radius = radius - stroke / 2, center = center, style = Stroke(stroke))
            }
            .padding(start = 32.dp, bottom = 32.dp),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                TimelineHeading(item)
            }
            TimelinePeriod(item)
        }

        if (item is TimelineItem.Career) {
            Text(
                text = item.description,
                modifier = Modifier.padding(bottom = 8.dp),
                style = PortfolioTextStyle.sans(size = 13.sp, color = PortfolioColors.Subtle),
            )
        }

        val details = when (item) {
            is TimelineItem.Career -> item.details
            is TimelineItem.Activity -> item.details
            is TimelineItem.Education -> emptyList()
        }
        if (details.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                details.forEach { detail ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        val style = PortfolioTextStyle.sans(size = 13.sp, color = PortfolioColors.Muted, lineHeight = 1.6f)
                        Text(text = "–", style = style.copy(color = PortfolioColors.Faint))
                        Text(text = detail, style = style)
                    }
                }
            }
        }

        if (item is TimelineItem.Career) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                item.techs.forEach { TechTag(it) }
            }
        }
    }
}

@Composable
private fun TimelineHeading(item: TimelineItem) {
    val subtitleStyle = PortfolioTextStyle.sans(size = 13.sp, color = PortfolioColors.Muted)
    when (item) {
        is TimelineItem.Career -> {
            Text(item.company, style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Heading, weight = FontWeight.Bold))
            Text(item.role, style = subtitleStyle)
        }

        is TimelineItem.Activity -> {
            Text(item.name, style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Heading, weight = FontWeight.SemiBold))
            Text(item.organization, style = subtitleStyle)
            item.award?.let { AwardBadge(it) }
        }

        is TimelineItem.Education -> {
            Text(item.school, style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Heading, weight = FontWeight.SemiBold))
            Text(item.major, style = subtitleStyle)
            Text(item.grade, style = PortfolioTextStyle.mono(size = 12.sp, color = PortfolioColors.Subtle))
        }
    }
}

/** 수상 배지. 디자인의 🏆 이모지는 트로피 아이콘으로 바꿨다 (이모지 폰트 미번들). */
@Composable
private fun AwardBadge(text: String) {
    Row(
        modifier = Modifier
            .background(PortfolioColors.BadgeBackground, BadgeShape)
            .border(1.dp, PortfolioColors.BadgeBorder, BadgeShape)
            .padding(horizontal = 8.dp, vertical = 1.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = LucideIcons.Trophy,
            contentDescription = null,
            modifier = Modifier.size(11.dp),
            tint = PortfolioColors.BadgeText,
        )
        Text(text = text, style = PortfolioTextStyle.sans(size = 11.sp, color = PortfolioColors.BadgeText))
    }
}

@Composable
private fun TimelinePeriod(item: TimelineItem) {
    Row {
        Text(item.period, style = PortfolioTextStyle.mono(size = 12.sp, color = PortfolioColors.Subtle))
        if (item is TimelineItem.Career) {
            Text(
                text = "· ${item.duration}",
                modifier = Modifier.padding(start = 6.dp),
                style = PortfolioTextStyle.mono(size = 12.sp, color = PortfolioColors.Faint),
            )
        }
    }
}
