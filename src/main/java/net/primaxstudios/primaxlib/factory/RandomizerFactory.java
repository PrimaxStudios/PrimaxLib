package net.primaxstudios.primaxlib.factory;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.primaxstudios.primaxlib.item.CustomItem;
import net.primaxstudios.primaxlib.randomizer.Randomizer;

import java.util.function.Function;

public final class RandomizerFactory {

    private RandomizerFactory() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static Randomizer<CustomItem> customItem(Section section) {
        return fromSection(section, CustomItemFactory::fromSection);
    }

    public static <V> Randomizer<V> fromSection(Section section, Function<Section, V> mapper) {
        int rolls = section.getInt("rolls", 1);
        boolean unique = section.getBoolean("unique", false);
        Randomizer<V> randomizer = new Randomizer<>(rolls, unique);
        parseSection(randomizer, section, mapper);
        return randomizer;
    }

    private static <V> void parseSection(Randomizer<V> randomizer, Section section, Function<Section, V> mapper) {
        for (String route : section.getRoutesAsStrings(false)) {
            Section child = section.getSection(route);
            double chance = child.getDouble("chance", 100.0);

            if (child.contains("objects")) {
                Randomizer<V> nested = fromSection(child.getSection("objects"), mapper);
                randomizer.addEntry(chance, nested);
            } else {
                randomizer.addEntry(chance, mapper.apply(child));
            }
        }
    }
}
