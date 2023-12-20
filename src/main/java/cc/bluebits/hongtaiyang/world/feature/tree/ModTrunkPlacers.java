package cc.bluebits.hongtaiyang.world.feature.tree;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.DarkdwellerTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTrunkPlacers {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, HongTaiyang.MOD_ID);
	
	public static void register(IEventBus eventBus) {
		TRUNK_PLACERS.register(eventBus);
	}
	
	public static final RegistryObject<TrunkPlacerType<DarkdwellerTrunkPlacer>> DARKDWELLER_TRUNK_PLACER = TRUNK_PLACERS.register(
		"darkdweller_trunk_placer",
		() -> new TrunkPlacerType<>(DarkdwellerTrunkPlacer.CODEC)
	);
}
