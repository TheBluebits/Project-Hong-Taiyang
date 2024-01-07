package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Class for generating block tags
 */
public class ModBlockTagGenerator extends BlockTagsProvider {
	/**
	 * Constructor for the block tag generator
	 * @param output The pack output passed to the super constructor
	 * @param lookupProvider The lookup provider passed to the super constructor
	 * @param existingFileHelper The existing file helper passed to the super constructor
	 */
	public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, HongTaiyang.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider pProvider) {
		tag(BlockTags.FENCES)
				.add(ModBlocks.DARKDWELLER_FENCE.get());
		
		tag(BlockTags.FENCE_GATES)
				.add(ModBlocks.DARKDWELLER_FENCE_GATE.get());
	}
}
