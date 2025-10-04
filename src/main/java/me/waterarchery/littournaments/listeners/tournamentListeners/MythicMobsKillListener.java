package me.waterarchery.littournaments.listeners.tournamentListeners;

import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.MythicMobsKillTournament;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class MythicMobsKillListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMythicMobDeath(MythicMobDeathEvent event) {
        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Entity victim = event.getEntity();
        World world = victim.getWorld();
        String mobName = event.getMobType().getInternalName();

        if (event.getKiller() instanceof Player player) {
            List<Tournament> tournaments = tournamentHandler.getTournaments(MythicMobsKillTournament.class);
            for (Tournament tournament : tournaments) {
                pointHandler.addPoint(player.getUniqueId(), tournament, world.getName(), mobName, 1);
            }
        }
    }

}
