package cc.bluebits.hongtaiyang.world.feature.tree.darkdweller;

import cc.bluebits.hongtaiyang.registries.world.feature.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DarkdwellerTreeGrower extends AbstractTreeGrower {

	@Nullable
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource randomSource, boolean b) {
		return ModConfiguredFeatures.DARKDWELLER_KEY;
	}

	@Override
	public boolean growTree(ServerLevel pLevel, @NotNull ChunkGenerator pGenerator, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull RandomSource pRandom) {
		if (pLevel.getRawBrightness(pPos, 0) >= 6) return false; // Do not generate if light level is 6 or above
		return super.growTree(pLevel, pGenerator, pPos, pState, pRandom);
	}
}
