package net.primaxstudios.primaxcore.events.locale;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter @Setter
public class LocaleReloadEvent extends LocaleEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private Map<String, String> messageById;

    public LocaleReloadEvent(String namespace, Map<String, String> messageById) {
        super(namespace);
        this.messageById = messageById;
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
