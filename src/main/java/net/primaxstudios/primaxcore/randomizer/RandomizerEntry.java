package net.primaxstudios.primaxcore.randomizer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class RandomizerEntry<T> {

    private final double weight;
    private double accumulatedWeight; // Used for weighted random selection

    protected RandomizerEntry(double weight) {
        this.weight = weight;
    }

    /**
     * Returns all objects this entry can produce.
     */
    public abstract List<T> getObjects();

    /**
     * Returns one or more randomly selected objects from this entry.
     */
    public abstract List<T> getRandom();

    /**
     * Returns a single randomly selected object from this entry.
     */
    public abstract T getSingleRandom();
}