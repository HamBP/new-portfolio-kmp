package me.algosketch.newportfolio.data

import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.model.Achievement
import me.algosketch.newportfolio.model.Highlight
import me.algosketch.newportfolio.model.Project
import me.algosketch.newportfolio.model.Screenshot
import newportfolio.shared.generated.resources.Res
import newportfolio.shared.generated.resources.boolti_payment
import newportfolio.shared.generated.resources.boolti_ticket
import newportfolio.shared.generated.resources.catchytape_home
import newportfolio.shared.generated.resources.catchytape_player
import newportfolio.shared.generated.resources.catchytape_playlist
import newportfolio.shared.generated.resources.soak_bookmark
import newportfolio.shared.generated.resources.soak_explore
import newportfolio.shared.generated.resources.soak_home

/** Catchy Tape 캡처(360×760) 비율 */
private const val CATCHY_TAPE_ASPECT_RATIO = 360f / 760f

/** 프로젝트 화면 문구. Figma Make 디자인의 내용을 그대로 옮겼다. */
object ProjectsContent {
    val projects = listOf(
        Project(
            name = "불티",
            period = "2024.01 ~",
            duration = "2년 3개월+",
            tag = "Nexters 24기",
            description = "인디 밴드를 위한 공연 주최 및 입장 서비스",
            techs = listOf("Compose", "MVVM", "Hilt", "Coroutine", "Firebase"),
            responsibilities = listOf(
                Highlight("공연 탐색·예매·티켓·선물·로그인 등 Android 앱의 주요 기능 개발 및 유지보수"),
                Highlight("누적 회원 수 1900명, 티켓 판매 수 2500장, 거래액 1100만 원 달성"),
                Highlight("2024년 출시 이후 30회 이상 버전 업데이트하며 지속적으로 서비스 운영"),
                Highlight("일부 화면을 WebView 기반으로 전환하고, WebView와 네이티브 간 통신 구현"),
                Highlight("사용자 행동 분석을 위해 Mixpanel 이벤트와 프로퍼티를 설계하고 주요 화면 및 사용자 행동 로그를 수집"),
                Highlight(
                    text = "Navigation 이벤트 동시 발생에 대한 방어 로직 작성",
                    link = Highlight.Link(
                        text = "Navigation 이벤트 동시 발생에 대한 방어 로직",
                        url = "https://algosketch.tistory.com/189",
                    ),
                ),
            ),
            screenshots = listOf(
                Screenshot("불티 티켓 화면", Res.drawable.boolti_ticket),
                Screenshot("불티 예매 화면", Res.drawable.boolti_payment),
                Screenshot("불티 공연 목록"),
            ),
            achievements = listOf(
                Achievement(
                    title = "ViewModel에서 예외를 처리하고 Crashlytics로 로그를 수집합니다",
                    body = "앱 안정성에서 크래시가 가장 큰 문제라고 판단했습니다. ViewModel 레이어에서 예외를 처리하여 크래시를 방어하고, Crashlytics에 수집된 예외 로그를 폴링해 Discord 웹훅으로 전송하여 즉각적으로 예외 상황을 인지하도록 구성했습니다.",
                ),
                Achievement(
                    title = "Navigation과 이벤트의 충돌 문제를 해결했습니다",
                    body = "뒤로 가기와 화면 이동 이벤트가 동시에 발생할 경우 발생하는 크래시를 분석하고, 화면 전환을 별도의 이벤트로 관리하여 순차적으로 처리될 수 있도록 수정했습니다.",
                ),
            ),
            githubUrl = "https://github.com/Nexters/Boolti",
            storeUrl = "https://play.google.com/store/apps/details?id=com.nexters.boolti&hl=ko",
            accent = PortfolioColors.BooltiAccent,
            textAccent = PortfolioColors.BooltiAccentText,
            borderAccent = PortfolioColors.BooltiAccent.copy(alpha = 0x50 / 255f),
        ),
        Project(
            name = "쏙",
            period = "2025.07 ~",
            duration = "9개월+",
            tag = "Nexters 27기",
            description = "AI가 요약하고 큐레이팅해 주는 데일리 뉴스레터 서비스",
            techs = listOf("Koin", "CI/CD", "Fastlane", "Firebase A/B Test"),
            responsibilities = listOf(
                Highlight("탐색·페이지네이션·정렬·홈 인터랙션 등 주요 기능 개발"),
                Highlight("Firebase App Distribution·Fastlane·GitHub Actions 기반 CI/CD 및 Play Store 배포 자동화"),
                Highlight("Firebase Remote Config 기반 A/B Testing 및 Analytics 로그 설계"),
            ),
            screenshots = listOf(
                Screenshot("쏙 추천 화면", Res.drawable.soak_home, Screenshot.PHONE_ASPECT_RATIO),
                Screenshot("쏙 탐색 화면", Res.drawable.soak_explore, Screenshot.PHONE_ASPECT_RATIO),
                Screenshot("쏙 보관함 화면", Res.drawable.soak_bookmark, Screenshot.PHONE_ASPECT_RATIO),
            ),
            achievements = listOf(
                Achievement(
                    title = "테스트 및 배포 과정의 간소화를 위해 자동 배포 시스템을 구축했습니다",
                    body = "PR merge 시 Firebase App Distribution에 자동 배포. Fastlane과 GitHub Actions를 이용해 Play 스토어 배포를 자동화했으며, Discord 봇 명령어로도 배포할 수 있도록 구성해 수동 빌드 과정을 완전히 제거했습니다.",
                ),
            ),
            githubUrl = "https://github.com/fairy-band/NewsLetter-Android",
            storeUrl = "https://play.google.com/store/apps/details?id=com.fairyband.soak&hl=ko",
            accent = PortfolioColors.Body,
            textAccent = PortfolioColors.Body,
            borderAccent = PortfolioColors.Body.copy(alpha = 0x50 / 255f),
        ),
        Project(
            name = "Catchy Tape",
            period = "2023.11 — 2024.01",
            duration = "2개월",
            tag = "팀 프로젝트",
            description = "노래 공유 및 음악 스트리밍 서비스",
            techs = listOf("View", "MVVM", "Hilt", "Coroutine", "Media3", "ExoPlayer", "CI/CD", "Kotest"),
            responsibilities = listOf(
                Highlight("Kotest와 GitHub Actions를 이용한 CI/CD 구축"),
                Highlight("Light/Dark theme 대응 및 디자인 시스템 적용"),
                Highlight("멀티 모듈의 gradle 중복 코드 제거를 위해 gradle 플러그인 개발"),
                Highlight("HLS 구조를 파악하여 ExoPlayer의 오작동 문제 해결"),
                Highlight("6주간 약 90개의 PR과 700개의 리뷰 코멘트 작성"),
            ),
            screenshots = listOf(
                Screenshot("Catchy Tape 홈", Res.drawable.catchytape_home, CATCHY_TAPE_ASPECT_RATIO),
                Screenshot("Catchy Tape 플레이어", Res.drawable.catchytape_player, CATCHY_TAPE_ASPECT_RATIO),
                Screenshot("Catchy Tape 재생목록", Res.drawable.catchytape_playlist, CATCHY_TAPE_ASPECT_RATIO),
            ),
            achievements = listOf(
                Achievement(
                    title = "일정 관리를 통해 개발 일정 정확도를 55%에서 86%까지 개선했습니다",
                    body = "PM 역할을 맡으면서 매주 플래닝 포커로 개발 시간을 산정하고, 개발 후 소요 시간을 기록했습니다. 스프린트 종료 후 팀 회고를 통해 누락 요소를 식별하고 다음 스프린트에 반영했습니다.",
                ),
            ),
            githubUrl = Profile.GITHUB_URL,
            storeUrl = null,
            accent = PortfolioColors.Muted,
            textAccent = PortfolioColors.Muted,
            borderAccent = PortfolioColors.Muted.copy(alpha = 0x50 / 255f),
        ),
    )
}

