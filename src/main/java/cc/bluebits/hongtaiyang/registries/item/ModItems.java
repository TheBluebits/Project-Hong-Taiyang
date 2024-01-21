package cc.bluebits.hongtaiyang.registries.item;


import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.entity.ModBoatEntity;
import cc.bluebits.hongtaiyang.item.ModBoatItem;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering items
 */
@SuppressWarnings("MissingJavadoc")
public class ModItems {
	/**
	 * The deferred register for items
	 */
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the items
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}


	// --------------------------------
	//  Chapter 1 Items
	// --------------------------------

	public static final RegistryObject<Item> LOGBOOK = ITEMS.register("logbook",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> WRITINGS = ITEMS.register("writings",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> TOME_OF_UNIVERSE = ITEMS.register("tome_of_universe",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> SOUL_CORE = ITEMS.register("soul_core",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CRACKED_SOUL_CORE = ITEMS.register("cracked_soul_core",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> WARDLING_ANTLER = ITEMS.register("wardling_antler",
			() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> UMBRAL_GEM = ITEMS.register("umbral_gem",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> UMBRAL_ESSENCE = ITEMS.register("umbral_essence",
			() -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> SONAR_COMPASS = ITEMS.register("sonar_compass",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> UMBRAL_LOCATOR = ITEMS.register("umbral_locator",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> MAGIC_CHALK = ITEMS.register("magic_chalk",
			() -> new Item(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> TUNING_FORK = ITEMS.register("tuning_fork",
			() -> new Item(new Item.Properties().stacksTo(1)));
	
	
	public static final RegistryObject<Item> DARKDWELLER_BOAT = ITEMS.register("darkdweller_boat",
			() -> new ModBoatItem(false, ModBoatEntity.Type.DARKDWELLER, new Item.Properties()));
	public static final RegistryObject<Item> DARKDWELLER_CHEST_BOAT = ITEMS.register("darkdweller_chest_boat",
			() -> new ModBoatItem(true, ModBoatEntity.Type.DARKDWELLER, new Item.Properties()));
	
	
	
	public static final RegistryObject<Item> UMBRAL_SWORD = ITEMS.register("umbral_sword",
			() -> new SwordItem(ModToolTiers.UMBRAL, 3, -2.4f, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_PICKAXE = ITEMS.register("umbral_pickaxe",
			() -> new PickaxeItem(ModToolTiers.UMBRAL, 1, -2.8f, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_AXE = ITEMS.register("umbral_axe",
			() -> new AxeItem(ModToolTiers.UMBRAL, 6, -3, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_SHOVEL = ITEMS.register("umbral_shovel",
			() -> new ShovelItem(ModToolTiers.UMBRAL, 1.5f, -3, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_HOE = ITEMS.register("umbral_hoe",
			() -> new HoeItem(ModToolTiers.UMBRAL, -2, -3, new Item.Properties()));
	
	
	
	public static final RegistryObject<Item> UMBRAL_HELMET = ITEMS.register("umbral_helmet",
			() -> new ArmorItem(ModArmorMaterials.UMBRAL, ArmorItem.Type.HELMET, new Item.Properties()) );
	public static final RegistryObject<Item> UMBRAL_CHESTPLATE = ITEMS.register("umbral_chestplate",
			() -> new ArmorItem(ModArmorMaterials.UMBRAL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_LEGGINGS = ITEMS.register("umbral_leggings",
			() -> new ArmorItem(ModArmorMaterials.UMBRAL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
	public static final RegistryObject<Item> UMBRAL_BOOTS = ITEMS.register("umbral_boots",
			() -> new ArmorItem(ModArmorMaterials.UMBRAL, ArmorItem.Type.BOOTS, new Item.Properties()));
	
	
	
	public static final RegistryObject<Item> DWELLBERRY = ITEMS.register("dwellberry",
			() -> new Item(new Item.Properties()
					.stacksTo(64)
					.food(ModFoods.DWELLBERRY)
			));
	public static final RegistryObject<Item> DWELLBERRY_SEED = ITEMS.register("dwellberry_seed",
			() -> new ItemNameBlockItem(ModBlocks.DWELLBERRY.get(), new Item.Properties().stacksTo(64)));
	
	
	
	public static final RegistryObject<Item> DARKDWELLER_SIGN = ITEMS.register("darkdweller_sign",
			() -> new SignItem(
					new Item.Properties().stacksTo(16),
					ModBlocks.DARKDWELLER_SIGN.get(),
					ModBlocks.DARKDWELLER_WALL_SIGN.get()));
	
	public static final RegistryObject<Item> DARKDWELLER_HANGING_SIGN = ITEMS.register("darkdweller_hanging_sign",
			() -> new HangingSignItem(
					ModBlocks.DARKDWELLER_HANGING_SIGN.get(),
					ModBlocks.DARKDWELLER_WALL_HANGING_SIGN.get(),
					new Item.Properties().stacksTo(16)));
}
