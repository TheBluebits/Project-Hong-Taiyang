package cc.bluebits.hongtaiyang.registries.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

/**
 * Class for registering placed features
 */
@SuppressWarnings("MissingJavadoc")
public class ModPlacedFeatures {
	@SuppressWarnings("SameParameterValue")
	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(HongTaiyang.MOD_ID, name));
	}

	@SuppressWarnings("SameParameterValue")
	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}


	public static final ResourceKey<PlacedFeature> DARKDWELLER_PLACED_KEY = registerKey("darkdweller_placed");


	/**
	 * Method for bootstrapping the placed features
	 * @param context The bootstrap context
	 */
	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, DARKDWELLER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DARKDWELLER_KEY),
				ImmutableList.<PlacementModifier>builder()
						.add(PlacementUtils.countExtra(12, 0.1f, 8))
						.add(InSquarePlacement.spread())
						.add(BiomeFilter.biome())
						.add(HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(0))))
						.add(EnvironmentScanPlacement.scanningFor(
								Direction.DOWN,
								BlockPredicate.wouldSurvive(ModBlocks.DARKDWELLER_ROOT.get().defaultBlockState(), BlockPos.ZERO),
								BlockPredicate.matchesBlocks(Blocks.AIR), 32))
						.build()
		);
	}
}
