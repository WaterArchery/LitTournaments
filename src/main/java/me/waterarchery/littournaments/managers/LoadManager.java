package me.waterarchery.littournaments.managers;

import com.chickennw.utils.libs.bstats.charts.SimpleBarChart;
import com.chickennw.utils.libs.bstats.charts.SimplePie;
import com.chickennw.utils.libs.bstats.charts.SingleLineChart;
import com.chickennw.utils.libs.cmd.core.suggestion.SuggestionKey;
import com.chickennw.utils.logger.LoggerFactory;
import com.chickennw.utils.managers.CommandManager;
import com.chickennw.utils.managers.ConfigManager;
import com.chickennw.utils.models.metrics.PluginMetrics;
import com.chickennw.utils.utils.ConfigUtils;
import lombok.Getter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.commands.TournamentCommand;
import me.waterarchery.littournaments.configurations.ConfigFile;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.configurations.SoundsFile;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.hooks.PlaceholderAPIHook;
import me.waterarchery.littournaments.listeners.JoinLeaveListeners;
import me.waterarchery.littournaments.listeners.tournaments.*;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class LoadManager {

    private static LoadManager instance;
    private final Logger logger;
    private PlaceholderAPIHook placeholderAPIHook;

    public static LoadManager getInstance() {
        if (instance == null) instance = new LoadManager();
        return instance;
    }

    private LoadManager() {
        logger = LoggerFactory.getLogger();
    }

    public void load() {
        logger.info("Loading files");
        loadConfigs();

        logger.info("Registering events, commands and hooks");
        registerEvents();
        registerCommands();
        registerHooks();

        logger.info("Loading tournaments");
        TournamentManager tournamentManager = TournamentManager.getInstance();
        tournamentManager.reloadTournaments();

        logger.info("Loading database");
        loadDatabase();
    }

    public void loadConfigs() {
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.loadOkaeriConfig(ConfigFile.class);
        configManager.loadOkaeriConfig(LangFile.class);
        configManager.loadOkaeriConfig(SoundsFile.class);
        configManager.createFiles("menu");

        LitTournaments instance = LitTournaments.getInstance();
        File tournamentsFolder = new File(instance.getDataFolder(), "/tournaments");

        File[] contents = tournamentsFolder.listFiles();
        if (contents != null) return;

        if (!tournamentsFolder.exists()) tournamentsFolder.mkdir();
        configManager.createFiles("tournaments");
    }

    public void loadDatabase() {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        List<Tournament> tournaments = tournamentManager.getTournaments();
        TournamentDatabase database = TournamentDatabase.getInstance();
        loadPlayers();
        database.load(tournaments);
    }

    public void loadPlayers() {
        PlayerManager playerManager = PlayerManager.getInstance();

        for (Player player : Bukkit.getOnlinePlayers()) {
            TournamentPlayer tournamentPlayer = new TournamentPlayer(player.getUniqueId());
            playerManager.getPlayers().put(tournamentPlayer.getUUID(), tournamentPlayer);
            playerManager.initializePlayer(tournamentPlayer, false);
        }
    }

    private void registerEvents() {
        LitTournaments instance = LitTournaments.getInstance();

        instance.getServer().getPluginManager().registerEvents(new JoinLeaveListeners(), instance);

        // Tournaments
        instance.getServer().getPluginManager().registerEvents(new BlockBreakListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new ItemCraftListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new MobKillListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new ItemBreakListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerFishListener(), instance);
        PlayTimeListener.getInstance(); // Starting task
    }

    private void registerCommands() {
        CommandManager commandManager = CommandManager.getInstance();

        commandManager.registerSuggestion(SuggestionKey.of("tournaments"), (sender) -> {
            TournamentManager tournamentManager = TournamentManager.getInstance();
            List<String> tournaments = new ArrayList<>();

            for (Tournament tournament : tournamentManager.getTournaments()) {
                tournaments.add(tournament.getIdentifier());
            }

            return tournaments;
        });

        commandManager.registerCommand(new TournamentCommand());
    }

    private void registerHooks() {
        LitTournaments instance = LitTournaments.getInstance();

        registerMetrics();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            logger.info("Found PlaceHolderAPI hook");
            placeholderAPIHook = new PlaceholderAPIHook();
            placeholderAPIHook.register();
        }

        if (Bukkit.getPluginManager().isPluginEnabled("Essentials") || Bukkit.getPluginManager().isPluginEnabled("EssentialsX")) {
            logger.info("Found EssentialsX hook.");
            instance.getServer().getPluginManager().registerEvents(new EssentialsXMoneyListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Votifier") || Bukkit.getPluginManager().isPluginEnabled("NuVotifier")) {
            logger.info("Found Votifier hook.");
            instance.getServer().getPluginManager().registerEvents(new VotifierVoteListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CrazyCrates")) {
            logger.info("Found CrazyCrates hook.");
            instance.getServer().getPluginManager().registerEvents(new CrazyCrateOpenListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ExcellentCrates")) {
            logger.info("Found ExcellentCrates hook.");
            instance.getServer().getPluginManager().registerEvents(new ExcellentCrateOpenListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Duels")) {
            logger.info("Found Duels hook.");
            instance.getServer().getPluginManager().registerEvents(new DuelsWinListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("RivalHarvesterHoes")) {
            logger.info("Found RivalHarvesterHoes hook.");
            instance.getServer().getPluginManager().registerEvents(new RivalHoeListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("EvenMoreFish")) {
            logger.info("Found EvenMoreFish hook.");
            instance.getServer().getPluginManager().registerEvents(new EvenMoreFishListener(), instance);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ShopGuiPlus")) {
            logger.info("Found ShopGuiPlus hook.");
            instance.getServer().getPluginManager().registerEvents(new ShopGuiPlusBuyListener(), instance);
        }
    }

    private void registerMetrics() {
        LitTournaments instance = LitTournaments.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        PluginMetrics metrics = new PluginMetrics(instance, 22957);

        metrics.addCustomChart(new SimpleBarChart("used_tournaments", () -> {
            Map<String, Integer> map = new HashMap<>();
            tournamentManager.getTournaments().forEach(tournament -> map.put(tournament.getIdentifier(), 1));
            return map;
        }));

        ConfigFile configFile = ConfigUtils.get(ConfigFile.class);
        metrics.addCustomChart(new SimplePie("webhook_used", () -> String.valueOf(configFile.getDiscordWebhook().isEnabled())));
        metrics.addCustomChart(new SimplePie("database_type", () -> configFile.getDatabase().getType()));
        metrics.addCustomChart(new SingleLineChart("tournaments_count", () -> tournamentManager.getTournaments().size()));
    }
}
