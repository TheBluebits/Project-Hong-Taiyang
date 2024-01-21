package cc.bluebits.hongtaiyang.registries.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering creative mode tabs
 */
@SuppressWarnings({"MissingJavadoc", "unused"})
public class ModCreativeModeTabs {
	/**
	 * The deferred register for creative mode tabs
	 */
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the creative mode tabs
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}

	public static final RegistryObject<CreativeModeTab> CHAPTER1_TAB = CREATIVE_MODE_TABS.register(
			"chapter1_tab",
			() -> CreativeModeTab.builder()
					.icon(() -> new ItemStack(ModItems.SOUL_CORE.get()))
					.title(Component.translatable("itemGroup.chapter1_tab"))
					.displayItems(((pParameters, pOutput) -> {
						// Items
						pOutput.accept(ModItems.LOGBOOK.get());
						pOutput.accept(ModItems.WRITINGS.get());
						pOutput.accept(ModItems.TOME_OF_UNIVERSE.get());
						pOutput.accept(ModItems.SOUL_CORE.get());
						pOutput.accept(ModItems.CRACKED_SOUL_CORE.get());
						pOutput.accept(ModItems.WARDLING_ANTLER.get());
						pOutput.accept(ModItems.UMBRAL_GEM.get());
						pOutput.accept(ModItems.UMBRAL_ESSENCE.get());
						pOutput.accept(ModItems.SONAR_COMPASS.get());
						pOutput.accept(ModItems.UMBRAL_LOCATOR.get());
						pOutput.accept(ModItems.MAGIC_CHALK.get());
						pOutput.accept(ModItems.TUNING_FORK.get());
						pOutput.accept(ModItems.DARKDWELLER_BOAT.get());
						pOutput.accept(ModItems.DARKDWELLER_CHEST_BOAT.get());
						pOutput.accept(ModItems.UMBRAL_SWORD.get());
						pOutput.accept(ModItems.UMBRAL_PICKAXE.get());
						pOutput.accept(ModItems.UMBRAL_AXE.get());
						pOutput.accept(ModItems.UMBRAL_SHOVEL.get());
						pOutput.accept(ModItems.UMBRAL_HOE.get());
						pOutput.accept(ModItems.UMBRAL_HELMET.get());
						pOutput.accept(ModItems.UMBRAL_CHESTPLATE.get());
						pOutput.accept(ModItems.UMBRAL_LEGGINGS.get());
						pOutput.accept(ModItems.UMBRAL_BOOTS.get());
						pOutput.accept(ModItems.DWELLBERRY.get());
						pOutput.accept(ModItems.DWELLBERRY_SEED.get());
						pOutput.accept(ModItems.DARKDWELLER_SIGN.get());
						pOutput.accept(ModItems.DARKDWELLER_HANGING_SIGN.get());

						//Blocks
						pOutput.accept(ModBlocks.UMBRAL_ORE.get());
						pOutput.accept(ModBlocks.DEEPSLATE_UMBRAL_ORE.get());
						pOutput.accept(ModBlocks.UMBRAL_BLOCK.get());
						pOutput.accept(ModBlocks.ROOTED_SCULK.get());
						pOutput.accept(ModBlocks.DARKDWELLER_ROOT.get());
						pOutput.accept(ModBlocks.DARKDWELLER_LOG.get());
						pOutput.accept(ModBlocks.DARKDWELLER_STICK.get());
						pOutput.accept(ModBlocks.DARKDWELLER_BUNDLE.get());
						pOutput.accept(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get());
						pOutput.accept(ModBlocks.DARKDWELLER_PLANKS.get());
						pOutput.accept(ModBlocks.DARKDWELLER_SLAB.get());
						pOutput.accept(ModBlocks.DARKDWELLER_STAIRS.get());
						pOutput.accept(ModBlocks.DARKDWELLER_FENCE.get());
						pOutput.accept(ModBlocks.DARKDWELLER_FENCE_GATE.get());
						pOutput.accept(ModBlocks.DARKDWELLER_DOOR.get());
						pOutput.accept(ModBlocks.DARKDWELLER_TRAPDOOR.get());
						pOutput.accept(ModBlocks.DARKDWELLER_BUTTON.get());
						pOutput.accept(ModBlocks.DARKDWELLER_PRESSURE_PLATE.get());
					}))
					.build());
}
