#!/usr/bin/env bash
# 포트폴리오를 빌드해 HamBP/portfolio-page 저장소(main, GitHub Pages)에 배포한다.
#
# 사용법: deploy.sh [--dry-run] [--skip-tests]
#   --dry-run     빌드·복사·커밋까지만 하고 push 하지 않는다 (배포 저장소 작업 폴더 경로를 출력)
#   --skip-tests  배포 전 테스트(:shared:wasmJsTest)를 건너뛴다
set -euo pipefail

DEPLOY_REPO="HamBP/portfolio-page"
DEPLOY_BRANCH="main"
SITE_URL="https://hambp.github.io/portfolio-page/"
DIST_REL="webApp/build/dist/wasmJs/productionExecutable"

DRY_RUN=0
SKIP_TESTS=0
for arg in "$@"; do
  case "$arg" in
    --dry-run) DRY_RUN=1 ;;
    --skip-tests) SKIP_TESTS=1 ;;
    *) echo "알 수 없는 옵션: $arg" >&2; exit 2 ;;
  esac
done

ROOT="$(git rev-parse --show-toplevel)"
cd "$ROOT"

# 1. 소스 상태 확인: 커밋되지 않은 변경이 있으면 배포 커밋에 적힌 해시와 실제 내용이 달라진다.
if [ -n "$(git status --porcelain)" ]; then
  echo "ERROR: 커밋되지 않은 변경이 있습니다. 커밋하거나 stash 한 뒤 다시 실행하세요." >&2
  git status --short >&2
  exit 1
fi
SRC_BRANCH="$(git rev-parse --abbrev-ref HEAD)"
SRC_SHA="$(git rev-parse --short HEAD)"
SRC_SUBJECT="$(git log -1 --format=%s)"
echo "== 소스: $SRC_BRANCH @ $SRC_SHA ($SRC_SUBJECT)"

# 2. 테스트·빌드
if [ "$SKIP_TESTS" -eq 0 ]; then
  echo "== 테스트: :shared:wasmJsTest"
  ./gradlew :shared:wasmJsTest -q
fi
echo "== 빌드: :webApp:wasmJsBrowserDistribution"
./gradlew :webApp:wasmJsBrowserDistribution -q
DIST="$ROOT/$DIST_REL"
if [ ! -f "$DIST/index.html" ]; then
  echo "ERROR: 빌드 결과물이 없습니다: $DIST/index.html" >&2
  exit 1
fi

# 3. 배포 저장소를 임시 폴더에 받는다.
WORK="$(mktemp -d)"
echo "== 배포 저장소 clone: $DEPLOY_REPO ($DEPLOY_BRANCH) -> $WORK"
gh repo clone "$DEPLOY_REPO" "$WORK" -- --depth 1 --branch "$DEPLOY_BRANCH" -q

# 4. 기존 파일을 모두 지우고 새 빌드로 교체한다. 해시가 붙은 .wasm 파일명이 빌드마다 바뀌므로
#    덮어쓰기만 하면 옛 파일이 계속 쌓인다. CNAME(커스텀 도메인)은 있으면 보존한다.
CNAME_BACKUP=""
if [ -f "$WORK/CNAME" ]; then CNAME_BACKUP="$(cat "$WORK/CNAME")"; fi
git -C "$WORK" rm -rq --ignore-unmatch .
cp -R "$DIST"/. "$WORK"/
if [ -n "$CNAME_BACKUP" ]; then printf '%s\n' "$CNAME_BACKUP" > "$WORK/CNAME"; fi
# legacy Pages 빌드가 Jekyll 을 거치지 않도록 한다 (`_` 로 시작하는 파일 무시 등 방지).
touch "$WORK/.nojekyll"
git -C "$WORK" add -A

if git -C "$WORK" diff --cached --quiet; then
  echo "== 변경 없음: 배포된 내용과 빌드 결과가 같습니다."
  exit 0
fi
echo "== 변경 요약"
git -C "$WORK" diff --cached --stat | tail -5

git -C "$WORK" commit -q -m "deploy: new-portfolio-kmp@$SRC_SHA

소스: HamBP/new-portfolio-kmp $SRC_BRANCH @ $SRC_SHA
$SRC_SUBJECT"

if [ "$DRY_RUN" -eq 1 ]; then
  echo "== DRY RUN: push 하지 않았습니다. 확인용 작업 폴더: $WORK"
  exit 0
fi

# 5. push
echo "== push: $DEPLOY_REPO $DEPLOY_BRANCH"
git -C "$WORK" push -q origin "$DEPLOY_BRANCH"
DEPLOY_SHA="$(git -C "$WORK" rev-parse HEAD)"
rm -rf "$WORK"

# 6. Pages 빌드 완료 대기 (최대 약 5분)
echo "== GitHub Pages 빌드 대기"
STATUS="unknown"
for _ in $(seq 1 30); do
  sleep 10
  BUILD="$(gh api "repos/$DEPLOY_REPO/pages/builds/latest" --jq '.commit + " " + .status' 2>/dev/null || true)"
  if [ "${BUILD%% *}" = "$DEPLOY_SHA" ]; then
    STATUS="${BUILD##* }"
    if [ "$STATUS" = "built" ] || [ "$STATUS" = "errored" ]; then break; fi
  fi
done
echo "== Pages 빌드 상태: $STATUS"
if [ "$STATUS" != "built" ]; then
  echo "WARN: Pages 빌드가 완료되지 않았거나 실패했습니다. https://github.com/$DEPLOY_REPO/actions 를 확인하세요." >&2
  exit 1
fi

# 7. 배포된 페이지가 새 빌드의 index.html 을 서빙하는지 확인 (CDN 캐시로 잠시 늦을 수 있음)
HTTP="$(curl -s -o /dev/null -w '%{http_code}' "$SITE_URL")"
echo "== $SITE_URL -> HTTP $HTTP"
echo "DONE: $SRC_SHA 배포 완료 ($SITE_URL)"
