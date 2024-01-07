package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.block.base.ModModularPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * The stick block used by the Darkdweller tree, which is a modular pillar block.
 */
public class DarkdwellerStickBlock extends ModModularPillarBlock {
	/**
	 * Constructs a {@code DarkdwellerStickBlock}
	 * @param pProperties The properties of the block, passed to the super constructor
	 */
	public DarkdwellerStickBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	protected VoxelShape makeBaseShape() {
		// One for each axis in order: X, Y, Z
		return Block.box(6, 6, 6, 10, 10, 10);
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
	protected int getLinkType(BlockPos rootPos, BlockPos linkPos, BlockState linkState, Direction.Axis mainAxis, Direction dir) {
		boolean axisCheckOverride = rootPos.get(mainAxis) != linkPos.get(mainAxis);
		if (linkState.hasProperty(AXIS) && linkState.getValue(AXIS) == mainAxis && !axisCheckOverride) return 0;

		if (linkState.getBlock() instanceof DarkdwellerStickBlock || linkState.getBlock() instanceof DarkdwellerLogBlock)
			return 1;

		return 0;
	}
}
