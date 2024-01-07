package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.block.base.ModFlammableModularPillarBlock;
import cc.bluebits.hongtaiyang.block.base.ModModularPillarBlock;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.util.AxisUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * The log block used by the Darkdweller tree, which is a modular pillar block.
 * @see ModModularPillarBlock
 */
public class DarkdwellerLogBlock extends ModFlammableModularPillarBlock {
	/**
	 * Constructs a {@code DarkdwellerLogBlock}
	 * @param pProperties The properties of the block, passed to the super constructor
	 */
	public DarkdwellerLogBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	protected VoxelShape makeBaseShape() {
		// One for each axis in order: X, Y, Z
		return Block.box(4, 4, 4, 12, 12, 12);
	}

	@Override
	protected VoxelShape[][] makeLinkShapes() {
		// First index is the variant, second is the orientation in order: DOWN, UP, NORTH, SOUTH, WEST, EAST
		return new VoxelShape[][]{
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
	protected int getLinkType(BlockPos rootPos, BlockPos linkPos, BlockState linkState, Direction.Axis mainAxis, Direction dir) {
		boolean axisCheckOverride = rootPos.get(mainAxis) != linkPos.get(mainAxis);
		if (linkState.hasProperty(AXIS) && linkState.getValue(AXIS) == mainAxis && !axisCheckOverride) return 0;

		Direction.Axis relativeAxis = AxisUtil.getRelativeAxisFromPos(rootPos, linkPos);

		if (linkState.is(Blocks.SCULK) || linkState.is(ModBlocks.ROOTED_SCULK.get())) {
			if (relativeAxis == mainAxis) return 1;
			return 0;
		}

		if (linkState.is(ModBlocks.DARKDWELLER_LOG.get())) return 1;
		if (linkState.is(ModBlocks.DARKDWELLER_STICK.get())) return 2;

		if (linkState.is(ModBlocks.DWELLBERRY.get()) && linkState.getValue(DwellberryBlock.FACING) == dir.getOpposite())
			return 2;

		return 0;
	}
}
