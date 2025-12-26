package me.waterarchery.littournaments.managers;

import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.configurations.ConfigFile;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.utils.DiscordWebhook;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.io.IOException;

public class WebhookHandler {

    public static void sendWebhook(Tournament tournament) {
        ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
        ConfigFile.DiscordWebhook discordWebhook = configFile.getDiscordWebhook();
        if (discordWebhook.isEnabled()) {
            ValueManager valueManager = ValueManager.getInstance();
            String title = discordWebhook.getTitle().replace("%tournament%", tournament.getCoolName());
            String description = discordWebhook.getDescription().replace("%tournament%", tournament.getCoolName());
            String url = discordWebhook.getWebhookUrl();
            String avatar = discordWebhook.getAvatar();

            DiscordWebhook webhook = new DiscordWebhook(url);
            DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();

            embedObject.setTitle(title);
            embedObject.setDescription(description);
            webhook.setAvatarUrl(avatar);

            FileConfiguration config = LitTournaments.getInstance().getConfig();
            for (String rawPos : config.getConfigurationSection("DiscordWebhook.Parts").getKeys(false)) {
                int pos = Integer.parseInt(rawPos);
                String partTitle = config.getString("DiscordWebhook.Parts." + rawPos + ".Title");
                String partDescription = config.getString("DiscordWebhook.Parts." + rawPos + ".Description");

                String player = valueManager.getPlayerNameWithPosition(pos, tournament);
                String score = String.valueOf(valueManager.getPlayerScoreWithPosition(pos, tournament));

                partDescription = partDescription.replace("%player%", player).replace("%score%", score);

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
