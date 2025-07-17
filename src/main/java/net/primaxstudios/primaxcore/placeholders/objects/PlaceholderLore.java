package net.primaxstudios.primaxcore.placeholders.objects;

import net.primaxstudios.primaxcore.placeholders.Placeholder;
import net.primaxstudios.primaxcore.placeholders.objects.abstracts.PlaceholderStringListAbstract;
import net.primaxstudios.primaxcore.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderLore extends PlaceholderStringListAbstract<List<Component>> {

    public PlaceholderLore(@NotNull List<Component> originalObject, Object ignored) {
        super(originalObject, ignored);
    }

    public PlaceholderLore(@NotNull List<String> rawObject) {
        super(rawObject);
    }

    @Override
    protected List<Component> getObjectAbstract(@NotNull Placeholder placeholder) {
        List<Component> lore = new ArrayList<>();
        for (String string : getRawObject()) {
            for (String line : placeholder.setPlaceholders(string).split("\n")) {
                if (line.isEmpty()) {
                    continue;
                }
                lore.add(ColorUtils.getComponent(line));
            }
        }
        return lore;
//        return getRawObject().stream()
//                .flatMap(this::split)
//                .map(placeholder::setPlaceholders)
//                .flatMap(this::split)
//                .filter(line -> !line.isEmpty())
//                .map(ColorUtils::getComponent)
//                .toList();
    }

//    private Stream<String> split(String line) {
//        if (line.contains("\n")) {
//            return Stream.of(line.split("\n"));
//        } else {
//            return Stream.of(line);
//        }
//    }
}
