import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.liquibase:liquibase-core:4.31.1")
        classpath("org.openapitools:openapi-generator-gradle-plugin:7.10.0")
    }
}

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.liquibase.gradle") version "3.0.2"
    id("org.openapi.generator") version "7.12.0"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "com.sovats"
version = "0.0.1-SNAPSHOT"
description = "lunch"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.swagger.core.v3:swagger-core:2.2.36")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

sourceSets {
    main {
        java {
            srcDir("${rootDir}/build/generate-resources/main/src")
        }
    }
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    library.set("spring-boot")
    inputSpec.set("$rootDir/src/main/resources/api/api-v1.yaml")
    generateApiTests.set(false)
    generateModelTests.set(false)
    apiPackage.set("com.sovats.lunch.api")
    invokerPackage.set("com.sovats.lunch.invoker")
    modelPackage.set("com.sovats.lunch.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useTags" to "true",
        )
    )
    additionalProperties.set(
        mapOf(
            "useSpringBoot3" to "true",
            "serializationLibrary" to "jackson",
            "dateLibrary" to "kotlinx-datetime",
            "useSealedClasses" to "true"
        )
    )
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn("openApiGenerate")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
