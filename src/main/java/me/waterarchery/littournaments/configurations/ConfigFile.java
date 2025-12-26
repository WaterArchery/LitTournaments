package me.waterarchery.littournaments.configurations;

import com.chickennw.utils.libs.config.configs.OkaeriConfig;
import com.chickennw.utils.libs.config.configs.annotation.Comment;
import com.chickennw.utils.libs.config.configs.annotation.NameModifier;
import com.chickennw.utils.libs.config.configs.annotation.NameStrategy;
import com.chickennw.utils.libs.config.configs.annotation.Names;
import com.chickennw.utils.models.config.database.DatabaseConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class ConfigFile extends OkaeriConfig {

    private String prefix = "<#47D4FF><bold>ʟɪᴛᴛᴏᴜʀɴᴀᴍᴇɴᴛs <reset><dark_gray>»<reset>  ";

    @Comment("Currently only en and tr available")
    private String language = "en";

    @Comment({"Its in seconds.", "Please don't set it under 60 seconds."})
    private int leaderboardRefresh = 60;

    @Comment({"Keep it true if this server is main server on a multi server environment.",
            "Keep it true if is not multi server environment."})
    private boolean mainServer = true;

    @Comment("Amount of players that listed in tournament leaderboard")
    private int leaderboardLimit = 32;

    @Comment({"Its in seconds.", "Its the time between finish and start time between tournaments."})
    private int waitTimeBetweenTournaments = 60;

    @Comment({"If you set this to true, it will disable leaderboard menu opening",
            "with right clicking tournament item in /tournament gui."})
    private boolean disableLeaderboardWithRightClick = false;

    @Comment({"You can enable Discord web hook support on", "tournament start and finish."})
    private DiscordWebhook discordWebhook = new DiscordWebhook();

    private DatabaseConfiguration database = new DatabaseConfiguration();

    @Getter
    @Setter
    public static class DiscordWebhook extends OkaeriConfig {
        private boolean enabled = false;
        private String avatar = "https://i.imgur.com/VDyO5IH.jpeg";
        private String webhookUrl = "your_url";
        private String title = "\uD83C\uDFC6 %tournament% Tournament Results";
        private String description = "The daily %tournament% Tournament has finished!\\n";
        private Map<Integer, WebhookPart> parts = Map.of(
                1, new WebhookPart("\uD83E\uDD47 1st Place", "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 1000 Game Balance\\n"),
                2, new WebhookPart("\uD83E\uDD48 2nd Place", "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 500 Game Balance\\n"),
                3, new WebhookPart("\uD83E\uDD49 3rd Place", "**Player:** %player%\\n**Score:** %score%\\n**Rewards:** 250 Game Balance\\n")
        );
    }

    @Getter
    @Setter
    public static class WebhookPart extends OkaeriConfig {
        private String title;
        private String description;

        public WebhookPart() {
        }

        public WebhookPart(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}
