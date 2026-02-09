package me.waterarchery.littournaments.models;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TournamentValueId implements Serializable {

    private String tournamentId;
    private UUID uuid;

}
