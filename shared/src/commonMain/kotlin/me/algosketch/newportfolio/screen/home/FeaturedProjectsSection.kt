package me.algosketch.newportfolio.screen.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.HighlightText
import me.algosketch.newportfolio.designsystem.component.IconTextLink
import me.algosketch.newportfolio.designsystem.component.SectionEyebrow
import me.algosketch.newportfolio.designsystem.component.SectionTitle
import me.algosketch.newportfolio.designsystem.component.TechTag
import me.algosketch.newportfolio.designsystem.component.webClickable
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.model.FeaturedProject
import me.algosketch.newportfolio.model.Metric
import me.algosketch.newportfolio.model.Screenshot
import org.jetbrains.compose.resources.painterResource

private val CardShape = RoundedCornerShape(16.dp)
private val PhoneShape = RoundedCornerShape(20.dp)

@Composable
internal fun FeaturedProjectsSection(
    projects: List<FeaturedProject>,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    ContentContainer(
        modifier = modifier
            .background(PortfolioColors.SectionBackground)
            .padding(vertical = PortfolioDimens.SectionVerticalPadding),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            itemVerticalAlignment = Alignment.Bottom,
        ) {
            Column {
                SectionEyebrow("Side Projects", modifier = Modifier.padding(bottom = 8.dp))
                SectionTitle("대표 사이드 프로젝트")
            }
            ViewAllProjectsLink(onClick = { onAction(HomeAction.ClickViewAllProjects) })
        }

        projects.forEach { project ->
            FeaturedProjectCard(project = project, onAction = onAction)
        }
    }
}

/** 호버하면 화살표와의 간격이 6px → 10px 로 벌어진다. */
@Composable
private fun ViewAllProjectsLink(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val gap by animateDpAsState(
        targetValue = if (isHovered) 10.dp else 6.dp,
        animationSpec = tween(200),
        label = "viewAllGap",
    )
    Row(
        modifier = Modifier.webClickable(interactionSource, role = Role.Button, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "프로젝트 모두 보기",
            style = PortfolioTextStyle.sans(size = 14.sp, color = PortfolioColors.BooltiAccent, weight = FontWeight.Medium),
        )
        Spacer(Modifier.width(gap))
        Icon(
            imageVector = LucideIcons.ArrowRight,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = PortfolioColors.BooltiAccent,
        )
    }
}

@Composable
private fun FeaturedProjectCard(
    project: FeaturedProject,
    onAction: (HomeAction) -> Unit,
) {
    val isCompact = PortfolioTheme.windowSize.isCompact

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(CardShape, Shadow(radius = 3.dp, color = Color.Black.copy(alpha = 0.06f), offset = DpOffset(0.dp, 1.dp)))
            .clip(CardShape)
            .background(PortfolioColors.White)
            .border(1.dp, PortfolioColors.Border, CardShape),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(project.accent),
        )
        Column(modifier = Modifier.padding(32.dp)) {
            ProjectTitle(project = project, modifier = Modifier.padding(bottom = 24.dp))

            if (isCompact) {
                Column(verticalArrangement = Arrangement.spacedBy(40.dp)) {
                    ProjectDetails(project = project, columns = 2, onAction = onAction)
                    PhoneMockups(project = project)
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
                    ProjectDetails(project = project, columns = 4, onAction = onAction, modifier = Modifier.weight(1f))
                    PhoneMockups(project = project, modifier = Modifier.width(200.dp))
                }
            }
        }
    }
}

@Composable
private fun ProjectTitle(
    project: FeaturedProject,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column {
            FlowRow(
                modifier = Modifier.padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = project.name,
                    style = PortfolioTextStyle.sans(size = 24.sp, color = PortfolioColors.Heading, weight = FontWeight.Bold),
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    project.techs.forEach { TechTag(it) }
                }
            }
            Text(
                text = project.description,
                style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Muted),
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = project.period,
                style = PortfolioTextStyle.mono(size = 12.sp, color = PortfolioColors.Subtle),
            )
            Text(
                text = project.duration,
                style = PortfolioTextStyle.sans(size = 11.sp, color = PortfolioColors.Faint),
            )
        }
    }
}

@Composable
private fun ProjectDetails(
    project: FeaturedProject,
    columns: Int,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        MetricsGrid(metrics = project.metrics, columns = columns, accent = project.accent)
        HorizontalDivider(
            modifier = Modifier.padding(top = 20.dp, bottom = 24.dp),
            thickness = 1.dp,
            color = PortfolioColors.Border,
        )

        Column(
            modifier = Modifier.padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            project.highlights.forEach { highlight ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val style = PortfolioTextStyle.sans(size = 13.sp, color = PortfolioColors.Muted, lineHeight = 1.6f)
                    Text(text = "–", style = style.copy(color = project.accent))
                    HighlightText(
                        highlight = highlight,
                        style = style,
                        onLinkClick = { onAction(HomeAction.ClickLink(it)) },
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            IconTextLink(
                text = "GitHub",
                icon = LucideIcons.Github,
                onClick = { onAction(HomeAction.ClickLink(project.githubUrl)) },
            )
            project.storeUrl?.let { url ->
                IconTextLink(
                    text = "Play 스토어",
                    icon = LucideIcons.ExternalLink,
                    onClick = { onAction(HomeAction.ClickLink(url)) },
                )
            }
        }
    }
}

/** 지표 그리드. 첫 칸을 뺀 모든 칸 왼쪽에 구분선이 있다 (디자인과 같음). */
@Composable
private fun MetricsGrid(
    metrics: List<Metric>,
    columns: Int,
    accent: Color,
) {
    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
        metrics.chunked(columns).forEachIndexed { rowIndex, row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEachIndexed { columnIndex, metric ->
                    val first = rowIndex == 0 && columnIndex == 0
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .drawBehind {
                                if (!first) {
                                    drawRect(PortfolioColors.Border, size = Size(1.dp.toPx(), size.height))
                                }
                            }
                            .padding(start = if (first) 0.dp else 16.dp),
                    ) {
                        Text(
                            text = metric.value,
                            style = PortfolioTextStyle.mono(size = 17.sp, color = accent, weight = FontWeight.Bold, lineHeight = 1.2f),
                        )
                        Text(
                            text = metric.label,
                            modifier = Modifier.padding(top = 4.dp),
                            style = PortfolioTextStyle.sans(size = 11.sp, color = PortfolioColors.Subtle),
                        )
                    }
                }
                repeat(columns - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun PhoneMockups(
    project: FeaturedProject,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        project.screenshots.forEach { screenshot ->
            PhoneMockup(screenshot = screenshot, accent = project.accent, modifier = Modifier.weight(1f))
        }
    }
}

/** 폰 화면 목업. 이미지가 없으면 점선 테두리와 설명만 보여준다. */
@Composable
private fun PhoneMockup(
    screenshot: Screenshot,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    val frame = modifier
        .aspectRatio(screenshot.aspectRatio)
        .dropShadow(PhoneShape, Shadow(radius = 20.dp, color = Color.Black.copy(alpha = 0.08f), offset = DpOffset(0.dp, 4.dp)))
        .clip(PhoneShape)
        .background(PortfolioColors.SurfaceAlt)
        .border(1.dp, PortfolioColors.Border, PhoneShape)
    screenshot.image?.let { image ->
        Image(
            painter = painterResource(image),
            contentDescription = screenshot.description,
            modifier = frame,
            contentScale = ContentScale.Crop,
        )
        return
    }
    Column(
        modifier = frame
            .drawBehind {
                val stroke = 2.dp.toPx()
                val inset = 1.dp.toPx() + stroke / 2
                drawRoundRect(
                    color = accent.copy(alpha = 0x40 / 255f),
                    topLeft = Offset(inset, inset),
                    size = Size(size.width - inset * 2, size.height - inset * 2),
                    cornerRadius = CornerRadius(19.dp.toPx() - stroke / 2),
                    style = Stroke(width = stroke, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()))),
                )
            },
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = LucideIcons.Image,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = accent.copy(alpha = 0x60 / 255f),
        )
        Text(
            text = screenshot.description,
            modifier = Modifier.padding(horizontal = 12.dp),
            style = PortfolioTextStyle.mono(size = 11.sp, color = accent.copy(alpha = 0x80 / 255f)),
            textAlign = TextAlign.Center,
        )
    }
}
