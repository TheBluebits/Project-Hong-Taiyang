package cc.bluebits.hongtaiyang.block.base;

import cc.bluebits.hongtaiyang.block.entity.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

/**
 * A custom sign block that extends the vanilla {@link StandingSignBlock}. This variant is standing on the ground.
 */
public class ModStandingSignBlock extends StandingSignBlock {
	/**
	 * Creates a new instance of the standing sign block.
	 * @param pProperties The block properties, passed to the super constructor
	 * @param pType The wood type, passed to the super constructor
	 */
	public ModStandingSignBlock(Properties pProperties, WoodType pType) {
		super(pProperties, pType);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
		return new ModSignBlockEntity(pPos, pState);
	}
}
