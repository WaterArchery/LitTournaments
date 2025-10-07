package me.waterarchery.littournaments.configurations;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.configuration.ConfigPart;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigFile extends ConfigManager {

    public ConfigFile(LitLibs litLibs, String folder, String name, boolean saveAfterLoad) {
        super(litLibs, folder, name, saveAfterLoad);
    }

    @Override
    public void initializeDefaults() {
        addDefault(ConfigPart.noComment("Prefix", "<#47D4FF><bold>ʟɪᴛᴛᴏᴜʀɴᴀᴍᴇɴᴛs <reset><dark_gray>»<reset>  "));
        addDefault(ConfigPart.of("Language", "en",
                                 Collections.singletonList("Currently only en and tr available")));

        addDefault(ConfigPart.of("LeaderboardRefresh", 60, Arrays.asList(
                "Its in seconds.",
                "Please don't set it under 60 seconds.")));
        addDefault(ConfigPart.of("MainServer", true, List.of(
                "Keep it true if this server is main server on a multi server environment.",
                "Keep it true if is not multi server environment.")));
        addDefault(ConfigPart.of("LeaderboardLimit", 32, List.of(
                "Amount of players that listed in tournament leaderboard"
        )));
        addDefault(ConfigPart.of("WaitTimeBetweenTournaments", 60, Arrays.asList(
                "Its in seconds.",
                "Its the time between finish and start time between tournaments."
        )));
        addDefault(ConfigPart.of("DisableLeaderboardWithRightClick", false, Arrays.asList(
                "If you set this to true, it will disable leaderboard menu opening",
                "with right clicking tournament item in /tournament gui."
        )));

        addDefault(ConfigPart.of("DiscordWebhook", null, Arrays.asList(
                "You can enable Discord web hook support on",
                "tournament start and finish."
        )));
        addDefault(ConfigPart.noComment("DiscordWebhook.Enabled", false));
        addDefault(ConfigPart.noComment("DiscordWebhook.Avatar", "https://i.imgur.com/VDyO5IH.jpeg"));
        addDefault(ConfigPart.noComment("DiscordWebhook.WebhookURL", "your_url"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Title", "\uD83C\uDFC6 %tournament% Tournament Results"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Description", "The daily %tournament% Tournament has finished!\\n"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.1.Title", "\uD83E\uDD47 1st Place"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.1.Description",
                                        "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 1000 Game Balance\\n"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.2.Title", "\uD83E\uDD48 2nd Place"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.2.Description",
                                        "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 500 Game Balance\\n"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.3.Title", "\uD83E\uDD49 3rd Place"));
        addDefault(ConfigPart.noComment("DiscordWebhook.Parts.3.Description",
                                        "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 250 Game Balance\\n"));

        addDefault(ConfigPart.noComment("SoundVolume", 2));
        addDefault(ConfigPart.of("Sounds.MenuOpen", "BLOCK_ANVIL_BREAK", Collections.singletonList("# You can set sounds \"\" to disable them.")));
        addDefault(ConfigPart.noComment("Sounds.InvalidCommand", "ENTITY_ARROW_HIT"));
        addDefault(ConfigPart.noComment("Sounds.NoPermission", "ENTITY_ARROW_HIT"));
        addDefault(ConfigPart.noComment("Sounds.TournamentFinish", "BLOCK_ANVIL_BREAK"));
        addDefault(ConfigPart.noComment("Sounds.AlreadyJoined", "ENTITY_ARROW_HIT"));
        addDefault(ConfigPart.noComment("Sounds.SuccessfullyJoined", "BLOCK_ANVIL_BREAK"));

        addDefault(ConfigPart.of("Database.DatabaseType", "sqlite", Arrays.asList("You can use these options:", "MySQL", "SQLite")));
        addDefault(ConfigPart.noComment("Database.MySQL.host", "localhost"));
        addDefault(ConfigPart.noComment("Database.MySQL.port", "3306"));
        addDefault(ConfigPart.noComment("Database.MySQL.database", "db"));
        addDefault(ConfigPart.noComment("Database.MySQL.user", "user"));
        addDefault(ConfigPart.noComment("Database.MySQL.password", "mypassword"));
    }

}
