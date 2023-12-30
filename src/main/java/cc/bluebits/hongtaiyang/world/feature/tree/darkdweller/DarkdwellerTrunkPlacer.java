package cc.bluebits.hongtaiyang.world.feature.tree.darkdweller;

import cc.bluebits.hongtaiyang.registries.world.feature.tree.ModTrunkPlacers;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DarkdwellerTrunkPlacer extends TrunkPlacer {
	public static final Codec<DarkdwellerTrunkPlacer> CODEC = RecordCodecBuilder.create(
			darkdwellerTrunkPlacerInstance -> trunkPlacerParts(darkdwellerTrunkPlacerInstance)
					.and(Codec.intRange(0, 16).fieldOf("minCrookedHeight").forGetter(tp -> tp.minCrookedHeight))
					.and(Codec.intRange(0, 16).fieldOf("minBranchingHeight").forGetter(tp -> tp.minBranchingHeight))
					.and(Codec.intRange(0, 16).fieldOf("maxBranchHeight").forGetter(tp -> tp.maxBranchHeight))
					.and(Codec.floatRange(0, 1).fieldOf("crookedProbability").forGetter(tp -> tp.crookedProbability))
					.and(Codec.floatRange(0, 1).fieldOf("maxBranchingProbability").forGetter(tp -> tp.maxBranchingProbability))
					.apply(darkdwellerTrunkPlacerInstance, DarkdwellerTrunkPlacer::new));

	protected int minCrookedHeight;
	protected int minBranchingHeight;
	protected int maxBranchHeight;
	protected float crookedProbability;
	protected float maxBranchingProbability;

	public DarkdwellerTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB, int crookedHeight, int branchingHeight, int maxBranchHeight, float crookedProbability, float maxBranchingProbability) {
		super(pBaseHeight, pHeightRandA, pHeightRandB);
		this.minCrookedHeight = crookedHeight;
		this.minBranchingHeight = branchingHeight;
		this.maxBranchHeight = maxBranchHeight;
		this.crookedProbability = crookedProbability;
		this.maxBranchingProbability = maxBranchingProbability;
	}

	@Override
	protected @NotNull TrunkPlacerType<?> type() {
		return ModTrunkPlacers.DARKDWELLER_TRUNK_PLACER.get();
	}

	@Override
	public int getTreeHeight(RandomSource pRandom) {
		return baseHeight + heightRandA + pRandom.nextInt(heightRandA - 2, heightRandA + 3) - pRandom.nextInt(heightRandB - 1, heightRandB + 1);
	}

	/**
	 * Places a log sideways when the block is valid. Also sets the IS_DIRTY state to true
	 *
	 * @param pLogPos The position of the to be placed log
	 * @param pAxis   The axis the new log should bo oriented in
	 * @return Returns true on success or false when block is not valid
	 */
	private boolean placeLogSidewaysDirty(@NotNull LevelSimulatedReader pLevel, @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter, TreeConfiguration pConfig, RandomSource pRandom, BlockPos pLogPos, Direction.Axis pAxis) {
		if (!validTreePos(pLevel, pLogPos)) return false;

		pBlockSetter.accept(pLogPos, (BlockState) Function.identity().apply(pConfig.trunkProvider.getState(pRandom, pLogPos)
				.setValue(RotatedPillarBlock.AXIS, pAxis)));

		return true;
	}


	@Override
	public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader pLevel, @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter, @NotNull RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, @NotNull TreeConfiguration pConfig) {
		setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
		int height = getTreeHeight(pRandom);
		int mainTrunkHeight = pRandom.nextInt(baseHeight, height);

		ArrayList<Integer> branchEndHeights = new ArrayList<>(List.of(mainTrunkHeight));
		ArrayList<BlockPos> activeLogPositions = new ArrayList<>(List.of(pPos));

		// Iterate over each slice of the tree
		for (int y = 0; y < height; y++) {
			ArrayList<BlockPos> newBranchPositions = new ArrayList<>();
			ArrayList<Integer> newBranchEndHeights = new ArrayList<>();
			ArrayList<Integer> finishedBranches = new ArrayList<>();

			// Iterate over all active log positions and update all of them
			for (int i = 0; i < activeLogPositions.size(); i++) {
				int branchEndHeight = branchEndHeights.get(i);
				if (y >= branchEndHeight) {
					finishedBranches.add(i);
					continue;
				}

				// Place log at active log position. If not possible the branch should end
				BlockPos logPos = activeLogPositions.get(i);
				if (!placeLog(pLevel, pBlockSetter, pRandom, logPos, pConfig)) {
					finishedBranches.add(i);
				}

				// Decide if crooking or branching should happen
				boolean doCrook = y >= minCrookedHeight && y < Math.min(branchEndHeight, height) - 1 && pRandom.nextFloat() < crookedProbability;
				boolean doBranch = y >= minBranchingHeight && y < Math.min(branchEndHeight, height) - 1 && pRandom.nextFloat() < (maxBranchingProbability / height) * y;

				// Create crook if flag is set
				if (doCrook) {
					Direction crookDir = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
					BlockPos crookPos = logPos.relative(crookDir);

					// When log placing was successful, advance position within current branch to the side
					if (placeLogSidewaysDirty(pLevel, pBlockSetter, pConfig, pRandom, crookPos, crookDir.getAxis())) {
						logPos = crookPos;
					}
				}

				// Create branch if flag is set
				if (doBranch) {
					Direction branchDir = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
					BlockPos branchPos = logPos.relative(branchDir);

					// When log placing was successful, add new branch
					if (placeLogSidewaysDirty(pLevel, pBlockSetter, pConfig, pRandom, branchPos, branchDir.getAxis())) {
						newBranchPositions.add(0, branchPos.above());
						newBranchEndHeights.add(0, pRandom.nextInt(2, maxBranchHeight) + y);
					}
				}

				// Advance position within current branch
				activeLogPositions.set(i, logPos.above());
			}

			// Apply new branches
			activeLogPositions.addAll(0, newBranchPositions);
			branchEndHeights.addAll(0, newBranchEndHeights);

			// Optimization to not check finished branches every time again
			finishedBranches.sort(Collections.reverseOrder());
			for (int index : finishedBranches) {
				branchEndHeights.remove(index);
				activeLogPositions.remove(index);
			}
		}

		return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.below(), 0, false));
	}
}
