# Upgrade Plan: complaint-portal (20260526171420)

- **Generated**: 2026-05-26 17:14:20
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 22.0.2: C:\Program Files\Java\jdk-22\bin (available, not used)
- JDK 26: C:\Program Files\Java\jdk-26\bin (available, not used)
- JDK 21: **<TO_BE_INSTALLED>** (required by step 1)

**Build Tools**
- Maven Wrapper 3.9.15: .mvn/wrapper/maven-wrapper.properties
- Local Maven: not available (wrapper will be used)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

- Upgrade the Java runtime for the project to the latest LTS version.

## Options

- Working branch: appmod/java-upgrade-20260526171420
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 21 runtime

## Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| --------------------- | ------- | -------------- | ---------------- |
| Java | 17 | 21 | User requested latest LTS runtime |
| Spring Boot | 4.0.6 | 4.0.6 | Already compatible with Java 21 |
| Maven Wrapper | 3.9.15 | 3.9.0 | Compatible with Java 21; wrapper available |

## Derived Upgrades

- `java.version` must be upgraded from `17` to `21` in `pom.xml` to meet the target runtime.
- No Spring Boot or dependency version changes are required for Java 21 because Spring Boot 4.0.6 already supports it.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|-----------|---------|--------|--------|--------|
| pom.xml | `java.version` property | 17 | upgrade | 21 | User requested latest LTS Java runtime |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|----------------|--------|
| None | N/A | N/A | N/A | No Java source changes are required for the runtime upgrade alone |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|-----------------|--------|
| None | N/A | N/A | N/A | No application configuration changes needed for Java 21 alone |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|-----------------|
| None detected | N/A | N/A | N/A |

### Risks & Warnings

- **Baseline JDK absence**: Java 17 is not installed on the local machine, so the baseline step will be skipped and the upgrade will be validated directly on Java 21. Mitigation: install JDK 21 and use the Maven wrapper for deterministic execution.
- **Runtime-only compatibility**: Spring Boot 4.0.6 supports Java 21, but any untested use of newer Java language/runtime behavior may surface only during full test execution. Mitigation: run the complete test suite in final validation.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Install and verify the required Java 21 runtime before applying the Java version change.
  - **Changes to Make**: Install JDK 21 and verify Maven wrapper execution.
  - **Verification**: Install JDK 21, then run `./mvnw -q -version` with JDK 21 active.

- Step 2: Setup Baseline
  - **Rationale**: Capture the current project baseline if the current declared JDK is available locally.
  - **Changes to Make**: Skip because Java 17 is not installed locally.
  - **Verification**: None; step is skipped.

- Step 3: Upgrade Java runtime to 21
  - **Rationale**: Update the project source and build configuration to the requested runtime without changing framework versions.
  - **Changes to Make**: Change `pom.xml` `<java.version>` from `17` to `21`.
  - **Verification**: `./mvnw -q clean test-compile` with JDK 21.

- Step 4: Final Validation
  - **Rationale**: Validate the upgrade with a full clean test run on the target runtime and resolve any issues.
  - **Changes to Make**: Address any JDK 21 compatibility issues discovered by tests.
  - **Verification**: `./mvnw -q clean test` with JDK 21.
