package net.primaxstudios.primaxcore.registries;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.items.properties.MaterialProperty;
import net.primaxstudios.primaxcore.items.properties.item.*;
import net.primaxstudios.primaxcore.items.properties.item.persistentdata.PersistentDataProperty;
import net.primaxstudios.primaxcore.items.properties.meta.*;
import net.primaxstudios.primaxcore.registries.types.IdRegistry;

import java.util.Collection;

@Getter
public final class ItemPropertyRegistry extends IdRegistry<ItemProperty> {

    private final MaterialProperty materialProperty = new MaterialProperty();

    public ItemPropertyRegistry() {
        put(DisplayNameProperty.ID, new DisplayNameProperty());
        put(LoreProperty.ID, new LoreProperty());
        put(CustomModelDataProperty.ID, new CustomModelDataProperty());
        put(EnchantProperty.ID, new EnchantProperty());
        put(GlowProperty.ID, new GlowProperty());
        put(FlagProperty.ID, new FlagProperty());
        put(UnbreakableProperty.ID, new UnbreakableProperty());
        put(PersistentDataProperty.ID, new PersistentDataProperty());
        put(AxolotlProperty.ID, new AxolotlProperty());
        put(BannerPatternProperty.ID, new BannerPatternProperty());
        put(BlockDataProperty.ID, new BlockDataProperty());
        put(ColorProperty.ID, new ColorProperty());
        put(DurabilityProperty.ID, new DurabilityProperty());
        put(EntityTypeProperty.ID, new EntityTypeProperty());
        put(PotionEffectProperty.ID, new PotionEffectProperty());
        put(TrimProperty.ID, new TrimProperty());
    }

    public Collection<ItemProperty> getProperties() {
        return values();
    }

    public ItemProperty getProperty(String id) {
        return get(id);
    }
}