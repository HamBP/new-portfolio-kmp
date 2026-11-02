package me.algosketch.newportfolio.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** 문제 해결 스토리 */
@Immutable
data class Achievement(
    val title: String,
    val body: String,
)

/**
 * 프로젝트 화면의 프로젝트 하나.
 *
 * @property accent 목업 자리 점선 등 장식용 강조색
 * @property textAccent 흰 배경 위 글자용 강조색 (대비 4.5:1 이상)
 * @property borderAccent 문제 해결 스토리 왼쪽 선 색
 */
@Immutable
data class Project(
    val name: String,
    val period: String,
    val duration: String,
    val tag: String,
    val description: String,
    val techs: List<String>,
    val responsibilities: List<String>,
    val screenshots: List<Screenshot>,
    val achievements: List<Achievement>,
    val githubUrl: String,
    val storeUrl: String?,
    val accent: Color,
    val textAccent: Color,
    val borderAccent: Color,
)
