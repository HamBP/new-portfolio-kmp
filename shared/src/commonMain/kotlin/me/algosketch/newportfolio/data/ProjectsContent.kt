package me.algosketch.newportfolio.data

import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.model.Achievement
import me.algosketch.newportfolio.model.Project
import me.algosketch.newportfolio.model.Screenshot

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
                "로그인, 푸시 알림, 동적 링크, 선물, 토스 페이먼츠 연동 담당",
                "스크롤 가능한 앱바를 포함한 UI 인터랙션 구현",
                "Navigation 이벤트 동시 발생에 대한 방어 로직 작성",
            ),
            screenshots = listOf(
                Screenshot("불티 홈 화면"),
                Screenshot("불티 공연 목록"),
                Screenshot("불티 티켓 상세"),
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
            githubUrl = Profile.GITHUB_URL,
            storeUrl = "https://play.google.com",
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
                "홈 카드 인터랙션, CI/CD, GA 로깅 담당",
                "Firebase A/B Testing 적용",
                "AI 및 상호 코드 리뷰",
            ),
            screenshots = listOf(
                Screenshot("쏙 홈 화면"),
                Screenshot("쏙 뉴스레터 상세"),
                Screenshot("쏙 큐레이팅 화면"),
            ),
            achievements = listOf(
                Achievement(
                    title = "테스트 및 배포 과정의 간소화를 위해 자동 배포 시스템을 구축했습니다",
                    body = "PR merge 시 Firebase App Distribution에 자동 배포. Fastlane과 GitHub Actions를 이용해 Play 스토어 배포를 자동화했으며, Discord 봇 명령어로도 배포할 수 있도록 구성해 수동 빌드 과정을 완전히 제거했습니다.",
                ),
            ),
            githubUrl = Profile.GITHUB_URL,
            storeUrl = "https://play.google.com",
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
                "Kotest와 GitHub Actions를 이용한 CI/CD 구축",
                "Light/Dark theme 대응 및 디자인 시스템 적용",
                "멀티 모듈의 gradle 중복 코드 제거를 위해 gradle 플러그인 개발",
                "HLS 구조를 파악하여 ExoPlayer의 오작동 문제 해결",
            ),
            screenshots = listOf(
                Screenshot("Catchy Tape 홈"),
                Screenshot("Catchy Tape 플레이어"),
                Screenshot("Catchy Tape 업로드"),
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
        Project(
            name = "Gradle Convention Plugin",
            period = "2024.03",
            duration = "1개월",
            tag = "개인 프로젝트",
            description = "멀티 모듈 프로젝트의 Gradle 중복 설정을 제거하는 컨벤션 플러그인",
            techs = listOf("Gradle", "Kotlin DSL", "Android"),
            responsibilities = listOf(
                "공통 Android 설정을 플러그인으로 추출하여 단일 지점에서 관리",
                "각 모듈은 필요한 플러그인만 선언해 설정 의도를 명확하게 표현",
                "신규 모듈 추가 시 보일러플레이트 없이 즉시 사용 가능한 구조 설계",
            ),
            screenshots = emptyList(),
            achievements = listOf(
                Achievement(
                    title = "Gradle 설정 중복으로 인한 유지보수 비용을 줄였습니다",
                    body = "모듈이 늘어날수록 compileSdk, jvmTarget, kotlinOptions 등 공통 설정이 각 build.gradle에 흩어져 한 곳을 수정할 때 전체 모듈을 돌아야 하는 문제가 있었습니다. Convention Plugin으로 추출하면서 변경이 필요한 지점이 단 하나로 줄어들었고, 설정 누락으로 인한 빌드 오류도 사라졌습니다.",
                ),
            ),
            githubUrl = Profile.GITHUB_URL,
            storeUrl = null,
            accent = PortfolioColors.SkyAccent,
            textAccent = PortfolioColors.SkyAccentText,
            borderAccent = PortfolioColors.SkyAccent.copy(alpha = 0x40 / 255f),
        ),
    )
}

