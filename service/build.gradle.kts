dependencies {
	implementation(project(":api"))
	implementation(project(":persistence"))
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}
