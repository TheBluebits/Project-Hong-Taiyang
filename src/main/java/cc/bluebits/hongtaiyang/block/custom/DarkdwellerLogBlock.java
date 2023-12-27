package cc.bluebits.hongtaiyang.block.custom;

import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.block.custom.base.ModFlammableThinPillarBlock;
import cc.bluebits.hongtaiyang.util.AxisUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DarkdwellerLogBlock extends ModFlammableThinPillarBlock {
    public DarkdwellerLogBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected VoxelShape makeBaseShape() {
        // One for each axis in order: X, Y, Z
        return Block.box(4, 4, 4, 12, 12, 12);
    }
    
    @Override
    protected VoxelShape[][] makeBranchShapes() {
        // First index is the variant, second is the orientation in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
        return new VoxelShape[][] {
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
    }

    @Override
    protected int getBranchType(BlockPos rootPos, BlockPos branchPos, BlockState branchState, Direction.Axis mainAxis) {
        boolean axisCheckOverride = rootPos.get(mainAxis) != branchPos.get(mainAxis);
        if(branchState.hasProperty(AXIS) && branchState.getValue(AXIS) == mainAxis && !axisCheckOverride) return 0;

        Direction.Axis relativeAxis = AxisUtil.getRelativeAxisFromPos(rootPos, branchPos);
        
        if(branchState.is(Blocks.SCULK) || branchState.is(ModBlocks.ROOTED_SCULK.get())) {
            if(relativeAxis == mainAxis) return 1;
            return 0;
        }
        
        if(branchState.is(ModBlocks.DARKDWELLER_LOG.get())) return 1;
        if(branchState.is(ModBlocks.DARKDWELLER_STICK.get())) return 2;

        return 0;
    }
}
