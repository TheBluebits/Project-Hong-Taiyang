package cc.bluebits.hongtaiyang.registries.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
	public static final FoodProperties DWELLBERRY = new FoodProperties.Builder()
			.nutrition(2)
			.saturationMod(0.1f)
			.effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100), 1f)
			.effect(() -> new MobEffectInstance(MobEffects.GLOWING, 100), 1f)
			.effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600), 1f)
			.alwaysEat()
			.fast()
			.build();
}
