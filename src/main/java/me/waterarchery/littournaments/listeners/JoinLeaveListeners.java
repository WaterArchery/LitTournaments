package me.waterarchery.littournaments.listeners;

import me.waterarchery.littournaments.managers.PlayerManager;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        Player player = event.getPlayer();

        TournamentPlayer tournamentPlayer = new TournamentPlayer(player.getUniqueId());
        playerManager.getPlayers().put(tournamentPlayer.getUUID(), tournamentPlayer);
        playerManager.initializePlayer(tournamentPlayer, true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        PlayerManager playerManager = PlayerManager.getInstance();
        Player player = event.getPlayer();

        playerManager.getPlayers().remove(player.getUniqueId());
    }
}
