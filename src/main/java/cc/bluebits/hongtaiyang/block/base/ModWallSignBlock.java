package cc.bluebits.hongtaiyang.block.base;

import cc.bluebits.hongtaiyang.block.entity.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

/**
 * A custom sign block that extends the vanilla {@link WallSignBlock}. This variant is hanging on the wall.
 */
public class ModWallSignBlock extends WallSignBlock {
	/**
	 * Creates a new instance of the wall sign block.
	 * @param pProperties The block properties, passed to the super constructor
	 * @param pType The wood type, passed to the super constructor
	 */
	public ModWallSignBlock(Properties pProperties, WoodType pType) {
		super(pProperties, pType);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
		 return new ModSignBlockEntity(pPos, pState);
	}
}
