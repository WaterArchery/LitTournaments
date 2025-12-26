package me.waterarchery.littournaments.api;

import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.managers.*;

@SuppressWarnings("unused")
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

    public LoadManager getLoadHandler() {
        return LoadManager.getInstance();
    }

    public PlayerManager getPlayerHandler() {
        return PlayerManager.getInstance();
    }

    public TournamentManager getTournamentHandler() {
        return TournamentManager.getInstance();
    }

    public PointManager getPointHandler() {
        return PointManager.getInstance();
    }

    public ValueManager getValueHandler() {
        return ValueManager.getInstance();
    }

}
