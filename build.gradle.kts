import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.adarshr.test-logger") version "4.0.0"
	kotlin("jvm") version "2.0.20"
	kotlin("plugin.spring") version "2.0.20"
}

group = "com.parkingapp"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	val ANNOTATIONS = "24.1.0"
	val ARCHUNIT = "1.3.0"
	val ASSERTJ = "3.26.3"
	val FLYWAY = "9.11.0"
	val JUNIT = "5.11.0"
	val JWT = "0.12.6"
	val LOMBOK = "1.18.34"
	val MOCKITO = "5.+"
	val OPEN_API = "2.6.0"
	val POSTGRE = "42.7.4"
	val REST_ASSURED = "5.5.0"
	val TEST_CONTAINERS = "1.20.1"

	annotationProcessor("org.projectlombok:lombok:$LOMBOK")

	compileOnly("org.projectlombok:lombok:$LOMBOK")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$OPEN_API")
	implementation("org.jetbrains:annotations:$ANNOTATIONS")
	implementation("org.postgresql:postgresql:$POSTGRE")
	implementation("org.flywaydb:flyway-core:$FLYWAY")
	implementation("io.jsonwebtoken:jjwt-api:$JWT")
	implementation("io.jsonwebtoken:jjwt-impl:$JWT")
	implementation("io.jsonwebtoken:jjwt-jackson:$JWT")

	testAnnotationProcessor("org.projectlombok:lombok:$LOMBOK")

	testCompileOnly("org.projectlombok:lombok:$LOMBOK")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(platform("org.junit:junit-bom:$JUNIT"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.assertj:assertj-core:$ASSERTJ")
	testImplementation("org.mockito:mockito-core:$MOCKITO")
	testImplementation(platform("org.testcontainers:testcontainers-bom:$TEST_CONTAINERS"))
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("com.tngtech.archunit:archunit:$ARCHUNIT")
	testImplementation("io.rest-assured:rest-assured:$REST_ASSURED")
	testImplementation("io.rest-assured:json-path:$REST_ASSURED")
	testImplementation("io.rest-assured:xml-path:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-mock-mvc:$REST_ASSURED")
	testImplementation("io.rest-assured:spring-commons:$REST_ASSURED")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.apply {
	test {
		enableAssertions = true
		useJUnitPlatform {
			excludeTags("integration")
			excludeTags("component")
			excludeTags("contract")
		}
	}

	testlogger {
		theme = ThemeType.STANDARD_PARALLEL
		showExceptions = true
		showStackTraces = true
		showFullStackTraces = false
		showCauses = true
		slowThreshold = 5000
		showSummary = true
		showSimpleNames = false
		showPassed = false
		showSkipped = true
		showFailed = true
		showOnlySlow = false
		showStandardStreams = false
		showPassedStandardStreams = false
		showSkippedStandardStreams = true
		showFailedStandardStreams = true
		logLevel = LogLevel.LIFECYCLE
	}

	task<Test>("integrationTest") {
		group = "verification"
		description = "Runs integration tests."
		useJUnitPlatform {
			includeTags("integration")
		}
		shouldRunAfter(test)
	}

	task<Test>("contractTest") {
		group = "verification"
		description = "Runs contract tests."
		useJUnitPlatform {
			includeTags("contract")
		}
		shouldRunAfter("integrationTest")
	}

	task<Test>("componentTest") {
		group = "verification"
		description = "Runs component tests."
		useJUnitPlatform {
			includeTags("component")
		}
		shouldRunAfter("contractTest")
	}

	check {
		dependsOn(test,"integrationTest", "contractTest", "componentTest")
	}

}
