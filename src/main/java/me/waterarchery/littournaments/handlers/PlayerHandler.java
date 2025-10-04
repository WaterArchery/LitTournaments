package me.waterarchery.littournaments.handlers;

import lombok.Getter;
import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.database.Database;
import me.waterarchery.littournaments.models.JoinChecker;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class PlayerHandler {

    private static PlayerHandler instance;
    private final List<TournamentPlayer> players = new ArrayList<>();

    public static PlayerHandler getInstance() {
        if (instance == null) instance = new PlayerHandler();
        return instance;
    }

    private PlayerHandler() {
    }

    public TournamentPlayer getPlayer(UUID uuid) {
        for (TournamentPlayer tournamentPlayer : players) {
            if (tournamentPlayer.getUUID().equals(uuid)) return tournamentPlayer;
        }

        return tryToLoadPlayer(uuid);
    }

    private TournamentPlayer tryToLoadPlayer(UUID uuid) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(uuid)) {
                TournamentPlayer tournamentPlayer = new TournamentPlayer(uuid);
                players.add(tournamentPlayer);
                initializePlayer(tournamentPlayer, false);
                return tournamentPlayer;
            }
        }

        return null;
    }

    public void clearPlayerValues(Tournament tournament) {
        for (TournamentPlayer player : players) {
            if (player.getTournamentValueMap().get(tournament) != null)
                player.getTournamentValueMap().replace(tournament, 0L);
        }
    }

    public void initializePlayer(TournamentPlayer player, boolean isJoinNow) {
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        List<Tournament> tournaments = tournamentHandler.getTournaments();
        Database database = LitTournaments.getDatabase();

        player.setLoading(true);

        CompletableFuture.supplyAsync(() -> {
            HashMap<Tournament, Long> pointMap = new HashMap<>();
            LitLibs libs = LitTournaments.getLitLibs();
            Player bukkitPlayer = Bukkit.getPlayer(player.getUUID());

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
                        if (joinChecker.isMessageOnAutoJoin() && bukkitPlayer != null)
                            libs.getMessageHandler().sendLangMessage(bukkitPlayer, "SuccessfullyRegisteredOnJoin");
                        player.join(tournament);
                    }
                }
            }

            return pointMap;
        }).thenAccept((map) -> {
            player.getTournamentValueMap().putAll(map);
            player.setLoading(false);
        });
    }

}
