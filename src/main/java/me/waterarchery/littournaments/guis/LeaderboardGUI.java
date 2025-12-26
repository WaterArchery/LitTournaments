package me.waterarchery.littournaments.guis;

import com.chickennw.utils.libs.themoep.inventorygui.GuiElement;
import com.chickennw.utils.libs.themoep.inventorygui.InventoryGui;
import com.chickennw.utils.models.menus.LitMenu;
import me.waterarchery.littournaments.models.Tournament;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LeaderboardGUI extends LitMenu {

    private final Tournament tournament;
    private final boolean sendMessage;

    public LeaderboardGUI(Player player, Tournament tournament, boolean sendMessage) {
        super("leaderboard", player);
        this.tournament = tournament;
        this.sendMessage = sendMessage;
    }

    @Override
    public HashMap<String, GuiElement.Action> getGuiActions() {
        return null;
    }

    @Override
    public String parsePlaceholder(String s) {
        return "";
    }

    @Override
    public List<String> parsePlaceholderAsList(String s) {
        return List.of();
    }

    @Override
    public InventoryGui.CloseAction getCloseAction() {
        return null;
    }

    private static void parseItemLore(ItemStack itemStack, OfflinePlayer player, String value, String pos) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> newLore = new ArrayList<>();

        assert itemMeta != null;
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("%player%", Objects.requireNonNull(player.getName())));

        Objects.requireNonNull(itemMeta.getLore()).forEach(part -> newLore.add(part.replace("%name%", player.getName()).replace("%stat%", value).replace(
                "%position%", pos)));
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
    }
}
