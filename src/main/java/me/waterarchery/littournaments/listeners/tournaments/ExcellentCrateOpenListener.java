package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.ExcellentCrateTournament;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import su.nightexpress.excellentcrates.api.event.CrateOpenEvent;

import java.util.List;

public class ExcellentCrateOpenListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCrateOpen(CrateOpenEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();

        Player player = event.getPlayer();

        List<Tournament> tournaments = tournamentManager.getTournaments(ExcellentCrateTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), "none", 1);
        }
    }

}
