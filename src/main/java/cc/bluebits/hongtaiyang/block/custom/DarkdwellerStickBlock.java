package cc.bluebits.hongtaiyang.block.custom;

import cc.bluebits.hongtaiyang.block.custom.base.ModFlammableRotatedPillarBlock;
import cc.bluebits.hongtaiyang.block.custom.base.ModFlammableThinPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DarkdwellerStickBlock extends ModFlammableThinPillarBlock {
    public DarkdwellerStickBlock(Properties pProperties) {
        super(pProperties);
    }
    
    @Override
    protected VoxelShape makeBaseShape() {
        // One for each axis in order: X, Y, Z
        return Block.box(6, 6, 6, 10, 10, 10);
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
                Block.box(6, 0, 6, 10, 6, 10),
                Block.box(6, 10, 6, 10, 16, 10),
                Block.box(6, 6, 0, 10, 10, 6),
                Block.box(6, 6, 10, 10, 10, 16),
                Block.box(0, 6, 6, 6, 10, 10),
                Block.box(10, 6, 6, 16, 10, 10)
            },
            {
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty(),
                Shapes.empty()
            }
        };
    }

    @Override
    protected int getBranchType(BlockPos rootPos, BlockPos branchPos, BlockState branchState, Direction.Axis mainAxis) {
        boolean axisCheckOverride = rootPos.get(mainAxis) != branchPos.get(mainAxis);
        if(branchState.hasProperty(AXIS) && branchState.getValue(AXIS) == mainAxis && !axisCheckOverride) return 0;

        if(branchState.getBlock() instanceof DarkdwellerStickBlock || branchState.getBlock() instanceof DarkdwellerLogBlock) return 1;
        
        return 0;
    }
}
