package me.waterarchery.littournaments;

import com.chickennw.utils.ChickenUtils;
import com.chickennw.utils.logger.LoggerFactory;
import com.chickennw.utils.managers.CommandManager;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.hooks.PlaceholderAPIHook;
import me.waterarchery.littournaments.managers.LoadManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public final class LitTournaments extends JavaPlugin {

    private Logger logger;

    @Override
    public void onEnable() {
        ChickenUtils.setPlugin(this);
        logger = LoggerFactory.getLogger();

        LoadManager loadManager = LoadManager.getInstance();
        loadManager.load();

        logger.info("LitTournaments enabled <#47D4FF>v{}", getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        CommandManager.getInstance().unregisterCommands();
        TournamentDatabase.getInstance().close();

        LoadManager loadManager = LoadManager.getInstance();
        PlaceholderAPIHook placeholderAPIHook = loadManager.getPlaceholderAPIHook();
        if (placeholderAPIHook != null) placeholderAPIHook.unRegister();

        logger.info("Good bye :(");
    }

    public static LitTournaments getInstance() {
        return getPlugin(LitTournaments.class);
    }
}