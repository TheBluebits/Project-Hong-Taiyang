package cc.bluebits.hongtaiyang.item;


import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, HongTaiyang.MOD_ID);


    public static final RegistryObject<Item> UMBRAL_GEM = ITEMS.register("umbral_gem",
            ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB)));

    public static final RegistryObject<Item> SOUL_CORE = ITEMS.register("soul_core",
            ()-> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }
}
