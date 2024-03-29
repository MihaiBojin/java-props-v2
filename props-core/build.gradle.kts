plugins {
    id("me.champeau.jmh")
}

jmh {
    iterations.set(1)
}

// https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:complete_example
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            group = project.group as String
            from(components["java"])

            // conditional signing: https://docs.gradle.org/current/userguide/signing_plugin.html#sec:conditional_signing
            signing {
                sign(publishing.publications["mavenJava"])
            }

            pom {
                name.set("${project.group}:${project.name}")
                description.set("Layered application property management library for Java")
                url.set("https://github.com/props-sh/props")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("mihaibojin")
                        name.set("Mihai Bojin")
                        email.set("mihai.bojin@gmail.com")
                        organizationUrl.set("https://MihaiBojin.com")
                    }
                }
                scm {
                    connection.set("scm:git:ssh://git@github.com:MihaiBojin/java-props-v2.git")
                    developerConnection.set("scm:git:ssh://git@github.com:MihaiBojin/java-props-v2.git")
                    url.set("https://github.com/MihaiBojin/java-props-v2")
                }
            }
        }
    }
}
