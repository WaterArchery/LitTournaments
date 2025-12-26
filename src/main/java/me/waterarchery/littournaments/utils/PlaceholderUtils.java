package me.waterarchery.littournaments.utils;

import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentValue;

public class PlaceholderUtils {

    public static String parseTournamentPlaceholders(String text, Tournament tournament) {
        return text.replace("tournament_id", tournament.getIdentifier());
    }

    public static String parseTournamentLeaderboardPlaceholders(String text, TournamentValue tournamentValue, int position) {
        return text.replace("%player-position%", String.valueOf(position))
                .replace("%player-score%", String.valueOf(tournamentValue.getValue()));
    }
}
