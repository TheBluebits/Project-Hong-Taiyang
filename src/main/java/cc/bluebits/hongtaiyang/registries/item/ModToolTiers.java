package cc.bluebits.hongtaiyang.registries.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

/**
 * A class for storing all the tool tiers used in the mod.
 */
@SuppressWarnings("MissingJavadoc")
public class ModToolTiers {
	public static final Tier UMBRAL = TierSortingRegistry.registerTier(
			new ForgeTier(5, 750, 12.0f, 3.0f, 22,
					ModTags.Blocks.NEEDS_UMBRAL_TOOL,
					() -> Ingredient.of(ModItems.UMBRAL_GEM.get())), 
			new ResourceLocation(HongTaiyang.MOD_ID, "umbral"),
			List.of(Tiers.NETHERITE),
			List.of()
	);
}
