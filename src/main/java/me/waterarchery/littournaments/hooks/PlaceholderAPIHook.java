package me.waterarchery.littournaments.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.waterarchery.littournaments.handlers.PlayerHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.handlers.ValueHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getAuthor() {
        return "WaterArchery";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "littournaments";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        ValueHandler valueHandler = ValueHandler.getInstance();
        TournamentPlayer tournamentPlayer = playerHandler.getPlayer(player.getUniqueId());

        if (params.endsWith("_position")) {
            String tournamentName = params.replace("_position", "");
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return valueHandler.getPlayerPosition(tournamentPlayer, tournament);
            }
        } else if (params.endsWith("_score")) {
            String tournamentName = params.replace("_score", "");
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return valueHandler.getPlayerScore(tournamentPlayer, tournament);
            }
        } else if (params.contains("_score_")) {
            String tournamentName = params.split("_score_")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                int pos = Integer.parseInt(params.split("_score_")[1]);
                return valueHandler.getPlayerScoreWithPosition(pos, tournament) + "";
            }
        } else if (params.contains("_player_")) {
            String tournamentName = params.split("_player_")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                int pos = Integer.parseInt(params.split("_player_")[1]);
                return valueHandler.getPlayerNameWithPosition(pos, tournament);
            }
        } else if (params.contains("_remaining_time_days")) {
            String tournamentName = params.split("_remaining_time_days")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return tournament.getRemainingTime().toDays() + "";
            }
        } else if (params.contains("_remaining_time_hours")) {
            String tournamentName = params.split("_remaining_time_hours")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return tournament.getRemainingTime().toHours() + "";
            }
        } else if (params.contains("_remaining_time_minutes")) {
            String tournamentName = params.split("_remaining_time_minutes")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return tournament.getRemainingTime().toMinutes() + "";
            }
        } else if (params.contains("_remaining_time_seconds")) {
            String tournamentName = params.split("_remaining_time_seconds")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return tournament.getRemainingTime().toSeconds() + "";
            }
        } else if (params.contains("_remaining_time")) {
            String tournamentName = params.split("_remaining_time")[0];
            Tournament tournament = tournamentHandler.getTournament(tournamentName);
            if (tournament != null) {
                return valueHandler.getRemainingTime(tournament);
            }
        }

        return null;
    }

    public void unRegister() {
        unregister();
    }

}