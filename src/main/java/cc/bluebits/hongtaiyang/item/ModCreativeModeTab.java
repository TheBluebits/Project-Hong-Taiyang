package cc.bluebits.hongtaiyang.item;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = HongTaiyang.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab CHAPTER1_TAB;
    
    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        CHAPTER1_TAB = event.registerCreativeModeTab(new ResourceLocation(HongTaiyang.MOD_ID, "chapter1_tab"),
                builder -> builder
                        .icon(() -> new ItemStack(ModItems.SOUL_CORE.get()))
                        .title(Component.translatable("itemGroup.chapter1_tab"))
                        .build());
    }
}
