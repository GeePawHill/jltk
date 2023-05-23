plugins {
    `java-library`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.5.0.202303070854-r")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("org.slf4j:slf4j-nop:2.0.7")
}
// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withJavadocJar()
    withSourcesJar()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("jltk")
                description.set("The Java Learning ToolKit")
                url.set("https://github.com/GeePawHill/jltk")
                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://github.com/GeePawHill/jltk/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("GeePawHill")
                        name.set("GeePaw Hill")
                        email.set("GeePawHill@geepawhill.org")
                    }
                }
                scm {
                    connection.set("git@github.com:GeePawHill/jltk.git")
                    developerConnection.set("git@github.com:GeePawHill/jltk.git")
                    url.set("https://github.com/GeePawHill/jltk.git")
                }
            }

        }
    }
}

nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}
