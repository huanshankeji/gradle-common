# Cloud-agent base image for Huanshankeji Gradle convention plugins.
# Temurin JDK 17 matches CI (kotlin-jvm-ci.yml) and kotlin.jvmToolchain(17).
FROM eclipse-temurin:17-jdk-noble

RUN apt-get update \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        ca-certificates \
        curl \
        git \
        sudo \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
