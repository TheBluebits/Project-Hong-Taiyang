package cc.bluebits.hongtaiyang.block.base;

import cc.bluebits.hongtaiyang.block.entity.ModHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

/**
 * A custom hanging sign block that extends the vanilla {@link WallHangingSignBlock}. This variant is hanging from the wall.
 */
public class ModWallHangingSignBlock extends WallHangingSignBlock {
	/**
	 * Creates a new instance of the hanging sign block.
	 * @param pProperties The block properties, passed to the super constructor
	 * @param pType The wood type, passed to the super constructor
	 */
	public ModWallHangingSignBlock(Properties pProperties, WoodType pType) {
		super(pProperties, pType);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
		return new ModHangingSignBlockEntity(pPos, pState);
	}
}