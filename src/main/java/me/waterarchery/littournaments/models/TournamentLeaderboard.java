package me.waterarchery.littournaments.models;

import lombok.Getter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Optional;

@Getter
public class TournamentLeaderboard {

    private final Tournament tournament;
    private final HashMap<Integer, TournamentValue> leaderboard = new HashMap<>();
    private BukkitTask refreshTask;

    public TournamentLeaderboard(Tournament tournament) {
        this.tournament = tournament;
        startRefreshTask();
    }

    public int getPlayerPos(TournamentPlayer tournamentPlayer) {
        for (int pos : leaderboard.keySet()) {
            TournamentValue value = leaderboard.get(pos);
            if (value.getUuid().equals(tournamentPlayer.getUUID())) return pos;
        }

        return 0;
    }

    public Optional<TournamentValue> getPlayer(int pos) {
        return Optional.ofNullable(leaderboard.getOrDefault(pos, null));
    }

    public void startRefreshTask() {
        if (refreshTask != null) refreshTask.cancel();

        LitTournaments instance = LitTournaments.getInstance();
        long taskInterval = instance.getConfig().getLong("LeaderboardRefresh") * 20L;

        refreshTask = Bukkit.getScheduler().runTaskTimerAsynchronously(LitTournaments.getInstance(), () -> {
            Database database = LitTournaments.getDatabase();
            database.reloadLeaderboard(tournament);
        }, taskInterval, taskInterval);
    }

    public void setPosition(TournamentValue value, int position) {
        if (leaderboard.get(position) == null) leaderboard.put(position, value);
        else leaderboard.replace(position, value);
    }

    public void clear() {
        leaderboard.clear();
    }

}
