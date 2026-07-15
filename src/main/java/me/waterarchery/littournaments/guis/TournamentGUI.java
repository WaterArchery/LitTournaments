package me.waterarchery.littournaments.guis;

import com.chickennw.utils.libs.gui.components.GuiAction;
import com.chickennw.utils.libs.gui.guis.GuiItem;
import com.chickennw.utils.managers.MenuManager;
import com.chickennw.utils.models.menus.constants.MenuType;
import com.chickennw.utils.models.menus.triumph.TriumphMenuWrapper;
import com.chickennw.utils.models.menus.triumph.item.TriumphItemHolder;
import com.chickennw.utils.utils.ChatUtils;
import com.chickennw.utils.utils.ConfigUtils;
import com.chickennw.utils.utils.SoundUtils;
import lombok.extern.slf4j.Slf4j;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.configurations.SoundsFile;
import me.waterarchery.littournaments.configurations.menus.TournamentMenuFile;
import me.waterarchery.littournaments.managers.PlayerManager;
import me.waterarchery.littournaments.managers.TournamentManager;
import me.waterarchery.littournaments.managers.ValueManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;

@Slf4j
public class TournamentGUI extends TriumphMenuWrapper {

    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
    private final Player player;
    private final TournamentPlayer tournamentPlayer;

    public TournamentGUI(Player player) {
        super(ConfigUtils.get(TournamentMenuFile.class), MenuType.SIMPLE);
        this.player = player;
        this.tournamentPlayer = PlayerManager.getInstance().getPlayer(player.getUniqueId());
    }

    @Override
    public List<TriumphItemHolder> getMenuItems(OfflinePlayer offlinePlayer) {
        List<TriumphItemHolder> items = new ArrayList<>();
        MenuManager menuManager = MenuManager.getInstance();

        menuConfiguration.getItems().forEach((key, menuItem) -> {
            try {
                Tournament tournament = TournamentManager.getInstance().getTournament(key);
                ItemStack itemStack = menuManager.parseMenuItem(offlinePlayer, menuItem, placeholderParser());

                if (tournament != null) parseLore(itemStack, tournament);

                GuiItem guiItem = new GuiItem(itemStack, event -> handleClick(event, tournament));
                items.add(new TriumphItemHolder(guiItem, parseSlots(menuItem.getSymbol())));
            } catch (Exception ex) {
                log.error("Couldn't create tournament menu item: {}", key, ex);
            }
        });

        return items;
    }

    private void handleClick(InventoryClickEvent event, Tournament tournament) {
        event.setCancelled(true);
        if (tournament == null) return;

        SoundsFile soundsFile = ConfigUtils.get(SoundsFile.class);
        LangFile langFile = ConfigUtils.get(LangFile.class);

        if (event.getClick() == ClickType.LEFT) {
            if (tournamentPlayer.isRegistered(tournament)) {
                ChatUtils.sendPrefixedMessage(player, langFile.getAlreadyJoined());
                SoundUtils.sendSoundRaw(player, soundsFile.getAlreadyJoined());
                player.closeInventory();
            } else {
                tournamentPlayer.join(tournament);
                ChatUtils.sendPrefixedMessage(player, langFile.getSuccessfullyRegistered());
                SoundUtils.sendSoundRaw(player, soundsFile.getSuccessfullyJoined());
                redraw();
            }
        } else if (event.getClick() == ClickType.RIGHT) {
            new LeaderboardGUI(player, tournament).openAsync(player);
        }
    }

    private void redraw() {
        if (cachedGui == null) return;
        createItems(player);
        cachedGui.update();
    }

    @Override
    public Function<String, String> placeholderParser() {
        return part -> part;
    }

    @Override
    public Runnable nextPageRunnable() {
        return () -> {};
    }

    @Override
    public Runnable previousPageRunnable() {
        return () -> {};
    }

    @Override
    public HashMap<String, GuiAction<InventoryClickEvent>> premadeGuiActions() {
        return new HashMap<>();
    }

    @Override
    public GuiAction<InventoryClickEvent> getDefaultClickAction() {
        return event -> event.setCancelled(true);
    }

    @Override
    public GuiAction<InventoryClickEvent> getDefaultTopClickAction() {
        return event -> event.setCancelled(true);
    }

    @Override
    public GuiAction<InventoryCloseEvent> getCloseGuiAction() {
        return null;
    }

    private void parseLore(ItemStack itemStack, Tournament tournament) {
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
