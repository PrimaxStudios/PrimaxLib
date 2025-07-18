package net.primaxstudios.primaxcore.registries;

import lombok.Getter;
import net.primaxstudios.primaxcore.items.properties.ItemProperty;
import net.primaxstudios.primaxcore.items.properties.MaterialProperty;
import net.primaxstudios.primaxcore.items.properties.item.*;
import net.primaxstudios.primaxcore.items.properties.item.persistentdata.PersistentDataProperty;
import net.primaxstudios.primaxcore.items.properties.meta.*;
import net.primaxstudios.primaxcore.registries.types.IdRegistry;

import java.util.List;
import java.util.Map;

@Getter
public final class ItemPropertyRegistry extends IdRegistry<ItemProperty> {

    private final MaterialProperty materialProperty = new MaterialProperty();

    public ItemPropertyRegistry() {
        register(DisplayNameProperty.ID, new DisplayNameProperty());
        register(LoreProperty.ID, new LoreProperty());
        register(CustomModelDataProperty.ID, new CustomModelDataProperty());
        register(EnchantProperty.ID, new EnchantProperty());
        register(GlowProperty.ID, new GlowProperty());
        register(FlagProperty.ID, new FlagProperty());
        register(UnbreakableProperty.ID, new UnbreakableProperty());
        register(PersistentDataProperty.ID, new PersistentDataProperty());
        register(AxolotlProperty.ID, new AxolotlProperty());
        register(BannerPatternProperty.ID, new BannerPatternProperty());
        register(BlockDataProperty.ID, new BlockDataProperty());
        register(ColorProperty.ID, new ColorProperty());
        register(DurabilityProperty.ID, new DurabilityProperty());
        register(EntityTypeProperty.ID, new EntityTypeProperty());
        register(PotionEffectProperty.ID, new PotionEffectProperty());
        register(TrimProperty.ID, new TrimProperty());
    }

    public List<ItemProperty> getProperties() {
        return super.getObjects();
    }

    public ItemProperty getProperty(String id) {
        return super.getObject(id);
    }

    public Map<String, ItemProperty> getPropertyById() {
        return super.getObjectById();
    }
}