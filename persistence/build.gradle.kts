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
					url = "jdbc:postgresql://${getParam("DB_HOST")}:5432/service"
					user = "service"
					password = "letmeeeen"
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
									includeTypes = "JSONB?"
								},
								ForcedType().apply {
									name = "varchar"
									includeExpression = ".*"
									includeTypes = "INET"
								}
						))
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.pauldaniv.promotion.yellowtaxi.totals.jooq"
						directory = "${buildDir}/generated/jooq/main"  // default (can be omitted)
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

flyway {
	url = "jdbc:postgresql://${getParam("DB_HOST")}:5432/service"
	user = "service"
	password = "letmeeeen"
	schemas = arrayOf("public")
	locations = arrayOf("filesystem:src/main/resources/migration/postgres")
}

tasks.withType<nu.studer.gradle.jooq.JooqGenerate> {
	dependsOn(tasks.flywayMigrate)
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	(launcher::set)(javaToolchains.launcherFor {
		languageVersion.set(JavaLanguageVersion.of(17))
	})
}

fun getParam(name: String, default: String? = ""): String? {
	return (System.getenv("name") ?: default)
}
