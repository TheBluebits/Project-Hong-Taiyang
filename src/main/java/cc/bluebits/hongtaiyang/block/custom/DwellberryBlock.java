package cc.bluebits.hongtaiyang.block.custom;

import cc.bluebits.hongtaiyang.block.custom.base.ModThinPillarFruitBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class DwellberryBlock extends ModThinPillarFruitBlock {
	public DwellberryBlock(Properties pProperties, Supplier<Block> survivesOn) {
		super(pProperties, survivesOn);
	}

	@Override
	protected VoxelShape[] makeShapes() {
		// Grouped by AGE and then DIRECTION in order: NORTH, EAST, SOUTH, WEST
		return new VoxelShape[] {
			Block.box(7, 4, -3, 9, 6, -1),
			Block.box(17, 4, 7, 19, 6, 9),
			Block.box(7, 4, 17, 9, 6, 19),
			Block.box(-3, 4, 7, -1, 6, 9),
				
			Block.box(6, 2, -3, 10, 6, 1),
			Block.box(15, 2, 6, 19, 6, 10),
			Block.box(6, 2, 15, 10, 6, 19),
			Block.box(-3, 2, 6, 1, 6, 10),
				
			Block.box(5, 0, -3, 11, 6, 3),
			Block.box(13, 0, 5, 19, 6, 11),
			Block.box(5, 0, 13, 11, 6, 19),
			Block.box(-3, 0, 5, 3, 6, 11),
		};
	}
}
