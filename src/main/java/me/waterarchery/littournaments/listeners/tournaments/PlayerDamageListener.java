package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
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
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();

        if (event.getDamager() instanceof Player player) {
            Entity entity = event.getEntity();
            World world = player.getWorld();
            int damage = (int) event.getDamage();

            List<Tournament> tournaments = tournamentManager.getTournaments(PlayerDamageTournament.class);
            for (Tournament tournament : tournaments) {
                pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), entity.getType().name(), damage);
            }
        }
    }

}
