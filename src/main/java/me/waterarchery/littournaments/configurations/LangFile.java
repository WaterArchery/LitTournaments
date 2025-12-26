package me.waterarchery.littournaments.configurations;

import com.chickennw.utils.libs.config.configs.OkaeriConfig;
import com.chickennw.utils.libs.config.configs.annotation.NameModifier;
import com.chickennw.utils.libs.config.configs.annotation.NameStrategy;
import com.chickennw.utils.libs.config.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class LangFile extends OkaeriConfig {

    private String noPermission = "<#CCFFEE>You don't have enough <#47D4FF>permission to execute <#CCFFEE>this command.";
    private String configReloaded = "<#47D4FF>Config and lang files reloaded successfully.";
    private String inGameOnly = "<#CCFFEE>You can only use this command from <#47D4FF>in-game.";
    private String notOnlinePlayer = "<#CCFFEE>Player is not in the server.";
    private String unknownCommand = "<#CCFFEE>There is <#47D4FF>no command <#CCFFEE>with this <#47D4FF>sub command.";
    private String tooManyArgs = "<#CCFFEE>You entered <#47D4FF>too many arguments <#CCFFEE>for this command.";
    private String tooFewArgs = "<#CCFFEE>You entered <#47D4FF>too few arguments <#CCFFEE>for this command.";
    private String invalidArg = "<#CCFFEE>You entered a <#47D4FF>invalid argument <#CCFFEE>for this command.";
    private String noTournamentWithName = "<#CCFFEE>There is <#47D4FF>no tournament <#CCFFEE>with this name!";
    private String joinFirst = "<#CCFFEE>You need to <#47D4FF>join this tournament <#CCFFEE>before leaving!";
    private String alreadyJoined = "<#CCFFEE>You already <#47D4FF>joined this tournament!";
    private String successfullyRegisteredOnJoin = "<#CCFFEE>You successfully registered to a tournament!";
    private String successfullyRegistered = "<#CCFFEE>You successfully registered to this tournament!";
    private String successfullyLeaved = "<#CCFFEE>You successfully leaved from this tournament!";
    private String stillLoading = "<#CCFFEE>Your data is <#47D4FF>still loading <#CCFFEE>please wait for <#47D4FF>your tournaments!";
    private String filesReloaded = "<#47D4FF>All files reloaded successfully!";
    private String leaderboardUpdated = "<#CCFFEE>Leaderboard <#47D4FF>updated!";
    private String notActiveTournament = "<red>This tournament is not active!";
    private String loadingLeaderboard = "<#47D4FF>Loading leaderboard! It can take several seconds.";
    private String tournamentEndAdmin = "<#CCFFEE>You have <#47D4FF>successfully finished <#CCFFEE>the tournament. It may take a <#47D4FF>few seconds to <#CCFFEE>take effect.";
    private String tournamentStartAdmin = "<#CCFFEE>You have <#47D4FF>successfully started <#CCFFEE>the tournament. It may take a <#47D4FF>few seconds to <#CCFFEE>take effect.";
    private String alreadyActiveTournament = "<#CCFFEE>This tournament is already <#47D4FF>active!";

    private Placeholders placeholders = new Placeholders();

    @Getter
    @Setter
    public static class Placeholders extends OkaeriConfig {
        private String notActive = "<red>Not active!";
        private String none = "None";
        private String notRegistered = "<#CCFFEE>Not Registered";
        private String remainingTime = "<red>%day% days %hour% hours and %minute% minutes";
    }
}
