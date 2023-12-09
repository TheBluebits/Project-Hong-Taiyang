package cc.bluebits.hongtaiyang.block.custom;

import cc.bluebits.hongtaiyang.util.BaseConverter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class DarkdwellerLogBlock extends ModFlammableRotatedPillarBlock {
    public static final int nBranches = 6;
    public static final int nBranchVariants = 3;
    
    public static final IntegerProperty NORTH = IntegerProperty.create("north", 0, nBranchVariants - 1);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south", 0, nBranchVariants - 1);
    public static final IntegerProperty WEST = IntegerProperty.create("west", 0, nBranchVariants - 1);
    public static final IntegerProperty EAST = IntegerProperty.create("east", 0, nBranchVariants - 1);
    public static final IntegerProperty DOWN = IntegerProperty.create("down", 0, nBranchVariants - 1);
    public static final IntegerProperty UP = IntegerProperty.create("up", 0, nBranchVariants - 1);

    protected static final VoxelShape[] SHAPES = makeShapes();

    public DarkdwellerLogBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH, 0)
                .setValue(EAST, 0)
                .setValue(SOUTH, 0)
                .setValue(WEST, 0)
                .setValue(UP, 0)
                .setValue(DOWN, 0));
    }

    protected static VoxelShape[] makeShapes() {
        // One for each axis in order: X, Y, Z
        VoxelShape baseShape = Block.box(4, 4, 4, 12, 12, 12);

        // First index is the variant, second is the orientation in order: WEST, EAST, DOWN, UP, NORTH, SOUTH
        VoxelShape[][] branchShapes = new VoxelShape[][] {
            {
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty()
            },
            {
                Block.box(0, 4, 4, 4, 12, 12),
                Block.box(12, 4, 4, 16, 12, 12),
                Block.box(4, 0, 4, 12, 4, 12),
                Block.box(4, 12, 4, 12, 16, 12),
                Block.box(4, 4, 0, 12, 12, 4),
                Block.box(4, 4, 12, 12, 12, 16)
            },
            {
                Block.box(0, 6, 6, 4, 10, 10),
                Block.box(12, 6, 6, 16, 10, 10),
                Block.box(6, 0, 6, 10, 4, 10),
                Block.box(6, 12, 6, 10, 16, 10),
                Block.box(6, 6, 0, 10, 10, 4),
                Block.box(6, 6, 12, 10, 10, 16)
            }
        };

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
    
    public static int getShapeIndex(Direction.Axis axis, int north, int east, int south, int west, int up, int down) {
        int nShapesPerAxis = (int) (Math.pow(nBranchVariants, nBranches));
        
        int axisIndex = ArrayUtils.indexOf(Direction.Axis.VALUES, axis);
        int axisOffset = nShapesPerAxis * axisIndex;
        
        int[] faces = new int[] { west, east, down, up, north, south };
        
        int variantIndex = BaseConverter.convertBaseNDigitsToDecimal(faces, nBranchVariants);
        return axisOffset + variantIndex;
    }
    
    public static IntegerProperty getPropertyByDirection(Direction direction) {
        switch (direction) {
            case UP -> {
                return UP;
            }
            case DOWN -> {
                return DOWN;
            }
            case EAST -> {
                return EAST;
            }
            case WEST -> {
                return WEST;
            }
            case NORTH -> {
                return NORTH;
            }
            case SOUTH -> {
                return SOUTH;
            }
        }
        
        return NORTH;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[getShapeIndex(pState.getValue(AXIS), pState.getValue(NORTH), pState.getValue(EAST), pState.getValue(SOUTH), pState.getValue(WEST), pState.getValue(UP), pState.getValue(DOWN))];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        int[] branches = new int[] {
           pState.getValue(DOWN),
           pState.getValue(UP),
           pState.getValue(NORTH),
           pState.getValue(SOUTH),
           pState.getValue(WEST),
           pState.getValue(EAST)
        };
        
        int branchType = getBranchType(pNeighborState);
        int directionIndex = ArrayUtils.indexOf(Direction.values(), pDirection);
        branches[directionIndex] = branchType;
        branches = fixBranches(branches, pState.getValue(AXIS));

        return pState
                .setValue(DOWN, branches[0])
                .setValue(UP, branches[1])
                .setValue(NORTH, branches[2])
                .setValue(SOUTH, branches[3])
                .setValue(WEST, branches[4])
                .setValue(EAST, branches[5]);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockGetter blockGetter = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        Direction.Axis mainAxis = pContext.getClickedFace().getAxis();

        // All branches in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
        int[] branches = new int[] {
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.Y, -1))),
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.Y, 1))),
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.Z, -1))),
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.Z, 1))),
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.X, -1))),
            getBranchType(blockGetter.getBlockState(blockPos.relative(Direction.Axis.X, 1)))
        };
        
        branches = fixBranches(branches, mainAxis);
        return this.defaultBlockState()
                .setValue(AXIS, mainAxis)
                .setValue(DOWN, branches[0])
                .setValue(UP, branches[1])
                .setValue(NORTH, branches[2])
                .setValue(SOUTH, branches[3])
                .setValue(WEST, branches[4])
                .setValue(EAST, branches[5]);
    }
    
    // TODO: Make defaulting log visual go away if no longer necessary
    public int[] fixBranches(int[] branches, Direction.Axis mainAxis) {
        int branchCount = 0;
        int firstBranchIndex = -1;
        for (int i = 0; i < branches.length; i++) {
            if (branches[i] == 1) { // Check if branch is present and if it is another log
                branchCount++;
                if(firstBranchIndex < 0) firstBranchIndex = i;
            }
        }

        // Use default log visual along the main axis when less than 2 branches are present
        if(branchCount < 2) {
            int axisIndex;

            if (firstBranchIndex >= 0) axisIndex = firstBranchIndex / 2;
            else axisIndex = ArrayUtils.indexOf(Direction.Axis.VALUES, mainAxis) + 1;
            if(axisIndex >= Direction.Axis.VALUES.length) axisIndex -= Direction.Axis.VALUES.length;

            int branchIndex = axisIndex * 2;
            branches[branchIndex] = 1;
            branches[branchIndex + 1] = 1;
        }
        
        return branches;
    }

    public final int getBranchType(BlockState state) {
        if(state.getBlock() instanceof DarkdwellerLogBlock) return 1;
        if(state.getBlock() instanceof DarkdwellerStickBlock) return 2;
        
        return 0;
    }
}
