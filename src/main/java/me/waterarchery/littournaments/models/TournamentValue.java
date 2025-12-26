package me.waterarchery.littournaments.models;

import jakarta.persistence.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

@Entity
@Table
public class TournamentValue {

    @Id
    private UUID uuid;

    @Column
    private long value;

    @Transient
    public String getName() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return offlinePlayer.getName();
    }
}
