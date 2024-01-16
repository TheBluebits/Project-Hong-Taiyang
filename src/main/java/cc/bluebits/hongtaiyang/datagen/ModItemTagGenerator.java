package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * Class for generating item tags
 */
public class ModItemTagGenerator extends ItemTagsProvider {
	/**
	 * Constructs a {@code ModItemTagGenerator}
	 * @param packOutput The pack output, passed to the super constructor
	 * @param providerCompletableFuture The holder lookup provider, passed to the super constructor
	 * @param lookupCompletableFuture The tag lookup, passed to the super constructor
	 * @param existingFileHelper The existing file helper, passed to the super constructor
	 */
	public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
		super(packOutput, providerCompletableFuture, lookupCompletableFuture, HongTaiyang.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider pProvider) {
		this.tag(ItemTags.TRIMMABLE_ARMOR)
				.add(ModItems.UMBRAL_HELMET.get())
				.add(ModItems.UMBRAL_CHESTPLATE.get())
				.add(ModItems.UMBRAL_LEGGINGS.get())
				.add(ModItems.UMBRAL_BOOTS.get());
	}
}
