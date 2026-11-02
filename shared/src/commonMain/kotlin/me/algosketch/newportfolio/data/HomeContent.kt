package me.algosketch.newportfolio.data

import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.model.Contribution
import me.algosketch.newportfolio.model.ContributionTag
import me.algosketch.newportfolio.model.FeaturedProject
import me.algosketch.newportfolio.model.Highlight
import me.algosketch.newportfolio.model.Metric
import me.algosketch.newportfolio.model.Screenshot
import me.algosketch.newportfolio.model.Strength
import me.algosketch.newportfolio.model.TimelineItem

/** 홈 화면 문구. Figma Make 디자인의 내용을 옮기고, 자기소개(strengths)는 사용자가 준 문구로 바꿨다. */
object HomeContent {
    val strengths = listOf(
        Strength(
            title = "Android 앱의 배포와 운영을 경험한 개발자입니다",
            description = "2020년부터 개발 동아리에 참여하여 현재까지 6개의 앱을 스토어에 배포했으며, 현재는 불티와 쏙 두 앱을 운영 및 업데이트하고 있습니다.",
        ),
        Strength(
            title = "다양한 직무 경험을 바탕으로 협업합니다",
            description = "디자인, 프론트엔드, 백엔드를 모두 경험했습니다. 협업 과정에서 더 효율적으로 소통할 수 있으며, 담당 영역의 경계를 따지기보다 제가 해결할 수 있는 문제라면 먼저 확인하고 해결하려 합니다.",
        ),
        Strength(
            title = "AI와 기본기를 함께 활용하는 개발을 지향합니다",
            description = "AI를 적극적으로 활용해 생산성을 높이는 한편, 개발 원리를 이해하고 직접 구현하는 역량 역시 중요하게 생각합니다. 이를 위해 AI 중심으로 진행하는 프로젝트와 직접 코드를 작성하며 학습하는 프로젝트를 병행하고 있습니다.",
        ),
    )

    val featuredProjects = listOf(
        FeaturedProject(
            name = "불티",
            period = "2024.01 ~ 진행중",
            duration = "2년 3개월+",
            description = "인디 밴드를 위한 공연 주최 및 입장 서비스",
            techs = listOf("Compose", "MVVM", "Hilt", "Coroutine", "Firebase"),
            metrics = listOf(
                Metric("1,900명", "누적 회원"),
                Metric("2,500장", "티켓 판매"),
                Metric("1,100만원", "거래액"),
                Metric("33개", "Play 스토어 버전"),
            ),
            highlights = listOf(
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
                Screenshot("불티 홈 화면"),
                Screenshot("불티 티켓 화면"),
            ),
            githubUrl = "https://github.com/Nexters/Boolti",
            storeUrl = "https://play.google.com/store/apps/details?id=com.nexters.boolti&hl=ko",
            accent = PortfolioColors.BooltiAccent,
        ),
    )

    val timeline = listOf(
        TimelineItem.Career(
            company = "(주)지넥스",
            role = "풀스택 개발자",
            period = "2024.12 — 2025.10",
            duration = "10개월",
            description = "솔루션 기반의 작은 기업. 인프라 구축부터 웹, 앱, 서버 개발 등 다양한 업무 담당",
            details = listOf(
                "민원 접수 시스템 — 모바일/PC 반응형 웹 + PWA 기반 웹 푸시 (React, Spring)",
                "팩트박스 — BLE 기반 DTG API 하드웨어 연결, 일반 사용자용 앱 기획·디자인·개발 (Android, Flutter)",
                "ERP / FMS / RMS — 고객사 요청 대응 및 유지 보수, 신규 디자인 템플릿으로 마이그레이션",
                "신규 회사 홈페이지 개발, SEO 적용을 위해 Next.js로 마이그레이션",
            ),
            techs = listOf("Android", "Flutter", "React", "Next.js", "Spring"),
        ),
        TimelineItem.Activity(
            name = "Nexters 27기",
            organization = "IT 연합 동아리",
            period = "2025.07 — 2025.08",
            details = listOf(
                "AI 큐레이팅 뉴스레터 서비스 \"쏙\" 프로젝트 참여",
                "Manifest Android Interview 책 스터디 운영",
            ),
        ),
        TimelineItem.Activity(
            name = "부스트캠프 웹·모바일 8기",
            organization = "NAVER Connect",
            period = "2023.07 — 2023.12",
            details = listOf(
                "멤버십 — 최종 프로젝트 팀장, 문서 및 일정 관리",
                "9기 과정 수료생 연사자로 발표 / 10기 수료생의 접근법 세션 참여",
                "챌린지 — CS 및 Android 지식을 Kotlin으로 직접 구현하며 학습",
            ),
        ),
        TimelineItem.Activity(
            name = "Nexters 24기",
            organization = "IT 연합 동아리",
            period = "2024.01 — 2024.02",
            award = "24기 프로젝트 중 1위 (10팀 중)",
            details = listOf("공연 티켓팅&입장 서비스 \"불티\" 프로젝트 참여"),
        ),
        TimelineItem.Activity(
            name = "Nexters 23기",
            organization = "IT 연합 동아리",
            period = "2023.07 — 2023.08",
            details = listOf("링크 저장 서비스 \"Linkllet\" 프로젝트 참여"),
        ),
        TimelineItem.Activity(
            name = "SW마에스트로 12기",
            organization = "과학기술정보통신부",
            period = "2021.04 — 2021.11",
        ),
        TimelineItem.Activity(
            name = "INU 앱센터",
            organization = "교내 앱 개발 동아리 · 회장",
            period = "2020.09 — 2023.02",
            award = "전공 취업 동아리 최우수상",
            details = listOf(
                "Android 파트장 — Android 강의식 스터디 개설",
                "4개의 프로젝트에서 Android 파트로 참여",
            ),
        ),
        TimelineItem.Education(
            school = "인천대학교",
            major = "컴퓨터공학부",
            period = "2017.03 — 2024.08",
            grade = "3.4 / 4.5",
        ),
    )

    val blogPosts = listOf(
        Contribution(
            title = "Compose Navigation 내부 코드 분석 및 구현",
            subtitle = "기술 블로그 작성 및 내부 로직을 기반으로 내비게이션 직접 구현",
            date = "2024.05",
            url = "https://algosketch.tistory.com/190",
            tag = ContributionTag.Blog,
        ),
        Contribution(
            title = "Navigation과 이벤트의 충돌 문제 해결",
            subtitle = "Jetpack Navigation 2.x 버전의 뒤로가기 + 화면전환 충돌 분석 및 해결",
            date = "2024.01",
            url = "https://algosketch.tistory.com/189",
            tag = ContributionTag.Blog,
        ),
    )

    val openSourceContributions = listOf(
        Contribution(
            title = "[북마크] 일괄 삭제 기능 추가 #311",
            subtitle = "드로이드 나이츠",
            date = "2024.06",
            url = "https://github.com/droidknights/DroidKnightsApp/pull/311",
            tag = ContributionTag.OpenSource,
        ),
        Contribution(
            title = "영구적으로 거부한 권한 확인 코드 오류 보고 #14",
            subtitle = "Manifest Android Interview 한국어",
            date = "2025.08",
            url = "https://github.com/skydoves/manifest-android-interview/issues/14",
            tag = ContributionTag.TechBook,
        ),
    )
}
