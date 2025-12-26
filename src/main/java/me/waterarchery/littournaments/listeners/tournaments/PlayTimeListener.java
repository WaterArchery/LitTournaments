package me.waterarchery.littournaments.listeners.tournaments;

import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.libs.folia.wrapper.task.WrappedTask;
import lombok.Getter;
import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
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
        PointManager pointManager = PointManager.getInstance();
        task = ChickenUtils.getFoliaLib().getScheduler().runTimerAsync(() -> {
            TournamentManager tournamentManager = TournamentManager.getInstance();
            List<Tournament> tournaments = tournamentManager.getTournaments(PlayTimeTournament.class);

            for (Tournament tournament : tournaments) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    World world = player.getWorld();
                    ChickenUtils.getFoliaLib().getScheduler().runNextTick((task) -> {
                        pointManager.addPoint(player.getUniqueId(), tournament, world.getName(), "none", 1);
                    });
                }
            }
        }, 20 * 60, 20 * 60);
    }
}
