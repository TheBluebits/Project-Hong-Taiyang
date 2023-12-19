package cc.bluebits.hongtaiyang.world.feature.tree.custom;

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

import java.util.List;
import java.util.function.BiConsumer;

public class DarkdwellerTrunkPlacer extends TrunkPlacer {
	public static final Codec<DarkdwellerTrunkPlacer> CODEC = RecordCodecBuilder.create(darkdwellerTrunkPlacerInstance -> trunkPlacerParts(darkdwellerTrunkPlacerInstance)
			.apply(darkdwellerTrunkPlacerInstance, DarkdwellerTrunkPlacer::new));
	
	public DarkdwellerTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
		super(pBaseHeight, pHeightRandA, pHeightRandB);
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return ;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
		return null;
	}
}
