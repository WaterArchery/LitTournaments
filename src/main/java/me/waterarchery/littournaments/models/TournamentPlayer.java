package me.waterarchery.littournaments.models;

import lombok.Getter;
import lombok.Setter;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.database.Database;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class TournamentPlayer {

    private final UUID uuid;
    private final HashMap<Tournament, Long> tournamentValueMap = new HashMap<>();
    private boolean isLoading;

    public TournamentPlayer(UUID uuid) {
        this.uuid = uuid;
        this.isLoading = true;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isRegistered(Tournament tournament) {
        return tournamentValueMap.containsKey(tournament);
    }

    public void join(Tournament tournament) {
        Database database = LitTournaments.getDatabase();
        database.registerToTournament(uuid, tournament);

        tournamentValueMap.put(tournament, 0L);
    }

    public void leave(Tournament tournament) {
        Database database = LitTournaments.getDatabase();
        database.deleteFromTournament(uuid, tournament);

        tournamentValueMap.remove(tournament);
    }

}
