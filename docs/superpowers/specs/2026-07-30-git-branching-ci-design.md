# Git Branching Strategy & CI Pipeline Design

## Overview

Configure the Spring Boot template project (`plantilla_spring_boot`) with a structured Git branching strategy and a GitHub Actions CI pipeline that enforces code quality gates before merging.

## Branching Strategy

### Convention
- `master` — production-ready code. Protected. Only accepts PRs from `release/*` branches.
- `develop` — default branch. Protected. Only accepts PRs from `feature/*` branches.
- `feature/<description>` — work branches. Push directly, no protection.
- `release/<major>.<minor>.<patch>` — release candidates. Push directly, no protection.

### Rulesets (configured via GitHub UI)
- **master**: Require PR before merging, require status checks, block force pushes.
- **develop**: Require PR before merging, require status checks, block force pushes.

## CI Pipeline (GitHub Actions)

### File: `.github/workflows/ci.yml`

Single workflow triggered on `pull_request` to `develop` and `master`.

Three jobs run in parallel:

### 1. `dependency-check`
- Uses OWASP Dependency Check Maven plugin.
- Fails on CVSS score >= 7 (or any critical/high vulnerability).

### 2. `static-analysis`
- Runs Checkstyle (Google-style rules via `checkstyle.xml`).
- Runs PMD (custom rules via `pmd.xml`).
- Runs SpotBugs (high threshold).
- All configured as Maven plugin executions bound to `verify` phase.

### 3. `coverage`
- JaCoCo Maven plugin bound to `prepare-agent` and `report` goals.
- Fails build if instruction coverage < 95%.

### 4. `branch-name-validation`
- Separate job that does NOT compile code — just a script step.
- If target is `develop`: source branch must start with `feature/`.
- If target is `master`: source branch must start with `release/`.
- Fails with clear message otherwise.

## Maven Plugin Configuration

### JaCoCo (coverage)
- `jacoco-maven-plugin` version 0.8.12+
- `prepare-agent` goal in `initialize` phase.
- `report` goal in `verify` phase.
- `check` goal with `<rule><element>INSTRUCTION</element><limits><limit><counter>INSTRUCTION</counter><value>COVEREDRATIO</value><minimum>0.95</minimum></limit></limits></rule>`

### Checkstyle
- `checkstyle.xml` at project root.
- Google Java Style as base, with minor adaptations.
- Checkstyle version 10.x.

### PMD
- `pmd.xml` at project root.
- Rules: `java-basic`, `java-unnecessary`, `java-design` (avoid deep nesting, etc.).
- PMD version 7.x.

### SpotBugs
- SpotBugs Maven plugin 4.8.x.
- Effort: `max`.
- Threshold: `high`.

### OWASP Dependency Check
- `dependency-check-maven` plugin.
- Fail build on CVSS >= 7.0.
- Suppression file at `dependency-check-suppressions.xml` (optional).

## Branch Name Validation

GitHub Actions job using bash:

```yaml
- name: Validate branch name
  run: |
    if [[ "${{ github.base_ref }}" == "develop" ]]; then
      if [[ ! "${{ github.head_ref }}" =~ ^feature/ ]]; then
        echo "❌ PRs to develop must come from a feature/* branch"
        exit 1
      fi
    elif [[ "${{ github.base_ref }}" == "master" ]]; then
      if [[ ! "${{ github.head_ref }}" =~ ^release/ ]]; then
        echo "❌ PRs to master must come from a release/* branch"
        exit 1
      fi
    fi
    echo "✅ Branch name validation passed"
```

## Files to Create/Modify

### New files
1. `.github/workflows/ci.yml` — GitHub Actions workflow
2. `checkstyle.xml` — Checkstyle rules (Google style based)
3. `pmd.xml` — PMD rules

### Modified files
4. `pom.xml` — Add all Maven plugins (JaCoCo, Checkstyle, PMD, SpotBugs, OWASP Dependency Check)
5. `.gitignore` — Add Maven target, dependency-check reports, SpotBugs output

## Future Considerations
- Status checks from the workflow must be added to the GitHub Rulesets once the workflow has run at least once.
