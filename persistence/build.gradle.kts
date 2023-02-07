plugins {
	id("org.flywaydb.flyway") version "9.14.1"
	id("nu.studer.jooq") version "8.1"
}

dependencies {
	implementation(project(":api"))
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.testcontainers:postgresql:1.17.6")
	jooqGenerator("org.postgresql:postgresql")
}
