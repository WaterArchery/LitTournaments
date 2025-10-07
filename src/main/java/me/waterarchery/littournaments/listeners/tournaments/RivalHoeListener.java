package me.waterarchery.littournaments.listeners.tournaments;

import me.rivaldev.harvesterhoes.api.events.RivalBlockBreakEvent;
import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
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
        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Player player = event.getPlayer();
        World world = player.getWorld();
        Material type = event.getCrop().getType();
        int amount = event.getAmount();

        List<Tournament> tournaments = tournamentHandler.getTournaments(RivalHoeTournament.class);
        for (Tournament tournament : tournaments) {
            pointHandler.addPoint(player.getUniqueId(), tournament, world.getName(), type.name(), amount);
        }
    }

}
