package net.primaxstudios.primaxcore.events.locale;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter @Setter
public class LocaleAddEvent extends LocaleEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final String id;
    private String message;
    private boolean cancelled;

    public LocaleAddEvent(String namespace, String id, String message) {
        super(namespace);
        this.id = id;
        this.message = message;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
