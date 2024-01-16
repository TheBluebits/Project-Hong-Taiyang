package cc.bluebits.hongtaiyang.registries.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * A class that represents the armor materials of the mod.
 */
@SuppressWarnings("MissingJavadoc")
public enum ModArmorMaterials implements ArmorMaterial {
	UMBRAL("umbral", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
				enumMap.put(ArmorItem.Type.BOOTS, 3);
				enumMap.put(ArmorItem.Type.LEGGINGS, 8);
				enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
				enumMap.put(ArmorItem.Type.HELMET, 3);
			}), 25, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0f, 0.1f,
			() -> Ingredient.of(ModItems.UMBRAL_GEM.get()));
	
	
	
	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;

	private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
		enumMap.put(ArmorItem.Type.BOOTS, 13);
		enumMap.put(ArmorItem.Type.LEGGINGS, 15);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 16);
		enumMap.put(ArmorItem.Type.HELMET, 11);
	});

	ModArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurabilityForType(ArmorItem.@NotNull Type pType) {
		return BASE_DURABILITY.get(pType) * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.@NotNull Type pType) {
		return this.protectionAmounts.get(pType);
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public @NotNull SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public @NotNull Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@Override
	public @NotNull String getName() {
		return HongTaiyang.MOD_ID + ":" + this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
