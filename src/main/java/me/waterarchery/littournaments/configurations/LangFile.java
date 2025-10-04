package me.waterarchery.littournaments.configurations;

import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.configuration.ConfigManager;
import me.waterarchery.litlibs.configuration.ConfigPart;

public class LangFile extends ConfigManager {

    public LangFile(LitLibs litLibs, String folder, String name, boolean saveAfterLoad) {
        super(litLibs, folder, name, saveAfterLoad);
    }

    @Override
    public void initializeDefaults() {
        addDefault(ConfigPart.noComment("NoPermission", "<#CCFFEE>You don't have enough <#47D4FF>permission to execute <#CCFFEE>this command."));
        addDefault(ConfigPart.noComment("ConfigReloaded", "<#47D4FF>Config and lang files reloaded successfully."));
        addDefault(ConfigPart.noComment("InGameOnly", "<#CCFFEE>You can only use this command from <#47D4FF>in-game."));
        addDefault(ConfigPart.noComment("NotOnlinePlayer", "<#CCFFEE>Player is not in the server."));
        addDefault(ConfigPart.noComment("UnknownCommand", "<#CCFFEE>There is <#47D4FF>no command <#CCFFEE>with this <#47D4FF>sub command."));
        addDefault(ConfigPart.noComment("TooManyArgs", "<#CCFFEE>You entered <#47D4FF>too many arguments <#CCFFEE>for this command."));
        addDefault(ConfigPart.noComment("TooFewArgs", "<#CCFFEE>You entered <#47D4FF>too few arguments <#CCFFEE>for this command."));
        addDefault(ConfigPart.noComment("InvalidArg", "<#CCFFEE>You entered a <#47D4FF>invalid argument <#CCFFEE>for this command."));
        addDefault(ConfigPart.noComment("NoTournamentWithName", "<#CCFFEE>There is <#47D4FF>no tournament <#CCFFEE>with this name!"));
        addDefault(ConfigPart.noComment("JoinFirst", "<#CCFFEE>You need to <#47D4FF>join this tournament <#CCFFEE>before leaving!"));
        addDefault(ConfigPart.noComment("AlreadyJoined", "<#CCFFEE>You already <#47D4FF>joined this tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyRegisteredOnJoin", "<#CCFFEE>You successfully registered to a tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyRegistered", "<#CCFFEE>You successfully registered to this tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyLeaved", "<#CCFFEE>You successfully leaved from this tournament!"));
        addDefault(ConfigPart.noComment("StillLoading", "<#CCFFEE>Your data is <#47D4FF>still loading <#CCFFEE>please wait for <#47D4FF>your tournaments!"));
        addDefault(ConfigPart.noComment("FilesReloaded", "<#47D4FF>All files reloaded successfully!"));
        addDefault(ConfigPart.noComment("LeaderboardUpdated", "<#CCFFEE>Leaderboard <#47D4FF>updated!"));
        addDefault(ConfigPart.noComment("NotActiveTournament", "<red>This tournament is not active!"));
        addDefault(ConfigPart.noComment("LoadingLeaderboard", "<#47D4FF>Loading leaderboard! It can take several seconds."));
        addDefault(ConfigPart.noComment("TournamentEndAdmin",
                                        "<#CCFFEE>You have <#47D4FF>successfully finished <#CCFFEE>the tournament. It may take a <#47D4FF>few seconds to <#CCFFEE>take effect."));
        addDefault(ConfigPart.noComment("TournamentStartAdmin",
                                        "<#CCFFEE>You have <#47D4FF>successfully started <#CCFFEE>the tournament. It may take a <#47D4FF>few seconds to <#CCFFEE>take effect."));
        addDefault(ConfigPart.noComment("AlreadyActiveTournament",
                                        "<#CCFFEE>This tournament is already <#47D4FF>active!"));

        addDefault(ConfigPart.noComment("Placeholders.NotActive", "<red>Not active!"));
        addDefault(ConfigPart.noComment("Placeholders.None", "None"));
        addDefault(ConfigPart.noComment("Placeholders.NotRegistered", "<#CCFFEE>Not Registered"));
        addDefault(ConfigPart.noComment("Placeholders.RemainingTime", "<red>%day% days %hour% hours and %minute% minutes"));
    }

}
