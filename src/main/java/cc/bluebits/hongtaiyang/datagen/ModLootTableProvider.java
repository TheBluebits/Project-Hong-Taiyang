package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.datagen.loot.ModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

/**
 * Class for generating loot tables
 */
public class ModLootTableProvider {
	/**
	 * Creates a loot table provider
	 * @param output The pack output
	 * @return The loot table provider
	 */
	public static LootTableProvider create(PackOutput output) {
		return new LootTableProvider(output, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
		));
	}
}
