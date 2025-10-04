package me.waterarchery.littournaments.handlers;

import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.utils.DiscordWebhook;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.io.IOException;

public class WebhookHandler {

    public static void sendWebhook(Tournament tournament) {
        FileConfiguration config = FileHandler.getConfig().getYml();

        if (config.getBoolean("DiscordWebhook.Enabled")) {
            ValueHandler valueHandler = ValueHandler.getInstance();
            String title = config.getString("DiscordWebhook.Title").replace("%tournament%", tournament.getCoolName());
            String description = config.getString("DiscordWebhook.Description").replace("%tournament%", tournament.getCoolName());
            String url = config.getString("DiscordWebhook.WebhookURL");
            String avatar = config.getString("DiscordWebhook.Avatar");

            DiscordWebhook webhook = new DiscordWebhook(url);
            DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

            embedObject.setTitle(title);
            embedObject.setDescription(description);
            webhook.setAvatarUrl(avatar);

            for (String rawPos : config.getConfigurationSection("DiscordWebhook.Parts").getKeys(false)) {
                int pos = Integer.parseInt(rawPos);
                String partTitle = config.getString("DiscordWebhook.Parts." + rawPos + ".Title");
                String partDescription = config.getString("DiscordWebhook.Parts." + rawPos + ".Description");

                String player = valueHandler.getPlayerNameWithPosition(pos, tournament);
                String score = String.valueOf(valueHandler.getPlayerScoreWithPosition(pos, tournament));

                partDescription = partDescription
                        .replace("%player%", player)
                        .replace("%score%", score);

                embedObject.addField(partTitle, partDescription, false);
            }

            embedObject.setColor(Color.ORANGE);
            webhook.addEmbed(embedObject);

            LitTournaments.getFoliaLib().getScheduler().runAsync((task) -> {
                try {
                    webhook.execute();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

}
