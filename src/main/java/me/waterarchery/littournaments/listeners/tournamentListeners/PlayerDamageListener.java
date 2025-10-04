package me.waterarchery.littournaments.listeners.tournamentListeners;

import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.PlayerDamageTournament;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PlayerDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();

        if (event.getDamager() instanceof Player player) {
            Entity entity = event.getEntity();
            World world = player.getWorld();
            int damage = (int) event.getDamage();

            List<Tournament> tournaments = tournamentHandler.getTournaments(PlayerDamageTournament.class);
            for (Tournament tournament : tournaments) {
                pointHandler.addPoint(player.getUniqueId(), tournament, world.getName(), entity.getType().name(), damage);
            }
        }
    }

}
