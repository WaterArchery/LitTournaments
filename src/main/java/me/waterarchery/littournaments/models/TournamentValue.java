package me.waterarchery.littournaments.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tournament_scores")
public class TournamentValue {

    @Id
    @Column(name = "tournament_id", nullable = false)
    private String tournamentId;

    @Column(name = "player", nullable = false)
    private UUID uuid;

    @Column(name = "score", nullable = false)
    private long value;

    public TournamentValue(String tournamentId, UUID uuid, long value) {
        this.tournamentId = tournamentId;
        this.uuid = uuid;
        this.value = value;
    }

    public TournamentValue(UUID uuid, long value) {
        this.uuid = uuid;
        this.value = value;
    }

    @Transient
    public String getName() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return offlinePlayer.getName();
    }
}
