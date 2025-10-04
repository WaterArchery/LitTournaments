package me.waterarchery.littournaments.api.events;

import lombok.Getter;
import me.waterarchery.littournaments.models.Tournament;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class TournamentEndEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Tournament tournament;

    public TournamentEndEvent(Tournament tournament) {
        this.tournament = tournament;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}
