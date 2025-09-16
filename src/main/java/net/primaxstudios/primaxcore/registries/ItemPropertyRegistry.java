package net.primaxstudios.primaxcore.registries;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.items.properties.MaterialProperty;
import net.primaxstudios.primaxcore.items.properties.item.*;
import net.primaxstudios.primaxcore.items.properties.meta.*;

import java.util.Collection;
import java.util.HashMap;

@Getter
public final class ItemPropertyRegistry extends HashMap<String, ItemProperty> {

    private final MaterialProperty materialProperty = new MaterialProperty();

    public ItemPropertyRegistry() {
        put(DisplayNameProperty.ID, new DisplayNameProperty());
        put(LoreProperty.ID, new LoreProperty());
        put(CustomModelDataProperty.ID, new CustomModelDataProperty());
        put(EnchantProperty.ID, new EnchantProperty());
        put(GlowProperty.ID, new GlowProperty());
        put(FlagProperty.ID, new FlagProperty());
        put(UnbreakableProperty.ID, new UnbreakableProperty());
        put(AxolotlVariantProperty.ID, new AxolotlVariantProperty());
        put(BannerPatternProperty.ID, new BannerPatternProperty());
        put(BlockDataProperty.ID, new BlockDataProperty());
        put(ColorProperty.ID, new ColorProperty());
        put(DamageProperty.ID, new DamageProperty());
        put(EntityTypeProperty.ID, new EntityTypeProperty());
        put(PotionEffectProperty.ID, new PotionEffectProperty());
        put(PotionTypeProperty.ID, new PotionTypeProperty());
        put(ArmorTrimProperty.ID, new ArmorTrimProperty());
    }

    public Collection<ItemProperty> getProperties() {
        return values();
    }

    public ItemProperty getProperty(String id) {
        return get(id);
    }
}