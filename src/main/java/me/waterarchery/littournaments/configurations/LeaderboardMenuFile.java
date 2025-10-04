package me.waterarchery.littournaments.configurations;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.configuration.ConfigPart;

import java.util.Arrays;
import java.util.List;

public class LeaderboardMenuFile extends ConfigManager {

    public LeaderboardMenuFile(LitLibs litLibs, String folder, String name, boolean saveAfterLoad) {
        super(litLibs, folder, name, saveAfterLoad);
    }

    @Override
    public void initializeDefaults() {
        addOptional(ConfigPart.noComment("Title", "<#47D4FF>%tournament% Leaderboard"));
        addOptional(ConfigPart.noComment("Size", 45));

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
        addOptional(ConfigPart.noComment("Decoration.item2.Slot", 1));
        addOptional(ConfigPart.noComment("Decoration.item2.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item3.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item3.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item3.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item3.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item3.Slot", 2));
        addOptional(ConfigPart.noComment("Decoration.item3.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item4.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item4.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item4.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item4.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item4.Slot", 3));
        addOptional(ConfigPart.noComment("Decoration.item4.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item5.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item5.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item5.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item5.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item5.Slot", 5));
        addOptional(ConfigPart.noComment("Decoration.item5.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item7.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item7.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item7.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item7.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item7.Slot", 6));
        addOptional(ConfigPart.noComment("Decoration.item7.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item8.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item8.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item8.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item8.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item8.Slot", 7));
        addOptional(ConfigPart.noComment("Decoration.item8.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item9.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item9.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item9.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item9.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item9.Slot", 8));
        addOptional(ConfigPart.noComment("Decoration.item9.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item10.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item10.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item10.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item10.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item10.Slot", 36));
        addOptional(ConfigPart.noComment("Decoration.item10.Lore", List.of()));

        addOptional(ConfigPart.noComment("Decoration.item11.Name", "<dark_gray>"));
        addOptional(ConfigPart.noComment("Decoration.item11.Material", "GREEN_STAINED_GLASS_PANE"));
        addOptional(ConfigPart.noComment("Decoration.item11.CustomModelData", -1));
        addOptional(ConfigPart.noComment("Decoration.item11.HideAttributes", true));
        addOptional(ConfigPart.noComment("Decoration.item11.Slot", 44));
        addOptional(ConfigPart.noComment("Decoration.item11.Lore", List.of()));

        addDefault(ConfigPart.of("Items.playerTemplate", null, Arrays.asList(
                "Don't change this id.",
                "This item will be displayed in /tournament leaderboard <tournament name>.",
                "This item is the template item of all leaderboard players."
        )));
        addDefault(ConfigPart.noComment("Items.playerTemplate.Name", "<#47D4FF>%player%"));
        addDefault(ConfigPart.of("Items.playerTemplate.Material", "PLAYER", List.of(
                "You can use custom head values like this:",
                "HEAD-eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg2MjJhM2Q1M2NjYzc4NDM2YzBmNjYwMDRjYjRiNzcyOWM0NjZlYTEwMDY1ZTgzOWEwNmI2Mjg4YmZkYTk4NiJ9fX0=",
                "or you can use PLAYER to display player's head."
        )));
        addDefault(ConfigPart.noComment("Items.playerTemplate.HideAttributes", true));
        addDefault(ConfigPart.of("Items.playerTemplate.CustomModelData", -1,
                                 List.of("You can set it -1 to disable")));
        addDefault(ConfigPart.noComment("Items.playerTemplate.Lore", Arrays.asList(
                "",
                "<#47D4FF><bold>PLAYER STATS",
                "<#47D4FF>| <#CCFFEE>Player's Name: <#47D4FF>%name%",
                "<#47D4FF>| <#CCFFEE>Score: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Position: <#47D4FF>%position%"
        )));

        addDefault(ConfigPart.of("Items.ownPlayer", null, Arrays.asList(
                "Don't change this id.",
                "This item will be displayed in /tournament leaderboard <tournament name>.",
                "This item is the template item of the player that runs the command."
        )));
        addDefault(ConfigPart.noComment("Items.ownPlayer.Name", "<#47D4FF>%player%"));
        addDefault(ConfigPart.noComment("Items.ownPlayer.Material", "PLAYER"));
        addDefault(ConfigPart.noComment("Items.ownPlayer.Slot", 4));
        addDefault(ConfigPart.noComment("Items.ownPlayer.HideAttributes", true));
        addDefault(ConfigPart.of("Items.playerTemplate.CustomModelData", -1,
                                 List.of("You can set it -1 to disable")));
        addDefault(ConfigPart.noComment("Items.ownPlayer.Lore", Arrays.asList(
                "",
                "<#47D4FF><bold>YOUR STATS",
                "<#47D4FF>| <#CCFFEE>Score: <#47D4FF>%stat%",
                "<#47D4FF>| <#CCFFEE>Position: <#47D4FF>%position%"
        )));

        addOptional(ConfigPart.noComment("Items.previousPage.Name", "<red>Previous Page"));
        addOptional(ConfigPart.noComment("Items.previousPage.Material", "ARROW"));
        addOptional(ConfigPart.noComment("Items.previousPage.Slot", 37));
        addOptional(ConfigPart.noComment("Items.previousPage.HideAttributes", true));
        addOptional(ConfigPart.of("Items.previousPage.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.previousPage.Lore", Arrays.asList(
                "",
                "<#CCFFEE>Click here to navigate",
                "<#47D4FF>previous page."
        )));

        addOptional(ConfigPart.noComment("Items.nextPage.Name", "<red>Next Page"));
        addOptional(ConfigPart.noComment("Items.nextPage.Material", "ARROW"));
        addOptional(ConfigPart.noComment("Items.nextPage.Slot", 43));
        addOptional(ConfigPart.noComment("Items.nextPage.HideAttributes", true));
        addOptional(ConfigPart.of("Items.nextPage.CustomModelData", -1,
                                  List.of("You can set it -1 to disable")));
        addOptional(ConfigPart.noComment("Items.nextPage.Lore", Arrays.asList(
                "",
                "<#CCFFEE>Click here to navigate",
                "<#47D4FF>next page."
        )));
    }

}
