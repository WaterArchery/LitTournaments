package me.waterarchery.littournaments.guis;

import com.chickennw.utils.configurations.menu.triumph.MenuFiller;
import com.chickennw.utils.configurations.menu.triumph.SymboledMenuItem;
import com.chickennw.utils.libs.gui.builder.item.ItemBuilder;
import com.chickennw.utils.libs.gui.components.GuiAction;
import com.chickennw.utils.libs.gui.guis.GuiItem;
import com.chickennw.utils.libs.gui.guis.PaginatedGui;
import com.chickennw.utils.managers.MenuManager;
import com.chickennw.utils.models.menus.constants.MenuType;
import com.chickennw.utils.models.menus.triumph.TriumphMenuWrapper;
import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.configurations.menus.LeaderboardMenuFile;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentValue;
import me.waterarchery.littournaments.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LeaderboardGUI extends TriumphMenuWrapper {

    private final Player player;
    private final Tournament tournament;

    public LeaderboardGUI(Player player, Tournament tournament) {
        super(ConfigUtils.get(LeaderboardMenuFile.class), MenuType.PAGINATED);
        this.player = player;
        this.tournament = tournament;
    }

    @Override
    public void createItems(OfflinePlayer offlinePlayer) {
        super.createItems(offlinePlayer);
        if (!(cachedGui instanceof PaginatedGui gui)) return;

        MenuManager menuManager = MenuManager.getInstance();
        SymboledMenuItem template = menuConfiguration.getTemplateItem();
        if (template == null) return;
        List<Integer> templateSlots = parseSlots(template.getSymbol());

        MenuFiller filler = menuConfiguration.getFiller();
        if (filler != null && filler.isEnabled()) {
            ItemStack fillerStack = menuManager.parseMenuItem(null, filler.getFillerItem(), null);
            int totalSlots = menuConfiguration.getPattern().size() * 9;
            for (int slot = 0; slot < totalSlots; slot++) {
                if (templateSlots.contains(slot) || gui.getGuiItems().containsKey(slot)) continue;
                gui.setItem(slot, new GuiItem(fillerStack));
            }
        }

        gui.setPageSize(templateSlots.size());
        int position = 1;
        for (Map.Entry<Integer, TournamentValue> entry : tournament.getLeaderboard().getLeaderboard().entrySet()) {
            TournamentValue tournamentValue = entry.getValue();
            OfflinePlayer leaderboardPlayer = Bukkit.getOfflinePlayer(tournamentValue.getUuid());

            ItemStack itemStack = menuManager.parseMenuItem(leaderboardPlayer, template, placeholderParser());
            applyLeaderboardPlaceholders(itemStack, leaderboardPlayer.getName(), tournamentValue, position);

            gui.addItem(ItemBuilder.from(itemStack).asGuiItem(event -> event.setCancelled(true)));
            position++;
        }
    }

    private void applyLeaderboardPlaceholders(ItemStack itemStack, String playerName, TournamentValue tournamentValue, int position) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        String itemName = itemMeta.getDisplayName();
        if (playerName != null && !itemName.equalsIgnoreCase("none")) {
            itemMeta.setDisplayName(itemName.replace("%player-name%", playerName));
        }

        List<String> oldLore = itemMeta.getLore();
        if (oldLore != null) {
            List<String> lore = new java.util.ArrayList<>();
            for (String part : oldLore) {
                part = PlaceholderUtils.parseTournamentLeaderboardPlaceholders(part, tournamentValue, position);
                if (playerName != null) part = part.replace("%player-name%", playerName);
                lore.add(part);
            }
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
    }

    @Override
    public Function<String, String> placeholderParser() {
        return placeholder -> PlaceholderUtils.parseTournamentPlaceholders(placeholder, tournament);
    }

    @Override
    public Runnable nextPageRunnable() {
        return () -> {
            super.createItems(player);
            update();
        };
    }

    @Override
    public Runnable previousPageRunnable() {
        return () -> {
            super.createItems(player);
            update();
        };
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
}
