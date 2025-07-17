package net.primaxstudios.primaxcore.randomizer;

import lombok.Getter;
import lombok.Setter;
import net.primaxstudios.primaxcore.randomizer.entries.ObjectEntry;
import net.primaxstudios.primaxcore.randomizer.entries.SelfEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Getter @Setter
public class Randomizer<T> {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final List<RandomizerEntry<T>> entries = new ArrayList<>();
    private final List<RandomizerEntry<T>> constantEntries = new ArrayList<>();
    private double accumulatedWeight = 0;
    private int rolls = 1;
    private boolean uniqueItems = false;

    public void addEntry(double weight, RandomizerEntry<T> entry) {
        if (weight >= 100) {
            constantEntries.add(entry);
            return;
        }
        accumulatedWeight += weight;
        entry.setAccumulatedWeight(accumulatedWeight);
        entries.add(entry);
    }

    public void addEntry(double weight, T object) {
        addEntry(weight, new ObjectEntry<>(weight, object));
    }

    public void addEntry(double weight, Randomizer<T> randomizer) {
        addEntry(weight, new SelfEntry<>(weight, randomizer));
    }

    public boolean hasConstantEntries() {
        return !constantEntries.isEmpty();
    }

    public List<RandomizerEntry<T>> getAllEntries() {
        List<RandomizerEntry<T>> allEntries = new ArrayList<>(entries);
        if (hasConstantEntries()) {
            allEntries.addAll(constantEntries);
        }
        return allEntries;
    }

    public List<T> getObjects() {
        List<T> objects = new ArrayList<>();
        for (RandomizerEntry<T> entry : getAllEntries()) {
            objects.addAll(entry.getObjects());
        }
        return objects;
    }

    public T getConstantSingleRandom() {
        int index = random.nextInt(0, constantEntries.size());
        RandomizerEntry<T> entry = constantEntries.get(index);
        return entry.getSingleRandom();
    }

    public T getSingleRandom() {
        if (hasConstantEntries()) {
            return getConstantSingleRandom();
        }
        return getWeighedEntry().getSingleRandom();
    }

    public List<T> getConstantRandom() {
        List<T> objects = new ArrayList<>();
        for (RandomizerEntry<T> entry : constantEntries) {
            objects.addAll(entry.getRandom());
        }
        return objects;
    }

    private RandomizerEntry<T> getWeighedEntry(List<RandomizerEntry<T>> entries, double accumulatedWeight) {
        double r = random.nextDouble() * accumulatedWeight;
        for (RandomizerEntry<T> entry : entries) {
            if (entry.getAccumulatedWeight() >= r) {
                return entry;
            }
        }
        return null;
    }

    private RandomizerEntry<T> getWeighedEntry() {
        return getWeighedEntry(entries, accumulatedWeight);
    }

    public List<T> getRandom() {
        if (hasConstantEntries()) {
            return getConstantRandom();
        }
        List<T> objects = new ArrayList<>();
        if (uniqueItems) {
            int size = entries.size();
            if (rolls == size) {
                for (RandomizerEntry<T> entry : entries) {
                    objects.addAll(entry.getRandom());
                }
                return objects;
            }else if (rolls < size) {
                List<RandomizerEntry<T>> entries = new ArrayList<>(this.entries);
                double accumulatedWeight = this.accumulatedWeight;
                for (int i = 0; i < rolls; i++) {
                    RandomizerEntry<T> entry = getWeighedEntry(entries, accumulatedWeight);
                    if (entry == null) {
                        continue;
                    }
                    entries.remove(entry);
                    accumulatedWeight -= entry.getAccumulatedWeight();
                    objects.addAll(entry.getRandom());
                }
                return objects;
            }
        }
        for (int i = 0; i < rolls; i++) {
            objects.addAll(getWeighedEntry().getRandom());
        }
        return objects;
    }
}