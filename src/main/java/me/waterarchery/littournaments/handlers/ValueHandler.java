package me.waterarchery.littournaments.handlers;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentLeaderboard;
import me.waterarchery.littournaments.models.TournamentPlayer;
import me.waterarchery.littournaments.models.TournamentValue;

import java.time.Duration;

public class ValueHandler {

    private static ValueHandler instance;

    public static ValueHandler getInstance() {
        if (instance == null) instance = new ValueHandler();
        return instance;
    }

    private ValueHandler() {
    }

    public String getPlayerScore(TournamentPlayer tournamentPlayer, Tournament tournament) {
        if (tournamentPlayer.isRegistered(tournament)) return tournamentPlayer.getTournamentValueMap().get(tournament) + "";

        return 0 + "";
    }

    public String getPlayerPosition(TournamentPlayer tournamentPlayer, Tournament tournament) {
        if (tournamentPlayer.isRegistered(tournament)) return tournament.getLeaderboard().getPlayerPos(tournamentPlayer) + "";

        LitLibs libs = LitTournaments.getLitLibs();
        return libs.getMessageHandler().getLangMessage("Placeholders.NotRegistered");
    }

    public String getPlayerNameWithPosition(int position, Tournament tournament) {
        TournamentLeaderboard leaderboard = tournament.getLeaderboard();
        TournamentValue value = leaderboard.getPlayer(position).orElse(null);

        if (value != null) return value.getName();

        LitLibs libs = LitTournaments.getLitLibs();
        return libs.getMessageHandler().getLangMessage("Placeholders.None");
    }

    public long getPlayerScoreWithPosition(int position, Tournament tournament) {
        TournamentLeaderboard leaderboard = tournament.getLeaderboard();
        TournamentValue value = leaderboard.getPlayer(position).orElse(null);

        if (value != null) return value.value();

        return 0;
    }

    public String getRemainingTime(Tournament tournament) {
        LitLibs libs = LitTournaments.getLitLibs();

        if (!tournament.isActive()) {
            return libs.getMessageHandler().getLangMessage("Placeholders.NotActive");
        }

        String remainingTime = libs.getMessageHandler().getLangMessage("Placeholders.RemainingTime");
        Duration remaining = tournament.getRemainingTime();

        return remainingTime.replace("%day%", remaining.toDaysPart() + "")
                .replace("%hour%", remaining.toHoursPart() + "")
                .replace("%minute%", remaining.toMinutesPart() + "");
    }

}
