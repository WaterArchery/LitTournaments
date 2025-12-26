package me.waterarchery.littournaments.guis;

import com.chickennw.utils.libs.themoep.inventorygui.GuiElement;
import com.chickennw.utils.libs.themoep.inventorygui.InventoryGui;
import com.chickennw.utils.models.menus.LitMenu;
import me.waterarchery.littournaments.handlers.*;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;

public class TournamentGUI extends LitMenu {

    private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

    public TournamentGUI(Player player) {
        super("tournaments", player);
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

    private void parseLore(ItemStack itemStack, TournamentPlayer tournamentPlayer, Tournament tournament) {
        ValueManager valueManager = ValueManager.getInstance();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null && itemMeta.hasLore()) {
            List<String> lore = new ArrayList<>();

            for (String part : Objects.requireNonNull(itemMeta.getLore())) {
                part = part.replace("%position%", valueManager.getPlayerPosition(tournamentPlayer, tournament)).replace("%stat%",
                        valueManager.getPlayerScore(tournamentPlayer, tournament)).replace("%remaining_time%", valueManager.getRemainingTime(tournament));

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
