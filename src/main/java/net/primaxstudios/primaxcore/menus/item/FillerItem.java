package net.primaxstudios.primaxcore.menus.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.menus.item.slot.MultiSlotItem;

@Getter @Setter
public class FillerItem extends MultiSlotItem implements OptionalItem {

    private boolean enabled;

    @Override
    public String getId() {
        return "filler";
    }

    @Override
    public void reload(Section section) {
        enabled = section.getBoolean("enabled", true);
        if (!enabled) return;

        super.reload(section);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
