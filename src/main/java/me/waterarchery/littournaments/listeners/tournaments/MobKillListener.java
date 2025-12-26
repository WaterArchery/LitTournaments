package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.MobKillTournament;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class MobKillListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobKill(EntityDeathEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Entity victim = event.getEntity();
        EntityDamageEvent damageEvent = victim.getLastDamageCause();
        World world = victim.getWorld();

        if (damageEvent == null || damageEvent.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;

        Player player = event.getEntity().getKiller();
        if (player == null) return;

        List<Tournament> tournaments = tournamentManager.getTournaments(MobKillTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), victim.getType().name(), 1);
        }
    }

}
