package me.waterarchery.littournaments.managers;

import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.configurations.ConfigFile;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentValue;
import me.waterarchery.littournaments.utils.DiscordWebhook;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class WebhookHandler {

    public static void sendWebhook(Tournament tournament) {
        ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
        ConfigFile.DiscordWebhook discordWebhook = configFile.getDiscordWebhook();
        if (discordWebhook.isEnabled()) {
            String title = discordWebhook.getTitle().replace("%tournament%", tournament.getCoolName());
            String description = discordWebhook.getDescription().replace("%tournament%", tournament.getCoolName());
            String url = discordWebhook.getWebhookUrl();
            String avatar = discordWebhook.getAvatar();

            DiscordWebhook webhook = new DiscordWebhook(url);
            DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

            embedObject.setTitle(title);
            embedObject.setDescription(description);
            webhook.setAvatarUrl(avatar);

            for (Map.Entry<Integer, ConfigFile.WebhookPart> entry : discordWebhook.getParts().entrySet()) {
                int pos = entry.getKey();
                ConfigFile.WebhookPart part = entry.getValue();

                TournamentValue value = tournament.getLeaderboard().getPlayer(pos).orElse(null);
                String player = value != null ? value.getName() : "None";
                String score = value != null ? String.valueOf(value.getValue()) : "0";

                String partTitle = part.getTitle();
                String partDescription = part.getDescription().replace("%player%", player).replace("%score%", score);

                embedObject.addField(partTitle, partDescription, false);
            }

            embedObject.setColor(Color.ORANGE);
            webhook.addEmbed(embedObject);

            ChickenUtils.getFoliaLib().getScheduler().runAsync((task) -> {
                try {
                    webhook.execute();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

}
