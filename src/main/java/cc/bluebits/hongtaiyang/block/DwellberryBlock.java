package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.block.base.ModThinPillarFruitBlock;
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
			Block.box(7, 4, 1, 9, 6, 3),
			Block.box(13, 4, 7, 15, 6, 9),
			Block.box(7, 4, 13, 9, 6, 15),
			Block.box(1, 4, 7, 3, 6, 9),
				
			Block.box(6, 2, 1, 10, 6, 5),
			Block.box(11, 2, 6, 15, 6, 10),
			Block.box(6, 2, 11, 10, 6, 15),
			Block.box(1, 2, 6, 5, 6, 10),
				
			Block.box(5, 0, 1, 11, 6, 7),
			Block.box(9, 0, 5, 15, 6, 11),
			Block.box(5, 0, 9, 11, 6, 15),
			Block.box(1, 0, 5, 7, 6, 11),
		};
	}
}
