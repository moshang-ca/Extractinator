package tech.alexnijjar.extractinator.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import tech.alexnijjar.extractinator.Extractinator;

@Config(Extractinator.MOD_ID)
@ConfigInfo(
    title = "Extractinator",
    description = "Convert blocks into resources.",

    icon = "cog",
    // I do not know this parameter was used to do what in 1.20
    // gradient = @ConfigInfo.Gradient(value = "45deg", first = "#c2e59c", second = "#64b3f4"),

    links = {
        @ConfigInfo.Link(value = "https://github.com/alexnijjar/Extractinator", icon = "github", text = "GitHub"),
        @ConfigInfo.Link(value = "https://www.curseforge.com/minecraft/mc-mods/extractinator", icon = "curseforge", text = "CurseForge"),
        @ConfigInfo.Link(value = "https://modrinth.com/mod/extractinator", icon = "modrinth", text = "Modrinth"),
    }
)
public final class ExtractinatorConfig {

    @ConfigEntry(
        id = "extractTicks",
        translation = "text.resourcefulconfig.extractinator.option.extractTicks"
    )
    @Comment(value = "How long it takes for the extractinator to extract a single block", translation = "text.resourcefulconfig.extractinator.option.extractTicks.tooltip")
    public static int extractTicks = 8;

    @ConfigEntry(
        id = "lootMultiplier",
        translation = "text.resourcefulconfig.extractinator.option.lootMultiplier"
    )
    @Comment(value = "Multiplies the amount of loot dropped by this value", translation = "text.resourcefulconfig.extractinator.option.lootMultiplier.tooltip")
    public static double lootMultiplier = 1;

    @ConfigEntry(
        id = "extractinatorDurability",
        translation = "text.resourcefulconfig.extractinator.option.extractinatorDurability"
    )
    @Comment(value = "Amount of uses before the extractinator breaks", translation = "text.resourcefulconfig.extractinator.option.extractinatorDurability.tooltip")
    public static int extractinatorDurability = -1;

    @ConfigEntry(
        id = "silent",
        translation = "text.resourcefulconfig.extractinator.option.silent"
    )
    @Comment(value = "If the extractinator should be silent", translation = "text.resourcefulconfig.extractinator.option.silent.tooltip")
    public static boolean silent = false;

    @ConfigEntry(
        id = "worldgen",
        translation = "text.resourcefulconfig.extractinator.option.worldgen"
    )
    @Comment(value = "[Fabric Only] Generates Silt and Slush ores", translation = "text.resourcefulconfig.extractinator.option.worldgen.tooltip")
    public static boolean worldgen = true;
}
