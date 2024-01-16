package cc.bluebits.hongtaiyang.registries.util;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Class for storing all the tags used in the mod.
 */
public class ModTags {
	/**
	 * Subclass for storing all the block tags used in the mod.
	 */
	@SuppressWarnings({"unused", "MissingJavadoc"})
	public static class Blocks {
		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(HongTaiyang.MOD_ID, name));
		}
		
		public static final TagKey<Block> UMBRAL_ORES = tag("umbral_ores");
		public static final TagKey<Block> NEEDS_UMBRAL_TOOL = tag("needs_umbral_tool");
	}

	/**
	 * Subclass for storing all the item tags used in the mod.
	 */
	@SuppressWarnings("unused")
	public static class Items {
		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(HongTaiyang.MOD_ID, name));
		}
		
	}
}
