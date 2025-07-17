package net.primaxstudios.primaxcore.randomizer.entries;

import lombok.Getter;
import net.primaxstudios.primaxcore.randomizer.Randomizer;
import net.primaxstudios.primaxcore.randomizer.RandomizerEntry;

import java.util.List;

@Getter
public class SelfEntry<T> extends RandomizerEntry<T> {

    private final Randomizer<T> randomizer;

    public SelfEntry(double weight, Randomizer<T> randomizer) {
        super(weight);
        this.randomizer = randomizer;
    }

    @Override
    public List<T> getObjects() {
        return randomizer.getObjects();
    }

    @Override
    public List<T> getRandom() {
        return randomizer.getRandom();
    }

    @Override
    public T getSingleRandom() {
        return randomizer.getSingleRandom();
    }
}
