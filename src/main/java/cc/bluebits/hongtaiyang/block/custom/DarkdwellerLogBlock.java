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
import java.util.Collections;

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
    
    // TODO: Fix orientation rotation (c2 not working with vertical north/west)
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockGetter blockGetter = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        Direction.Axis mainAxis = pContext.getClickedFace().getAxis();
        
        ArrayList<Direction.Axis> axes = new ArrayList<>(Arrays.asList(Direction.Axis.VALUES));
        Collections.rotate(axes, 1);
        axes.remove(mainAxis);

        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        blockPosList.add(blockPos.relative(axes.get(0), -1));
        blockPosList.add(blockPos.relative(axes.get(1), 1));
        blockPosList.add(blockPos.relative(axes.get(0), 1));
        blockPosList.add(blockPos.relative(axes.get(1), -1));
        
        ArrayList<BlockState> blockStateList = new ArrayList<>();
        for(BlockPos pos : blockPosList) blockStateList.add(blockGetter.getBlockState(pos));

        ArrayList<Boolean> connectionList = new ArrayList<>();
        for(int i = 0; i < blockPosList.size(); i++) {
            boolean isStickInstance = blockStateList.get(i).getBlock() instanceof DarkdwellerStickBlock;
            if(!isStickInstance) {
                connectionList.add(false);
                continue;
            }
            
            boolean isConnectingFace = blockStateList.get(i).getValue(AXIS) == axes.get(i % 2);
            connectionList.add(isConnectingFace);
        }

        int connections = 0;
        for(boolean connection : connectionList) {
            if(connection) connections++;
        }
        
        int orientation = connectionList.indexOf(true);
        if (orientation == -1) orientation = 0;
        
        boolean straight = (connectionList.get(0) && connectionList.get(2)) || (connectionList.get(1) && connectionList.get(3));
        
        return this.defaultBlockState()
                .setValue(AXIS, mainAxis)
                .setValue(CONNECTIONS, connections)
                .setValue(ORIENTATION, orientation)
                .setValue(STRAIGHT, straight);
    }
}
