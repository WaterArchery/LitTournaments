package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.ItemBreakTournament;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemCraft(PlayerItemBreakEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Player player = event.getPlayer();
        ItemStack itemStack = event.getBrokenItem();
        World world = player.getWorld();

        List<Tournament> tournaments = tournamentManager.getTournaments(ItemBreakTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), itemStack.getType().name(), 1);
        }
    }

}
