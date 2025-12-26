package me.waterarchery.littournaments.handlers;


import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.utils.ChatUtils;
import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.api.events.PointAddEvent;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PointManager {

    private static PointManager instance;

    public static PointManager getInstance() {
        if (instance == null) instance = new PointManager();
        return instance;
    }

    private PointManager() {
    }

    public void addPoint(UUID uuid, Tournament tournament, String worldName, String actionName, int point) {
        if (tournament.checkWorldEnabled(worldName) && tournament.checkActionAllowed(actionName)) {
            addPoint(uuid, tournament, point, actionName);
        }
    }

    public void addPoint(UUID uuid, Tournament tournament, int point, String actionName) {
        if (!tournament.isActive()) return;

        PlayerManager playerManager = PlayerManager.getInstance();
        TournamentPlayer tournamentPlayer = playerManager.getPlayer(uuid);

        if (tournamentPlayer == null) return;

        if (tournamentPlayer.isRegistered(tournament)) {
            ChickenUtils.getFoliaLib().getScheduler().runNextTick((task) -> {
                PointAddEvent event = new PointAddEvent(tournament, uuid, point, actionName);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) return;

                TournamentDatabase database = TournamentDatabase.getInstance();
                database.addPoint(uuid, tournament, point);

                HashMap<Tournament, Long> map = tournamentPlayer.getTournamentValueMap();
                long currentPoint = map.getOrDefault(tournament, 0L);
                map.replace(tournament, currentPoint + point);
            });
        } else if (tournamentPlayer.isLoading()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                LangFile langFile = ConfigUtils.get(LangFile.class);
                ChatUtils.sendPrefixedMessage(player, langFile.getStillLoading());
            }
        }
    }

}
