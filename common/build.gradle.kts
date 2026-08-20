architectury {
    val enabledPlatforms: String by rootProject
    common(enabledPlatforms.split(","))
}

// common 模块不生成运行配置，否则 Loom 会为它生成一个 Fabric 目标的 runClient，
// 导致根项目的 `runClient` 任务同时启动 Fabric 和 NeoForge 两个客户端。
tasks.matching { it.name.startsWith("run") }.configureEach {
    enabled = false
}

dependencies {
    modCompileOnly(group = "tech.thatgravyboat", name = "commonats", version = "1.0")
}
