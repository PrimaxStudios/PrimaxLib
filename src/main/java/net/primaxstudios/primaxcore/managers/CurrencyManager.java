package net.primaxstudios.primaxcore.managers;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxcore.currencies.Currency;
import net.primaxstudios.primaxcore.registries.CurrencyRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

@Getter
public class CurrencyManager {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyManager.class);
    private final CurrencyRegistry registry = new CurrencyRegistry();

    public <T extends Currency> T getCurrency(Section section, Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(Section.class);
            return constructor.newInstance(section);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency getCurrency(Section section) {
        String type = section.getString("type");
        Class<? extends Currency> clazz = registry.getClass(type);
        return getCurrency(section, clazz);
    }
}