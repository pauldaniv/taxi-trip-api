import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging

plugins {
	id("org.flywaydb.flyway") version "9.14.1"
	id("nu.studer.jooq") version "8.2"
}

dependencies {
	implementation(project(":api"))
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.jooq:jooq")

	runtimeOnly("org.postgresql:postgresql")
	jooqGenerator("org.postgresql:postgresql")

	testImplementation("org.testcontainers:postgresql:1.17.6")
}

val dbHost = findParam("DB_HOST") ?: "localhost"
val dbPort = findParam("DB_PORT") ?: 5432
val dbUser = findParam("DB_USER") ?: "service"
val dbPass = findParam("DB_PASS") ?: "letmeeeen"
val dbName = findParam("DB_NAME") ?: "service"

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)

jooq {
	version.set("3.18.2")  // default (can be omitted)
//	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)  // default (can be omitted)

	configurations {
		create("main") {  // name of the jOOQ configuration
			generateSchemaSourceOnCompilation.set(true)  // default (can be omitted)

			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
					user = dbUser
					password = dbPass
//                    properties.add(Property().apply {
//                        key = "ssl"
//                        value = "true"
//                    })
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						forcedTypes.addAll(listOf(
								ForcedType().apply {
									name = "varchar"
									includeExpression = ".*"
									includeTypes = "TIMESTAMP?"
								},
						))
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.pauldaniv.promotion.yellowtaxi.jooq"
						directory = "${buildDir}/generated/jooq/main"  // default (can be omitted)
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

flyway {
	url =  "jdbc:postgresql://$dbHost:$dbPort/$dbName"
	user = dbUser
	password = dbPass
	schemas = arrayOf("public")
	locations = arrayOf("filesystem:src/main/resources/migration/postgres")
}

tasks.withType<nu.studer.gradle.jooq.JooqGenerate> {
	dependsOn(tasks.flywayMigrate)
}

val containers = listOf("yt-db", "yt-kafka")

tasks.register("startServices") {
	doLast {
		containers.forEach {
			if (isDockerRunning(it)) {
				println("$it service is already running. Skipping...")
			} else {
				println("Bringing up containers...")
				startContainers()
			}
		}
	}
}

tasks.register("stopServices") {
	doLast {
		containers.forEach {
			if (isDockerRunning(it)) {
				stopService(it)
			} else {
				println("$it service is already stopped")
			}
		}
	}
}

tasks.clean {
	dependsOn(tasks.findByName("stopServices"))
}

tasks.flywayMigrate {
	dependsOn(tasks.findByName("startServices"))
}

fun isDockerRunning(containerName: String) = listOf("docker", "inspect", "-f", "'{{json .State.Running}}'", containerName)
		.exec().apply { println("ServiceState: $this") }.replace("'", "").toBoolean()

fun isServiceHealthy(containerName: String) =
		isDockerRunning(containerName)
				&&
		!listOf("docker", "inspect", "-f", "'{{json .State.Health.Status}}'", containerName)
		.exec(envs = mapOf("PGPASSWORD" to "letmeeeen")).apply { println(this) }
		.contains("unhealthy")



fun startContainers() {
	"docker compose -f ${rootProject.projectDir}/services.yaml up -d".exec()
	println("Waiting for postgres to be healthy...")
	waitTillHealthy("yt-db")
}

fun stopService(containerName: String) {
	"docker compose -f ${rootProject.projectDir}/services.yaml rm --stop --volumes $containerName --force"
			.exec()
			.apply { println(this) }
}

fun isPostgresHealthy(containerName: String) = listOf("docker", "exec", containerName, "psql", "-c", "select version()", "-U", dbUser)
		.exec()
		.apply { println(this) }
		.contains("PostgreSQL 15.*compiled by".toRegex())

fun waitTillHealthy(service: String) {
	var count = 0
	val retries = 50
	if (System.getenv("GITHUB_ACTIONS").toBoolean()) {
		println("Detected GitHub Actions env. Skipping postgres checks...")
		return
	}
	while (!isPostgresHealthy(service) && count < retries) {
		count++
		Thread.sleep(1000L)
		println(count)
		println("Retrying...")
	}
	if (count >= retries) {
		println("Unable to bring up $service service...")
	} else {
		println("Postgres container is up!")
	}
}

fun List<String>.exec(workingDir: File = file("./"), envs: Map<String, String> = mapOf()): String {
	val procBuilder = ProcessBuilder(*this.toTypedArray())
			.directory(workingDir)
			.redirectErrorStream(true)
			.redirectOutput(ProcessBuilder.Redirect.PIPE)
			.redirectError(ProcessBuilder.Redirect.PIPE)

	procBuilder.environment().putAll(envs)

	val proc = procBuilder.start()

	proc.waitFor(1, TimeUnit.MINUTES)
	return proc.inputStream.bufferedReader().readLines().joinToString("\n")
}

fun String.exec(envs: Map<String, String> = mapOf()): String {
	val parts = this.split("\\s".toRegex())
	return parts.toList().exec(envs = envs)
}


tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	(launcher::set)(javaToolchains.launcherFor {
		languageVersion.set(JavaLanguageVersion.of(17))
	})
}

fun getParam(name: String, default: String? = ""): String? {
	return System.getenv(name) ?: default
}
