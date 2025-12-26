package me.waterarchery.littournaments.listeners.tournaments;

import me.rivaldev.harvesterhoes.api.events.RivalBlockBreakEvent;
import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.RivalHoeTournament;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class RivalHoeListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(RivalBlockBreakEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Player player = event.getPlayer();
        World world = player.getWorld();
        Material type = event.getCrop().getType();
        int amount = event.getAmount();

        List<Tournament> tournaments = tournamentManager.getTournaments(RivalHoeTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), type.name(), amount);
        }
    }

}
