package cc.bluebits.hongtaiyang.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.DarkdwellerFoliagePlacer;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModConfiguredFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATRUES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, HongTaiyang.MOD_ID);
	
	public static void register(IEventBus eventBus) {
		CONFIGURED_FEATRUES.register(eventBus);
	}
	
	
	
//	public static final RegistryObject<ConfiguredFeature<?, ?>> DARKDWELLER = CONFIGURED_FEATRUES.register(
//			"darkdweller",
//			() -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
//					BlockStateProvider.simple(ModBlocks.DARKDWELLER_LOG.get()),
//					new StraightTrunkPlacer(5, 6, 3),
//					BlockStateProvider.simple(ModBlocks.DARKDWELLER_STICK.get()),
//					new DarkdwellerFoliagePlacer
//			)))
}
