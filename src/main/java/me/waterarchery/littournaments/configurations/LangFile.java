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
        addDefault(ConfigPart.noComment("NoPermission", "&7You don't have enough &bpermission to execute &7this command."));
        addDefault(ConfigPart.noComment("ConfigReloaded", "&bConfig and lang files reloaded successfully."));
        addDefault(ConfigPart.noComment("InGameOnly", "&7You can only use this command from &bin-game."));
        addDefault(ConfigPart.noComment("UnknownCommand", "&7There is &bno command &7with this &bsub command."));
        addDefault(ConfigPart.noComment("TooManyArgs", "&7You entered &btoo many arguments &7for this command."));
        addDefault(ConfigPart.noComment("TooFewArgs", "&7You entered &btoo few arguments &7for this command."));
        addDefault(ConfigPart.noComment("InvalidArg", "&7You entered a &binvalid argument &7for this command."));
        addDefault(ConfigPart.noComment("NoTournamentWithName", "&7There is &bno tournament &7with this name!"));
        addDefault(ConfigPart.noComment("JoinFirst", "&7You need to &bjoin this tournament &7before leaving!"));
        addDefault(ConfigPart.noComment("AlreadyJoined", "&7You already &bjoined this tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyRegisteredOnJoin", "&7You successfully registered to a tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyRegistered", "&7You successfully registered to this tournament!"));
        addDefault(ConfigPart.noComment("SuccessfullyLeaved", "&7You successfully leaved from this tournament!"));
        addDefault(ConfigPart.noComment("StillLoading", "&7Your data is &bstill loading &7please wait for &byour tournaments!"));
        addDefault(ConfigPart.noComment("FilesReloaded", "&bAll files reloaded successfully!"));
        addDefault(ConfigPart.noComment("LeaderboardUpdated", "&7Leaderboard &bupdated!"));
        addDefault(ConfigPart.noComment("NotActiveTournament", "&cThis tournament is not active!"));
        addDefault(ConfigPart.noComment("LoadingLeaderboard", "&bLoading leaderboard! It can take several seconds."));
        addDefault(ConfigPart.noComment("TournamentEndAdmin",
                "&7You have &bsuccessfully finished &7the tournament. It may take a &bfew seconds to &7take effect."));
        addDefault(ConfigPart.noComment("TournamentStartAdmin",
                "&7You have &bsuccessfully started &7the tournament. It may take a &bfew seconds to &7take effect."));
        addDefault(ConfigPart.noComment("AlreadyActiveTournament",
                "&7This tournament is already &bactive!"));

        addDefault(ConfigPart.noComment("Placeholders.NotActive", "&cNot active!"));
        addDefault(ConfigPart.noComment("Placeholders.None", "None"));
        addDefault(ConfigPart.noComment("Placeholders.NotRegistered", "&7Not Registered"));
        addDefault(ConfigPart.noComment("Placeholders.RemainingTime", "&c%day% days %hour% hours and %minute% minutes"));
    }
    
}
