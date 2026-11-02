---
name: deploy
description: 포트폴리오 사이트를 빌드해 GitHub Pages 저장소 HamBP/portfolio-page(main)에 배포한다. 사용자가 /deploy 로 실행한다.
disable-model-invocation: true
argument-hint: "[--dry-run] [--skip-tests]"
---

# 포트폴리오 배포

이 프로젝트(Kotlin/Wasm 포트폴리오)의 배포 빌드를 **HamBP/portfolio-page** 저장소 `main` 브랜치 루트에 올린다.
그 저장소는 GitHub Pages(branch: `main`, path: `/`)로 https://hambp.github.io/portfolio-page/ 를 서빙한다.
배포 저장소에는 빌드 결과물만 있고, 소스는 이 저장소(HamBP/new-portfolio-kmp)에 있다.

## 실행

모든 과정은 번들 스크립트가 한다. 인자(`$ARGUMENTS`)를 그대로 넘긴다.

```bash
bash .claude/skills/deploy/scripts/deploy.sh $ARGUMENTS
```

빌드·Pages 대기 때문에 몇 분 걸리므로 Bash `timeout` 을 600000 으로 준다.

스크립트가 하는 일:
1. 작업 트리가 깨끗한지 확인한다. 배포 커밋 메시지에 소스 커밋 해시를 남기므로, 커밋되지 않은 변경이 있으면 멈춘다.
2. `:shared:wasmJsTest` 테스트(`--skip-tests` 로 생략) 후 `:webApp:wasmJsBrowserDistribution` 빌드.
3. 배포 저장소를 임시 폴더에 얕게 clone 하고, 기존 파일을 **전부 지운 뒤** 빌드 결과물로 교체한다. 해시가 붙은 `.wasm` 파일명이 빌드마다 바뀌어서 덮어쓰기만 하면 옛 파일이 쌓이기 때문이다. `CNAME` 은 보존하고 `.nojekyll` 을 추가한다.
4. `deploy: new-portfolio-kmp@<해시>` 로 커밋하고 push 한다. 변경이 없으면 커밋하지 않고 끝난다.
5. Pages 빌드가 `built` 가 될 때까지 기다리고 사이트 HTTP 상태를 확인한다.

## 실행 전 판단

- **어느 브랜치에서 배포하나**: 스크립트는 현재 체크아웃된 커밋을 배포한다. 현재 브랜치가 `develop`·`main` 이 아니면(아직 병합 안 된 작업 브랜치) 사용자에게 그 브랜치를 배포할지 먼저 묻는다. 공개 사이트에 검토 안 된 코드가 올라가는 것을 막기 위해서다.
- **작업 트리가 더러울 때**: 스크립트가 멈춘다. 임의로 stash·커밋하지 말고 사용자에게 알린다.
- `/deploy` 명령 자체가 배포 승인이므로 push 전에 다시 묻지 않는다. 단 `--dry-run` 이면 push 하지 않는다.

## 결과 보고

짧게 알린다:
- 배포한 소스 브랜치·커밋 해시
- 배포 저장소 변경 요약(스크립트의 `--stat` 출력 마지막 줄)
- Pages 빌드 상태와 사이트 주소 https://hambp.github.io/portfolio-page/
- 브라우저 캐시 때문에 새 버전이 바로 안 보이면 강력 새로고침(Ctrl+Shift+R)하라고 안내

`--dry-run` 이면 출력된 작업 폴더 경로를 알려 주고, 확인이 끝나면 지워도 된다고 말한다.

## 실패했을 때

- **테스트·빌드 실패**: 오류 출력의 핵심을 보여 주고 멈춘다. 코드를 고치는 일은 별도 요청으로 다룬다.
- **push 거부 (권한·인증)**: `gh auth status` 결과를 확인해 알려 준다. `gh auth login` 은 대화형이라 사용자에게 `! gh auth login` 으로 직접 실행하라고 안내한다.
- **Pages 빌드 `errored` 또는 시간 초과**: `gh api repos/HamBP/portfolio-page/pages/builds/latest` 의 `error.message` 를 보여 준다. 이미 push 는 끝났으므로 다시 배포하기 전에 원인을 먼저 본다.
- **되돌리기**: 배포 저장소에서 직전 커밋으로 `git revert` 후 push 하면 이전 사이트로 돌아간다. 사용자가 요청할 때만 한다.
