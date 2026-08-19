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

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) {
        isTransitive = false
    }

    val minecraftVersion: String by project
    val forgeVersion: String by project
    val reiVersion: String by project

    add("neoForge", "net.neoforged:neoforge:$forgeVersion")

    modCompileOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-api-neoforge", version = reiVersion)
    modLocalRuntime(group = "me.shedaniel", name = "RoughlyEnoughItems-neoforge", version = reiVersion)
    modCompileOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-default-plugin", version = reiVersion)
}
