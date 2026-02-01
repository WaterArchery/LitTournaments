package me.waterarchery.littournaments.managers;

import com.chickennw.utils.logger.LoggerFactory;
import com.chickennw.utils.utils.ChatUtils;
import com.chickennw.utils.utils.ConfigUtils;
import lombok.Getter;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.models.JoinChecker;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class PlayerManager {

    private static PlayerManager instance;
    private final ConcurrentHashMap<UUID, TournamentPlayer> players = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger();

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }

    public TournamentPlayer getPlayer(UUID uuid) {
        TournamentPlayer tournamentPlayer = players.get(uuid);
        if (tournamentPlayer != null) return tournamentPlayer;

        return tryToLoadPlayer(uuid);
    }

    private TournamentPlayer tryToLoadPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            TournamentPlayer tournamentPlayer = new TournamentPlayer(uuid);
            players.put(tournamentPlayer.getUUID(), tournamentPlayer);
            initializePlayer(tournamentPlayer, false);
            return tournamentPlayer;
        }

        return null;
    }

    public void clearPlayerValues(Tournament tournament) {
        for (TournamentPlayer player : players.values()) {
            if (player.getTournamentValueMap().get(tournament.getIdentifier()) != null)
                player.getTournamentValueMap().replace(tournament.getIdentifier(), 0L);
        }
    }

    public void initializePlayer(TournamentPlayer player, boolean isJoinNow) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        List<Tournament> tournaments = tournamentManager.getTournaments();
        TournamentDatabase database = TournamentDatabase.getInstance();
        player.setLoading(true);

        CompletableFuture.supplyAsync(() -> {
            HashMap<Tournament, Long> pointMap = new HashMap<>();
            Player bukkitPlayer = Bukkit.getPlayer(player.getUUID());
            if (bukkitPlayer == null) return null;

            for (Tournament tournament : tournaments) {
                // Returns -9999 if player's data is not exist
                long point = database.getPoint(player.getUUID(), tournament);
                if (point != -9999) {
                    // Loading existing tournaments
                    pointMap.put(tournament, point);
                } else if (isJoinNow) {
                    // Joining it if player can join
                    JoinChecker joinChecker = tournament.getJoinChecker();
                    UUID uuid = player.getUUID();

                    if (joinChecker.isAutoJoinEnabled() && joinChecker.canJoin(uuid)) {
                        if (joinChecker.isMessageOnAutoJoin()) {
                            LangFile langFile = ConfigUtils.get(LangFile.class);
                            ChatUtils.sendPrefixedMessage(bukkitPlayer, langFile.getSuccessfullyRegisteredOnJoin());
                        }

                        player.join(tournament);
                    }
                }
            }

            return pointMap;
        }, database.getExecutor()).thenAccept((map) -> {
            if (map == null) throw new NullPointerException("Map is null");

            map.forEach((k, v) -> player.getTournamentValueMap().put(k.getIdentifier(), v));
            player.setLoading(false);
        });
    }
}
