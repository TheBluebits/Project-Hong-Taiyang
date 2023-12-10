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
    public static final IntegerProperty NUM_LOG_BRANCHES = IntegerProperty.create("num_log_branches", 0, 6);

    protected static final VoxelShape[] SHAPES = makeShapes();

    public DarkdwellerLogBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH, 0)
                .setValue(EAST, 0)
                .setValue(SOUTH, 0)
                .setValue(WEST, 0)
                .setValue(UP, 0)
                .setValue(DOWN, 0)
                .setValue(NUM_LOG_BRANCHES, 0));
    }

    protected static VoxelShape[] makeShapes() {
        // One for each axis in order: X, Y, Z
        VoxelShape baseShape = Block.box(4, 4, 4, 12, 12, 12);

        // First index is the variant, second is the orientation in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
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
                Block.box(4, 0, 4, 12, 4, 12),
                Block.box(4, 12, 4, 12, 16, 12),
                Block.box(4, 4, 0, 12, 12, 4),
                Block.box(4, 4, 12, 12, 12, 16),
                Block.box(0, 4, 4, 4, 12, 12),
                Block.box(12, 4, 4, 16, 12, 12)
            },
            {
                Block.box(6, 0, 6, 10, 4, 10),
                Block.box(6, 12, 6, 10, 16, 10),
                Block.box(6, 6, 0, 10, 10, 4),
                Block.box(6, 6, 12, 10, 10, 16),
                Block.box(0, 6, 6, 4, 10, 10),
                Block.box(12, 6, 6, 16, 10, 10)
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
        
        int[] faces = new int[] { down, up, north, south, west, east };
        
        if(getNumLogBranches(faces) < 2) {
            int faceOffset = getFaceOffsetFromAxisIndex(axisIndex);
            faces[faceOffset] = 1;
            faces[faceOffset + 1] = 1;
        }
        
        int variantIndex = BaseConverter.convertBaseNDigitsToDecimal(faces, nBranchVariants);
        return axisOffset + variantIndex;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES[getShapeIndex(pState.getValue(AXIS), pState.getValue(NORTH), pState.getValue(EAST), pState.getValue(SOUTH), pState.getValue(WEST), pState.getValue(UP), pState.getValue(DOWN))];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, NUM_LOG_BRANCHES);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
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
    
    private BlockState applyBlockState(BlockState state, int[] branches, Direction.Axis mainAxis) {
        int numLogBranches = getNumLogBranches(branches);
        if(numLogBranches < 2) {
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
                .setValue(NUM_LOG_BRANCHES, numLogBranches);
    }
    
    private static int getNumLogBranches(int[] branches) {
        int numLogBranches = 0;
        for(int branch : branches) {
            if(branch == 1) numLogBranches++;
        }
        
        return numLogBranches;
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

    private static int getAxisIndexFromFaceIndex(int faceIndex) {
        int axisIndex = (faceIndex / 2) + 1;
        if(axisIndex >= Direction.Axis.VALUES.length) axisIndex -= Direction.Axis.VALUES.length;
        return axisIndex;
    }

    private static int getFaceOffsetFromAxisIndex(int axisIndex) {
        int faceOffset = (axisIndex - 1) * 2;
        if(faceOffset < 0) faceOffset += nBranches;
        return faceOffset;
    }

    private int getBranchType(BlockPos rootPos, BlockPos branchPos, BlockState branchState, Direction.Axis mainAxis) {
        boolean axisCheckOverride = rootPos.get(mainAxis) != branchPos.get(mainAxis);
		if(branchState.hasProperty(AXIS) && branchState.getValue(AXIS) == mainAxis && !axisCheckOverride) return 0;
        
        if(branchState.getBlock() instanceof DarkdwellerLogBlock || branchState.getBlock() instanceof SculkSoilBlock) return 1;
        if(branchState.getBlock() instanceof DarkdwellerStickBlock) return 2;
        
        return 0;
    }
}
