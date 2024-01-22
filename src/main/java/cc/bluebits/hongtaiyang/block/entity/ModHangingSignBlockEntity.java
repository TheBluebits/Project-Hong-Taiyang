package cc.bluebits.hongtaiyang.block.entity;

import cc.bluebits.hongtaiyang.registries.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Class for the mod hanging sign block entity
 */
public class ModHangingSignBlockEntity extends SignBlockEntity {
	/**
	 * Constructor for the mod hanging sign block entity
	 * @param pPos The position of the block entity
	 * @param pBlockState The block state of the block entity
	 */
	public ModHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.MOD_HANGING_SIGN.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull BlockEntityType<?> getType() {
		return ModBlockEntities.MOD_HANGING_SIGN.get();
	}
}
