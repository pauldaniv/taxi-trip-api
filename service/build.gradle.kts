dependencies {
	implementation(project(":api"))
	implementation(project(":persistence"))
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.kafka:spring-kafka")
	runtimeOnly("org.flywaydb:flyway-core")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

// We need to have migration files from 'persistence' module so that they are visible
// during application start
tasks.register<Copy>("copyMigrations") {
	from(project(":persistence").sourceSets.main.get().resources.srcDir("migration"))
	into("$buildDir/resources/main")
}

tasks.processResources {
	dependsOn(tasks.findByName("copyMigrations"))
}
