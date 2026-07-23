package com.huanshankeji.team.artifacts

import com.huanshankeji.team.HUANSHANKEJI_GROUP
import org.gradle.api.artifacts.dsl.RepositoryHandler

fun RepositoryHandler.mavenCentralExcludingHuanshankeji() {
    mavenCentral {
        content {
            excludeGroupAndSubgroups(HUANSHANKEJI_GROUP)
        }
    }
}
