package cc.bluebits.hongtaiyang.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The flammable variant of the rotated pillar block
 */
public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
	/**
	 * Constructor for the flammable rotated pillar block
	 * @param pProperties The properties of the block
	 */
	public ModFlammableRotatedPillarBlock(Properties pProperties) {
		super(pProperties);
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 5; // General value for logs
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 5; // General value for logs
	}
}
