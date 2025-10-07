package me.waterarchery.littournaments.listeners.tournaments;

import com.tcoded.folialib.wrapper.task.WrappedTask;
import lombok.Getter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.PlayTimeTournament;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public class PlayTimeListener {

    private static PlayTimeListener instance;
    private final WrappedTask task;

    public synchronized static PlayTimeListener getInstance() {
        if (instance == null) instance = new PlayTimeListener();
        return instance;
    }

    private PlayTimeListener() {
        PointHandler pointHandler = PointHandler.getInstance();
        task = LitTournaments.getFoliaLib().getScheduler().runTimerAsync(() -> {
            TournamentHandler tournamentHandler = TournamentHandler.getInstance();
            List<Tournament> tournaments = tournamentHandler.getTournaments(PlayTimeTournament.class);

            for (Tournament tournament : tournaments) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    World world = player.getWorld();
                    LitTournaments.getFoliaLib().getScheduler().runNextTick((task) -> {
                        pointHandler.addPoint(player.getUniqueId(), tournament, world.getName(), "none", 1);
                    });
                }
            }
        }, 20 * 60, 20 * 60);
    }
}
