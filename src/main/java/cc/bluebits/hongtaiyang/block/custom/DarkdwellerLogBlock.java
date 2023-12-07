package cc.bluebits.hongtaiyang.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DarkdwellerLogBlock extends ModFlammableRotatedPillarBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty ABOVE = BooleanProperty.create("above");
    public static final BooleanProperty BELOW = BooleanProperty.create("below");
    
    public static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
    
    // TODO: Change VoxelShape with model

    public DarkdwellerLogBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(ABOVE, false)
                .setValue(BELOW, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, ABOVE, BELOW);
    }
    
    // TODO: Implement BlockState changes when neighbor is changed
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockGetter blockGetter = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        Direction.Axis mainAxis = pContext.getClickedFace().getAxis();

        boolean north = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.Z, -1);
        boolean east = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.X, 1);
        boolean south = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.Z, 1);
        boolean west = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.X, -1);
        boolean above = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.Y, 1);
        boolean below = attachesTo(blockGetter, blockPos, mainAxis, Direction.Axis.Y, -1);
        
        return this.defaultBlockState()
                .setValue(AXIS, mainAxis)
                .setValue(NORTH, north)
                .setValue(EAST, east)
                .setValue(SOUTH, south)
                .setValue(WEST, west)
                .setValue(ABOVE, above)
                .setValue(BELOW, below);
    }
    
    public final boolean attachesTo(BlockGetter blockGetter, BlockPos pos, Direction.Axis mainAxis, Direction.Axis branchAxis, int direction) {
        BlockState state = blockGetter.getBlockState(pos.relative(branchAxis, direction));
        
        if(mainAxis == branchAxis) return false;
        if(!(state.getBlock() instanceof DarkdwellerStickBlock)) return false;
        return state.getValue(AXIS) == branchAxis;
    }
}
