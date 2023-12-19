package cc.bluebits.hongtaiyang.world.feature.tree;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTrunkPlacers {
	public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(ForgeRegistries.TRUNK_PLACER_TYPES, HongTaiyang.MOD_ID);
}
