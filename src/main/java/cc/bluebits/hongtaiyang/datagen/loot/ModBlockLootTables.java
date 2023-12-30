package cc.bluebits.hongtaiyang.datagen.loot;

import cc.bluebits.hongtaiyang.block.DwellberryBlock;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;


public class ModBlockLootTables extends BlockLootSubProvider {
	public ModBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	@Override
	protected void generate() {
		LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.DWELLBERRY.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DwellberryBlock.AGE, DwellberryBlock.MAX_AGE));

		this.add(ModBlocks.DWELLBERRY.get(), createFruitDrops(
				ModBlocks.DWELLBERRY.get(),
				ModItems.DWELLBERRY.get(),
				ModItems.DWELLBERRY_SEEDS.get(),
				lootitemcondition$builder)
		);
	}

	/**
	 * If {@code dropGrownCropCondition} fails (i.e. crop is not ready), drops 1 {@code seedsItem}.
	 * If {@code dropGrownCropCondition} succeeds (i.e. crop is ready), drops 1-2 {@code grownCropItem} with fortune applied, and 1 {@code
	 * seedsItem}.
	 */
	protected LootTable.Builder createFruitDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
		return this.applyExplosionDecay(pCropBlock, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(pGrownCropItem).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))
								.when(pDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem))))
				.withPool(LootPool.lootPool().when(pDropGrownCropCondition)
						.add(LootItem.lootTableItem(pSeedsItem))));
	}
}
