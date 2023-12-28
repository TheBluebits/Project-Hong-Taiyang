package cc.bluebits.hongtaiyang.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.ModBlocks;
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
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(HongTaiyang.MOD_ID, name));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}
	
	
	
	public static final ResourceKey<PlacedFeature> DARKDWELLER_PLACED_KEY = registerKey("darkdweller_placed");
	
	
	
	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		
		register(context, DARKDWELLER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DARKDWELLER_KEY),
				ImmutableList.<PlacementModifier>builder()
						//.add(PlacementUtils.countExtra(7, 0.1f, 3))
						.add(CountPlacement.of(200))
						.add(InSquarePlacement.spread())
						.add(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.absolute(256)))
						.add(BiomeFilter.biome())
						.add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.DARKDWELLER_ROOT.get().defaultBlockState(), BlockPos.ZERO)))
						.build()
		);
	}
}
