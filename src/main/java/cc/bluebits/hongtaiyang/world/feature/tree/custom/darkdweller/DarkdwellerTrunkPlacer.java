package cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller;

import cc.bluebits.hongtaiyang.world.feature.tree.ModTrunkPlacers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class DarkdwellerTrunkPlacer extends TrunkPlacer {
	public static final Codec<DarkdwellerTrunkPlacer> CODEC = RecordCodecBuilder.create(
			darkdwellerTrunkPlacerInstance -> trunkPlacerParts(darkdwellerTrunkPlacerInstance)
			.apply(darkdwellerTrunkPlacerInstance, DarkdwellerTrunkPlacer::new));
	
	public DarkdwellerTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
		super(pBaseHeight, pHeightRandA, pHeightRandB);
	}

	@Override
	public int getTreeHeight(RandomSource pRandom) {
		return baseHeight + pRandom.nextInt(heightRandA - 2, heightRandA + 1) + pRandom.nextInt(heightRandB - 1, heightRandB + 1);
	}

	@Override
	protected @NotNull TrunkPlacerType<?> type() {
		return ModTrunkPlacers.DARKDWELLER_TRUNK_PLACER.get();
	}

	@Override
	public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader pLevel, @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter, @NotNull RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, @NotNull TreeConfiguration pConfig) {
		setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
		int height = getTreeHeight(pRandom);
		
		for(int i = 0; i < height; i++) {
			placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);
		}
		
		return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.below(), 0, false));
	}
}
