rootProject.name = "props"

include("props-core")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.diffplug.spotless").version("5.17.1")
        id("net.ltgt.errorprone").version("2.0.2")
        id("me.champeau.jmh").version("0.6.6")
    }
}

// https://docs.gradle.org/current/userguide/platforms.html#header
// https://docs.gradle.org/current/userguide/dependency_version_alignment.html#header
// https://docs.gradle.org/current/userguide/single_versions.html#header
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("junit", "5.7.2")
            version("checkstyle", "9.0")
            version("hamcrest", "2.2")
            version("mockito", "3.12.4")
            version("awaitility", "4.1.0")

            alias("errorprone-core").to("com.google.errorprone", "error_prone_core")
                .version("2.9.0")
            alias("nullaway").to("com.uber.nullaway", "nullaway").version("0.9.2")

            alias("junit-jupiter-api").to("org.junit.jupiter", "junit-jupiter-api")
                .versionRef("junit")
            alias("junit-jupiter-engine").to("org.junit.jupiter", "junit-jupiter-engine")
                .versionRef("junit")
            alias("org-hamcrest-core").to("org.hamcrest", "hamcrest-core").versionRef("hamcrest")
            alias("org-mockito-core").to("org.mockito", "mockito-core").versionRef("mockito")
            alias("org-awaitility-awaitility").to("org.awaitility", "awaitility")
                .versionRef("awaitility")

            alias("guava").to("com.google.guava:guava:30.1.1-jre")
        }
    }
}
