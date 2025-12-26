package me.waterarchery.littournaments.listeners.tournaments;

import com.badbones69.crazycrates.api.events.CrateOpenEvent;
import com.badbones69.crazycrates.api.objects.Crate;
import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.CrazyCrateTournament;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class CrazyCrateOpenListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCrateOpen(CrateOpenEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();

        Player player = event.getPlayer();
        Crate crate = event.getCrate();

        List<Tournament> tournaments = tournamentManager.getTournaments(CrazyCrateTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), crate.getName(), 1);
        }
    }

}
