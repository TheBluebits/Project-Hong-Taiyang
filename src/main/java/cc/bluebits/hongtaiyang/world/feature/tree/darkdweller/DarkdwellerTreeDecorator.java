package cc.bluebits.hongtaiyang.world.feature.tree.darkdweller;

import cc.bluebits.hongtaiyang.block.DarkdwellerStickBlock;
import cc.bluebits.hongtaiyang.block.DwellberryBlock;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.world.feature.tree.ModTreeDecorators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A {@code TreeDecorator} for the Darkdweller tree, which places branches and berries on the tree.
 */
public class DarkdwellerTreeDecorator extends TreeDecorator {
	/**
	 * A {@code Codec} for {@code DarkdwellerTreeDecorator}.
	 */
	public static final Codec<DarkdwellerTreeDecorator> CODEC = RecordCodecBuilder.create(
			darkdwellerTreeDecoratorInstance -> darkdwellerTreeDecoratorInstance.group(
					Codec.INT.fieldOf("branchStartHeight").forGetter(DarkdwellerTreeDecorator::getBranchStartHeight),
					Codec.INT.fieldOf("berryStartHeight").forGetter(DarkdwellerTreeDecorator::getBerryStartHeight),
					Codec.floatRange(0, 1).fieldOf("branchProbability").forGetter(DarkdwellerTreeDecorator::getBranchProbability),
					Codec.floatRange(0, 1).fieldOf("berryProbability").forGetter(DarkdwellerTreeDecorator::getBerryProbability)
			).apply(darkdwellerTreeDecoratorInstance, DarkdwellerTreeDecorator::new)
	);

	private final int branchStartHeight;
	private final int berryStartHeight;
	private final float branchProbability;
	private final float berryProbability;

	protected int getBranchStartHeight() {
		return branchStartHeight;
	}

	protected int getBerryStartHeight() {
		return berryStartHeight;
	}

	protected float getBranchProbability() {
		return branchProbability;
	}

	protected float getBerryProbability() {
		return berryProbability;
	}

	/**
	 * Constructs a {@code DarkdwellerTreeDecorator}
	 * @param branchStartHeight The height at which branches start generating
	 * @param berryStartHeight The height at which berries start generating
	 * @param branchProbability The probability of generating a branch
	 * @param berryProbability The probability of generating a berry
	 */
	public DarkdwellerTreeDecorator(int branchStartHeight, int berryStartHeight, float branchProbability, float berryProbability) {
		super();
		this.branchStartHeight = branchStartHeight;
		this.berryStartHeight = berryStartHeight;
		this.branchProbability = branchProbability;
		this.berryProbability = berryProbability;
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

		for (BlockPos pos : logPositions) {
			if (pos.getY() == startY) continue;
			boolean isAboveBranchStartHeight = pos.getY() - startY >= branchStartHeight;
			boolean isAboveBerryStartHeight = pos.getY() - startY >= berryStartHeight;

			for (Direction dir : Direction.Plane.HORIZONTAL) {
				BlockPos decorPos = pos.offset(dir.getOpposite().getStepX(), 0, dir.getOpposite().getStepZ());

				if (!pContext.isAir(decorPos)) continue;

				boolean genBerry = randomSource.nextFloat() < getBerryProbability() && isAboveBerryStartHeight;
				boolean genBranch = randomSource.nextFloat() < getBranchProbability() && isAboveBranchStartHeight;

				if (genBranch && genBerry) {
					if (randomSource.nextBoolean()) genBranch = false;
					else genBerry = false;
				}

				if (genBerry) {
					pContext.setBlock(decorPos, ModBlocks.DWELLBERRY.get().defaultBlockState()
							.setValue(DwellberryBlock.FACING, dir)
							.setValue(DwellberryBlock.AGE, randomSource.nextInt(0, DwellberryBlock.MAX_AGE + 1))
					);
				}

				if (genBranch) {
					pContext.setBlock(decorPos, ModBlocks.DARKDWELLER_STICK.get().defaultBlockState()
							.setValue(DarkdwellerStickBlock.AXIS, dir.getAxis())
					);
				}
			}
		}
	}
}
