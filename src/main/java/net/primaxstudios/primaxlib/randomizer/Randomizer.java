package net.primaxstudios.primaxlib.randomizer;

import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxlib.randomizer.entry.NestedRandomizerEntry;
import net.primaxstudios.primaxlib.randomizer.entry.ObjectEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A weighted randomizer that supports multiple draws, uniqueness, and constant entries.
 * @param <T> type of object being randomized
 */
@Getter
@Setter
public class Randomizer<T> {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final List<RandomizerEntry<T>> weightedEntries = new ArrayList<>();
    private final List<RandomizerEntry<T>> guaranteedEntries = new ArrayList<>();

    private double totalWeight = 0;
    private final int draws;
    private final boolean unique;

    public Randomizer(int draws, boolean unique) {
        this.draws = draws;
        this.unique = unique;
    }

    /* ------------------------- ENTRY ADDITION ------------------------- */

    public void addEntry(double weight, RandomizerEntry<T> entry) {
        if (weight >= 100) { // treat "100+" as a constant entry
            guaranteedEntries.add(entry);
            return;
        }
        totalWeight += weight;
        entry.setAccumulatedWeight(totalWeight);
        weightedEntries.add(entry);
    }

    public void addEntry(double weight, T object) {
        addEntry(weight, new ObjectEntry<>(weight, object));
    }

    public void addEntry(double weight, Randomizer<T> randomizer) {
        addEntry(weight, new NestedRandomizerEntry<>(weight, randomizer));
    }

    /* ------------------------- ENTRY ACCESS ------------------------- */

    public boolean hasGuaranteedEntries() {
        return !guaranteedEntries.isEmpty();
    }

    public List<RandomizerEntry<T>> getAllEntries() {
        List<RandomizerEntry<T>> all = new ArrayList<>(weightedEntries);
        all.addAll(guaranteedEntries);
        return Collections.unmodifiableList(all);
    }

    public List<T> getAllObjects() {
        List<T> result = new ArrayList<>();
        for (RandomizerEntry<T> entry : getAllEntries()) {
            result.addAll(entry.getObjects());
        }
        return result;
    }

    /* ------------------------- RANDOM SELECTION ------------------------- */

    public T getRandomSingle() {
        if (hasGuaranteedEntries()) {
            return pickGuaranteedSingle();
        }
        return pickWeightedEntry(weightedEntries, totalWeight).getSingleRandom();
    }

    public List<T> getRandomObjects() {
        if (hasGuaranteedEntries()) {
            return pickAllGuaranteed();
        }
        return unique ? pickUniqueRandomObjects() : pickRandomObjects();
    }

    /* ------------------------- CONSTANTS ------------------------- */

    private T pickGuaranteedSingle() {
        int index = random.nextInt(guaranteedEntries.size());
        return guaranteedEntries.get(index).getSingleRandom();
    }

    private List<T> pickAllGuaranteed() {
        List<T> result = new ArrayList<>();
        for (RandomizerEntry<T> entry : guaranteedEntries) {
            result.addAll(entry.getRandom());
        }
        return result;
    }

    /* ------------------------- WEIGHTED ------------------------- */

    private RandomizerEntry<T> pickWeightedEntry(List<RandomizerEntry<T>> pool, double weightSum) {
        double r = random.nextDouble() * weightSum;
        for (RandomizerEntry<T> entry : pool) {
            if (entry.getAccumulatedWeight() >= r) {
                return entry;
            }
        }
        return pool.getLast(); // fallback, should never happen
    }

    private List<T> pickRandomObjects() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < draws; i++) {
            result.addAll(pickWeightedEntry(weightedEntries, totalWeight).getRandom());
        }
        return result;
    }

    private List<T> pickUniqueRandomObjects() {
        List<T> result = new ArrayList<>();
        List<RandomizerEntry<T>> pool = new ArrayList<>(weightedEntries);
        double weightSum = totalWeight;

        int available = pool.size();
        int picks = Math.min(draws, available);

        for (int i = 0; i < picks; i++) {
            RandomizerEntry<T> entry = pickWeightedEntry(pool, weightSum);
            result.addAll(entry.getRandom());
            pool.remove(entry);
            weightSum -= entry.getWeight(); // remove its weight
        }

        return result;
    }
}