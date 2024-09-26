import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	jacoco
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val appMainClassName = "com.musinsa.coordinator.CoordinatorApplicationKt"
tasks.getByName<BootJar>("bootJar") {
	mainClass.set(appMainClassName)
}

dependencies {
	// Springboot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-graphql")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
	}

	// Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Mapper
	implementation("org.modelmapper:modelmapper:3.2.1")

	// Graphql
	implementation("com.graphql-java:graphql-java-extended-scalars:22.0")

	// Json
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.2")

	// db
	runtimeOnly("com.h2database:h2")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.graphql:spring-graphql-test:1.3.2")
	implementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
	testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
	testImplementation("io.kotest:kotest-assertions-core:5.9.1")
	runtimeOnly("io.kotest:kotest-property:5.9.1")
	testImplementation("io.mockk:mockk:1.13.12")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

jacoco {
	toolVersion = "0.8.12"
	reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	reports {
		xml.required = false
		csv.required = false
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}