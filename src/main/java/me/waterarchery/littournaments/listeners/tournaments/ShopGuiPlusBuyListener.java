package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.handlers.PointHandler;
import me.waterarchery.littournaments.handlers.TournamentHandler;
import me.waterarchery.littournaments.models.Tournament;
import me.waterarchery.littournaments.models.tournaments.ShopGuiPlusBuyTournament;
import net.brcdev.shopgui.event.ShopPostTransactionEvent;
import net.brcdev.shopgui.shop.ShopManager;
import net.brcdev.shopgui.shop.ShopTransactionResult;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class ShopGuiPlusBuyListener implements Listener {

    @EventHandler
    public void onShopGuiPlusBuy(ShopPostTransactionEvent event) {
        ShopTransactionResult result = event.getResult();
        Player player = result.getPlayer();

        if (result.getShopAction() != ShopManager.ShopAction.BUY ||
                result.getResult() != ShopTransactionResult.ShopTransactionResultType.SUCCESS) {
            return;
        }

        double pricePerItem = result.getPrice();
        int points = (int) Math.floor(pricePerItem);

        PointHandler pointHandler = PointHandler.getInstance();
        TournamentHandler tournamentHandler = TournamentHandler.getInstance();
        List<Tournament> tournaments = tournamentHandler.getTournaments(ShopGuiPlusBuyTournament.class);

        for (Tournament tournament : tournaments) {
            pointHandler.addPoint(
                    player.getUniqueId(),
                    tournament,
                    player.getWorld().getName(),
                    "ShopPurchase",
                    points
            );
        }
    }
}
