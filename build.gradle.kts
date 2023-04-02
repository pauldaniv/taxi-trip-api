import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	idea
	java
	`maven-publish`
	id("org.springframework.boot") version "3.0.2" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	id("io.freefair.lombok") version "8.0.1" apply false
}

subprojects {
	group = "com.pauldaniv.promotion.yellowtaxi"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "idea")
	apply(plugin = "java")
	apply(plugin = "maven-publish")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "io.freefair.lombok")
	java.sourceCompatibility = JavaVersion.VERSION_17

	repositories {
		mavenCentral()
		mavenLocal()
	}

  dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
  }

	tasks.creating(Jar::class) {
		archiveClassifier.set("sources")
		from(sourceSets["main"].allSource)
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

  tasks.withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
