# Deferred agent notes

Notes extracted from the archived [copilot-instructions.md](copilot-instructions.md) during the move to root [AGENTS.md](../../AGENTS.md). These were judged worth keeping on record but **not** included in `AGENTS.md` yet.

Task names below use the current Gradle task name (`updateKotlinAbi`). The archived copilot file still says `apiDump`.

## Do not auto-update public API dumps (#3)

Do **not** run `updateKotlinAbi` automatically — even when `./gradlew check` fails because `apiCheck` detected API changes.

Leave ABI dump updates to a human developer after they have reviewed the public API surface. Running `updateKotlinAbi` too early creates unnecessary Git-tracked churn before review.

Only run `updateKotlinAbi` if you are very confident the task is fully complete and no further public API edits will be needed.

## When `apiCheck` fails (#4)

If `./gradlew check` fails solely due to `apiCheck` / public API changes, do **not** run `updateKotlinAbi` automatically.

Validate the change with:

```bash
./gradlew test
./gradlew publishToMavenLocal
```

Then leave `updateKotlinAbi` to the human developer after they review the API surface.

## Known warnings safe to ignore (#13)

These build warnings are expected and do not indicate a problem with your changes:

- **"Unsupported Kotlin plugin version"** — Gradle Kotlin DSL / embedded Kotlin vs. the Kotlin Gradle plugin version pinned in this repo (see [README.md](../../README.md) and [buildSrc/build.gradle.kts](../../buildSrc/build.gradle.kts)).
- **`buildDir` deprecation warnings** — from dependencies or older Gradle APIs; not actionable in routine agent work.
