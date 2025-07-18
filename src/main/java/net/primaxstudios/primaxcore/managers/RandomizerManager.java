package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxcore.PrimaxCore;
import net.primaxstudios.primaxcore.items.CustomItem;
import net.primaxstudios.primaxcore.randomizer.Randomizer;

import java.util.function.Function;

public class RandomizerManager {

    public <V> Randomizer<V> getRandomizer(Section randomizerSection, Function<Section, V> function) {
        Randomizer<V> randomizer = new Randomizer<>();
        if (randomizerSection.contains("rolls")) {
            randomizer.setRolls(randomizerSection.getInt("rolls"));
        }
        for (String route : randomizerSection.getRoutesAsStrings(false)) {
            Section section = randomizerSection.getSection(route);
            double chance;
            if (section.contains("chance")) {
                chance = section.getDouble("chance");
            }else {
                chance = 100;
            }
            if (section.contains("objects")) {
                Section objectsSection = section.getSection("objects");
                Randomizer<V> objectRandomizer = getRandomizer(objectsSection, function);
                randomizer.addEntry(chance, objectRandomizer);
            } else {
                randomizer.addEntry(chance, function.apply(section));
            }
        }
        return randomizer;
    }

    public Randomizer<CustomItem> getCustomItemRandomizer(Section randomizerSection) {
        return getRandomizer(randomizerSection, section -> PrimaxCore.inst().getItemManager().getItem(section));
    }
}
