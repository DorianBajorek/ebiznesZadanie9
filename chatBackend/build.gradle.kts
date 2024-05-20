plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
}

kotlin {
    // Usunięcie zbędnego ustawienia wersji JVM
    // jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}

