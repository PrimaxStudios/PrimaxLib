package net.primaxstudios.primaxcore.menu.item;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.util.ConfigUtils;

import java.util.List;

@Getter @Setter
public abstract class MultiSlotItem extends AbstractMenuItem {

    private List<Integer> slots;

    @Override
    public void reload(Section section) {
        super.reload(section);
        slots = ConfigUtils.parseSlots(section, "slots");
    }

    @Override
    public List<Integer> getSlots() {
        return slots;
    }
}
