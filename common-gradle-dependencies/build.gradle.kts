import com.huanshankeji.generateKotlinVersion

plugins {
    conventions
}

// "x.y.z" indicates the version of the way of organizing the code,
// and the date indicates the version when the dependency versions are updated.
version = "0.3.2-20220728-test"

generateKotlinVersion(kotlinVersion)

gradlePlugin {
    plugins {
        scriptPlugin(
            "com.huanshankeji",
            "dummy-plugin",
            "A dummy plugin defined to enable publishing the repository to the Gradle Plugin Portal"
        )
    }
}