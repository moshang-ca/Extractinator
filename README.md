<div align="center">

## Extractinator

[![Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/extractinator)
<hr>

### 📖About 📖

<hr>
</div>

This mod adds a new machine, the Extractinator, which converts your throwaway blocks, such as gravel and cobblestone,
into valuable resources. It's based on Terraria's [Silt Extractinator](https://terraria.fandom.com/wiki/Extractinator),
incorporating silt and slush ore, along with some
new features, such as full automation, support for additional blocks and extensive support for other mods.

> **Notice:** This repository is a **port / maintained fork** of the original
> [Extractinator](https://github.com/alexnijjar/Extractinator) by [Alex Nijjar](https://github.com/alexnijjar),
> updated to **Minecraft 1.21.1** for **NeoForge and Fabric**. All original code and assets belong to Alex Nijjar
> and the contributors. This port is maintained by [Moshang](https://github.com/moshang-ca) and is **not affiliated**
> with the original author.

## Supported Versions

- Minecraft **1.21.1**
- NeoForge (>= 21.1)
- Fabric

## Dependencies

- [Resourceful Lib](https://modrinth.com/mod/resourceful-lib) `>= 3.0.12`
- [Resourceful Config](https://modrinth.com/mod/resourceful-config) `>= 3.0.11`

## Default Recipe

![Recipe](https://i.imgur.com/cx5XuCZ.png)

## Building from Source

This is an [Architectury](https://docs.architectury.dev/) multi-loader project with `common`, `fabric` and `neoforge`
modules. Requires **JDK 21**.

```bash
./gradlew :fabric:build    # Fabric jar
./gradlew :neoforge:build  # NeoForge jar
./gradlew build            # everything
```

Output jars are located in `fabric/build/libs/` and `neoforge/build/libs/`.
