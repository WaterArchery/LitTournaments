package me.waterarchery.littournaments.guis;

import com.chickennw.utils.libs.themoep.inventorygui.GuiElement;
import com.chickennw.utils.libs.themoep.inventorygui.InventoryGui;
import com.chickennw.utils.libs.themoep.inventorygui.StaticGuiElement;
import com.chickennw.utils.models.menus.LitMenu;
import com.chickennw.utils.utils.ChatUtils;
import com.chickennw.utils.utils.ConfigUtils;
import com.chickennw.utils.utils.SoundUtils;
import lombok.extern.slf4j.Slf4j;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.configurations.SoundsFile;
import me.waterarchery.littournaments.managers.PlayerManager;
import me.waterarchery.littournaments.managers.TournamentManager;
import me.waterarchery.littournaments.managers.ValueManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;

@Slf4j
public class TournamentGUI extends LitMenu {

    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    private final TournamentPlayer tournamentPlayer;

    public TournamentGUI(Player player) {
        super("tournaments", player);

        PlayerManager playerManager = PlayerManager.getInstance();
        this.tournamentPlayer = playerManager.getPlayer(player.getUniqueId());
    }

    public List<GuiElement> getGuiElements() {
        List<GuiElement> elements = new ArrayList<>();
        yaml.getConfigurationSection("items").getKeys(false).forEach((key) -> {
            char symbol = Objects.requireNonNull(yaml.getString("items." + key + ".symbol")).charAt(0);
            ItemStack created = createItemStack(player.getUniqueId(), "items." + key);
            Tournament tournament = TournamentManager.getInstance().getTournament(key);

            if (tournament != null) {
                parseLore(created, tournamentPlayer, tournament);
            }

            GuiElement.Action action = (click) -> {
                SoundsFile soundsFile = ConfigUtils.get(SoundsFile.class);
                LangFile langFile = ConfigUtils.get(LangFile.class);
                if (tournament == null) return true;

                if (click.getType() == ClickType.LEFT) {
                    if (tournamentPlayer.isRegistered(tournament)) {
                        ChatUtils.sendPrefixedMessage(player, langFile.getAlreadyJoined());
                        SoundUtils.sendSoundRaw(player, soundsFile.getAlreadyJoined());
                        player.closeInventory();
                    } else {
                        tournamentPlayer.join(tournament);
                        ChatUtils.sendPrefixedMessage(player, langFile.getSuccessfullyRegistered());
                        SoundUtils.sendSoundRaw(player, soundsFile.getSuccessfullyJoined());
                        redraw(player);
                    }
                } else if (click.getType() == ClickType.RIGHT) {
                    LeaderboardGUI leaderboardGUI = new LeaderboardGUI(player, tournament);
                    leaderboardGUI.openAsync(player);
                }

                return true;
            };

            elements.add(new StaticGuiElement(symbol, created, action));
        });
        return elements;
    }

    @Override
    public HashMap<String, GuiElement.Action> getGuiActions() {
        return new HashMap<>();
    }

    @Override
    public String parsePlaceholder(String part) {
        return part;
    }

    @Override
    public List<String> parsePlaceholderAsList(String s) {
        return List.of(s);
    }

    @Override
    public InventoryGui.CloseAction getCloseAction() {
        return (action) -> true;
    }

    private void parseLore(ItemStack itemStack, TournamentPlayer tournamentPlayer, Tournament tournament) {
        ValueManager valueManager = ValueManager.getInstance();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String part : Objects.requireNonNull(itemMeta.getLore())) {
            part = part.replace("%position%", ChatUtils.colorizeLegacy(valueManager.getPlayerPosition(tournamentPlayer, tournament)))
                    .replace("%stat%", ChatUtils.colorizeLegacy(valueManager.getPlayerScore(tournamentPlayer, tournament)))
                    .replace("%remaining_time%", ChatUtils.colorizeLegacy(valueManager.getRemainingTime(tournament)));

            List<String> otherPlaceholders = getPlaceholders(part);

            for (String placeholder : otherPlaceholders) {
                if (placeholder.contains("leader_score_formatted_")) {
                    int pos = Integer.parseInt(placeholder.replace("leader_score_formatted_", "").replace("%", ""));

                    String score = numberFormat.format(valueManager.getPlayerScoreWithPosition(pos, tournament));
                    part = part.replace(placeholder, score);
                } else if (placeholder.contains("leader_score_")) {
                    int pos = Integer.parseInt(placeholder.replace("leader_score_", "").replace("%", ""));

                    String score = String.valueOf(valueManager.getPlayerScoreWithPosition(pos, tournament));
                    part = part.replace(placeholder, score);
                }
                if (placeholder.contains("leader_name_")) {
                    int pos = Integer.parseInt(placeholder.replace("leader_name_", "").replace("%", ""));

                    part = part.replace(placeholder, valueManager.getPlayerNameWithPosition(pos, tournament));
                }
            }

            lore.add(part);
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    private List<String> getPlaceholders(String part) {
        List<String> placeholders = new ArrayList<>();
        boolean found = false;
        StringBuilder placeholder = new StringBuilder();

        for (char c : part.toCharArray()) {
            if (c == '%') {
                if (found) {
                    placeholder.append(c);
                    placeholders.add(placeholder.toString());
                    found = false;
                    placeholder = new StringBuilder();
                } else {
                    found = true;
                }
            }

            if (!found) continue;
            placeholder.append(c);
        }

        return placeholders;
    }
}
