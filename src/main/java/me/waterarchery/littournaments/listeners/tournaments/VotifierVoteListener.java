package me.waterarchery.littournaments.listeners.tournaments;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.PlayerVoteTournament;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class VotifierVoteListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVotifierVote(VotifierEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Vote vote = event.getVote();
        String playerName = vote.getUsername();
        String service = vote.getServiceName();
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) return;

        List<Tournament> tournaments = tournamentManager.getTournaments(PlayerVoteTournament.class);
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), service, 1);
        }
    }

}
