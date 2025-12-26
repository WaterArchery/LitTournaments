package me.waterarchery.littournaments.listeners.tournaments;

import me.waterarchery.littournaments.managers.PointManager;
import me.waterarchery.littournaments.managers.TournamentManager;
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

        if (result.getShopAction() != ShopManager.ShopAction.BUY || result.getResult() != ShopTransactionResult.ShopTransactionResultType.SUCCESS) {
            return;
        }

        double pricePerItem = result.getPrice();
        int points = (int) Math.floor(pricePerItem);

        PointManager pointManager = PointManager.getInstance();
        TournamentManager tournamentManager = TournamentManager.getInstance();
        List<Tournament> tournaments = tournamentManager.getTournaments(ShopGuiPlusBuyTournament.class);

        for (Tournament tournament : tournaments) {
            pointManager.addPoint(player.getUniqueId(), tournament, player.getWorld().getName(), "ShopPurchase", points);
        }
    }
}
