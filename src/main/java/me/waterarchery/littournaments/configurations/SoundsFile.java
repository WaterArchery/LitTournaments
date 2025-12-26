package me.waterarchery.littournaments.configurations;

import com.chickennw.utils.libs.config.configs.OkaeriConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoundsFile extends OkaeriConfig {

    private int soundVolume = 2;
    private String menuOpen = "BLOCK_ANVIL_BREAK";
    private String invalidCommand = "ENTITY_ARROW_HIT";
    private String noPermission = "ENTITY_ARROW_HIT";
    private String tournamentFinish = "BLOCK_ANVIL_BREAK";
    private String alreadyJoined = "ENTITY_ARROW_HIT";
    private String successfullyJoined = "BLOCK_ANVIL_BREAK";
}
