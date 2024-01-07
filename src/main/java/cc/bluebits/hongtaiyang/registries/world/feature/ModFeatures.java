package cc.bluebits.hongtaiyang.registries.world.feature;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.world.feature.ModUpdatingTreeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering features
 */
@SuppressWarnings("MissingJavadoc")
public class ModFeatures {
	/**
	 * The deferred register for features
	 */
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the features
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		FEATURES.register(eventBus);
	}

	public static final RegistryObject<ModUpdatingTreeFeature> UPDATING_TREE_FEATURE = FEATURES.register(
			"updating_tree_feature",
			() -> new ModUpdatingTreeFeature(TreeConfiguration.CODEC, 3)
	);
}
