# Git Branching Strategy & CI Pipeline Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Configure Maven plugins for static analysis, coverage, and dependency checking; create GitHub Actions workflow with branch name validation.

**Architecture:** Maven plugins (JaCoCo, Checkstyle, PMD, SpotBugs, OWASP) run during `verify` phase; GitHub Actions orchestrates them in parallel jobs alongside a branch-name validation job.

**Tech Stack:** Spring Boot 4.1.0, Maven, JaCoCo 0.8.12+, Checkstyle 10.x, PMD 7.x, SpotBugs 4.8.x, OWASP Dependency Check, GitHub Actions

## Global Constraints

- Java 25, Spring Boot 4.1.0 (parent POM)
- JaCoCo instruction coverage minimum: 95%
- OWASP Dependency Check fail on CVSS >= 7.0
- SpotBugs effort: max, threshold: high
- Branch naming: `feature/*` → develop, `release/*` → master

---

### Task 1: Add Maven Plugins to pom.xml

**Files:**
- Modify: `pom.xml`

- [ ] **Step 1: Add JaCoCo plugin inside `<plugins>` section**

After the spring-boot-maven-plugin, add:

```xml
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.12</version>
                <executions>
                    <execution>
                        <id>prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>check</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                        <configuration>
                            <rules>
                                <rule>
                                    <element>INSTRUCTION</element>
                                    <limits>
                                        <limit>
                                            <counter>INSTRUCTION</counter>
                                            <value>COVEREDRATIO</value>
                                            <minimum>0.95</minimum>
                                        </limit>
                                    </limits>
                                </rule>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

- [ ] **Step 2: Add Checkstyle plugin**

After JaCoCo, add:

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-checkstyle-plugin</artifactId>
                <version>3.6.0</version>
                <configuration>
                    <configLocation>checkstyle.xml</configLocation>
                    <failOnViolation>true</failOnViolation>
                    <violationSeverity>warning</violationSeverity>
                </configuration>
                <executions>
                    <execution>
                        <id>checkstyle</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

- [ ] **Step 3: Add PMD plugin**

After Checkstyle, add:

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-pmd-plugin</artifactId>
                <version>3.26.0</version>
                <configuration>
                    <rulesets>
                        <ruleset>pmd.xml</ruleset>
                    </rulesets>
                    <failOnViolation>true</failOnViolation>
                    <minimumPriority>3</minimumPriority>
                </configuration>
                <executions>
                    <execution>
                        <id>pmd-check</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                            <goal>cpd-check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

- [ ] **Step 4: Add SpotBugs plugin**

After PMD, add:

```xml
            <plugin>
                <groupId>com.github.spotbugs</groupId>
                <artifactId>spotbugs-maven-plugin</artifactId>
                <version>4.8.6</version>
                <configuration>
                    <effort>Max</effort>
                    <threshold>High</threshold>
                    <failOnError>true</failOnError>
                </configuration>
                <executions>
                    <execution>
                        <id>spotbugs-check</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

- [ ] **Step 5: Add OWASP Dependency Check plugin**

After SpotBugs, add:

```xml
            <plugin>
                <groupId>org.owasp</groupId>
                <artifactId>dependency-check-maven</artifactId>
                <version>10.0.4</version>
                <configuration>
                    <failBuildOnCVSS>7</failBuildOnCVSS>
                    <formats>
                        <format>HTML</format>
                        <format>JSON</format>
                    </formats>
                </configuration>
                <executions>
                    <execution>
                        <id>dependency-check</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>check</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

- [ ] **Step 6: Verify pom.xml is valid**

Run: `mvn validate -q`
Expected: BUILD SUCCESS (no output)

- [ ] **Step 7: Commit**

```bash
git add pom.xml
git commit -m "chore: add static analysis, coverage, and dependency check Maven plugins"
```

---

### Task 2: Create Checkstyle Configuration

**Files:**
- Create: `checkstyle.xml`

- [ ] **Step 1: Create checkstyle.xml**

Write `checkstyle.xml` with Google Java Style checks:

```xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
    <module name="BeforeExecutionExclusionFileFilter">
        <property name="fileNamePattern" value="module\-info\.java$"/>
    </module>
    <module name="FileTabCharacter"/>
    <module name="NewlineAtEndOfFile"/>
    <module name="TreeWalker">
        <module name="OuterTypeFilename"/>
        <module name="IllegalTokenText"/>
        <module name="AvoidEscapedUnicodeCharacters"/>
        <module name="OneTopLevelClass"/>
        <module name="NoLineWrap"/>
        <module name="EmptyBlock"/>
        <module name="NeedBraces"/>
        <module name="LeftCurly"/>
        <module name="RightCurly"/>
        <module name="WhitespaceAround"/>
        <module name="EmptyLineSeparator"/>
        <module name="OneStatementPerLine"/>
        <module name="MultipleVariableDeclarations"/>
        <module name="MissingSwitchDefault"/>
        <module name="FallThrough"/>
        <module name="UpperEll"/>
        <module name="ModifierOrder"/>
        <module name="EmptyLineSeparator">
            <property name="tokens"
                value="PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF,
                       INTERFACE_DEF, ENUM_DEF, STATIC_INIT,
                       INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF"/>
            <property name="allowNoEmptyLineBetweenFields" value="true"/>
        </module>
        <module name="SeparatorWrap"/>
        <module name="PackageName"/>
        <module name="TypeName"/>
        <module name="MemberName"/>
        <module name="ParameterName"/>
        <module name="LambdaParameterName"/>
        <module name="CatchParameterName"/>
        <module name="LocalVariableName"/>
        <module name="ClassTypeParameterName"/>
        <module name="MethodTypeParameterName"/>
        <module name="InterfaceTypeParameterName"/>
        <module name="MethodLength"/>
        <module name="ParameterNumber"/>
        <module name="EmptyForIteratorPad"/>
        <module name="GenericWhitespace"/>
        <module name="MethodParamPad"/>
        <module name="NoWhitespaceAfter"/>
        <module name="NoWhitespaceBefore"/>
        <module name="OperatorWrap"/>
        <module name="ParenPad"/>
        <module name="TypecastParenPad"/>
        <module name="WhitespaceAfter"/>
        <module name="RedundantImport"/>
        <module name="UnusedImports"/>
    </module>
</module>
```

- [ ] **Step 2: Commit**

```bash
git add checkstyle.xml
git commit -m "chore: add Checkstyle configuration (Google Java Style)"
```

---

### Task 3: Create PMD Configuration

**Files:**
- Create: `pmd.xml`

- [ ] **Step 1: Create pmd.xml**

Write `pmd.xml`:

```xml
<?xml version="1.0"?>
<ruleset name="Custom Rules"
    xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0
        https://pmd.sourceforge.io/ruleset_2_0_0.xsd">
    <description>PMD rules for Spring Boot template</description>
    <rule ref="category/java/bestpractices.xml"/>
    <rule ref="category/java/design.xml">
        <exclude name="LawOfDemeter"/>
        <exclude name="LoosePackageCoupling"/>
        <exclude name="DataClass"/>
    </rule>
    <rule ref="category/java/errorprone.xml"/>
    <rule ref="category/java/performance.xml"/>
    <rule ref="category/java/multithreading.xml"/>
</ruleset>
```

- [ ] **Step 2: Commit**

```bash
git add pmd.xml
git commit -m "chore: add PMD rules configuration"
```

---

### Task 4: Create GitHub Actions Workflow

**Files:**
- Create: `.github/workflows/ci.yml`

- [ ] **Step 1: Create .github/workflows/ci.yml**

```yaml
name: CI

on:
  pull_request:
    branches: [develop, master]

jobs:
  branch-name-validation:
    name: Validate branch name
    runs-on: ubuntu-latest
    steps:
      - name: Check branch naming convention
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

  dependency-check:
    name: Dependency check
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          java-version: '25'
          distribution: 'microsoft'
          cache: maven
      - name: Run OWASP Dependency Check
        run: mvn dependency-check:check -DskipTests

  static-analysis:
    name: Static analysis
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          java-version: '25'
          distribution: 'microsoft'
          cache: maven
      - name: Run Checkstyle
        run: mvn checkstyle:check
      - name: Run PMD
        run: mvn pmd:check pmd:cpd-check
      - name: Run SpotBugs
        run: mvn spotbugs:check

  coverage:
    name: Coverage
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 25
        uses: actions/setup-java@v4
        with:
          java-version: '25'
          distribution: 'microsoft'
          cache: maven
      - name: Run tests with coverage
        run: mvn verify jacoco:report jacoco:check
```

- [ ] **Step 2: Commit**

```bash
git add .github/workflows/ci.yml
git commit -m "feat: add CI workflow with branch validation, dependency check, static analysis, and coverage"
```

---

### Task 5: Update .gitignore

**Files:**
- Modify: `.gitignore`

- [ ] **Step 1: Add new build artifact patterns to .gitignore**

Append to `.gitignore`:

```
# Maven target
target/

# OWASP Dependency Check reports
dependency-check-report*

# SpotBugs output
spotbugs*

# IDE
.idea/
*.iml
.project
.classpath
.settings/
```

- [ ] **Step 2: Commit**

```bash
git add .gitignore
git commit -m "chore: update .gitignore with build artifacts"
```

---

### Task 6: Push and Verify

- [ ] **Step 1: Push all commits to develop**

```bash
git push origin develop
```

- [ ] **Step 2: Create a test feature branch and PR to verify**

```bash
git checkout -b feature/test-branch-validation
git push origin feature/test-branch-validation
```

Then create a PR from `feature/test-branch-validation` → `develop` on GitHub to verify the CI workflow runs.
