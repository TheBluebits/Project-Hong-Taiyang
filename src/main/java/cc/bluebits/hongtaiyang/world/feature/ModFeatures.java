package cc.bluebits.hongtaiyang.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.world.feature.tree.ModTreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, HongTaiyang.MOD_ID);

	public static void register(IEventBus eventBus) {
		FEATURES.register(eventBus);
	}
	
	public static final RegistryObject<ModTreeFeature> CONNECTING_TREE_FEATURE = FEATURES.register(
			"connecting_tree_feature",
			() -> new ModTreeFeature(TreeConfiguration.CODEC, 3)
	);
}
