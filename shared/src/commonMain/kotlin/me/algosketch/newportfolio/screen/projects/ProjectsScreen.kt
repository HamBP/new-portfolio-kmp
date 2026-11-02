package me.algosketch.newportfolio.screen.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.HighlightText
import me.algosketch.newportfolio.designsystem.component.IconTextLink
import me.algosketch.newportfolio.designsystem.component.SectionEyebrow
import me.algosketch.newportfolio.designsystem.component.TechTag
import me.algosketch.newportfolio.designsystem.component.TechTagSize
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioDimens
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.model.Achievement
import me.algosketch.newportfolio.model.Project
import me.algosketch.newportfolio.model.Screenshot

private val ScreenShape = RoundedCornerShape(14.dp)
private val TagShape = RoundedCornerShape(4.dp)

/** 프로젝트 화면. ViewModel 과 이벤트 처리를 맡고, 그리기는 [ProjectsScreenContent] 에 맡긴다. */
@Composable
fun ProjectsScreen(
    viewModel: ProjectsViewModel = viewModel { ProjectsViewModel() },
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                is ProjectsEvent.OpenUrl -> uriHandler.openUri(event.url)
            }
        }
    }

    ProjectsScreenContent(uiState = uiState, onAction = viewModel::onAction)
}

@Composable
private fun ProjectsScreenContent(
    uiState: ProjectsUiState,
    onAction: (ProjectsAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PortfolioColors.White)
            .padding(top = PortfolioDimens.NavigationHeight),
    ) {
        PageHeader()
        uiState.projects.forEach { project ->
            ProjectArticle(project = project, onAction = onAction)
            HorizontalDivider(thickness = 1.dp, color = PortfolioColors.Border)
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    SectionEyebrow(text = text, color = PortfolioColors.Muted, modifier = Modifier.padding(bottom = 20.dp))
}

@Composable
private fun PageHeader() {
    ContentContainer(modifier = Modifier.padding(top = 72.dp, bottom = 64.dp)) {
        SectionLabel("Side Projects")
        Text(
            text = "프로젝트",
            style = PortfolioTextStyle.sans(
                size = PortfolioTheme.windowSize.fluidSp(min = 36f, vw = 6f, max = 60f),
                color = PortfolioColors.Heading,
                weight = FontWeight.Bold,
                lineHeight = 1f,
                letterSpacing = (-2).sp,
            ),
        )
    }
    HorizontalDivider(thickness = 1.dp, color = PortfolioColors.Border)
}

@Composable
private fun ProjectArticle(
    project: Project,
    onAction: (ProjectsAction) -> Unit,
) {
    ContentContainer(modifier = Modifier.padding(vertical = 80.dp)) {
        ProjectIdentity(project = project, modifier = Modifier.padding(bottom = 56.dp))

        if (project.screenshots.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                project.screenshots.forEachIndexed { index, screenshot ->
                    ScreenMockup(
                        screenshot = screenshot,
                        accent = project.accent,
                        modifier = Modifier
                            .weight(1f)
                            .offset(y = if (index == 1) 16.dp else 0.dp),
                    )
                }
            }
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                project.techs.forEach { TechTag(it, size = TechTagSize.Medium) }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                IconTextLink(
                    text = "GitHub",
                    icon = LucideIcons.Github,
                    color = PortfolioColors.Secondary,
                    onClick = { onAction(ProjectsAction.ClickLink(project.githubUrl)) },
                )
                project.storeUrl?.let { url ->
                    IconTextLink(
                        text = "Play 스토어",
                        icon = LucideIcons.ExternalLink,
                        color = PortfolioColors.Secondary,
                        onClick = { onAction(ProjectsAction.ClickLink(url)) },
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(bottom = 52.dp)) {
            SectionLabel("담당 업무")
            Responsibilities(project, onAction)
        }

        Column {
            SectionLabel("문제 해결 스토리")
            Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                project.achievements.forEachIndexed { index, achievement ->
                    AchievementStory(
                        number = index + 1,
                        achievement = achievement,
                        textAccent = project.textAccent,
                        borderAccent = project.borderAccent,
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectIdentity(
    project: Project,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = project.name,
                style = PortfolioTextStyle.sans(
                    size = PortfolioTheme.windowSize.fluidSp(min = 30f, vw = 5f, max = 46f),
                    color = PortfolioColors.Heading,
                    weight = FontWeight.Bold,
                    lineHeight = 1f,
                    letterSpacing = (-1.5).sp,
                ),
            )
            Text(
                text = project.tag,
                modifier = Modifier
                    .background(PortfolioColors.TagBackground, TagShape)
                    .padding(horizontal = 10.dp, vertical = 3.dp),
                style = PortfolioTextStyle.sans(size = 12.sp, color = PortfolioColors.Muted),
            )
            // CSS `margin-left: auto`: 남은 폭을 채워 기간을 오른쪽 끝에 붙인다.
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = project.period,
                    style = PortfolioTextStyle.mono(size = 13.sp, color = project.textAccent, weight = FontWeight.SemiBold),
                )
            }
        }
        Text(
            text = project.description,
            style = PortfolioTextStyle.sans(size = 17.sp, color = PortfolioColors.Body, lineHeight = 1.7f),
        )
    }
}

/** 9:19 화면 자리. 아직 이미지가 없어 아이콘과 설명만 보여준다. */
@Composable
private fun ScreenMockup(
    screenshot: Screenshot,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .aspectRatio(9f / 19f)
            .dropShadow(ScreenShape, Shadow(radius = 20.dp, color = Color.Black.copy(alpha = 0.07f), offset = DpOffset(0.dp, 4.dp)))
            .clip(ScreenShape)
            .background(PortfolioColors.SurfaceAlt)
            .border(1.dp, PortfolioColors.Border, ScreenShape),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .drawBehind {
                    val stroke = 2.dp.toPx()
                    drawCircle(
                        color = accent.copy(alpha = 0x50 / 255f),
                        radius = (size.minDimension - stroke) / 2,
                        style = Stroke(width = stroke, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 3.dp.toPx()))),
                    )
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = LucideIcons.Image,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = PortfolioColors.Muted,
            )
        }
        Text(
            text = screenshot.description,
            modifier = Modifier.padding(horizontal = 10.dp),
            style = PortfolioTextStyle.mono(size = 11.sp, color = PortfolioColors.Muted, lineHeight = 1.4f),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Responsibilities(
    project: Project,
    onAction: (ProjectsAction) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        project.responsibilities.forEach { item ->
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "—",
                    modifier = Modifier.padding(top = 2.dp),
                    style = PortfolioTextStyle.mono(size = 13.sp, color = project.textAccent, lineHeight = 1.7f),
                )
                HighlightText(
                    highlight = item,
                    style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Body, lineHeight = 1.7f),
                    onLinkClick = { onAction(ProjectsAction.ClickLink(it)) },
                )
            }
        }
    }
}

@Composable
private fun AchievementStory(
    number: Int,
    achievement: Achievement,
    textAccent: Color,
    borderAccent: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRect(borderAccent, topLeft = Offset.Zero, size = Size(2.dp.toPx(), size.height))
            }
            .padding(start = 22.dp),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = number.toString().padStart(2, '0'),
                modifier = Modifier.padding(top = 3.dp),
                style = PortfolioTextStyle.mono(size = 12.sp, color = textAccent, weight = FontWeight.Bold),
            )
            Text(
                text = achievement.title,
                style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Heading, weight = FontWeight.SemiBold),
            )
        }
        Text(
            text = achievement.body,
            modifier = Modifier.padding(start = 24.dp),
            style = PortfolioTextStyle.sans(size = 15.sp, color = PortfolioColors.Secondary, lineHeight = 1.9f),
        )
    }
}
