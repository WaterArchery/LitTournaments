package me.waterarchery.littournaments.guis;

import com.chickennw.utils.libs.themoep.inventorygui.GuiElement;
import com.chickennw.utils.libs.themoep.inventorygui.InventoryGui;
import com.chickennw.utils.models.menus.LitMenuItemHolder;
import com.chickennw.utils.models.menus.LitPaginatedMenu;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentValue;
import me.waterarchery.littournaments.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardGUI extends LitPaginatedMenu {

    private final Tournament tournament;
    private final boolean sendMessage;

    public LeaderboardGUI(Player player, Tournament tournament, boolean sendMessage) {
        super("leaderboard", player);
        this.tournament = tournament;
        this.sendMessage = sendMessage;
    }

    @Override
    public HashMap<String, GuiElement.Action> getGuiActions() {
        return new HashMap<>();
    }

    @Override
    public String parsePlaceholder(String placeholder) {
        return PlaceholderUtils.parseTournamentPlaceholders(placeholder, tournament);
    }

    @Override
    public List<String> parsePlaceholderAsList(String placeholder) {
        return List.of(placeholder);
    }

    @Override
    public InventoryGui.CloseAction getCloseAction() {
        return (close) -> false;
    }

    @Override
    public List<LitMenuItemHolder> getTemplateItems() {
        List<LitMenuItemHolder> items = new ArrayList<>();

        int i = 1;
        for (Map.Entry<Integer, TournamentValue> entry : tournament.getLeaderboard().getLeaderboard().entrySet()) {
            TournamentValue tournamentValue = entry.getValue();
            LitMenuItemHolder itemHolder = createItem(tournamentValue.getUuid(), "items.template-item");
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(tournamentValue.getUuid());
            ItemStack itemStack = getItemStack(itemHolder, offlinePlayer.getName(), tournamentValue, i);
            itemHolder.setItemStack(itemStack);
            items.add(itemHolder);

            itemHolder.getItem().setAction(click -> {
                return true;
            });

            i++;
        }

        return items;
    }

    private ItemStack getItemStack(LitMenuItemHolder itemHolder, String playerName, TournamentValue tournamentValue, int position) {
        ItemStack itemStack = itemHolder.getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        String itemName = itemMeta.getDisplayName();
        if (!itemName.equalsIgnoreCase("none")) itemMeta.setDisplayName(itemName.replace("%player-name%", playerName));

        List<String> oldLore = itemMeta.getLore();
        List<String> lore = new ArrayList<>();
        oldLore.forEach(part -> {
            part = PlaceholderUtils.parseTournamentLeaderboardPlaceholders(part, tournamentValue, position)
                    .replace("%player-name%", playerName);
            lore.add(part);
        });
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
