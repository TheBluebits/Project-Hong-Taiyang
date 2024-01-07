package cc.bluebits.hongtaiyang.registries.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
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
@SuppressWarnings("MissingJavadoc")
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
					.build());
}
