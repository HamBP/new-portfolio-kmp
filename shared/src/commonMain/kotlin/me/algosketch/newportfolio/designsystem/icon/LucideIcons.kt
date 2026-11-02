package me.algosketch.newportfolio.designsystem.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/**
 * Figma 디자인이 쓰는 lucide 아이콘(lucide-static 0.468.0, ISC 라이선스).
 * 24x24 뷰박스, 선 두께 2, 둥근 끝. 색은 `Icon(tint = ...)` 로 정한다.
 * `rect`·`circle` 요소는 같은 모양의 path 로 옮겼다.
 */
object LucideIcons {
    val Github: ImageVector by lazy {
        lucide(
            "Github",
            "M15 22v-4a4.8 4.8 0 0 0-1-3.5c3 0 6-2 6-5.5.08-1.25-.27-2.48-1-3.5.28-1.15.28-2.35 0-3.5 0 0-1 0-3 1.5-2.64-.5-5.36-.5-8 0C6 2 5 2 5 2c-.3 1.15-.3 2.35 0 3.5A5.403 5.403 0 0 0 4 9c0 3.5 3 5.5 6 5.5-.39.49-.68 1.05-.85 1.65-.17.6-.22 1.23-.15 1.85v4",
            "M9 18c-4.51 2-5-2-7-2",
        )
    }

    val Linkedin: ImageVector by lazy {
        lucide(
            "Linkedin",
            "M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z",
            rect(x = 2f, y = 9f, width = 4f, height = 12f),
            circle(cx = 4f, cy = 4f, r = 2f),
        )
    }

    val BookOpen: ImageVector by lazy {
        lucide(
            "BookOpen",
            "M12 7v14",
            "M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z",
        )
    }

    val Mail: ImageVector by lazy {
        lucide(
            "Mail",
            rect(x = 2f, y = 4f, width = 20f, height = 16f, rx = 2f),
            "m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7",
        )
    }

    val ArrowDown: ImageVector by lazy {
        lucide("ArrowDown", "M12 5v14", "m19 12-7 7-7-7")
    }

    val ArrowRight: ImageVector by lazy {
        lucide("ArrowRight", "M5 12h14", "m12 5 7 7-7 7")
    }

    val ExternalLink: ImageVector by lazy {
        lucide(
            "ExternalLink",
            "M15 3h6v6",
            "M10 14 21 3",
            "M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6",
        )
    }

    val Image: ImageVector by lazy {
        lucide(
            "Image",
            rect(x = 3f, y = 3f, width = 18f, height = 18f, rx = 2f),
            circle(cx = 9f, cy = 9f, r = 2f),
            "m21 15-3.086-3.086a2 2 0 0 0-2.828 0L6 21",
        )
    }

    val Trophy: ImageVector by lazy {
        lucide(
            "Trophy",
            "M6 9H4.5a2.5 2.5 0 0 1 0-5H6",
            "M18 9h1.5a2.5 2.5 0 0 0 0-5H18",
            "M4 22h16",
            "M10 14.66V17c0 .55-.47.98-.97 1.21C7.85 18.75 7 20.24 7 22",
            "M14 14.66V17c0 .55.47.98.97 1.21C16.15 18.75 17 20.24 17 22",
            "M18 2H6v7a6 6 0 0 0 12 0V2Z",
        )
    }
}

private fun lucide(name: String, vararg paths: String): ImageVector =
    ImageVector.Builder(
        name = "Lucide.$name",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        paths.forEach { pathData ->
            addPath(
                pathData = addPathNodes(pathData),
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
            )
        }
    }.build()

private fun rect(x: Float, y: Float, width: Float, height: Float, rx: Float = 0f): String {
    if (rx == 0f) return "M$x ${y}h${width}v${height}h${-width}z"
    val w = width - 2 * rx
    val h = height - 2 * rx
    return "M${x + rx} ${y}h${w}a$rx $rx 0 0 1 $rx ${rx}v${h}a$rx $rx 0 0 1 ${-rx} ${rx}" +
        "h${-w}a$rx $rx 0 0 1 ${-rx} ${-rx}v${-h}a$rx $rx 0 0 1 $rx ${-rx}z"
}

private fun circle(cx: Float, cy: Float, r: Float): String =
    "M${cx - r} ${cy}a$r $r 0 1 0 ${2 * r} 0a$r $r 0 1 0 ${-2 * r} 0z"
