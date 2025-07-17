package net.primaxstudios.primaxcore.randomizer.entries;

import lombok.Getter;
import net.primaxstudios.primaxcore.randomizer.RandomizerEntry;

import java.util.Collections;
import java.util.List;

@Getter
public class ObjectEntry<T> extends RandomizerEntry<T> {

    private T object;

    public ObjectEntry(double weight, T object) {
        super(weight);
        this.object = object;
    }

    @Override
    public List<T> getObjects() {
        return Collections.singletonList(object);
    }

    @Override
    public List<T> getRandom() {
        return Collections.singletonList(object);
    }

    @Override
    public T getSingleRandom() {
        return object;
    }
}
