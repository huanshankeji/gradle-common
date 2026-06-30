#!/usr/bin/env bash
# Attempt to delete accidental Gradle Plugin Portal test publishes.
# Portal deletion is UI-only (7-day window) or via gradle/plugin-portal-requests;
# this script probes OAuth-signed API paths and lists manual deletion links.
set -euo pipefail

TEST_VERSION_PREFIX="${TEST_VERSION_PREFIX:-0.99.0-test}"
PORTAL_BASE="${PORTAL_BASE:-https://plugins.gradle.org}"

PLUGINS=(
  com.huanshankeji.common-gradle-dependencies-dummy-plugin
  com.huanshankeji.git-version
  com.huanshankeji.kotlin-abi-validation-conventions
  com.huanshankeji.kotlin-multiplatform-conventional-targets
  com.huanshankeji.kotlin-multiplatform-js-browser-conventions
  com.huanshankeji.maven-central-publish-conventions
  com.huanshankeji.github-packages-maven-publish
  com.huanshankeji.gitlab-package-registry-project-level-maven-endpoint-publish
  com.huanshankeji.jvm-integration-test
  com.huanshankeji.jvm-test-common-feature-variant
  com.huanshankeji.benchmark.kotlinx-benchmark-jvm-conventions
  com.huanshankeji.benchmark.kotlinx-benchmark-multiplatform-conventions
  com.huanshankeji.dokka.dokka-convention
  com.huanshankeji.default-web-frontend-conventions
  com.huanshankeji.default-material-web-frontend-conventions
  com.huanshankeji.generate-kotlin-js-browser-webroot-for-vertx-web
  com.huanshankeji.jvm.native.osandarch.register-default-supported-feature-variants
)

echo "=== Published test versions on the Gradle Plugin Portal ==="
for plugin_id in "${PLUGINS[@]}"; do
  group_path="${plugin_id//./\/}"
  metadata_url="${PORTAL_BASE}/m2/${group_path}/${plugin_id}.gradle.plugin/maven-metadata.xml"
  if ! metadata="$(curl -fsS "$metadata_url" 2>/dev/null)"; then
    echo "${plugin_id}: (not found)"
    continue
  fi
  mapfile -t versions < <(echo "$metadata" | grep -oE "<version>${TEST_VERSION_PREFIX}[^<]*</version>" | sed 's/<[^>]*>//g' | sort -u)
  if ((${#versions[@]} == 0)); then
    echo "${plugin_id}: (no test versions)"
    continue
  fi
  for version in "${versions[@]}"; do
    echo "${plugin_id}@${version} -> ${PORTAL_BASE}/plugin/${plugin_id}/${version}"
  done
done

if [[ -z "${GRADLE_PUBLISH_KEY:-}" || -z "${GRADLE_PUBLISH_SECRET:-}" ]]; then
  echo
  echo "GRADLE_PUBLISH_KEY/SECRET not set; skipping OAuth API probe."
  echo "Delete within 7 days while logged in on each plugin version page, or file:"
  echo "https://github.com/gradle/plugin-portal-requests/issues/new?template=60_plugin_removal.yml"
  exit 0
fi

echo
echo "=== Probing OAuth-signed delete endpoints (expected to fail; no public delete API) ==="
python3 "$(dirname "$0")/probe-portal-delete-api.py"
