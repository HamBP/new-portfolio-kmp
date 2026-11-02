package me.algosketch.newportfolio.screen.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.data.Profile
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme
import me.algosketch.newportfolio.model.Strength
import kotlin.random.Random

@Composable
internal fun HeroSection(
    strengths: List<Strength>,
    modifier: Modifier = Modifier,
) {
    val windowSize = PortfolioTheme.windowSize

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = windowSize.height)
            .background(PortfolioColors.White),
        contentAlignment = Alignment.CenterStart,
    ) {
        PaperGrain(modifier = Modifier.matchParentSize())

        ContentContainer {
            Text(
                text = Profile.NAME,
                modifier = Modifier.padding(bottom = 12.dp),
                style = PortfolioTextStyle.sans(
                    size = windowSize.fluidSp(min = 48f, vw = 8f, max = 88f),
                    color = PortfolioColors.Heading,
                    weight = FontWeight.Bold,
                    lineHeight = 1f,
                    letterSpacing = (-3).sp,
                ),
            )
            Text(
                text = Profile.JOB_TITLE,
                modifier = Modifier.padding(bottom = 36.dp),
                style = PortfolioTextStyle.sans(
                    size = windowSize.fluidSp(min = 20f, vw = 3f, max = 30f),
                    color = PortfolioColors.Muted,
                    letterSpacing = (-0.5).sp,
                ),
            )
            Column(
                modifier = Modifier.widthIn(max = 600.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                strengths.forEach { StrengthRow(it) }
            }
        }

        BouncingArrow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )
    }
}

@Composable
private fun StrengthRow(strength: Strength) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "▸",
            modifier = Modifier.padding(top = 3.dp),
            style = PortfolioTextStyle.sans(size = 12.sp, color = PortfolioColors.Faint),
        )
        val descriptionStyle = PortfolioTextStyle.sans(size = 14.sp, color = PortfolioColors.Muted, lineHeight = 1.6f)
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = PortfolioColors.Heading)) {
                    append(strength.title)
                }
                append(" — ")
                append(strength.description)
            },
            style = descriptionStyle.copy(lineHeight = 1.6.em),
        )
    }
}

/** CSS `bounce` 키프레임: 2초 주기로 6px 내려갔다 올라온다. */
@Composable
private fun BouncingArrow(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "heroArrow")
    val offset = transition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "heroArrowOffset",
    )
    Icon(
        imageVector = LucideIcons.ArrowDown,
        contentDescription = null,
        modifier = modifier
            .graphicsLayer { translationY = offset.value * density }
            .size(16.dp),
        tint = PortfolioColors.Faint,
    )
}

private const val GrainTileSize = 250
/** 디자인의 `opacity: 0.045` × feTurbulence 알파 평균(약 0.5) */
private const val GrainOpacity = 0.0225f
private const val GrainLevels = 8

/**
 * 종이 질감 노이즈. 디자인의 SVG `feTurbulence` 대신, 무작위 회색 점을 찍은 250px 타일을
 * 한 번 만들어 반복한다.
 */
@Composable
private fun PaperGrain(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.drawWithCache {
            val tile = createGrainTile(density = this)
            val brush = ShaderBrush(ImageShader(tile, TileMode.Repeated, TileMode.Repeated))
            onDrawBehind { drawRect(brush) }
        },
    )
}

private fun createGrainTile(density: Density): ImageBitmap {
    val bitmap = ImageBitmap(GrainTileSize, GrainTileSize)
    val random = Random(seed = 42)
    val buckets = List(GrainLevels) { mutableListOf<Offset>() }
    for (y in 0 until GrainTileSize) {
        for (x in 0 until GrainTileSize) {
            buckets[random.nextInt(GrainLevels)] += Offset(x + 0.5f, y + 0.5f)
        }
    }
    CanvasDrawScope().draw(density, LayoutDirection.Ltr, Canvas(bitmap), Size(GrainTileSize.toFloat(), GrainTileSize.toFloat())) {
        buckets.forEachIndexed { level, points ->
            val gray = level / (GrainLevels - 1f)
            drawPoints(
                points = points,
                pointMode = PointMode.Points,
                color = Color(gray, gray, gray, GrainOpacity),
                strokeWidth = 1f,
            )
        }
    }
    return bitmap
}
