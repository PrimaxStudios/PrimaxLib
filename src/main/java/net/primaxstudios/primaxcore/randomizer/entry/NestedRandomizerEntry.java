package net.primaxstudios.primaxcore.randomizer.entry;

import lombok.Getter;
import net.primaxstudios.primaxcore.randomizer.Randomizer;
import net.primaxstudios.primaxcore.randomizer.RandomizerEntry;

import java.util.List;

/**
 * Represents a nested Randomizer inside another Randomizer.
 */
@Getter
public class NestedRandomizerEntry<T> extends RandomizerEntry<T> {

    private final Randomizer<T> nestedRandomizer;

    public NestedRandomizerEntry(double weight, Randomizer<T> nestedRandomizer) {
        super(weight);
        this.nestedRandomizer = nestedRandomizer;
    }

    @Override
    public List<T> getObjects() {
        return nestedRandomizer.getAllObjects();
    }

    @Override
    public List<T> getRandom() {
        return nestedRandomizer.getRandomObjects();
    }

    @Override
    public T getSingleRandom() {
        return nestedRandomizer.getRandomSingle();
    }
}