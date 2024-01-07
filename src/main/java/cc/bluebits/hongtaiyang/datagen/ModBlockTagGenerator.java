package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.ModTags;
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
		// ================================
		//  Modded Tags
		// ================================
		tag(ModTags.Blocks.UMBRAL_ORES)
				.add(ModBlocks.DEEPSLATE_UMBRAL_ORE.get())
				.add(ModBlocks.UMBRAL_ORE.get());
		

		
		// ================================
		//  Vanilla Tags
		// ================================
		
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(ModBlocks.DEEPSLATE_UMBRAL_ORE.get())
				.add(ModBlocks.UMBRAL_BLOCK.get())
				.add(ModBlocks.UMBRAL_ORE.get());
		
		tag(BlockTags.MINEABLE_WITH_AXE)
				.add(ModBlocks.DARKDWELLER_BUNDLE.get())
				.add(ModBlocks.DARKDWELLER_BUTTON.get())
				.add(ModBlocks.DARKDWELLER_DOOR.get())
				.add(ModBlocks.DARKDWELLER_FENCE.get())
				.add(ModBlocks.DARKDWELLER_FENCE_GATE.get())
				.add(ModBlocks.DARKDWELLER_LOG.get())
				.add(ModBlocks.DARKDWELLER_PLANKS.get())
				.add(ModBlocks.DARKDWELLER_PRESSURE_PLATE.get())
				.add(ModBlocks.DARKDWELLER_SLAB.get())
				.add(ModBlocks.DARKDWELLER_STAIRS.get())
				.add(ModBlocks.DARKDWELLER_STICK.get())
				.add(ModBlocks.DARKDWELLER_TRAPDOOR.get())
				.add(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get());
		
		tag(BlockTags.MINEABLE_WITH_HOE)
				.add(ModBlocks.ROOTED_SCULK.get());
		
		tag(BlockTags.LOGS_THAT_BURN)
				.add(ModBlocks.DARKDWELLER_BUNDLE.get())
				.add(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get())
				.add(ModBlocks.DARKDWELLER_LOG.get())
				.add(ModBlocks.DARKDWELLER_STICK.get());
		
		tag(BlockTags.WOODEN_BUTTONS)
				.add(ModBlocks.DARKDWELLER_BUTTON.get());
		
		tag(BlockTags.WOODEN_DOORS)
				.add(ModBlocks.DARKDWELLER_DOOR.get());

		tag(BlockTags.WOODEN_FENCES)
				.add(ModBlocks.DARKDWELLER_FENCE.get());

		tag(BlockTags.FENCE_GATES)
				.add(ModBlocks.DARKDWELLER_FENCE_GATE.get());
		
		tag(BlockTags.PLANKS)
				.add(ModBlocks.DARKDWELLER_PLANKS.get());
		
		tag(BlockTags.WOODEN_PRESSURE_PLATES)
				.add(ModBlocks.DARKDWELLER_PRESSURE_PLATE.get());
		
		tag(BlockTags.SAPLINGS)
				.add(ModBlocks.DARKDWELLER_ROOT.get());
		
		tag(BlockTags.WOODEN_SLABS)
				.add(ModBlocks.DARKDWELLER_SLAB.get());
		
		tag(BlockTags.WOODEN_STAIRS)
				.add(ModBlocks.DARKDWELLER_STAIRS.get());
		
		tag(BlockTags.WOODEN_TRAPDOORS)
				.add(ModBlocks.DARKDWELLER_TRAPDOOR.get());
	}
}
