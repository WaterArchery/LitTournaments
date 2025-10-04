package me.waterarchery.littournaments;

import com.tcoded.folialib.FoliaLib;
import lombok.Getter;
import lombok.Setter;
import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.logger.Logger;
import me.waterarchery.littournaments.database.Database;
import me.waterarchery.littournaments.handlers.CommandHandler;
import me.waterarchery.littournaments.handlers.LoadHandler;
import me.waterarchery.littournaments.hooks.PlaceholderAPIHook;
import org.bukkit.plugin.java.JavaPlugin;

public final class LitTournaments extends JavaPlugin {

    @Getter
    private static LitLibs litLibs;
    @Getter
    @Setter
    private static Database database;
    @Getter
    private static FoliaLib foliaLib;

    @Override
    public void onEnable() {
        litLibs = LitLibs.of(this);
        foliaLib = new FoliaLib(this);
        Logger logger = litLibs.getLogger();
        LoadHandler loadHandler = LoadHandler.getInstance();
        loadHandler.load();
        logger.log("LitTournaments enabled <#47D4FF>v" + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        Logger logger = litLibs.getLogger();

        CommandHandler commandHandler = CommandHandler.getInstance();
        commandHandler.unRegisterCommands();

        database.shutdownPool();

        LoadHandler loadHandler = LoadHandler.getInstance();
        PlaceholderAPIHook placeholderAPIHook = loadHandler.getPlaceholderAPIHook();
        if (placeholderAPIHook != null) placeholderAPIHook.unRegister();

        logger.log("Good bye :(");
    }

    public static LitTournaments getInstance() {
        return getPlugin(LitTournaments.class);
    }
}