package cc.bluebits.hongtaiyang.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The flammable variant of the modular pillar block
 */
public class ModFlammableModularPillarBlock extends ModModularPillarBlock {
	/**
	 * Constructor for the flammable modular pillar block
	 * @param pProperties The properties of the block
	 */
	public ModFlammableModularPillarBlock(Properties pProperties) {
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
