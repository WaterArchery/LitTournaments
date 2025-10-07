package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.BlockBreakTournament;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = block.getWorld();

        List<Tournament> tournaments = tournamentHandler.getTournaments(BlockBreakTournament.class);
        for (Tournament tournament : tournaments) {
            pointHandler.addPoint(player.getUniqueId(), tournament, world.getName(), block.getType().name(), 1);
        }
    }

}
