package me.waterarchery.littournaments.models;


import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.libs.folia.wrapper.task.WrappedTask;
import com.chickennw.utils.utils.ConfigUtils;
import lombok.Getter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.api.events.TournamentEndEvent;
import me.waterarchery.littournaments.api.events.TournamentStartEvent;
import me.waterarchery.littournaments.configurations.ConfigFile;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.handlers.PlayerManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.handlers.WebhookHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@Getter
public class Tournament {

    private final String identifier;
    private final YamlConfiguration yamlConfiguration;
    private final boolean shouldRestartAfterFinished;
    private boolean isActive;
    private final String timePeriod;
    private final String coolName;
    private WrappedTask finishTask;
    private JoinChecker joinChecker;
    private ActionChecker actionChecker;
    private TournamentLeaderboard leaderboard;

    public Tournament(String identifier, YamlConfiguration yamlConfiguration) {
        this.identifier = identifier;
        this.yamlConfiguration = yamlConfiguration;

        isActive = yamlConfiguration.getBoolean("Active");
        timePeriod = yamlConfiguration.getString("TimePeriod");
        shouldRestartAfterFinished = yamlConfiguration.getBoolean("RestartAfterFinished", true);

        coolName = yamlConfiguration.getString("CoolName", identifier);

        load();
    }

    public void load() {
        joinChecker = new JoinChecker(yamlConfiguration, this);
        actionChecker = new ActionChecker(yamlConfiguration, this);
        leaderboard = new TournamentLeaderboard(this);

        if (isActive) startFinishTask();
    }

    public boolean checkWorldEnabled(String worldName) {
        if (actionChecker.getWorldWhitelist().contains(worldName)) return true;
        else {
            if (actionChecker.getWorldWhitelist().contains("*")) {
                return !actionChecker.getWorldBlacklist().contains(worldName);
            }

            return false;
        }
    }

    public boolean checkActionAllowed(String actionName) {
        if (actionChecker.getActionWhitelist().contains(actionName)) return true;
        else {
            if (actionChecker.getActionWhitelist().contains("*")) {
                return !actionChecker.getActionBlacklist().contains(actionName);
            }

            return false;
        }
    }

    public LocalDateTime getFinishTime() {
        LocalDate now = LocalDate.now();

        if (timePeriod.equalsIgnoreCase("daily")) {
            return now.atTime(23, 59, 59);
        } else if (timePeriod.equalsIgnoreCase("weekly")) {
            LocalDate endOfWeekDate = now.with(DayOfWeek.SUNDAY);
            LocalTime endOfDayTime = LocalTime.of(23, 59, 59);

            return LocalDateTime.of(endOfWeekDate, endOfDayTime);
        } else if (timePeriod.equalsIgnoreCase("monthly")) {
            int lastDayOfMonth = now.lengthOfMonth();
            return now.withDayOfMonth(lastDayOfMonth).atTime(23, 59, 59);
        }

        return null;
    }

    public Duration getRemainingTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime finishTime = getFinishTime();
        return Duration.between(now, finishTime);
    }

    public void startFinishTask() {
        stopFinishTask();

        LocalDateTime finishTime = getFinishTime();
        LocalDateTime now = LocalDateTime.now();
        Duration remaining = Duration.between(now, finishTime);
        long inTicks = remaining.getSeconds() * 20L;

        finishTask = ChickenUtils.getFoliaLib().getScheduler().runLater(this::finishTournament, inTicks);
    }

    public void stopFinishTask() {
        if (finishTask != null) finishTask.cancel();
    }

    public void finishTournament() {
        Tournament tournament = this;
        TournamentDatabase database = TournamentDatabase.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        PlayerManager playerManager = PlayerManager.getInstance();
        tournamentManager.parseConditionalCommand(tournament, "TOURNAMENT_END");

        TournamentEndEvent tournamentEndEvent = new TournamentEndEvent(tournament);
        Bukkit.getPluginManager().callEvent(tournamentEndEvent);
        WebhookHandler.sendWebhook(tournament);

        ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
        int waitTime = configFile.getWaitTimeBetweenTournaments();

        CompletableFuture.runAsync(database.getReloadTournamentRunnable(tournament)).thenRun(() -> {
            boolean mainServer = LitTournaments.getInstance().getConfig().getBoolean("MainServer");
            if (!mainServer) return;

            if (shouldRestartAfterFinished) {
                tournamentManager.parseRewards(tournament);
                database.clearTournament(tournament);
                playerManager.clearPlayerValues(tournament);
                getLeaderboard().clear();
                stopFinishTask();
                return;
            }

            File file = new File(LitTournaments.getInstance().getDataFolder(), "/tournaments/" + identifier + ".yml");
            yamlConfiguration.set("Active", false);
            try {
                yamlConfiguration.save(file);
            } catch (IOException e) {
                LitTournaments.getInstance().getLogger().log(Level.WARNING, "Error saving tournament file: " + identifier, e);
            }

            isActive = false;
            tournamentManager.parseRewards(tournament);
            stopFinishTask();
        }).thenRun(() -> {
            if (!shouldRestartAfterFinished) return;

            ChickenUtils.getFoliaLib().getScheduler().runLater(this::startTournament, waitTime * 20L);
        });
    }

    public void startTournament() {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        TournamentDatabase database = TournamentDatabase.getInstance();
        PlayerManager playerManager = PlayerManager.getInstance();

        startFinishTask();
        database.clearTournament(this);
        playerManager.clearPlayerValues(this);
        getLeaderboard().clear();

        TournamentStartEvent tournamentStartEvent = new TournamentStartEvent(this);
        Bukkit.getPluginManager().callEvent(tournamentStartEvent);
        tournamentManager.parseConditionalCommand(this, "TOURNAMENT_START");
        isActive = true;

        File file = new File(LitTournaments.getInstance().getDataFolder(), "/tournaments/" + identifier + ".yml");
        yamlConfiguration.set("Active", true);
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            LitTournaments.getInstance().getLogger().log(Level.WARNING, "Error saving tournament file: " + identifier, e);
        }
    }

}
