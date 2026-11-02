package me.algosketch.newportfolio.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

/** 히어로 섹션의 강점 한 줄 */
@Immutable
data class Strength(
    val title: String,
    val description: String,
)

@Immutable
data class Metric(
    val value: String,
    val label: String,
)

/** 화면 목업. [image] 가 없으면 [description] 을 적은 빈 자리를 보여준다. 이미지는 9:16 스토어 스크린샷. */
@Immutable
data class Screenshot(
    val description: String,
    val image: DrawableResource? = null,
)

/** 홈의 대표 사이드 프로젝트 카드 */
@Immutable
data class FeaturedProject(
    val name: String,
    val period: String,
    val duration: String,
    val description: String,
    val techs: List<String>,
    val metrics: List<Metric>,
    val highlights: List<Highlight>,
    val screenshots: List<Screenshot>,
    val githubUrl: String,
    val storeUrl: String?,
    val accent: Color,
)

enum class TimelineType { Career, Activity, Education }

/** 경력·활동 타임라인 칩. [type] 이 null 이면 전체 */
enum class TimelineFilter(val label: String, val type: TimelineType?) {
    All("전체", null),
    Career("경력", TimelineType.Career),
    Activity("활동", TimelineType.Activity),
    Education("학력", TimelineType.Education),
    ;

    fun matches(item: TimelineItem): Boolean = type == null || type == item.type
}

@Immutable
sealed interface TimelineItem {
    val type: TimelineType
    val period: String

    @Immutable
    data class Career(
        val company: String,
        val role: String,
        override val period: String,
        val duration: String,
        val description: String,
        val details: List<String>,
        val techs: List<String>,
    ) : TimelineItem {
        override val type get() = TimelineType.Career
    }

    @Immutable
    data class Activity(
        val name: String,
        val organization: String,
        override val period: String,
        val award: String? = null,
        val details: List<String> = emptyList(),
    ) : TimelineItem {
        override val type get() = TimelineType.Activity
    }

    @Immutable
    data class Education(
        val school: String,
        val major: String,
        override val period: String,
        val grade: String,
    ) : TimelineItem {
        override val type get() = TimelineType.Education
    }
}

/** 그 외 활동 카드의 분류 태그와 색 */
enum class ContributionTag(
    val label: String,
    val background: Color,
    val border: Color,
    val text: Color,
) {
    Blog("블로그", Color(0xFFF0FDF4), Color(0xFFBBF7D0), Color(0xFF15803D)),
    OpenSource("오픈소스", Color(0xFFEFF6FF), Color(0xFFBFDBFE), Color(0xFF1D4ED8)),
    TechBook("기술서적", Color(0xFFFAF5FF), Color(0xFFE9D5FF), Color(0xFF7E22CE)),
}

/** 블로그 글·오픈소스 기여 카드. [subtitle] 은 글 요약 또는 프로젝트 이름 */
@Immutable
data class Contribution(
    val title: String,
    val subtitle: String,
    val date: String,
    val url: String,
    val tag: ContributionTag,
)
