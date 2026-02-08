package me.waterarchery.littournaments.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.waterarchery.littournaments.database.TournamentDatabase;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
@Setter
public class TournamentPlayer {

    private final UUID uuid;
    private final ConcurrentHashMap<String, Long> tournamentValueMap = new ConcurrentHashMap<>();
    private boolean isLoading;

    public TournamentPlayer(UUID uuid) {
        this.uuid = uuid;
        isLoading = true;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isRegistered(Tournament tournament) {
        return tournamentValueMap.containsKey(tournament.getIdentifier());
    }

    public void join(Tournament tournament) {
        TournamentDatabase database = TournamentDatabase.getInstance();
        database.registerToTournament(uuid, tournament);

        tournamentValueMap.put(tournament.getIdentifier(), 0L);
    }

    public void leave(Tournament tournament) {
        TournamentDatabase database = TournamentDatabase.getInstance();
        database.deleteFromTournament(uuid, tournament);

        tournamentValueMap.remove(tournament.getIdentifier());
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
