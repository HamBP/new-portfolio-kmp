# New Portfolio 명세

Android 개발자 송준영의 포트폴리오 웹사이트. Figma Make 로 만든 React 디자인을 Kotlin/Wasm + Compose Multiplatform(Web)으로 구현한다.

- 디자인 원본: [Figma Make — Recreate Portfolio Site](https://www.figma.com/make/G2rFE0jBl7snSyl1wjWCp3/Recreate-Portfolio-Site)
- 사용자 프롬프트 원문: [PROMPTS.md](PROMPTS.md)
- 이 문서는 요구사항이 추가되거나 결정사항이 생길 때마다 갱신한다. (맨 아래 "결정 기록" 참고)

---

## 1. 요구사항 (사용자 요청 정리)

| # | 요구사항 | 구현 위치 |
|---|---|---|
| R1 | Figma Make 의 포트폴리오를 Kotlin/Wasm 으로 구현한다. | `shared/src/commonMain`, `webApp` |
| R2 | 요청 내용을 기능·세부 구현사항으로 정리한 md 파일을 관리하고, 결정사항이 생길 때마다 갱신한다. 프롬프트는 전용 md 로 관리한다. | `docs/SPEC.md`, `docs/PROMPTS.md`, `CLAUDE.md` |
| R3 | 작업은 자동으로 커밋한다. 커밋은 작은 단위로 나누고, 문서·스타일·리팩토링·기능 수정·기능 추가를 서로 다른 커밋으로 분리한다. | `CLAUDE.md`(커밋 규칙) |
| R4 | 작업 단위마다 브랜치와 PR 을 만든다. 기본 작업 브랜치는 `develop`, 작업 브랜치는 `feature/{번호}-{작업내용}`. 병합 전에 "머지할까요?" 라고 묻는다. 연속된 비슷한 요청은 기존 브랜치·PR 에서 이어서 작업한다. | `CLAUDE.md`(브랜치·PR 규칙) |

---

## 2. 페이지 구성 (Figma Make 기준)

라우팅은 해시 방식이다. 페이지를 바꾸면 스크롤을 맨 위로 되돌린다.

| 경로 | 페이지 | 구성 |
|---|---|---|
| `/#/` | 홈 | HeroSection → FeaturedProjects → CareerTimeline → OtherActivities |
| `/#/projects` | 프로젝트 | 페이지 헤더 + 프로젝트 목록(불티, 쏙, Catchy Tape, Gradle Convention Plugin) |

- **공통**: 상단 고정 Navigation(홈·프로젝트 탭, GitHub·LinkedIn·블로그 아이콘, 이메일), 하단 Footer.
  - Navigation 은 스크롤 20px 를 넘으면 반투명 흰 배경 + 하단 구분선으로 바뀐다.
- **코드 위치**: 라우팅 `navigation/` (`Route`, `PortfolioNavigator`, 해시 접근은 `BrowserLocation` expect/actual), 앱 뼈대 `app/` (`PortfolioApp`, `NavigationBar`, `Footer`), 화면 `screen/{home,projects}/`.
  - `PortfolioNavigator.navigate` 는 해시만 바꾸고 `hashchange` 이벤트로 화면을 바꾼다. 브라우저 뒤로 가기·주소 직접 입력도 같은 흐름.
  - 페이지 전체가 하나의 `verticalScroll` 이고 그 위에 Navigation 이 겹쳐 고정된다. 페이지 내용은 `SelectionContainer` 로 감싸 텍스트를 복사할 수 있다.
- **HeroSection**: 화면 높이 가득, 이름·직무·강점 3개, 종이 질감 노이즈 배경, 아래쪽 바운스 화살표.
- **FeaturedProjects**: 대표 프로젝트 카드(지표 4개, 하이라이트, 링크, 폰 목업 이미지 2장). "프로젝트 모두 보기" → `/projects`.
- **ProjectsPage**: 프로젝트별 소개, 화면 목업 3장(가운데 장은 16px 아래로), 기술 태그, 담당 업무, 문제 해결 스토리.

### 2.1 코드 구조 (이슈 #1)

[Boolti](https://github.com/Nexters/Boolti) 구조를 참고하되 **싱글 모듈**(`shared` 의 `commonMain`)에 패키지로 나눈다.

| 패키지 | 내용 |
|---|---|
| `designsystem/` | 테마·토큰·아이콘·공통 컴포넌트 (§3.1) |
| `navigation/` | 해시 라우팅 |
| `app/` | 앱 뼈대(`PortfolioApp`), `NavigationBar`, `Footer` |
| `model/` | 화면에 쓰는 데이터 모델 |
| `data/` | 문구 데이터(`Profile`, `HomeContent`, …). 서버가 없어 정적 객체로 둔다 |
| `screen/{화면}/` | `{화면}Contract.kt`(UiState·Action·Event), `{화면}ViewModel.kt`, `{화면}Screen.kt`, 섹션 컴포저블 |

**MVI** ([Boolti PR #582](https://github.com/Nexters/Boolti/pull/582) 방식)
- ViewModel 의 공개 멤버는 `uiState: StateFlow<UiState>`, `event: Flow<Event>`(`Channel` 기반 1회성), `onAction(Action)` 뿐이다.
- `{화면}Screen` 은 ViewModel 을 받아 상태를 구독하고 Event(화면 이동, 링크 열기)를 처리한다. 실제 그리기는 상태와 `onAction` 만 받는 `{화면}ScreenContent` 가 맡는다.
- 사용자 입력은 모두 Action 으로 보낸다. 상태가 바뀌는 입력(타임라인 칩)은 UiState 를, 밖으로 나가는 입력(링크, 화면 이동)은 Event 를 만든다.
- 웹 특성상 바꾼 점: Hilt 대신 `viewModel { HomeViewModel() }` 로 직접 생성. 화면 이동은 Navigation Compose 대신 `PortfolioNavigator`.

## 3. 디자인 토큰 (Figma Make 기준)

| 용도 | 값 |
|---|---|
| 제목 | `#111827` |
| 본문 | `#374151` |
| 보조 본문 | `#4b5563` |
| 흐린 텍스트(12px 이상) | `#6b7280` |
| 더 흐린 텍스트 | `#9ca3af`, `#d1d5db` |
| 구분선 | `#e5e7eb` |
| 보조 배경 | `#f9fafb`, 태그 배경 `#f3f4f6` |
| 불티 강조색 | `#FF6827` (흰 배경 텍스트용 `#c05214`) |
| 폰트 | Pretendard 서브셋 (본문, 400·500·600·700), JetBrains Mono NL (기간·태그·수치, 400·600·700) |
| 기본 줄 높이 | 1.5 (CSS 기본값과 같게, 남는 공간은 글자 위아래에 균등 분배) |
| 콘텐츠 최대 폭 / 좌우 여백 | 1000px / 24px |
| Navigation 높이 | 60px |
| 반응형 기준 | 700px 이하에서 2열 그리드 → 1열, 지표 4열 → 2열 |

### 3.1 코드 위치 (`shared/src/commonMain/kotlin/me/algosketch/newportfolio/designsystem`)

| 파일 | 내용 |
|---|---|
| `theme/Color.kt` | `PortfolioColors` 색 토큰 |
| `theme/Dimens.kt` | `PortfolioDimens` 콘텐츠 폭·여백·Navigation 높이·반응형 기준 |
| `theme/Type.kt` | 번들 폰트 로드, `PortfolioTextStyle.sans/mono` |
| `theme/WindowSize.kt` | 창 크기(`isCompact`, CSS `clamp()` 대체 `fluidSp`) |
| `theme/Theme.kt` | `PortfolioTheme` (폰트를 다 받은 뒤 콘텐츠 표시) |
| `icon/LucideIcons.kt` | lucide 아이콘 `ImageVector` |
| `component/` | `ContentContainer`, `SectionEyebrow`, `SectionTitle`, `TechTag`, `IconTextLink`, `Modifier.webClickable` |

## 4. 웹(Canvas) 구현 시 고려사항

Compose for Web 은 DOM 이 아니라 Canvas 에 그리므로 아래 사항을 따로 처리해야 한다. (#1 에서 검토)

- **폰트**: 브라우저 폰트로 대체되지 않으므로 Pretendard 와 모노 폰트를 번들한다. 한글 글리프 용량이 크므로 서브셋 또는 Variable 폰트를 검토한다.
- **첫 로딩**: Wasm + Skiko + 폰트로 수 MB. `index.html` 로딩 표시, 서버 압축 적용.
- **SEO·링크 미리보기**: `index.html` 에 meta·OG 태그를 직접 작성한다.
- **텍스트 선택**: 이메일 등 복사가 필요한 텍스트는 `SelectionContainer` 로 감싼다.
- **링크**: `LocalUriHandler` 로 연다. 호버 색 변화는 `hoverable` + `collectIsHoveredAsState()`.
- **CSS 효과 대체**: `backdropFilter: blur` (Haze 또는 생략), `feTurbulence` 노이즈 (타일 PNG 또는 `RuntimeShader`), `clamp()`·`@media` (`BoxWithConstraints`).
- **아이콘**: lucide 아이콘 SVG 를 `ImageVector` 로 변환한다.

---

## 5. 결정 기록

| 날짜 | 결정 | 이유 |
|---|---|---|
| 2026-11-02 | HanilKeyboard 의 Claude 설정(`CLAUDE.md`)과 문서 체계(`SPEC.md`, `PROMPTS.md`)를 가져옴. `CONVERSION_RULES.md` 는 키보드 전용이라 제외 | 사용자 요청 (프롬프트 #2) |
| 2026-11-02 | 기본 보호 브랜치는 `master` (HanilKeyboard 의 `main` 대신) | 이 저장소의 기본 브랜치 이름이 `master` |
| 2026-11-02 | 커밋 메시지는 한국어, `.idea/` 등 IDE 설정은 커밋하지 않음 | HanilKeyboard 규칙 유지 |
| 2026-11-02 | 원격 기본 브랜치는 `main`. `develop` 을 원격에 올리고 PR 은 `develop` 대상으로 만든다 | 원격 저장소(HamBP/new-portfolio-kmp)의 기본 브랜치가 `main` |
| 2026-11-02 | 폰트는 Pretendard 1.3.9 OTF 4종을 **KS X 1001 한글 2,350자 + 라틴·문장부호·화살표·도형·한글 자모**로 서브셋(굵기당 약 350KB)하고, JetBrains Mono **NL** TTF 3종과 함께 번들 (약 2MB). 라이선스는 `licenses/` | 전체 Pretendard 는 굵기당 1.5MB 이상. Pretendard **Std** 는 한글이 없는 라틴 전용판이라 쓸 수 없음. NL 은 합자(ligature) 없는 버전이라 `->` 등이 바뀌지 않음. 서브셋 밖 글자(예: `뷁`)는 표시되지 않으므로 문구 추가 시 확인 필요 |
| 2026-11-02 | 폰트를 모두 받은 뒤에 화면을 그리고, 두 글꼴을 `FontFamily.Resolver.preload` 로 등록해 서로 없는 글리프를 대체 (모노의 한글 → Pretendard, Pretendard 의 `▸` → JetBrains Mono) | Canvas 는 브라우저 폰트 대체가 없어 늦게 온 폰트·없는 글리프가 빈 칸으로 보임. 디자인의 `monospace` 텍스트에 한글(`2,200장`, `블로그`)이 섞여 있음 |
| 2026-11-02 | CSS `clamp(min, Nvw, max)` 는 `WindowSize.fluidSp`, `@media (max-width: 700px)` 는 `WindowSize.isCompact` 로 대체 | Compose 에는 CSS 단위가 없음 |
| 2026-11-02 | 라우팅은 Navigation Compose 대신 직접 만든 해시 라우터(`PortfolioNavigator`) | 페이지가 2개뿐이고, 디자인과 같은 `/#/projects` 주소·브라우저 뒤로 가기를 단순하게 맞출 수 있음 |
| 2026-11-02 | Navigation 의 `backdrop-filter: blur(12px)` 는 생략하고 92% 불투명 흰 배경만 적용 | Canvas 에서 뒤 콘텐츠 블러는 비용이 크고, 92% 불투명이라 차이가 거의 없음 |
| 2026-11-02 | 창 폭 480px 미만에서는 Navigation 의 이메일 글자 대신 메일 아이콘을 보여준다 | 디자인은 좁은 화면에서 줄바꿈 없이 넘쳐 잘림 |
| 2026-11-02 | 타임라인 수상 배지의 🏆 이모지를 lucide `Trophy` 아이콘(11px)으로 대체 | 컬러 이모지 폰트(약 10MB)를 번들하지 않기 위함 |
| 2026-11-02 | Hero 의 SVG `feTurbulence` 노이즈를 250px 무작위 회색 점 타일(`ImageShader` 반복)로 대체. 불투명도는 0.045 × 0.5 | Canvas 에 SVG 필터가 없음. feTurbulence 출력 알파 평균(약 0.5)을 곱해 밝기를 맞춤 |
| 2026-11-02 | 그 외 활동 설명의 블로그 링크는 굵기·색만 적용하고 `border-bottom: 1px #e5e7eb` 밑줄은 생략 | Compose 텍스트 밑줄은 글자색과 같은 색만 지원 |
| 2026-11-02 | 지표 그리드가 2열일 때도 첫 칸만 왼쪽 구분선이 없다 (3번째 칸에 구분선 있음) | 디자인 CSS 와 동일하게 유지 |
| 2026-11-02 | 그림자(`box-shadow`)는 `Modifier.dropShadow` 로 같은 반경·오프셋·투명도 적용 | |
| 2026-11-02 | 호버 효과는 `Modifier.webClickable` + `hoverColor` (150ms 색 전환), 물결 효과 없음 | 디자인의 `transition: 0.15s` 와 웹 링크 느낌 유지 |

---

## 6. 향후 과제
- GitHub 원격 저장소 연결 (PR 규칙 적용 전제).
- 프로젝트 화면 목업 이미지 채우기 (디자인에서는 빈 자리 표시).
- 배포 대상 결정 (GitHub Pages 등).
