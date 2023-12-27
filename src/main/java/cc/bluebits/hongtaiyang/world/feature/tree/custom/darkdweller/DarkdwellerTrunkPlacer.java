package cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller;

import cc.bluebits.hongtaiyang.world.feature.tree.ModTrunkPlacers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DarkdwellerTrunkPlacer extends TrunkPlacer {
	public static final Codec<DarkdwellerTrunkPlacer> CODEC = RecordCodecBuilder.create(
			darkdwellerTrunkPlacerInstance -> trunkPlacerParts(darkdwellerTrunkPlacerInstance)
			.and(Codec.intRange(0, 16).fieldOf("crookedHeight").forGetter(tp -> tp.crookedHeight))
			.and(Codec.floatRange(0, 1).fieldOf("crookedProbability").forGetter(tp -> tp.crookedProbability))
			.apply(darkdwellerTrunkPlacerInstance, DarkdwellerTrunkPlacer::new));
	
	protected int crookedHeight;
	protected float crookedProbability;
	
	public DarkdwellerTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB, int crookedHeight, float crookedProbability) {
		super(pBaseHeight, pHeightRandA, pHeightRandB);
		this.crookedHeight = crookedHeight;
		this.crookedProbability = crookedProbability;
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
		
		BlockPos logPos = pPos;
		
		// TODO: Make it possible for multiple trunks to grow / branch off of each other (Probability to branch should scale with total height)
		for(int i = 0; i < height; i++) {
			placeLog(pLevel, pBlockSetter, pRandom, logPos, pConfig);

			if(i >= crookedHeight && i < height - 1 && pRandom.nextFloat() < crookedProbability) {
				Direction crookedDir = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
				logPos = logPos.relative(crookedDir);

				if(isFree(pLevel, logPos)) {
					pBlockSetter.accept(logPos, (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pPos)
							.setValue(RotatedPillarBlock.AXIS, crookedDir.getAxis())));
				}
				else {
					logPos = logPos.relative(crookedDir.getOpposite());
				}
			}
			
			logPos = logPos.above();
		}
		
		return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.below(), 0, false));
	}
}
