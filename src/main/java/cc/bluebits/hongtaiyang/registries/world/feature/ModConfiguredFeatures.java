package cc.bluebits.hongtaiyang.registries.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerFoliagePlacer;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerTreeDecorator;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

/**
 * Class for registering configured features
 */
@SuppressWarnings("MissingJavadoc")
public class ModConfiguredFeatures {
	/**
	 * Method for registering the configured features
	 * @param name The name of the configured feature
	 * @return The ResourceKey to the configured feature
	 */
	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HongTaiyang.MOD_ID, name));
	}

	@SuppressWarnings("SameParameterValue")
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}


	public static final ResourceKey<ConfiguredFeature<?, ?>> DARKDWELLER_KEY = registerKey("darkdweller");


	/**
	 * Method for bootstrapping the configured features
	 * @param context The bootstrap context
	 */
	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		@SuppressWarnings("unused")
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

		register(context, DARKDWELLER_KEY, ModFeatures.UPDATING_TREE_FEATURE.get(), new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(ModBlocks.DARKDWELLER_LOG.get()),
				new DarkdwellerTrunkPlacer(2, 4, 3, 1, 1, 6, 0.65f, 0.9f),
				BlockStateProvider.simple(ModBlocks.ROOTED_SCULK.get()),
				new DarkdwellerFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2, 0.75f),
				new TwoLayersFeatureSize(1, 0, 1))
				.dirt(BlockStateProvider.simple(Blocks.SCULK))
				.decorators(List.of(new DarkdwellerTreeDecorator(2, 1, 0.15f, 0.20f)))
				.build());
	}
}
