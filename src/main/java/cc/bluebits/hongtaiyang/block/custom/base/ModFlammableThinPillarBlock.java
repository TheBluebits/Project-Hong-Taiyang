package cc.bluebits.hongtaiyang.block.custom.base;

import cc.bluebits.hongtaiyang.util.BaseConverter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ModFlammableThinPillarBlock extends ModFlammableRotatedPillarBlock{
	public static final int nBranches = 6;
	public static final int nBranchVariants = 3;

	public static final IntegerProperty NORTH = IntegerProperty.create("north", 0, nBranchVariants - 1);
	public static final IntegerProperty SOUTH = IntegerProperty.create("south", 0, nBranchVariants - 1);
	public static final IntegerProperty WEST = IntegerProperty.create("west", 0, nBranchVariants - 1);
	public static final IntegerProperty EAST = IntegerProperty.create("east", 0, nBranchVariants - 1);
	public static final IntegerProperty DOWN = IntegerProperty.create("down", 0, nBranchVariants - 1);
	public static final IntegerProperty UP = IntegerProperty.create("up", 0, nBranchVariants - 1);
	public static final IntegerProperty NUM_PRIMARY_BRANCHES = IntegerProperty.create("num_primary_branches", 0, nBranches);

	protected VoxelShape makeBaseShape() {
		return Shapes.empty();
	}
	
	protected VoxelShape[][] makeBranchShapes() {
		// First index is the variant, second is the orientation in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
		return new VoxelShape[][] {};
	}

	protected final VoxelShape[] SHAPES = makeShapes(makeBaseShape(), makeBranchShapes());
	
	
	protected VoxelShape[] makeShapes(VoxelShape baseShape, VoxelShape[][] branchShapes) {
		int nShapesPerAxis = (int) (Math.pow(nBranchVariants, nBranches));
		int nShapes = nShapesPerAxis * 3;

		VoxelShape[] shapes = new VoxelShape[nShapes];

		for(int a = 0; a < 3; a++) {
			for(int i = 0; i < nShapesPerAxis; i++) {
				VoxelShape shape = baseShape;

				int idx = a * nShapesPerAxis + i;
				int[] baseNDigits = BaseConverter.convertDecimalToBaseNDigits(i, nBranchVariants, nBranches);

				for(int d = 0; d < baseNDigits.length; d++) {
					int variant = baseNDigits[d];
					shape = Shapes.or(shape, branchShapes[variant][d]);
				}

				shapes[idx] = shape;
			}
		}

		return shapes;
	}
	
	
	
	public ModFlammableThinPillarBlock(Properties pProperties) {
		super(pProperties);
		
		this.registerDefaultState(this.defaultBlockState()
				.setValue(NORTH, 0)
				.setValue(EAST, 0)
				.setValue(SOUTH, 0)
				.setValue(WEST, 0)
				.setValue(UP, 0)
				.setValue(DOWN, 0)
				.setValue(NUM_PRIMARY_BRANCHES, 0));
	}



	private static int getNumPrimaryBranches(int[] branches) {
		int numLogBranches = 0;
		for(int branch : branches) {
			if(branch == 1) numLogBranches++;
		}

		return numLogBranches;
	}
	
	

	private static int getFaceOffsetFromAxisIndex(int axisIndex) {
		int faceOffset = (axisIndex - 1) * 2;
		if(faceOffset < 0) faceOffset += nBranches;
		return faceOffset;
	}

	private static int getAxisIndexFromFaceIndex(int faceIndex) {
		int axisIndex = (faceIndex / 2) + 1;
		if(axisIndex >= Direction.Axis.VALUES.length) axisIndex -= Direction.Axis.VALUES.length;
		return axisIndex;
	}
	
	
	
	public static int getShapeIndex(Direction.Axis axis, int north, int east, int south, int west, int up, int down) {
		int nShapesPerAxis = (int) (Math.pow(nBranchVariants, nBranches));

		int axisIndex = ArrayUtils.indexOf(Direction.Axis.VALUES, axis);
		int axisOffset = nShapesPerAxis * axisIndex;

		int[] faces = new int[] { down, up, north, south, west, east };

		if(getNumPrimaryBranches(faces) < 2) {
			int faceOffset = getFaceOffsetFromAxisIndex(axisIndex);
			faces[faceOffset] = 1;
			faces[faceOffset + 1] = 1;
		}

		int variantIndex = BaseConverter.convertBaseNDigitsToDecimal(faces, nBranchVariants);
		return axisOffset + variantIndex;
	}

	
	
	@Override
	public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
		return SHAPES[getShapeIndex(pState.getValue(AXIS), pState.getValue(NORTH), pState.getValue(EAST), pState.getValue(SOUTH), pState.getValue(WEST), pState.getValue(UP), pState.getValue(DOWN))];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, NUM_PRIMARY_BRANCHES);
	}



	private Direction.Axis fixMainAxis(int[] branches) {
		int firstIndex = -1;

		for(int i = 0; i < branches.length; i++) {
			if(branches[i] == 1) {
				firstIndex = i;
				break;
			}
		}

		if(firstIndex == -1) return null;

		int axisIndex = getAxisIndexFromFaceIndex(firstIndex);
		return Direction.Axis.VALUES[axisIndex];
	}
	
	private BlockState applyBlockState(BlockState state, int[] branches, Direction.Axis mainAxis) {
		int numPrimaryBranches = getNumPrimaryBranches(branches);
		if(numPrimaryBranches < 2) {
			Direction.Axis fixedMainAxis = fixMainAxis(branches);
			if(fixedMainAxis != null) mainAxis = fixedMainAxis;
		}

		return state
				.setValue(AXIS, mainAxis)
				.setValue(DOWN, branches[0])
				.setValue(UP, branches[1])
				.setValue(NORTH, branches[2])
				.setValue(SOUTH, branches[3])
				.setValue(WEST, branches[4])
				.setValue(EAST, branches[5])
				.setValue(NUM_PRIMARY_BRANCHES, numPrimaryBranches);
	}
	
	
	
	protected int getBranchType(BlockPos rootPos, BlockPos branchPos, BlockState branchState, Direction.Axis mainAxis) {
		return 0;
	}
	
	@Override
	public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
		Direction.Axis mainAxis = pState.getValue(AXIS);
		int[] branches = new int[] {
				pState.getValue(DOWN),
				pState.getValue(UP),
				pState.getValue(NORTH),
				pState.getValue(SOUTH),
				pState.getValue(WEST),
				pState.getValue(EAST)
		};

		int branchType = getBranchType(pCurrentPos, pNeighborPos, pNeighborState, mainAxis);
		int directionIndex = ArrayUtils.indexOf(Direction.values(), pDirection);
		branches[directionIndex] = branchType;

		return applyBlockState(pState, branches, mainAxis);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
		BlockGetter blockGetter = pContext.getLevel();
		BlockPos blockPos = pContext.getClickedPos();
		Direction.Axis mainAxis = pContext.getClickedFace().getAxis();

		// All branches in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
		int[] branches = new int[nBranches];

		for(int i = 0; i < nBranches; i++) {
			int axisIndex = getAxisIndexFromFaceIndex(i);

			Direction.Axis branchAxis = Direction.Axis.VALUES[axisIndex];
			BlockPos branchPos = blockPos.relative(branchAxis, i % 2 == 0 ? -1 : 1); // Inline condition to oscillate between negative and positive distance
			BlockState branchState = blockGetter.getBlockState(branchPos);

			branches[i] = getBranchType(blockPos, branchPos, branchState, mainAxis);
		}

		return applyBlockState(this.defaultBlockState(), branches, mainAxis);
	}
	
	public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
		return false;
	}
}
