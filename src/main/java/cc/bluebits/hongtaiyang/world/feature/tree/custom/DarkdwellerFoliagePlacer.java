package cc.bluebits.hongtaiyang.world.feature.tree.custom;

import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.world.feature.tree.ModFoliagePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;
import org.openjdk.nashorn.internal.ir.Block;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class DarkdwellerFoliagePlacer extends FoliagePlacer {
	public static final Codec<DarkdwellerFoliagePlacer> CODEC = RecordCodecBuilder.create(
			darkdwellerFoliagePlacerInstance -> foliagePlacerParts(darkdwellerFoliagePlacerInstance)
					.and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height))
					.apply(darkdwellerFoliagePlacerInstance, DarkdwellerFoliagePlacer::new));
	
	protected static final int placementChance = 75; // Numeric value in percent e.g. 70 -> 70% chance of placement 
	protected final int height;
	
	public DarkdwellerFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
		super(pRadius, pOffset);
		this.height = height;
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return ModFoliagePlacers.DARKDWELLER_FOLIAGE_PLACER.get();
	}
	
	@Override
	protected void createFoliage(@NotNull LevelSimulatedReader pLevel, FoliagePlacer.@NotNull FoliageSetter pBlockSetter, @NotNull RandomSource pRandom, @NotNull TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliagePlacer.@NotNull FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
		for(int y = pOffset; y >= pOffset - pFoliageRadius; --y) {
			for(int x = -pFoliageRadius; x <= pFoliageRadius; x++) {
				for(int z = -pFoliageRadius; z <= pFoliageRadius; z++) {
					final BlockPos pos = pAttachment.pos()
							.relative(Direction.Axis.X, x)
							.relative(Direction.Axis.Y, y)
							.relative(Direction.Axis.Z, z);

					if(pLevel.isStateAtPosition(pos, state -> state.is(pConfig.dirtProvider.getState(pRandom, pos).getBlock())))
					{
						int relX = Math.abs(pos.getX() - pAttachment.pos().getX());
						int relY = Math.abs(pos.getY() - pAttachment.pos().getY());
						int relZ = Math.abs(pos.getZ() - pAttachment.pos().getZ());
						
						int effectiveRadius = pFoliageRadius - relY;
						
						boolean skipPlacement = (relX == relZ && relZ > effectiveRadius) ||
								(relX >= effectiveRadius && relZ == pFoliageRadius) ||
								(relX == pFoliageRadius && relZ >= effectiveRadius);
						
						// Skip randomly sometimes
						if(pRandom.nextInt(0, 100) > placementChance) skipPlacement = true;
						
						if(!skipPlacement) {
							pBlockSetter.set(pos, pConfig.foliageProvider.getState(pRandom, pos));
						}
					}
				}
			}
		}
	}

	@Override
	public int foliageHeight(@NotNull RandomSource pRandom, int pHeight, @NotNull TreeConfiguration pConfig) {
		return this.height;
	}

	@Override
	protected boolean shouldSkipLocation(@NotNull RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
		return pLocalX >= pRange - pLocalY && pLocalZ >= pRange - pLocalY;
	}
}
