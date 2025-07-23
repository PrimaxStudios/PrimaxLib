package net.primaxstudios.primaxcore.menus.item.slot;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.utils.ConfigUtils;

import java.util.List;

@Getter @Setter
public abstract class MultiSlotItem extends SlotBoundItem {

    private List<Integer> slots;

    @Override
    public void reload(Section section) {
        super.reload(section);
        slots = ConfigUtils.parseSlots(section, "slots");
    }

    @Override
    public List<Integer> getBoundSlots() {
        return slots;
    }
}
