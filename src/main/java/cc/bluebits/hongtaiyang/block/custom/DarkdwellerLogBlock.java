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

import java.util.ArrayList;
import java.util.Arrays;

public class DarkdwellerLogBlock extends ModFlammableRotatedPillarBlock {
    public static final IntegerProperty CONNECTIONS = IntegerProperty.create("connections", 0, 4);
    public static final IntegerProperty ORIENTATION = IntegerProperty.create("orientation", 0, 3);
    public static final BooleanProperty STRAIGHT = BooleanProperty.create("straight");
    
    public static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 16, 12);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
    
    // TODO: Change VoxelShape with model

    public DarkdwellerLogBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(CONNECTIONS, 0)
                .setValue(ORIENTATION, 0)
                .setValue(STRAIGHT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CONNECTIONS);
        builder.add(ORIENTATION);
        builder.add(STRAIGHT);
    }
    
    // TODO: Implement BlockState changes when neighbor is changed
    
    // TODO: Fix orientation rotation
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockGetter blockGetter = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        Direction.Axis mainAxis = pContext.getClickedFace().getAxis();
        
        ArrayList<Boolean> connectionList = new ArrayList<>(Arrays.asList(false, false, false, false));
        ArrayList<Direction.Axis> axes = new ArrayList<>(Arrays.asList(Direction.Axis.VALUES));
        axes.remove(mainAxis);
        
        int i = 0;
        for (Direction.Axis axis : axes) {
            BlockState axisNegative = blockGetter.getBlockState(blockPos.relative(axis, -1));
            BlockState axisPositive = blockGetter.getBlockState(blockPos.relative(axis, 1));
                
            connectionList.set(i, axisNegative.getBlock() instanceof DarkdwellerStickBlock);
            connectionList.set(i + 2, axisPositive.getBlock() instanceof DarkdwellerStickBlock);
            
            i++;
        }

        int connections = 0;
        for(boolean connection : connectionList) {
            if(connection) connections++;
        }
        
        int firstConnectionIndex = connectionList.indexOf(true);
        if (firstConnectionIndex == -1) firstConnectionIndex = 0;
        
        boolean straight = (connectionList.get(0) && connectionList.get(2)) || (connectionList.get(1) && connectionList.get(3));
        
        return this.defaultBlockState()
                .setValue(AXIS, mainAxis)
                .setValue(CONNECTIONS, connections)
                .setValue(ORIENTATION, firstConnectionIndex)
                .setValue(STRAIGHT, straight);
    }
}
