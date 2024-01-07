package cc.bluebits.hongtaiyang.block.base;

import cc.bluebits.hongtaiyang.util.BaseConverter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@SuppressWarnings("deprecation")
public class ModModularPillarBlock extends RotatedPillarBlock {
	public static final int nLinks = 6;
	public static final int nLinkTypes = 3;

	public static final IntegerProperty NORTH = IntegerProperty.create("north", 0, nLinkTypes - 1);
	public static final IntegerProperty EAST = IntegerProperty.create("east", 0, nLinkTypes - 1);
	public static final IntegerProperty SOUTH = IntegerProperty.create("south", 0, nLinkTypes - 1);
	public static final IntegerProperty WEST = IntegerProperty.create("west", 0, nLinkTypes - 1);
	public static final IntegerProperty UP = IntegerProperty.create("up", 0, nLinkTypes - 1);
	public static final IntegerProperty DOWN = IntegerProperty.create("down", 0, nLinkTypes - 1);
	public static final Map<Direction, IntegerProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), (map) -> {
		map.put(Direction.NORTH, NORTH);
		map.put(Direction.EAST, EAST);
		map.put(Direction.SOUTH, SOUTH);
		map.put(Direction.WEST, WEST);
		map.put(Direction.UP, UP);
		map.put(Direction.DOWN, DOWN);
	}));
	public static final IntegerProperty NUM_PRIMARY_LINKS = IntegerProperty.create("num_primary_links", 0, nLinks);

	protected VoxelShape makeBaseShape() {
		return Shapes.empty();
	}

	protected VoxelShape[][] makeLinkShapes() {
		// First index is the variant, second is the orientation in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
		return new VoxelShape[][]{};
	}

	protected final VoxelShape[] SHAPES = makeShapes(makeBaseShape(), makeLinkShapes());


	protected VoxelShape[] makeShapes(VoxelShape baseShape, VoxelShape[][] branchShapes) {
		int nShapesPerAxis = (int) (Math.pow(nLinkTypes, nLinks));
		int nShapes = nShapesPerAxis * 3;

		VoxelShape[] shapes = new VoxelShape[nShapes];

		for (int a = 0; a < 3; a++) {
			for (int i = 0; i < nShapesPerAxis; i++) {
				VoxelShape shape = baseShape;

				int idx = a * nShapesPerAxis + i;
				int[] baseNDigits = BaseConverter.convertDecimalToBaseNDigits(i, nLinkTypes, nLinks);

				for (int d = 0; d < baseNDigits.length; d++) {
					int type = baseNDigits[d];
					shape = Shapes.or(shape, branchShapes[type][d]);
				}

				shapes[idx] = shape;
			}
		}

		return shapes;
	}


	public ModModularPillarBlock(Properties pProperties) {
		super(pProperties);

		this.registerDefaultState(this.defaultBlockState()
				.setValue(NORTH, 0)
				.setValue(EAST, 0)
				.setValue(SOUTH, 0)
				.setValue(WEST, 0)
				.setValue(UP, 0)
				.setValue(DOWN, 0)
				.setValue(NUM_PRIMARY_LINKS, 0));
	}


	private static int getNumPrimaryLinks(int[] links) {
		int numPrimaryLinks = 0;
		for (int link : links) {
			if (link == 1) numPrimaryLinks++;
		}

		return numPrimaryLinks;
	}


	private static int getFaceOffsetFromAxisIndex(int axisIndex) {
		int faceOffset = (axisIndex - 1) * 2;
		if (faceOffset < 0) faceOffset += nLinks;
		return faceOffset;
	}

	private static int getAxisIndexFromFaceIndex(int faceIndex) {
		int axisIndex = (faceIndex / 2) + 1;
		if (axisIndex >= Direction.Axis.VALUES.length) axisIndex -= Direction.Axis.VALUES.length;
		return axisIndex;
	}


	public static int getShapeIndex(Direction.Axis axis, int north, int east, int south, int west, int up, int down) {
		int nShapesPerAxis = (int) (Math.pow(nLinkTypes, nLinks));

		int axisIndex = ArrayUtils.indexOf(Direction.Axis.VALUES, axis);
		int axisOffset = nShapesPerAxis * axisIndex;

		int[] faces = new int[]{down, up, north, south, west, east};

		if (getNumPrimaryLinks(faces) < 2) {
			int faceOffset = getFaceOffsetFromAxisIndex(axisIndex);
			faces[faceOffset] = 1;
			faces[faceOffset + 1] = 1;
		}

		int typeIndex = BaseConverter.convertBaseNDigitsToDecimal(faces, nLinkTypes);
		return axisOffset + typeIndex;
	}


	@Override
	public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
		return SHAPES[getShapeIndex(pState.getValue(AXIS), pState.getValue(NORTH), pState.getValue(EAST), pState.getValue(SOUTH), pState.getValue(WEST), pState.getValue(UP), pState.getValue(DOWN))];
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, NUM_PRIMARY_LINKS);
	}


	private Direction.Axis fixMainAxis(int[] links) {
		int firstIndex = -1;

		for (int i = 0; i < links.length; i++) {
			if (links[i] == 1) {
				firstIndex = i;
				break;
			}
		}

		if (firstIndex == -1) return null;

		int axisIndex = getAxisIndexFromFaceIndex(firstIndex);
		return Direction.Axis.VALUES[axisIndex];
	}

	private BlockState applyBlockState(BlockState state, int[] links, Direction.Axis mainAxis) {
		int numPrimaryLinks = getNumPrimaryLinks(links);
		if (numPrimaryLinks < 2) {
			Direction.Axis fixedMainAxis = fixMainAxis(links);
			if (fixedMainAxis != null) mainAxis = fixedMainAxis;
		}

		return state
				.setValue(AXIS, mainAxis)
				.setValue(DOWN, links[0])
				.setValue(UP, links[1])
				.setValue(NORTH, links[2])
				.setValue(SOUTH, links[3])
				.setValue(WEST, links[4])
				.setValue(EAST, links[5])
				.setValue(NUM_PRIMARY_LINKS, numPrimaryLinks);
	}


	protected int getLinkType(BlockPos rootPos, BlockPos linkPos, BlockState linkState, Direction.Axis mainAxis, Direction dir) {
		return 0;
	}

	@Override
	public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
		Direction.Axis mainAxis = pState.getValue(AXIS);
		int[] links = new int[]{
				pState.getValue(DOWN),
				pState.getValue(UP),
				pState.getValue(NORTH),
				pState.getValue(SOUTH),
				pState.getValue(WEST),
				pState.getValue(EAST)
		};

		int linkType = getLinkType(pCurrentPos, pNeighborPos, pNeighborState, mainAxis, pDirection);
		int directionIndex = ArrayUtils.indexOf(Direction.values(), pDirection);
		links[directionIndex] = linkType;

		return applyBlockState(pState, links, mainAxis);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
		BlockGetter blockGetter = pContext.getLevel();
		BlockPos blockPos = pContext.getClickedPos();
		Direction.Axis mainAxis = pContext.getClickedFace().getAxis();

		// All links in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
		int[] links = new int[nLinks];

		for (int i = 0; i < nLinks; i++) {
			int axisIndex = getAxisIndexFromFaceIndex(i);

			Direction.Axis linkAxis = Direction.Axis.VALUES[axisIndex];
			BlockPos linkPos = blockPos.relative(linkAxis, i % 2 == 0 ? -1 : 1); // Inline condition to oscillate between negative and positive distance
			BlockState linkState = blockGetter.getBlockState(linkPos);

			links[i] = getLinkType(blockPos, linkPos, linkState, mainAxis, Direction.values()[i]);
		}

		return applyBlockState(this.defaultBlockState(), links, mainAxis);
	}


	public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
		return false;
	}
}
