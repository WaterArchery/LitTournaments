package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointManager;
import me.waterarchery.littournaments.handlers.TournamentManager;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.MoneyReceiveTournament;
import me.waterarchery.littournaments.models.tournaments.MoneySpendTournament;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.util.List;

public class EssentialsXMoneyListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoneyAction(UserBalanceUpdateEvent event) {
        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        Player player = event.getPlayer();

        BigDecimal updatedBalance = event.getNewBalance().subtract(event.getOldBalance());

        if (event.getCause() == UserBalanceUpdateEvent.Cause.COMMAND_PAY) return;

        List<Tournament> tournaments = updatedBalance.longValue() > 0 ? tournamentManager.getTournaments(MoneyReceiveTournament.class) :
                tournamentManager.getTournaments(MoneySpendTournament.class);

        String causeName = event.getCause().name();
        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), causeName, Math.abs(updatedBalance.intValue()));
        }
    }

}
