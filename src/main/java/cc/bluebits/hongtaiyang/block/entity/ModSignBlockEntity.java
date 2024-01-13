package cc.bluebits.hongtaiyang.block.entity;

import cc.bluebits.hongtaiyang.registries.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Class for the mod sign block entity
 */
public class ModSignBlockEntity extends SignBlockEntity {
	/**
	 * Constructor for the mod sign block entity
	 * @param pPos The position of the block entity, passed to the super constructor
	 * @param pBlockState The block state of the block entity, passed to the super constructor
	 */
	public ModSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.MOD_SIGN.get(), pPos, pBlockState);
	}

	@Override
	public @NotNull BlockEntityType<?> getType() {
		return ModBlockEntities.MOD_SIGN.get();
	}
}
