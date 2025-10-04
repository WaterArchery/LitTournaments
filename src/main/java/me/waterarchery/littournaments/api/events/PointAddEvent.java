package me.waterarchery.littournaments.api.events;

import lombok.Getter;
import lombok.Setter;
import me.waterarchery.littournaments.models.Tournament;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
@Setter
public class PointAddEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Tournament tournament;
    private final UUID player;
    private final String actionName;
    private boolean isCancelled;
    private int amount;

    public PointAddEvent(Tournament tournament, UUID player, int amount, String actionName) {
        this.tournament = tournament;
        this.player = player;
        this.amount = amount;
        this.actionName = actionName;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
