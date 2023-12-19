package cc.bluebits.hongtaiyang.world.feature.tree.custom;

import cc.bluebits.hongtaiyang.world.feature.tree.ModFoliagePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class DarkdwellerFoliagePlacer extends FoliagePlacer {
	public static final Codec<DarkdwellerFoliagePlacer> CODEC = RecordCodecBuilder.create(
			darkdwellerFoliagePlacerInstance -> foliagePlacerParts(darkdwellerFoliagePlacerInstance)
					.and(Codec.intRange(0, 16).fieldOf("height").forGetter(fp -> fp.height))
					.apply(darkdwellerFoliagePlacerInstance, DarkdwellerFoliagePlacer::new));
	
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
	protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i, FoliageAttachment foliageAttachment, int i1, int i2, int i3) {
		
	}

	@Override
	public int foliageHeight(@NotNull RandomSource pRandom, int pHeight, @NotNull TreeConfiguration pConfig) {
		return this.height;
	}

	@Override
	protected boolean shouldSkipLocation(@NotNull RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
		return false;
	}
}
