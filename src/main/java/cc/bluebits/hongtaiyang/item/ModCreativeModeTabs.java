package cc.bluebits.hongtaiyang.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HongTaiyang.MOD_ID);
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
    
    public static RegistryObject<CreativeModeTab> CHAPTER1_TAB = CREATIVE_MODE_TABS.register(
            "chapter1_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.SOUL_CORE.get()))
                    .title(Component.translatable("itemGroup.chapter1_tab"))
                    .build());
}
