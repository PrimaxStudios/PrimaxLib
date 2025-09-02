package net.primaxstudios.primaxcore.events.locale;

import lombok.Getter;
import net.primaxstudios.primaxcore.PrimaxCore;
import org.bukkit.event.Event;

@Getter
public abstract class LocaleEvent extends Event {

    private final String namespace;

    protected LocaleEvent(String namespace) {
        super(true);
        this.namespace = namespace;
    }

    public boolean isCore() {
        return namespace.equals(PrimaxCore.NAMESPACE);
    }
}
