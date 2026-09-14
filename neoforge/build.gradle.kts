architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    runs {
        create("data") {
            data()
            programArgs("--all", "--mod", "extractinator")
            programArgs("--output", project(":common").file("src/main/generated/resources").absolutePath)
            programArgs("--existing", project(":common").file("src/main/resources").absolutePath)
        }
    }
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentNeoForge"].extendsFrom(this)
}

repositories {
    maven(url = "https://api.modrinth.com/maven")
    maven {
        url = uri("https://maven.latvian.dev/releases")
        content {
            includeGroup("dev.latvian.mods")
            includeGroup("dev.latvian.apps")
        }
    }
    maven {
        url = uri("https://jitpack.io")
        content {
            includeGroup("com.github.rtyley")
        }
    }
}

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionNeoForge")) {
        isTransitive = false
    }

    val forgeVersion: String by project
    val reiVersion: String by project
    val kubejsVersion: String by project
    val rhinoVersion: String by project

    add("neoForge", "net.neoforged:neoforge:$forgeVersion")

    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-neoforge:$reiVersion")
    modLocalRuntime("me.shedaniel:RoughlyEnoughItems-neoforge:$reiVersion")
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin:$reiVersion")
    modLocalRuntime("maven.modrinth:nvQzSEkH:eYz2YBGT")     // Jade

    compileOnly("dev.latvian.mods:kubejs-neoforge:$kubejsVersion")
    localRuntime("dev.latvian.mods:kubejs-neoforge:$kubejsVersion")
    compileOnly("dev.latvian.mods:rhino:$rhinoVersion")      // Rhino
    localRuntime("dev.latvian.mods:rhino:$rhinoVersion")
}
