package me.waterarchery.littournaments.commands;

import com.chickennw.utils.libs.cmd.bukkit.annotation.Permission;
import com.chickennw.utils.libs.cmd.core.annotations.Command;
import com.chickennw.utils.libs.cmd.core.annotations.Suggestion;
import com.chickennw.utils.models.commands.BaseCommand;
import com.chickennw.utils.utils.ChatUtils;
import com.chickennw.utils.utils.ConfigUtils;
import com.chickennw.utils.utils.SoundUtils;
import me.waterarchery.littournaments.configurations.LangFile;
import me.waterarchery.littournaments.configurations.SoundsFile;
import me.waterarchery.littournaments.database.TournamentDatabase;
import me.waterarchery.littournaments.guis.LeaderboardGUI;
import me.waterarchery.littournaments.guis.TournamentGUI;
import me.waterarchery.littournaments.managers.LoadManager;
import me.waterarchery.littournaments.managers.PlayerManager;
import me.waterarchery.littournaments.managers.TournamentManager;
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

    private final LangFile langFile;
    private final SoundsFile soundsFile;

    public TournamentCommand() {
        langFile = ConfigUtils.get(LangFile.class);
        soundsFile = ConfigUtils.get(SoundsFile.class);
    }

    @Command
    public void defaultCmd(Player player) {
        TournamentGUI gui = new TournamentGUI(player);
        gui.openAsync(player, gui.getGuiElements());
    }

    @Command("join")
    public void join(Player player, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        PlayerManager playerManager = PlayerManager.getInstance();
        TournamentPlayer tournamentPlayer = playerManager.getPlayer(player.getUniqueId());
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (!player.hasPermission("littournaments.player.join." + tournamentName) && !player.hasPermission("littournaments.player.join.*")) {
            ChatUtils.sendPrefixedMessage(player, langFile.getNoPermission());
            SoundUtils.sendSoundRaw(player, soundsFile.getNoPermission());
            return;
        }

        if (tournament != null) {
            JoinChecker joinChecker = tournament.getJoinChecker();
            if (!tournamentPlayer.isRegistered(tournament) && joinChecker.canJoin(player.getUniqueId())) {
                tournamentPlayer.join(tournament);
                ChatUtils.sendPrefixedMessage(player, langFile.getSuccessfullyRegistered());
                SoundUtils.sendSoundRaw(player, soundsFile.getSuccessfullyJoined());
            } else {
                ChatUtils.sendPrefixedMessage(player, langFile.getAlreadyJoined());
                SoundUtils.sendSoundRaw(player, soundsFile.getAlreadyJoined());
            }
        } else {
            ChatUtils.sendPrefixedMessage(player, langFile.getNoTournamentWithName());
        }
    }

    @Command("leave")
    public void leave(Player player, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        PlayerManager playerManager = PlayerManager.getInstance();
        TournamentPlayer tournamentPlayer = playerManager.getPlayer(player.getUniqueId());
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (!player.hasPermission("littournaments.player.leave." + tournamentName) && !player.hasPermission("littournaments.player.leave.*")) {
            ChatUtils.sendPrefixedMessage(player, langFile.getNoPermission());
            SoundUtils.sendSoundRaw(player, soundsFile.getNoPermission());
            return;
        }

        if (tournament != null) {
            if (tournamentPlayer.isRegistered(tournament)) {
                tournamentPlayer.leave(tournament);
                ChatUtils.sendPrefixedMessage(player, langFile.getSuccessfullyLeaved());
            } else {
                ChatUtils.sendPrefixedMessage(player, langFile.getJoinFirst());
            }
        } else {
            ChatUtils.sendPrefixedMessage(player, langFile.getNoTournamentWithName());
        }
    }

    @Permission("littournaments.player.leaderboard")
    @Command("leaderboard")
    public void leaderboard(Player player, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (tournament != null) {
            LeaderboardGUI leaderboardGUI = new LeaderboardGUI(player, tournament, true);
            leaderboardGUI.openAsync(player);
        } else {
            ChatUtils.sendPrefixedMessage(player, langFile.getNoTournamentWithName());
        }
    }

    @Command("reload")
    @Permission("littournaments.admin.reload")
    public void reload(CommandSender sender) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        tournamentManager.reloadTournaments();

        LoadManager loadManager = LoadManager.getInstance();
        loadManager.loadConfigs();

        List<Tournament> tournaments = tournamentManager.getTournaments();
        TournamentDatabase database = TournamentDatabase.getInstance();
        database.load(tournaments);

        ChatUtils.sendPrefixedMessage(sender, langFile.getFilesReloaded());
    }

    @Command("end")
    @Permission("littournaments.admin.end")
    public void end(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (tournament != null) {
            if (tournament.isActive()) {
                ChatUtils.sendPrefixedMessage(sender, langFile.getTournamentEndAdmin());
                tournament.finishTournament();
            } else {
                ChatUtils.sendPrefixedMessage(sender, langFile.getNotActiveTournament());
            }
        } else {
            ChatUtils.sendPrefixedMessage(sender, langFile.getNoTournamentWithName());
        }
    }

    @Command("start")
    @Permission("littournaments.admin.start")
    public void start(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (tournament != null) {
            if (!tournament.isActive()) {
                ChatUtils.sendPrefixedMessage(sender, langFile.getTournamentStartAdmin());
                tournament.startTournament();
            } else {
                ChatUtils.sendPrefixedMessage(sender, langFile.getAlreadyActiveTournament());
            }
        } else {
            ChatUtils.sendPrefixedMessage(sender, langFile.getNoTournamentWithName());
        }
    }

    @Command("update")
    @Permission("littournaments.admin.update")
    public void update(CommandSender sender, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (tournament != null) {
            if (tournament.isActive()) {
                TournamentDatabase database = TournamentDatabase.getInstance();
                database.reloadLeaderboard(tournament);
                ChatUtils.sendPrefixedMessage(sender, langFile.getLeaderboardUpdated());
            } else {
                ChatUtils.sendPrefixedMessage(sender, langFile.getAlreadyActiveTournament());
            }
        } else {
            ChatUtils.sendPrefixedMessage(sender, langFile.getNoTournamentWithName());
        }
    }

    @Command("deletedata")
    @Permission("littournaments.admin.delete")
    public void deleteData(CommandSender sender, @Suggestion("players") String playerName, @Suggestion("tournaments") String tournamentName) {
        TournamentManager tournamentManager = TournamentManager.getInstance();
        PlayerManager playerManager = PlayerManager.getInstance();
        Tournament tournament = tournamentManager.getTournament(tournamentName);

        if (tournament == null) {
            ChatUtils.sendPrefixedMessage(sender, langFile.getNoTournamentWithName());
            return;
        }

        if (!tournament.isActive()) {
            ChatUtils.sendPrefixedMessage(sender, langFile.getAlreadyActiveTournament());
            return;
        }

        CompletableFuture.supplyAsync(() -> Bukkit.getOfflinePlayer(playerName)).thenAcceptAsync(player -> {
            TournamentDatabase database = TournamentDatabase.getInstance();
            TournamentPlayer tournamentPlayer = playerManager.getPlayer(player.getUniqueId());

            if (tournamentPlayer == null) database.deleteFromTournament(player.getUniqueId(), tournament);
            else tournamentPlayer.leave(tournament);

            database.reloadLeaderboard(tournament);
        });
    }
}
