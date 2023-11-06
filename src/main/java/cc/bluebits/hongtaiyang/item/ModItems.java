package cc.bluebits.hongtaiyang.item;


import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HongTaiyang.MOD_ID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    
    
    
    // --------------------------------
    //  Chapter 1 Items
    // --------------------------------
    
    public static final RegistryObject<Item> TOME_OF_UNIVERSE = ITEMS.register("tome_of_universe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> SOUL_CORE = ITEMS.register("soul_core",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(64)));
    public static final RegistryObject<Item> DARKDWELLER_STICK = ITEMS.register("darkdweller_stick",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(64)));
    public static final RegistryObject<Item> DARKDWELLER_ROOT = ITEMS.register("darkdweller_root",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(64)));
    public static final RegistryObject<Item> WARDLING_ANTLER = ITEMS.register("wardling_antler",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(16)));
    public static final RegistryObject<Item> UMBRAL_GEM = ITEMS.register("umbral_gem",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(64)));
    public static final RegistryObject<Item> UMBRAL_ESSENCE = ITEMS.register("umbral_essence",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(64)));
    public static final RegistryObject<Item> SONAR_COMPASS = ITEMS.register("sonar_compass",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMBRAL_LOCATOR = ITEMS.register("umbral_locator",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> TUNING_FORK = ITEMS.register("tuning_fork",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> DARKDWELLER_BOAT = ITEMS.register("darkdweller_boat",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMRBAL_SWORD = ITEMS.register("umbral_sword",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMBRAL_PICKAXE = ITEMS.register("umbral_pickaxe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMBRAL_AXE = ITEMS.register("umbral_axe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMBRAL_SHOVEL = ITEMS.register("umbral_shovel",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> UMBRAL_HOE = ITEMS.register("umbral_hoe",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> WARDLING_HELMET = ITEMS.register("wardling_helmet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> WARDLING_CHESTPLATE = ITEMS.register("wardling_chestplate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> WARDSLING_LEGGINGS = ITEMS.register("wardling_leggings",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
    public static final RegistryObject<Item> WARDLING_BOOTS = ITEMS.register("wardling_boots",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB).stacksTo(1)));
}
