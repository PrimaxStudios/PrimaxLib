package net.primaxstudios.primaxcore.randomizer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public abstract class RandomizerEntry<T> {

    private final double weight;
    private double accumulatedWeight;

    public RandomizerEntry(double weight) {
        this.weight = weight;
    }

    public abstract List<T> getObjects();

    public abstract List<T> getRandom();

    public abstract T getSingleRandom();
}
