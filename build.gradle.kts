plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    // Paper API (works against 1.21.x, including experimental builds)
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    // Optional deps (compileOnly so the jar doesn't shade them)
    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    // (Optional) annotations
    compileOnly("org.jetbrains:annotations:24.0.1")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand("version" to (project.version.toString()))
    }
}

tasks.jar {
    archiveBaseName.set("BuzzCore")
}
