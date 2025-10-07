package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
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
        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        Player player = event.getPlayer();

        BigDecimal updatedBalance = event.getNewBalance().subtract(event.getOldBalance());

        if (event.getCause() == UserBalanceUpdateEvent.Cause.COMMAND_PAY) return;

        List<Tournament> tournaments = updatedBalance.longValue() > 0
                ? tournamentHandler.getTournaments(MoneyReceiveTournament.class) :
                tournamentHandler.getTournaments(MoneySpendTournament.class);

        String causeName = event.getCause().name();
        for (Tournament tournament : tournaments) {
            pointHandler.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), causeName, Math.abs(updatedBalance.intValue()));
        }
    }

}
