package me.waterarchery.littournaments.managers;

import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.logger.LoggerFactory;
import com.chickennw.utils.utils.ChatUtils;
import lombok.Getter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentLeaderboard;
import me.waterarchery.littournaments.models.TournamentValue;
import me.waterarchery.littournaments.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
public class TournamentManager {

    private final Logger logger = LoggerFactory.getLogger();
    private final List<Tournament> tournaments = new ArrayList<>();
    private final List<Class<Tournament>> tournamentClasses = new ArrayList<>();
    private static TournamentManager instance;

    public static TournamentManager getInstance() {
        if (instance == null) instance = new TournamentManager();
        return instance;
    }

    private TournamentManager() {
    }

    public void reloadTournaments() {
        LitTournaments instance = LitTournaments.getInstance();
        File tournamentsFolder = new File(instance.getDataFolder(), "/tournaments/");

        File[] contents = tournamentsFolder.listFiles();
        if (contents == null) return;

        tournamentClasses.clear();
        tournamentClasses.addAll(ReflectionUtils.getTournamentClasses());

        for (File file : contents) {
            try {
                FileConfiguration yml = new YamlConfiguration();
                yml.load(file);

                String identifier = file.getName().split("\\.")[0];
                loadTournament(identifier, yml);
            } catch (IOException | InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadTournament(String identifier, FileConfiguration yml) {
        logger.info("Loading tournament: {}", identifier);

        String classType = yml.getString("Objective");
        assert classType != null;
        classType = classType.replace("_", "");

        for (Class<Tournament> tournamentClass : tournamentClasses) {
            if (tournamentClass.getSimpleName().equalsIgnoreCase(classType)) {
                loadClass(tournamentClass, identifier, yml);
                return;
            }
        }

        logger.error("There is no tournament called: {}", classType);
    }

    private void loadClass(Class<Tournament> tournamentClass, Object... args) {
        try {
            Class<?>[] argTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);

            Tournament tournament = tournamentClass.getDeclaredConstructor(argTypes).newInstance(args);
            logger.info("Tournament loaded: {}", tournament.getIdentifier());
            tournaments.add(tournament);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T extends Tournament> List<Tournament> getTournaments(Class<T> className) {
        List<Tournament> applicableTournaments = new ArrayList<>();

        for (Tournament tournament : tournaments) {
            if (tournament.getClass() == className) applicableTournaments.add(tournament);
        }

        return applicableTournaments;
    }

    public @Nullable Tournament getTournament(String tournamentName) {
        for (Tournament tournament : tournaments) {
            if (tournament.getIdentifier().equalsIgnoreCase(tournamentName)) return tournament;
        }

        return null;
    }

    public void parseRewards(Tournament tournament) {
        YamlConfiguration yml = tournament.getYamlConfiguration();
        TournamentLeaderboard leaderboard = tournament.getLeaderboard();

        for (String rawPos : Objects.requireNonNull(yml.getConfigurationSection("Rewards")).getKeys(false)) {
            List<String> rewards = yml.getStringList("Rewards." + rawPos);

            for (String reward : rewards) {
                int pos = Integer.parseInt(rawPos);
                TournamentValue value = leaderboard.getPlayer(pos).orElse(null);
                if (value != null) {
                    String name = value.getName();
                    parseTournamentReward(reward, name);
                }
            }
        }
    }

    public void parseConditionalCommand(Tournament tournament, String condition) {
        YamlConfiguration yml = tournament.getYamlConfiguration();

        List<String> commands = yml.getStringList("ConditionalCommands." + condition);
        commands.forEach(command -> {
            TournamentLeaderboard leaderboard = tournament.getLeaderboard();
            String name = "None";
            if (command.contains("tournament_pos_1")) {
                TournamentValue value = leaderboard.getPlayer(1).orElse(null);
                if (value != null) name = value.getName();
                command = command.replace("%tournament_pos_1%", name);
            } else if (command.contains("tournament_pos_2")) {
                TournamentValue value = leaderboard.getPlayer(2).orElse(null);
                if (value != null) name = value.getName();
                command = command.replace("%tournament_pos_2%", name);
            } else if (command.contains("tournament_pos_3")) {
                TournamentValue value = leaderboard.getPlayer(3).orElse(null);
                if (value != null) name = value.getName();
                command = command.replace("%tournament_pos_3%", name);
            } else if (command.contains("tournament_pos_4")) {
                TournamentValue value = leaderboard.getPlayer(4).orElse(null);
                if (value != null) name = value.getName();
                command = command.replace("%tournament_pos_4%", name);
            } else if (command.contains("tournament_pos_5")) {
                TournamentValue value = leaderboard.getPlayer(5).orElse(null);
                if (value != null) name = value.getName();
                command = command.replace("%tournament_pos_5%", name);
            }
            parseTournamentReward(command, null);
        });
    }

    public void parseTournamentReward(String command, @Nullable String targetPlayer) {
        if (command.startsWith("[MESSAGE]") && targetPlayer != null) {
            Player player = Bukkit.getPlayer(targetPlayer);
            if (player != null) {
                ChatUtils.sendMessage(player, command.replace("[MESSAGE] ", ""));
            }
        } else if (command.startsWith("[BROADCAST]")) {
            String message = ChatUtils.colorizeLegacy(command.replace("[BROADCAST] ", ""));
            Bukkit.broadcastMessage(message);
        } else if (command.startsWith("[COMMAND]")) {
            command = command.replace("[COMMAND] ", "");
            if (targetPlayer != null) command = command.replace("%player%", targetPlayer);

            String finalCommand = command;
            ChickenUtils.getFoliaLib().getScheduler().runNextTick((task) -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), finalCommand));
        }
    }

}
