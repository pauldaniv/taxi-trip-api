import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    idea
    java
    `maven-publish`
    jacoco
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("io.freefair.lombok") version "8.0.1" apply false
}

val awsDomainOwner: String? = System.getenv("AWS_DOMAIN_OWNER_ID")
val codeArtifactRepository = "https://promotion-${awsDomainOwner}.d.codeartifact.us-east-2.amazonaws.com/maven/releases/"
val codeArtifactPassword: String? = System.getenv("CODEARTIFACT_AUTH_TOKEN")

subprojects {
    group = "com.pauldaniv.promotion.yellowtaxi"
    version = "0.0.6-SNAPSHOT"

    apply(plugin = "idea")
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "jacoco")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "io.freefair.lombok")
    java.sourceCompatibility = JavaVersion.VERSION_17

    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            name = "CodeArtifact"
            url = uri(codeArtifactRepository)
            credentials {
                username = "aws"
                password = codeArtifactPassword
            }
        }
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.testng:testng:7.8.0")
    }

    if (project.name != "service") {
        tasks.getByName<BootRun>("bootRun") {
            enabled = false
        }
        tasks.getByName<BootJar>("bootJar") {
            enabled = false
        }
        tasks.getByName<BootBuildImage>("bootBuildImage") {
            enabled = false
        }
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }


    publishing {
        repositories {
            maven {
                name = "CodeArtifactPackages"
                url = uri(codeArtifactRepository)
                credentials {
                    username = "aws"
                    password = codeArtifactPassword
                }
            }
        }

        val sourcesJar by tasks.creating(Jar::class) {
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifact(sourcesJar)
            }
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<Test> {
        useTestNG()
    }

    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test) // tests are required to run before generating the report
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(false)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }

    tasks.withType<JacocoReport> {
        afterEvaluate {
            classDirectories.setFrom(classDirectories.files.map {
                fileTree(it).matching {
                    exclude(
                        "**/config",
                        "**/jooq",
                        "**/model",
                        "**/*Application*"
                    )
                }
            })
        }
    }

    tasks.jacocoTestCoverageVerification {
        violationRules {
//            rule {
//                limit {
//                    minimum = "0.9".toBigDecimal()
//                }
//            }

            rule {
//                element = "CLASS"
//                includes = listOf("com.pauldaniv.*")
                classDirectories.setFrom(tasks.jacocoTestReport.get().classDirectories)
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
    }
}
