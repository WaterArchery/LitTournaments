package me.waterarchery.littournaments.commands;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Default;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import me.waterarchery.litlibs.LitLibs;
import me.waterarchery.litlibs.libs.gui.guis.BaseGui;
import me.waterarchery.littournaments.LitTournaments;
import me.waterarchery.littournaments.database.Database;
import me.waterarchery.littournaments.guis.LeaderboardGUI;
import me.waterarchery.littournaments.guis.TournamentGUI;
import me.waterarchery.littournaments.handlers.FileHandler;
import me.waterarchery.littournaments.handlers.PlayerHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.JoinChecker;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.TournamentPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Command(value = "littournaments", alias = {"tournaments", "tournament"})
public class TournamentCommand extends BaseCommand {

    @Default
    public void defaultCmd(CommandSender sender) {
        LitLibs libs = LitTournaments.getLitLibs();

        if (sender instanceof Player player) {
            BaseGui gui = TournamentGUI.of(player);
            gui.open(player);
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "InGameOnly");
        }
    }

    @SubCommand("join")
    public void join(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();

        if (sender instanceof Player player) {
            TournamentHandler tournamentHandler = TournamentHandler.getInstance();
            PlayerHandler playerHandler = PlayerHandler.getInstance();
            TournamentPlayer tournamentPlayer = playerHandler.getPlayer(player.getUniqueId());
            Tournament tournament = tournamentHandler.getTournament(tournamentName);

            if (!sender.hasPermission("littournaments.player.join." + tournamentName) &&
                    !sender.hasPermission("littournaments.player.join.*")) {
                libs.getMessageHandler().sendLangMessage(sender, "NoPermission");
                libs.getSoundHandler().sendSound(player, "Sounds.NoPermission");
                return;
            }

            if (tournament != null) {
                JoinChecker joinChecker = tournament.getJoinChecker();
                if (!tournamentPlayer.isRegistered(tournament) && joinChecker.canJoin(player.getUniqueId())) {
                    tournamentPlayer.join(tournament);
                    libs.getMessageHandler().sendLangMessage(sender, "SuccessfullyRegistered");
                    libs.getSoundHandler().sendSound(player, "Sounds.SuccessfullyJoined");
                } else {
                    libs.getSoundHandler().sendSound(player, "Sounds.AlreadyJoined");
                    libs.getMessageHandler().sendLangMessage(sender, "AlreadyJoined");
                }
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "InGameOnly");
        }
    }

    @SubCommand("leave")
    public void leave(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();

        if (sender instanceof Player player) {
            TournamentHandler tournamentHandler = TournamentHandler.getInstance();
            PlayerHandler playerHandler = PlayerHandler.getInstance();
            TournamentPlayer tournamentPlayer = playerHandler.getPlayer(player.getUniqueId());
            Tournament tournament = tournamentHandler.getTournament(tournamentName);

            if (!sender.hasPermission("littournaments.player.leave." + tournamentName) &&
                    !sender.hasPermission("littournaments.player.leave.*")) {
                libs.getMessageHandler().sendLangMessage(sender, "NoPermission");
                libs.getSoundHandler().sendSound(player, "Sounds.NoPermission");
                return;
            }

            if (tournament != null) {
                if (tournamentPlayer.isRegistered(tournament)) {
                    tournamentPlayer.leave(tournament);
                    libs.getMessageHandler().sendLangMessage(sender, "SuccessfullyLeaved");
                } else {
                    libs.getMessageHandler().sendLangMessage(sender, "JoinFirst");
                }
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "InGameOnly");
        }
    }

    @Permission("littournaments.player.leaderboard")
    @SubCommand("leaderboard")
    public void leaderboard(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();

        if (sender instanceof Player player) {
            TournamentHandler tournamentHandler = TournamentHandler.getInstance();
            Tournament tournament = tournamentHandler.getTournament(tournamentName);

            if (tournament != null) {
                LeaderboardGUI.openMenu(player, tournament, true);
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "InGameOnly");
        }
    }

    @SubCommand("reload")
    @Permission("littournaments.admin.reload")
    public void reload(CommandSender sender) {
        LitLibs libs = LitTournaments.getLitLibs();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Database database = LitTournaments.getDatabase();

        FileHandler.load();
        tournamentHandler.reloadTournaments();

        List<Tournament> tournaments = tournamentHandler.getTournaments();
        database.load(tournaments);
        libs.getMessageHandler().sendLangMessage(sender, "FilesReloaded");
    }


    @SubCommand("end")
    @Permission("littournaments.admin.end")
    public void end(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Tournament tournament = tournamentHandler.getTournament(tournamentName);

        if (tournament != null) {
            if (tournament.isActive()) {
                libs.getMessageHandler().sendLangMessage(sender, "TournamentEndAdmin");
                tournament.finishTournament();
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "NotActiveTournament");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
        }
    }

    @SubCommand("start")
    @Permission("littournaments.admin.start")
    public void start(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Tournament tournament = tournamentHandler.getTournament(tournamentName);

        if (tournament != null) {
            if (!tournament.isActive()) {
                libs.getMessageHandler().sendLangMessage(sender, "TournamentStartAdmin");
                tournament.startTournament();
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "AlreadyActiveTournament");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
        }
    }

    @SubCommand("update")
    @Permission("littournaments.admin.update")
    public void update(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Tournament tournament = tournamentHandler.getTournament(tournamentName);

        if (tournament != null) {
            if (tournament.isActive()) {
                Database database = LitTournaments.getDatabase();
                database.reloadLeaderboard(tournament);
                libs.getMessageHandler().sendLangMessage(sender, "LeaderboardUpdated");
            } else {
                libs.getMessageHandler().sendLangMessage(sender, "NotActiveTournament");
            }
        } else {
            libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
        }
    }

    @SubCommand("deletedata")
    @Permission("littournaments.admin.delete")
    public void deleteData(CommandSender sender, @Suggestion("players") String playerName, @Suggestion("tournaments") String tournamentName) {
        LitLibs libs = LitTournaments.getLitLibs();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        PlayerHandler playerHandler = PlayerHandler.getInstance();
        Tournament tournament = tournamentHandler.getTournament(tournamentName);

        if (tournament == null) {
            libs.getMessageHandler().sendLangMessage(sender, "NoTournamentWithName");
            return;
        }

        if (!tournament.isActive()) {
            libs.getMessageHandler().sendLangMessage(sender, "NotActiveTournament");
            return;
        }

        CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(playerName)).thenAcceptAsync(player -> {
            Database database = LitTournaments.getDatabase();

            TournamentPlayer tournamentPlayer = playerHandler.getPlayer(player.getUniqueId());

            if (tournamentPlayer == null) database.deleteFromTournament(player.getUniqueId(), tournament);
            else tournamentPlayer.leave(tournament);

            database.reloadLeaderboard(tournament);
        });
    }

}
