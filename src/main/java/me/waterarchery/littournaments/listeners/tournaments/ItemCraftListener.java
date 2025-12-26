package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.ItemCraftTournament;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemCraftListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemCraft(CraftItemEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();

        if (event.getWhoClicked() instanceof Player player) {
            ItemStack itemStack = event.getCurrentItem();
            World world = player.getWorld();
            if (itemStack == null) return;
            List<Tournament> tournaments = tournamentManager.getTournaments(ItemCraftTournament.class);
            for (Tournament tournament : tournaments) {
                pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), itemStack.getType().name(), 1);
            }
        }
    }

}
