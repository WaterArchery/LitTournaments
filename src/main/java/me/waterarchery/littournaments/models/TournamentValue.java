package me.waterarchery.littournaments.models;

import com.chickennw.utils.utils.ConfigUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.waterarchery.littournaments.configurations.LangFile;
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

    @Transient
    private String name;

    public TournamentValue(String tournamentId, UUID uuid, long value) {
        this.tournamentId = tournamentId;
        this.uuid = uuid;
        this.value = value;
    }

    @Transient
    public String getName() {
        if (name == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            name = offlinePlayer.getName();
        }

        if (name == null) {
            LangFile langFile = ConfigUtils.get(LangFile.class);
            name = langFile.getPlaceholders().getNone();
        }

        return name;
    }
}
