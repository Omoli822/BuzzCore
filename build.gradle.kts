plugins { java }

group = "com.buzzmc"
version = "0.0.1"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    // Paper API for compilation (match 1.21.x family; works fine against 1.21.9 server)
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

    // LuckPerms API (needed for imports like net.luckperms.api.*)
    compileOnly("net.luckperms:api:5.4")

    // Vault API (only if your code imports net.milkbowl.vault.*)
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.jar {
    archiveBaseName.set("BuzzCore")
}