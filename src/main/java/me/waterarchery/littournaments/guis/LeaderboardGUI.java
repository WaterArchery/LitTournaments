package me.waterarchery.littournaments.guis;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.libs.adventure.text.Component;
import me.waterarchery.litlibs.libs.gui.builder.item.ItemBuilder;
import me.waterarchery.litlibs.libs.gui.guis.Gui;
import me.waterarchery.litlibs.libs.gui.guis.GuiItem;
import me.waterarchery.litlibs.libs.gui.guis.PaginatedGui;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.handlers.FileHandler;
import me.waterarchery.littournaments.handlers.GUIHandler;
import me.waterarchery.littournaments.handlers.PlayerHandler;
import me.waterarchery.littournaments.handlers.ValueHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import me.waterarchery.littournaments.models.TournamentValue;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaderboardGUI {

    public static void openMenu(Player player, Tournament tournament, boolean sendMessage) {
        GUIHandler guiHandler = GUIHandler.getInstance();
        ConfigManager manager = FileHandler.getLeaderboardMenu();
        FileConfiguration yml = manager.getYml();
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        ValueHandler valueHandler = ValueHandler.getInstance();
        LitLibs libs = LitTournaments.getLitLibs();

        if (sendMessage) libs.getMessageHandler().sendLangMessage(player, "LoadingLeaderboard");

        LitTournaments.getFoliaLib().getScheduler().runAsync((task) -> {
            String title = guiHandler.getMenuTitle(manager).replace("%tournament%", tournament.getCoolName());
            int size = guiHandler.getMenuSize(manager);

            PaginatedGui gui = Gui.paginated()
                    .title(Component.text(title))
                    .rows(size)
                    .disableAllInteractions()
                    .create();

            ItemStack nextPageItem = guiHandler.craftItemStack(manager, "nextPage", "Items", null);
            ItemStack previousPageItem = guiHandler.craftItemStack(manager, "previousPage", "Items", null);
            int nextPageSlot = yml.getInt("Items.nextPage.Slot");
            int previousPageSlot = yml.getInt("Items.previousPage.Slot");

            gui.setItem(nextPageSlot, ItemBuilder.from(nextPageItem).asGuiItem(event -> gui.next()));
            gui.setItem(previousPageSlot, ItemBuilder.from(previousPageItem).asGuiItem(event -> gui.previous()));

            // Adding decoration items into GUI
            guiHandler.decorateGUI(yml, gui, manager);

            // Adding player heads into GUI
            ItemStack ownPlayer = guiHandler.craftItemStack(manager, "ownPlayer", "Items", player.getUniqueId());
            int ownPlayerSlot = yml.getInt("Items.ownPlayer.Slot");
            TournamentPlayer tournamentPlayer = playerHandler.getPlayer(player.getUniqueId());
            String score = valueHandler.getPlayerScore(tournamentPlayer, tournament);
            String pos = valueHandler.getPlayerPosition(tournamentPlayer, tournament);

            parseItemLore(ownPlayer, player, score, pos);
            GuiItem playerItem = ItemBuilder.from(ownPlayer).asGuiItem();
            gui.setItem(ownPlayerSlot, playerItem);

            // Adding other players
            int limit = LitTournaments.getInstance().getConfig().getInt("LeaderboardLimit");
            int i = 0;
            for (TournamentValue value : tournament.getLeaderboard().getLeaderboard().values()) {
                if (i == limit) break;

                i++;
                ItemStack itemStack = guiHandler.craftItemStack(manager, "playerTemplate", "Items", value.uuid());
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(value.uuid());

                parseItemLore(itemStack, offlinePlayer, String.valueOf(value.value()), String.valueOf(i));

                GuiItem guiItem = ItemBuilder.from(itemStack)
                        .flags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                        .asGuiItem();
                gui.addItem(guiItem);
            }

            LitTournaments.getFoliaLib().getScheduler().runNextTick((task2) -> gui.open(player));
        });
    }

    private static void parseItemLore(ItemStack itemStack, OfflinePlayer player, String value, String pos) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> newLore = new ArrayList<>();

        assert itemMeta != null;
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("%player%",
                                                                  Objects.requireNonNull(player.getName())));

        Objects.requireNonNull(itemMeta.getLore()).forEach(part -> newLore.add(part
                                                                                       .replace("%name%", player.getName())
                                                                                       .replace("%stat%", value)
                                                                                       .replace("%position%", pos)));
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
    }

}
