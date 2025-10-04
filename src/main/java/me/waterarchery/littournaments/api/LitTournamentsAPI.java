package me.waterarchery.littournaments.api;

import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.handlers.*;

public class LitTournamentsAPI {

    private static LitTournamentsAPI instance;

    public synchronized static LitTournamentsAPI getInstance() {
        if (instance == null) instance = new LitTournamentsAPI();
        return instance;
    }

    private LitTournamentsAPI() {
    }

    public LitTournaments getLitTournaments() {
        return LitTournaments.getInstance();
    }

    public CommandHandler getCommandHandler() {
        return CommandHandler.getInstance();
    }

    public GUIHandler getGUIHandler() {
        return GUIHandler.getInstance();
    }

    public LoadHandler getLoadHandler() {
        return LoadHandler.getInstance();
    }

    public PlayerHandler getPlayerHandler() {
        return PlayerHandler.getInstance();
    }

    public TournamentHandler getTournamentHandler() {
        return TournamentHandler.getInstance();
    }

    public PointHandler getPointHandler() {
        return PointHandler.getInstance();
    }

    public ValueHandler getValueHandler() {
        return ValueHandler.getInstance();
    }

}
