package cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller;

import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.block.custom.DarkdwellerStickBlock;
import cc.bluebits.hongtaiyang.world.feature.tree.ModTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class DarkdwellerTreeDecorator extends TreeDecorator {
	public static final Codec<DarkdwellerTreeDecorator> CODEC = RecordCodecBuilder.create(
			darkdwellerTreeDecoratorInstance -> darkdwellerTreeDecoratorInstance.group(
					Codec.INT.fieldOf("branchStartHeight").forGetter(DarkdwellerTreeDecorator::getBranchStartHeight),
					Codec.intRange(0, 100).fieldOf("probability").forGetter(DarkdwellerTreeDecorator::getProbability)
			).apply(darkdwellerTreeDecoratorInstance, DarkdwellerTreeDecorator::new)
	);
	
	private final int branchStartHeight;
	private final int probability; // Numeric value in percent e.g. 70 -> 70% chance of placement 
	
	protected int getBranchStartHeight() { return branchStartHeight; }
	protected int getProbability() { return probability; }
	
	public DarkdwellerTreeDecorator(int branchStartHeight, int probability) {
		super();
		this.branchStartHeight = branchStartHeight;
		this.probability = probability;
	}
	
	@Override
	protected @NotNull TreeDecoratorType<?> type() {
		return ModTreeDecorators.DARKDWELLER_TREE_DECORATOR.get();
	}

	@Override
	public void place(Context pContext) {
		RandomSource randomSource = pContext.random();
		List<BlockPos> logPositions = pContext.logs();
		int startY = logPositions.get(0).getY();
		
		for(BlockPos pos : logPositions) {
			if(pos.getY() - startY < branchStartHeight) continue;
			
			for(Direction dir : Direction.Plane.HORIZONTAL) {
				BlockPos stickPos = pos.offset(dir.getOpposite().getStepX(), 0, dir.getOpposite().getStepZ());
				
				if(pContext.isAir(stickPos) && randomSource.nextInt(0, 100) < getProbability()) {
					pContext.setBlock(stickPos, ModBlocks.DARKDWELLER_STICK.get().defaultBlockState()
							.setValue(DarkdwellerStickBlock.AXIS, dir.getAxis())
					);
				}
			}
		}
	}
}
