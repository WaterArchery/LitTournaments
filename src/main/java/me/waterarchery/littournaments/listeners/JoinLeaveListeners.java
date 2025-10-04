package me.waterarchery.littournaments.listeners;

import me.waterarchery.littournaments.handlers.PlayerHandler;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinLeaveListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        Player player = event.getPlayer();

        TournamentPlayer tournamentPlayer = new TournamentPlayer(player.getUniqueId());
        playerHandler.getPlayers().add(tournamentPlayer);
        playerHandler.initializePlayer(tournamentPlayer, true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerLeave(PlayerJoinEvent event) {
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        Player player = event.getPlayer();

        playerHandler.getPlayers().removeIf(tPlayer -> tPlayer.getUUID().equals(player.getUniqueId()));
    }

}
