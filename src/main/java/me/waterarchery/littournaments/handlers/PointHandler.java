package me.waterarchery.littournaments.handlers;


import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.api.events.PointAddEvent;
import me.waterarchery.littournaments.database.Database;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PointHandler {

    private static PointHandler instance;

    public static PointHandler getInstance() {
        if (instance == null) instance = new PointHandler();
        return instance;
    }

    private PointHandler() {
    }

    public void addPoint(UUID uuid, Tournament tournament, String worldName, String actionName, int point) {
        if (tournament.checkWorldEnabled(worldName) && tournament.checkActionAllowed(actionName)) {
            addPoint(uuid, tournament, point, actionName);
        }
    }

    public void addPoint(UUID uuid, Tournament tournament, int point, String actionName) {
        if (!tournament.isActive()) return;

        LitLibs libs = LitTournaments.getLitLibs();
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        TournamentPlayer tournamentPlayer = playerHandler.getPlayer(uuid);

        if (tournamentPlayer == null) return;

        if (tournamentPlayer.isRegistered(tournament)) {
            LitTournaments.getFoliaLib().getScheduler().runNextTick((task) -> {
                PointAddEvent event = new PointAddEvent(tournament, uuid, point, actionName);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) return;

                Database database = LitTournaments.getDatabase();
                database.addPoint(uuid, tournament, point);

                HashMap<Tournament, Long> map = tournamentPlayer.getTournamentValueMap();
                long currentPoint = map.getOrDefault(tournament, 0L);
                map.replace(tournament, currentPoint + point);
            });
        } else if (tournamentPlayer.isLoading()) {
            Player player = Bukkit.getPlayer(uuid);
            libs.getMessageHandler().sendLangMessage(player, "StillLoading");
        }
    }

}
