# New Portfolio

Android 개발자 송준영의 포트폴리오 웹사이트. Kotlin/Wasm + Compose Multiplatform(Web)으로 만든다. 상세 명세는 `docs/SPEC.md`, 사용자 프롬프트 원문은 `docs/PROMPTS.md`.

- 디자인 원본: Figma Make https://www.figma.com/make/G2rFE0jBl7snSyl1wjWCp3/Recreate-Portfolio-Site (React + inline style 소스. Figma MCP `get_design_context` 에 nodeId `0:1` 로 소스 파일 목록을 받을 수 있다)

## 문서 갱신 규칙 (필수)
- 사용자의 새 프롬프트(요청)는 `docs/PROMPTS.md` 맨 아래에 원문 그대로 추가한다. 상시 지침이면 같은 파일의 "작업 규칙"에도 추가한다.
- 새 요구사항이나 결정사항이 생기면 `docs/SPEC.md` 의 해당 절과 "결정 기록" 표를 갱신한다.
- Figma 디자인과 다르게 구현하는 부분(웹 Canvas 제약으로 바꾼 효과, 반응형 기준 등)은 `docs/SPEC.md` "결정 기록" 에 이유와 함께 남긴다.
- 페이지·섹션 구성이나 디자인 토큰(색, 폰트, 간격)을 바꾸면 `docs/SPEC.md` §2·§3 을 함께 수정한다.

## 브랜치·PR 규칙 (필수)
- 기본 작업 브랜치는 `develop`. `master` 에 직접 커밋하지 않는다.
- 적당한 작업 단위(섹션 하나, 페이지 하나 등)마다 `develop` 에서 작업 브랜치를 만든다.
- **연속된 요청이 같거나 비슷한 부분을 수정하면 새 브랜치를 만들지 않는다.** 아직 병합되지 않은 기존 작업 브랜치에 커밋을 추가하고, 같은 PR 의 제목·본문을 갱신한다. (예: Hero 섹션 수정 요청이 연달아 오면 하나의 브랜치·PR 에서 진행)
  - 기존 PR 이 이미 병합됐거나, 다른 부분을 수정하는 요청이면 새 브랜치를 만든다.
  - 브랜치 번호를 정하기 직전에 이슈·PR 번호를 다시 확인한다 (사용자가 그사이 이슈를 만들 수 있음).
- 브랜치 이름: `feature/{번호}-{작업내용}` (작업내용은 영어 kebab-case). 예: `feature/3-hero-section`
  - 번호는 이 브랜치로 만들 GitHub PR 번호와 맞춘다 (이슈·PR 공용 번호의 다음 값: `gh pr list --state all --limit 1`, `gh issue list --state all --limit 1` 중 큰 값 + 1).
- 작업 브랜치 안에서는 아래 커밋 규칙대로 작은 커밋을 쌓고, push 후 `develop` 대상 PR 을 `gh pr create --base develop` 으로 만든다.
- PR 을 만든 뒤 **병합 전에 사용자에게 "머지할까요?" 라고 묻고**, 승인을 받으면 병합한다: `gh pr merge {번호} --merge --delete-branch` (작은 커밋 이력 보존을 위해 merge commit 방식).
  - 승인은 해당 PR 에만 적용된다. PR 마다 다시 묻는다.
  - 묻기 전에 빌드와 테스트 통과를 확인한다. 충돌이 나면 `develop` 을 작업 브랜치에 병합해 해결한 뒤 묻는다.
  - 다음 작업이 병합 전 PR 에 의존하면 그 브랜치에서 새 브랜치를 따고 PR base 를 해당 브랜치로 지정한다.
  - 병합 후 로컬 `develop` 을 최신으로 맞추고 다음 브랜치를 만든다.
- PR 본문: 변경 요약, 커밋 목록 성격(문서/기능 등), 테스트 결과.
- GitHub 원격 저장소가 아직 없으면 브랜치·커밋까지만 진행하고, 원격 연결 여부를 사용자에게 묻는다.

## 커밋 규칙 (필수)
- 작업 후 사용자가 요청하지 않아도 **자동으로 커밋**한다.
- 커밋은 하나의 작업으로 분리할 수 있는 **작은 단위**로 나눈다. (예: 섹션 컴포저블 하나 추가 정도)
- **변경 종류를 섞지 않는다**: 문서 수정 / 스타일(포맷·네이밍 등 동작 무관) / 리팩토링(동작 유지 구조 변경) / 기능 수정(버그 수정·동작 변경) / 기능 추가는 각각 별도 커밋으로 분리한다.
  - 기능과 함께 바뀌는 테스트는 해당 기능 커밋에 포함한다. 그 기능에 따른 문서 갱신은 별도의 문서 커밋으로 분리한다.
- 각 커밋은 가능한 한 단독으로 빌드가 깨지지 않도록 순서를 정한다. 커밋 메시지는 한국어로 작성한다.
- 사용자 IDE 설정 파일(`.idea/` 등)은 사용자가 요청하지 않는 한 커밋하지 않는다.

## 개발
- 모듈: `shared`(UI·로직, `commonMain` 에 작성) / `webApp`(진입점 `webApp/src/webMain/kotlin/.../main.kt`). 타깃은 `wasmJs` 와 `js` 두 가지이며 둘 다 빌드가 깨지지 않게 유지한다.
- 플랫폼 API(브라우저 `window`, URL 해시 등)가 필요하면 `expect`/`actual` 로 분리해 `jsMain`·`wasmJsMain` 에 둔다.
- 실행: `./gradlew :webApp:wasmJsBrowserDevelopmentRun` (JS: `./gradlew :webApp:jsBrowserDevelopmentRun`)
- 테스트: `./gradlew :shared:wasmJsTest` (JS: `./gradlew :shared:jsTest`)
- 배포 빌드: `./gradlew :webApp:wasmJsBrowserDistribution`
- 배포: `/deploy` 스킬 (`.claude/skills/deploy`). 빌드 결과물을 HamBP/portfolio-page `main` 에 올린다 → https://hambp.github.io/portfolio-page/
- Canvas 렌더링이라 브라우저 폰트로 대체되지 않는다. 한글·모노 폰트는 반드시 `composeResources/font` 에 번들한다 (`docs/SPEC.md` §4).
