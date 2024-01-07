package cc.bluebits.hongtaiyang.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller.DarkdwellerFoliagePlacer;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller.DarkdwellerTreeDecorator;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller.DarkdwellerTrunkPlacer;
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
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Collections;
import java.util.List;

public class ModConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARKDWELLER_KEY = registerKey("darkdweller");
	
	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HongTaiyang.MOD_ID, name));
	}
	
	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,  ResourceKey<ConfiguredFeature<?, ?>> key,  F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
	
	
	
	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		
		register(context, DARKDWELLER_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
				BlockStateProvider.simple(ModBlocks.DARKDWELLER_LOG.get()),
				new DarkdwellerTrunkPlacer(2, 2, 1),
				BlockStateProvider.simple(ModBlocks.ROOTED_SCULK.get()),
				new DarkdwellerFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 2, 75), 
				new TwoLayersFeatureSize(0, 0, 0))
				.dirt(BlockStateProvider.simple(Blocks.SCULK))
				.decorators(Collections.singletonList(new DarkdwellerTreeDecorator(2, 35)))
				.build());
	}
}