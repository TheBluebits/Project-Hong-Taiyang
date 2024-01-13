package cc.bluebits.hongtaiyang;

//import com.mojang.logging.LogUtils;

import cc.bluebits.hongtaiyang.registries.block.ModBlockEntities;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.item.ModCreativeModeTabs;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import cc.bluebits.hongtaiyang.registries.util.ModWoodTypes;
import cc.bluebits.hongtaiyang.registries.world.feature.ModFeatures;
import cc.bluebits.hongtaiyang.registries.world.feature.tree.ModFoliagePlacers;
import cc.bluebits.hongtaiyang.registries.world.feature.tree.ModTreeDecorators;
import cc.bluebits.hongtaiyang.registries.world.feature.tree.ModTrunkPlacers;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import org.slf4j.Logger;

/**
 * The class containing the entirety of the mod
 */
// The value here should match an entry in the META-INF/mods.toml file
@Mod(HongTaiyang.MOD_ID)
public class HongTaiyang {
	/**
	 * The ID of the mod used in various places
	 */
	public static final String MOD_ID = "hongtaiyang";
	//private static final Logger LOGGER = LogUtils.getLogger();

	/**
	 * Constructor for the mod
	 */
	public HongTaiyang() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModCreativeModeTabs.register(modEventBus);

		ModItems.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModBlockEntities.register(modEventBus);

		ModFoliagePlacers.register(modEventBus);
		ModTrunkPlacers.register(modEventBus);
		ModTreeDecorators.register(modEventBus);
		ModFeatures.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.register(this);

		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		
	}

	private void commonSetup(final FMLCommonSetupEvent event) {

	}

	/**
	 * Event handler for the ClientSetup event
	 */
	// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		/**
		 * Event handler for the ClientSetup event
		 * @param event The ClientSetup event
		 */
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			Sheets.addWoodType(ModWoodTypes.DARKDWELLER);
		}
	}
}
