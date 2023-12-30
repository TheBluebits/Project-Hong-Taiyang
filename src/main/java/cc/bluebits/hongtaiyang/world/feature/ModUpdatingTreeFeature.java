package cc.bluebits.hongtaiyang.world.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * This is mostly a copy of {@link net.minecraft.world.level.levelgen.feature.TreeFeature} but
 * replaced hard coded block update flags (Set to 19) with a variable that is set in the constructor
 * and a constant that is available statically set to {@value DEFAULT_BLOCK_UPDATE_FLAGS}
 *
 * @see net.minecraft.world.level.levelgen.feature.Feature
 */
public class ModUpdatingTreeFeature extends Feature<TreeConfiguration> {
	private static final int DEFAULT_BLOCK_UPDATE_FLAGS = 3;
	private final int blockUpdateFlags;

	public ModUpdatingTreeFeature(Codec<TreeConfiguration> pCodec, int blockUpdateFlags) {
		super(pCodec);
		this.blockUpdateFlags = blockUpdateFlags;
	}

	private static boolean isVine(LevelSimulatedReader pLevel, BlockPos pPos) {
		return pLevel.isStateAtPosition(pPos, (state) -> state.is(Blocks.VINE));
	}

	@SuppressWarnings("unused")
	public static boolean isAirOrLeaves(LevelSimulatedReader pLevel, BlockPos pPos) {
		return pLevel.isStateAtPosition(pPos, (state) -> state.isAir() || state.is(BlockTags.LEAVES));
	}

	private static void setBlockKnownShape(LevelWriter pLevel, BlockPos pPos, BlockState pState) {
		pLevel.setBlock(pPos, pState, DEFAULT_BLOCK_UPDATE_FLAGS);
	}

	@SuppressWarnings("unused")
	public static boolean validTreePos(LevelSimulatedReader pLevel, BlockPos pPos) {
		return pLevel.isStateAtPosition(pPos, (state) -> state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES));
	}

	private boolean doPlace(WorldGenLevel pLevel, RandomSource pRandom, BlockPos pPos, BiConsumer<BlockPos, BlockState> pRootBlockSetter, BiConsumer<BlockPos, BlockState> pTrunkBlockSetter, FoliagePlacer.FoliageSetter pFoliageBlockSetter, TreeConfiguration pConfig) {
		int i = pConfig.trunkPlacer.getTreeHeight(pRandom);
		int j = pConfig.foliagePlacer.foliageHeight(pRandom, i, pConfig);
		int k = i - j;
		int l = pConfig.foliagePlacer.foliageRadius(pRandom, k);
		BlockPos blockpos = pConfig.rootPlacer.map((p_225286_) -> p_225286_.getTrunkOrigin(pPos, pRandom)).orElse(pPos);
		int i1 = Math.min(pPos.getY(), blockpos.getY());
		int j1 = Math.max(pPos.getY(), blockpos.getY()) + i + 1;
		if (i1 >= pLevel.getMinBuildHeight() + 1 && j1 <= pLevel.getMaxBuildHeight()) {
			OptionalInt optionalint = pConfig.minimumSize.minClippedHeight();
			int k1 = this.getMaxFreeTreeHeight(pLevel, i, blockpos, pConfig);
			if (k1 >= i || optionalint.isPresent() && k1 >= optionalint.getAsInt()) {
				if (pConfig.rootPlacer.isPresent() && !pConfig.rootPlacer.get().placeRoots(pLevel, pRootBlockSetter, pRandom, pPos, blockpos, pConfig)) {
					return false;
				} else {
					List<FoliagePlacer.FoliageAttachment> list = pConfig.trunkPlacer.placeTrunk(pLevel, pTrunkBlockSetter, pRandom, k1, blockpos, pConfig);
					list.forEach((p_272582_) -> pConfig.foliagePlacer.createFoliage(pLevel, pFoliageBlockSetter, pRandom, pConfig, k1, p_272582_, j, l));
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private int getMaxFreeTreeHeight(LevelSimulatedReader pLevel, int pTrunkHeight, BlockPos pTopPosition, TreeConfiguration pConfig) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int i = 0; i <= pTrunkHeight + 1; ++i) {
			int j = pConfig.minimumSize.getSizeAtHeight(pTrunkHeight, i);

			for (int k = -j; k <= j; ++k) {
				for (int l = -j; l <= j; ++l) {
					blockpos$mutableblockpos.setWithOffset(pTopPosition, k, i, l);
					if (!pConfig.trunkPlacer.isFree(pLevel, blockpos$mutableblockpos) || !pConfig.ignoreVines && isVine(pLevel, blockpos$mutableblockpos)) {
						return i - 2;
					}
				}
			}
		}

		return pTrunkHeight;
	}

	protected void setBlock(LevelWriter pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
		pLevel.setBlock(pPos, pState, blockUpdateFlags);
	}

	protected void setBlockAndUpdateWorldGen(WorldGenLevel worldGenLevel, BlockPos pos, BlockState state) {
		worldGenLevel.setBlock(pos, state, blockUpdateFlags);

		for (Direction dir : Direction.values()) {
			BlockPos neighborPos = pos.relative(dir);
			BlockState neighborState = worldGenLevel.getBlockState(neighborPos);
			worldGenLevel.neighborShapeChanged(dir, neighborState, pos, neighborPos, blockUpdateFlags, 512);            // Update self

			BlockState newState = worldGenLevel.getBlockState(pos);
			worldGenLevel.neighborShapeChanged(dir.getOpposite(), newState, neighborPos, pos, blockUpdateFlags, 512);    // Update neighbor
		}
	}

	/**
	 * Places the given feature at the given location.
	 * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
	 * that they can safely generate into.
	 *
	 * @param pContext A context object with a reference to the level and the position the feature is being placed at
	 */
	public final boolean place(FeaturePlaceContext<TreeConfiguration> pContext) {
		final WorldGenLevel worldgenlevel = pContext.level();
		RandomSource randomsource = pContext.random();
		BlockPos blockpos = pContext.origin();
		TreeConfiguration treeconfiguration = pContext.config();
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set1 = Sets.newHashSet();
		final Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		BiConsumer<BlockPos, BlockState> dirtSetter = (pos, state) -> {
			set.add(pos.immutable());
			setBlockAndUpdateWorldGen(worldgenlevel, pos, state);
		};
		BiConsumer<BlockPos, BlockState> logSetter = (pos, state) -> {
			set1.add(pos.immutable());
			setBlockAndUpdateWorldGen(worldgenlevel, pos, state);
		};
		FoliagePlacer.FoliageSetter foliageplacer$foliagesetter = new FoliagePlacer.FoliageSetter() {
			public void set(BlockPos pos, @NotNull BlockState state) {
				set2.add(pos.immutable());
				setBlockAndUpdateWorldGen(worldgenlevel, pos, state);
			}

			public boolean isSet(@NotNull BlockPos pos) {
				return set2.contains(pos);
			}
		};
		BiConsumer<BlockPos, BlockState> decorationSetter = (pos, state) -> {
			set3.add(pos.immutable());
			setBlockAndUpdateWorldGen(worldgenlevel, pos, state);
		};
		boolean flag = this.doPlace(worldgenlevel, randomsource, blockpos, dirtSetter, logSetter, foliageplacer$foliagesetter, treeconfiguration);
		if (flag && (!set1.isEmpty() || !set2.isEmpty())) {
			if (!treeconfiguration.decorators.isEmpty()) {
				TreeDecorator.Context treedecorator$context = new TreeDecorator.Context(worldgenlevel, decorationSetter, randomsource, set1, set2, set);
				treeconfiguration.decorators.forEach((treeDecorator) -> treeDecorator.place(treedecorator$context));
			}

			return BoundingBox.encapsulatingPositions(Iterables.concat(set, set1, set2, set3)).map((boundingBox) -> {
				DiscreteVoxelShape discretevoxelshape = updateLeaves(worldgenlevel, boundingBox, set1, set3, set);
				StructureTemplate.updateShapeAtEdge(worldgenlevel, 3, discretevoxelshape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
				return true;
			}).orElse(false);
		} else {
			return false;
		}
	}

	private static DiscreteVoxelShape updateLeaves(LevelAccessor pLevel, BoundingBox pBox, Set<BlockPos> pRootPositions, Set<BlockPos> pTrunkPositions, Set<BlockPos> pFoliagePositions) {
		DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(pBox.getXSpan(), pBox.getYSpan(), pBox.getZSpan());
		List<Set<BlockPos>> list = Lists.newArrayList();

		for (int j = 0; j < 7; ++j) {
			list.add(Sets.newHashSet());
		}

		for (BlockPos blockpos : Lists.newArrayList(Sets.union(pTrunkPositions, pFoliagePositions))) {
			if (pBox.isInside(blockpos)) {
				discretevoxelshape.fill(blockpos.getX() - pBox.minX(), blockpos.getY() - pBox.minY(), blockpos.getZ() - pBox.minZ());
			}
		}

		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int k1 = 0;
		list.get(0).addAll(pRootPositions);

		while (true) {
			while (k1 >= 7 || !list.get(k1).isEmpty()) {
				if (k1 >= 7) {
					return discretevoxelshape;
				}

				Iterator<BlockPos> iterator = list.get(k1).iterator();
				BlockPos blockPos1 = iterator.next();
				iterator.remove();
				if (pBox.isInside(blockPos1)) {
					if (k1 != 0) {
						BlockState blockstate = pLevel.getBlockState(blockPos1);
						setBlockKnownShape(pLevel, blockPos1, blockstate.setValue(BlockStateProperties.DISTANCE, k1));
					}

					discretevoxelshape.fill(blockPos1.getX() - pBox.minX(), blockPos1.getY() - pBox.minY(), blockPos1.getZ() - pBox.minZ());

					for (Direction direction : Direction.values()) {
						blockpos$mutableblockpos.setWithOffset(blockPos1, direction);
						if (pBox.isInside(blockpos$mutableblockpos)) {
							int k = blockpos$mutableblockpos.getX() - pBox.minX();
							int l = blockpos$mutableblockpos.getY() - pBox.minY();
							int i1 = blockpos$mutableblockpos.getZ() - pBox.minZ();
							if (!discretevoxelshape.isFull(k, l, i1)) {
								BlockState blockState1 = pLevel.getBlockState(blockpos$mutableblockpos);
								OptionalInt optionalint = LeavesBlock.getOptionalDistanceAt(blockState1);
								if (optionalint.isPresent()) {
									int j1 = Math.min(optionalint.getAsInt(), k1 + 1);
									if (j1 < 7) {
										list.get(j1).add(blockpos$mutableblockpos.immutable());
										k1 = Math.min(k1, j1);
									}
								}
							}
						}
					}
				}
			}

			++k1;
		}
	}
}
