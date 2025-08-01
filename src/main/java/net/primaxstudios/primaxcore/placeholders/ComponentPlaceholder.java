package net.primaxstudios.primaxcore.placeholders;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextReplacementConfig;
import org.intellij.lang.annotations.RegExp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter @Setter
public class ComponentPlaceholder extends ArrayList<TextReplacementConfig> {

    public void add(@RegExp String regex, Component replacement) {
        super.add(TextReplacementConfig.builder().match(regex).replacement(replacement).build());
    }

    public void add(@RegExp String regex, String replacement) {
        add(regex, Component.text(replacement));
    }

    public void add(@RegExp String regex, List<Component> replacement) {
        add(regex, Component.join(JoinConfiguration.newlines(), replacement));
    }

    public void add(Pattern pattern, Component replacement) {
        super.add(TextReplacementConfig.builder().match(pattern).replacement(replacement).build());
    }

    public void add(Pattern pattern, String replacement) {
        add(pattern, Component.text(replacement));
    }

    public void add(Pattern pattern, List<Component> replacement) {
        add(pattern, Component.join(JoinConfiguration.newlines(), replacement));
    }

    public void addLiteral(String literal, Component replacement) {
        super.add(TextReplacementConfig.builder().matchLiteral(literal).replacement(replacement).build());
    }

    public void addLiteral(String literal, String replacement) {
        addLiteral(literal, Component.text(replacement));
    }

    public void addLiteral(String literal, List<Component> replacement) {
        addLiteral(literal, Component.join(JoinConfiguration.newlines(), replacement));
    }

    public Component setPlaceholders(Component component) {
        for (TextReplacementConfig config : this) {
            component = component.replaceText(config);
        }
        return component;
    }

    public List<Component> setPlaceholders(List<Component> components) {
        return components.stream().map(this::setPlaceholders).collect(Collectors.toList());
    }

    public void addPlaceholder(ComponentPlaceholder placeholder) {
        addAll(placeholder);
    }
}
