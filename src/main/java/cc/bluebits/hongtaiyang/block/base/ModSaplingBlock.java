package cc.bluebits.hongtaiyang.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModSaplingBlock extends SaplingBlock {
	private final Supplier<Block> soilBlock;
	private final String plantType;
	
	public ModSaplingBlock(AbstractTreeGrower pTreeGrower, Properties pProperties, Supplier<Block> soilBlock, String plantType) {
		super(pTreeGrower, pProperties);
		this.soilBlock = soilBlock;
		this.plantType = plantType;
	}

	@Override
	protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos) {
		return pState.is(soilBlock.get());
	}

	@Override
	public PlantType getPlantType(BlockGetter level, BlockPos pos) {
		return PlantType.get(plantType);
	}
}