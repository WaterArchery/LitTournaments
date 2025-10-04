package me.waterarchery.littournaments.configurations;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.configuration.ConfigPart;

import java.util.Arrays;
import java.util.List;

public class TournamentMenuFile extends ConfigManager {

    public TournamentMenuFile(LitLibs litLibs, String folder, String name, boolean saveAfterLoad) {
        super(litLibs, folder, name, saveAfterLoad);
    }

    @Override
    public void initializeDefaults() {
        addDefault(ConfigPart.noComment("Title", "<#47D4FF>Tournaments"));
        addDefault(ConfigPart.noComment("Size", 45));
        addDefault(ConfigPart.noComment("FillMenu.Enabled", true));
        addDefault(ConfigPart.noComment("FillMenu.FillItem.Name", "<dark_gray>"));
        addDefault(ConfigPart.noComment("FillMenu.FillItem.Material", "GRAY_STAINED_GLASS_PANE"));
        addDefault(ConfigPart.noComment("FillMenu.FillItem.CustomModelData", -1));
        addDefault(ConfigPart.noComment("FillMenu.FillItem.HideAttributes", true));

        addOptional(ConfigPart.noComment("Decoration.item1.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item1.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item1.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item1.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item1.Slot", 0));
        addOptional(ConfigPart.noComment("Decoration.item1.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item2.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item2.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item2.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item2.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item2.Slot", 8));
        addOptional(ConfigPart.noComment("Decoration.item2.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item3.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item3.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item3.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item3.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item3.Slot", 36));
        addOptional(ConfigPart.noComment("Decoration.item3.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.closeItem.Name", "<red>Close Menu"));
        addOptional(ConfigPart.noComment("Decoration.closeItem.Material", "BARRIER"));
        addOptional(ConfigPart.noComment("Decoration.closeItem.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.closeItem.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.closeItem.Action", "close"));
        addOptional(ConfigPart.noComment("Decoration.closeItem.Slot", 40));
        addOptional(ConfigPart.noComment("Decoration.closeItem.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item4.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item4.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item4.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item4.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item4.Slot", 44));
        addOptional(ConfigPart.noComment("Decoration.item4.Lore", List.of()));

        addOptional(ConfigPart.of("Items.block_break", null, Arrays.asList(
                "Use tournament id only.",
                "The tournament id is the file name in the tournaments folder.",
                "You need to remove .yml extension whilere using them here.",
                "For example, if you have a tournament named block_break.yml in your tournaments folder,",
                "you need to use block_break here."
        )));
        addOptional(ConfigPart.noComment("Items.block_break.Name", "<#47D4FF>Block Break Tournament"));
        addOptional(ConfigPart.of("Items.block_break.Material", "COBBLESTONE", List.of(
                "You can use custom head values like this:",
                "HEAD-eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg2MjJhM2Q1M2NjYzc4NDM2YzBmNjYwMDRjYjRiNzcyOWM0NjZlYTEwMDY1ZTgzOWEwNmI2Mjg4YmZkYTk4NiJ9fX0="
        )));
        addOptional(ConfigPart.noComment("Items.block_break.Slot", 10));
        addOptional(ConfigPart.noComment("Items.block_break.HideAttributes", true));
        addOptional(ConfigPart.of("Items.block_break.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.block_break.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>break blocks <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Broken Blocks: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));

        addOptional(ConfigPart.noComment("Items.block_place.Name", "<#47D4FF>Block Place Tournament"));
        addOptional(ConfigPart.noComment("Items.block_place.Material", "OAK_LOG"));
        addOptional(ConfigPart.noComment("Items.block_place.Slot", 13));
        addOptional(ConfigPart.noComment("Items.block_place.HideAttributes", true));
        addOptional(ConfigPart.of("Items.block_place.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.block_place.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>place blocks <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Placed Blocks: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));

        addOptional(ConfigPart.noComment("Items.item_craft.Name", "<#47D4FF>Craft Item Tournament"));
        addOptional(ConfigPart.noComment("Items.item_craft.Material", "CRAFTING_TABLE"));
        addOptional(ConfigPart.noComment("Items.item_craft.Slot", 16));
        addOptional(ConfigPart.noComment("Items.item_craft.HideAttributes", true));
        addOptional(ConfigPart.of("Items.item_craft.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.item_craft.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>craft items <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Placed Blocks: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));

        addOptional(ConfigPart.noComment("Items.mob_kill.Name", "<#47D4FF>Mob Kill Tournament"));
        addOptional(ConfigPart.noComment("Items.mob_kill.Material", "DIAMOND_SWORD"));
        addOptional(ConfigPart.noComment("Items.mob_kill.Slot", 28));
        addOptional(ConfigPart.noComment("Items.mob_kill.HideAttributes", true));
        addOptional(ConfigPart.of("Items.mob_kill.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.mob_kill.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>kill mobs <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Killed Mobs: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));

        addOptional(ConfigPart.noComment("Items.item_break.Name", "<#47D4FF>Item Break Tournament"));
        addOptional(ConfigPart.noComment("Items.item_break.Material", "ANVIL"));
        addOptional(ConfigPart.noComment("Items.item_break.Slot", 31));
        addOptional(ConfigPart.noComment("Items.item_break.HideAttributes", true));
        addOptional(ConfigPart.of("Items.item_break.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.item_break.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>break your items <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Broken Items: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));

        addOptional(ConfigPart.noComment("Items.player_kill.Name", "<#47D4FF>Player Kill Tournament"));
        addOptional(ConfigPart.noComment("Items.player_kill.Material", "SHIELD"));
        addOptional(ConfigPart.noComment("Items.player_kill.Slot", 34));
        addOptional(ConfigPart.noComment("Items.player_kill.HideAttributes", true));
        addOptional(ConfigPart.of("Items.player_kill.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.player_kill.Lore", Arrays.asList(
                "",
                "<#CCFFEE>You can <#47D4FF>kill players <#CCFFEE>to become",
                "<#CCFFEE>the champion of <#47D4FF>this tournament!",
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Killed Players: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Your Position: <#47D4FF>%position%",
                "",
                "<#47D4FF><bold>LEADERBOARD",
                "<#47D4FF>| <#ff001e>\uD83D\uDD25 #1 <white>%leader_name_1% <dark_gray>(<#ff001e>%leader_score_formatted_1%<dark_gray>)",
                "<#47D4FF>| <red>\uD83D\uDD25 #2 <white>%leader_name_2% <dark_gray>(<red>%leader_score_formatted_2%<dark_gray>)",
                "<#47D4FF>| <yellow>\uD83D\uDD25 #3 <white>%leader_name_3% <dark_gray>(<yellow>%leader_score_formatted_3%<dark_gray>)",
                "<#47D4FF>| <#ffb700>⭐ #4 <white>%leader_name_4% <dark_gray>(<#ffb700>%leader_score_formatted_4%<dark_gray>)",
                "<#47D4FF>| <#47D4FF>⭐ #5 <white>%leader_name_5% <dark_gray>(<#47D4FF>%leader_score_formatted_5%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #6 <white>%leader_name_6% <dark_gray>(<dark_gray>%leader_score_formatted_6%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #7 <white>%leader_name_7% <dark_gray>(<dark_gray>%leader_score_formatted_7%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #8 <white>%leader_name_8% <dark_gray>(<dark_gray>%leader_score_formatted_8%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #9 <white>%leader_name_9% <dark_gray>(<dark_gray>%leader_score_formatted_9%<dark_gray>)",
                "<#47D4FF>| <dark_gray>   #10 <white>%leader_name_10% <dark_gray>(<dark_gray>%leader_score_formatted_10%<dark_gray>)",
                "<#47D4FF>|",
                "<#47D4FF>| <#CCFFEE>Leaderboard refreshes",
                "<#47D4FF>| <#CCFFEE>itself every 1 minute",
                "",
                "<#47D4FF><bold>REMAINING TIME",
                "<#47D4FF>| <#CCFFEE>There is <red>%remaining_time%",
                "<#47D4FF>| <#CCFFEE>to this tournament finish.",
                "<#47D4FF>",
                "<#47D4FF>| <green>Left Click To Join",
                "<#47D4FF>| <green>Right Click To View Leaderboard"
        )));
    }

}
