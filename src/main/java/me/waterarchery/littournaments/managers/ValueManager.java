package me.waterarchery.littournaments.managers;

import com.chickennw.utils.utils.ConfigUtils;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentLeaderboard;
import me.waterarchery.littournaments.models.TournamentPlayer;
import me.waterarchery.littournaments.models.TournamentValue;

import java.time.Duration;

public class ValueManager {

    private static ValueManager instance;

    public static ValueManager getInstance() {
        if (instance == null) instance = new ValueManager();
        return instance;
    }

    private ValueManager() {
    }

    public String getPlayerScore(TournamentPlayer tournamentPlayer, Tournament tournament) {
        if (tournamentPlayer.isRegistered(tournament)) return String.valueOf(tournamentPlayer.getTournamentValueMap().get(tournament));

        return String.valueOf(0);
    }

    public String getPlayerPosition(TournamentPlayer tournamentPlayer, Tournament tournament) {
        if (tournamentPlayer.isRegistered(tournament)) return String.valueOf(tournament.getLeaderboard().getPlayerPos(tournamentPlayer));

        LangFile langFile = ConfigUtils.get(LangFile.class);
        return langFile.getPlaceholders().getNotRegistered();
    }

    public String getPlayerNameWithPosition(int position, Tournament tournament) {
        TournamentLeaderboard leaderboard = tournament.getLeaderboard();
        TournamentValue value = leaderboard.getPlayer(position).orElse(null);

        if (value != null) return value.getName();

        LangFile langFile = ConfigUtils.get(LangFile.class);
        return langFile.getPlaceholders().getNone();
    }

    public long getPlayerScoreWithPosition(int position, Tournament tournament) {
        TournamentLeaderboard leaderboard = tournament.getLeaderboard();
        TournamentValue value = leaderboard.getPlayer(position).orElse(null);

        if (value != null) return value.getValue();
        return 0;
    }

    public String getRemainingTime(Tournament tournament) {
        LangFile langFile = ConfigUtils.get(LangFile.class);
        if (!tournament.isActive()) {
            return langFile.getPlaceholders().getNotActive();
        }

        String remainingTime = langFile.getPlaceholders().getRemainingTime();
        Duration remaining = tournament.getRemainingTime();

        return remainingTime.replace("%day%", remaining.toDaysPart() + "")
                .replace("%hour%", remaining.toHoursPart() + "")
                .replace("%minute%", remaining.toMinutesPart() + "");
    }
}
