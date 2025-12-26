package me.waterarchery.littournaments.listeners.tournaments;

import me.realized.duels.api.event.match.MatchEndEvent;
import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.DuelsWinTournament;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public class DuelsWinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCrateOpen(MatchEndEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();

        UUID winner = event.getWinner();
        Location location = event.getMatch().getArena().getPosition(0);

        List<Tournament> tournaments = tournamentManager.getTournaments(DuelsWinTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(winner, tournament, location.getWorld().getName(), "none", 1);
        }
    }

}
