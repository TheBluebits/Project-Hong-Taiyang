package cc.bluebits.hongtaiyang.registries.world.feature.tree;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering foliage placers
 */
@SuppressWarnings("MissingJavadoc")
public class ModFoliagePlacers {
	/**
	 * The deferred register for foliage placers
	 */
	public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the foliage placers
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		FOLIAGE_PLACERS.register(eventBus);
	}


	public static final RegistryObject<FoliagePlacerType<DarkdwellerFoliagePlacer>> DARKDWELLER_FOLIAGE_PLACER = FOLIAGE_PLACERS.register(
			"darkdweller_foliage_placer",
			() -> new FoliagePlacerType<>(DarkdwellerFoliagePlacer.CODEC)
	);
}
