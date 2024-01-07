package cc.bluebits.hongtaiyang.registries.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Class for registering biome modifiers
 */
public class ModBiomeModifiers {
	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<BiomeModifier> registerKey(String name) {
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(HongTaiyang.MOD_ID, name));
	}


	/**
	 * The biome modifier for adding the Darkdweller tree to the Deep Dark biome
	 */
	public static final ResourceKey<BiomeModifier> ADD_TREE_DARKDWELLER = registerKey("add_tree_darkdweller");


	/**
	 * Method for bootstrapping the biome modifiers
	 * @param context The bootstrap context
	 */
	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		var biomes = context.lookup(Registries.BIOME);

		context.register(ADD_TREE_DARKDWELLER, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				HolderSet.direct(biomes.getOrThrow(Biomes.DEEP_DARK)),
				HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DARKDWELLER_PLACED_KEY)),
				GenerationStep.Decoration.VEGETAL_DECORATION));
	}
}
