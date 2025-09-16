package net.primaxstudios.primaxlib.item.property;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import lombok.Getter;
import net.primaxstudios.primaxlib.item.property.type.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
public enum ItemPropertyType {

    AMOUNT("amount", new AmountProperty()),
    DISPLAY_NAME("display_name", new DisplayNameProperty()),
    LORE("lore", new LoreProperty()),
    CUSTOM_MODEL_DATA("custom_model_data", new CustomModelDataProperty()),
    ENCHANTS("enchants", new EnchantsProperty()),
    GLOW("glow", new GlowProperty()),
    FLAGS("flags", new FlagsProperty()),
    UNBREAKABLE("unbreakable", new UnbreakableProperty()),
    AXOLOTL_VARIANT("axolotl_variant", new AxolotlVariantProperty()),
    BANNER_PATTERNS("banner_patterns", new BannerPatternsProperty()),
    BLOCK_DATA("block_data", new BlockDataProperty()),
    COLOR("color", new ColorProperty()),
    DAMAGE("damage", new DamageProperty()),
    ENTITY_TYPE("entity_type", new EntityTypeProperty()),
    POTION_EFFECTS("potion_effects", new PotionEffectsProperty()),
    POTION_TYPE("potion_type", new PotionTypeProperty()),
    ARMOR_TRIM("armor_trim", new ArmorTrimProperty());

    private final String id;
    private final ItemProperty property;

    ItemPropertyType(String id, ItemProperty property) {
        this.id = id;
        this.property = property;
    }

    public static List<ItemPropertyType> fromSection(Section section) {
        Set<String> routes = section.getRoutesAsStrings(false);
        return routes.stream()
                .map(ItemPropertyType::fromId)
                .filter(Objects::nonNull)
                .toList();
    }

    public static ItemPropertyType fromId(String id) {
        for (ItemPropertyType type : values()) {
            if (type.getId().equalsIgnoreCase(id)) {
                return type;
            }
        }
        return null;
    }
}
