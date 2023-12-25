package cc.bluebits.hongtaiyang;

//import com.mojang.logging.LogUtils;
import cc.bluebits.hongtaiyang.block.ModBlocks;
import cc.bluebits.hongtaiyang.item.ModCreativeModeTabs;
import cc.bluebits.hongtaiyang.item.ModItems;
import cc.bluebits.hongtaiyang.world.feature.tree.ModFoliagePlacers;
import cc.bluebits.hongtaiyang.world.feature.tree.ModTreeDecorators;
import cc.bluebits.hongtaiyang.world.feature.tree.ModTrunkPlacers;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HongTaiyang.MOD_ID)
public class HongTaiyang
{
    public static final String MOD_ID = "hongtaiyang";
    //private static final Logger LOGGER = LogUtils.getLogger();
    
    public HongTaiyang()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);
        
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModFoliagePlacers.register(modEventBus);
        ModTrunkPlacers.register(modEventBus);
        ModTreeDecorators.register(modEventBus);
        
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::addCreative);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ModCreativeModeTabs.CHAPTER1_TAB.get()) {
            // Items
            event.accept(ModItems.LOGBOOK);
            event.accept(ModItems.WRITINGS);
            event.accept(ModItems.TOME_OF_UNIVERSE);
            event.accept(ModItems.SOUL_CORE);
            event.accept(ModItems.CRACKED_SOUL_CORE);
            event.accept(ModItems.WARDLING_ANTLER);
            event.accept(ModItems.UMBRAL_GEM);
            event.accept(ModItems.UMBRAL_ESSENCE);
            event.accept(ModItems.SONAR_COMPASS);
            event.accept(ModItems.UMBRAL_LOCATOR);
            event.accept(ModItems.MAGIC_CHALK);
            event.accept(ModItems.TUNING_FORK);
            event.accept(ModItems.DARKDWELLER_BOAT);
            event.accept(ModItems.UMBRAL_SWORD);
            event.accept(ModItems.UMBRAL_PICKAXE);
            event.accept(ModItems.UMBRAL_AXE);
            event.accept(ModItems.UMBRAL_SHOVEL);
            event.accept(ModItems.UMBRAL_HOE);
            event.accept(ModItems.WARDLING_HELMET);
            event.accept(ModItems.WARDLING_CHESTPLATE);
            event.accept(ModItems.WARDLING_LEGGINGS);
            event.accept(ModItems.WARDLING_BOOTS);
            event.accept(ModItems.WARDLING_ANTLERS);
            
            //Blocks
            event.accept(ModBlocks.UMBRAL_ORE);
            event.accept(ModBlocks.DEEPSLATE_UMBRAL_ORE);
            event.accept(ModBlocks.UMBRAL_BLOCK);
            event.accept(ModBlocks.ROOTED_SCULK);
            event.accept(ModBlocks.DARKDWELLER_ROOT);
            event.accept(ModBlocks.DARKDWELLER_LOG);
            event.accept(ModBlocks.DARKDWELLER_STICK);
            event.accept(ModBlocks.DARKDWELLER_BUNDLE);
            event.accept(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE);
            event.accept(ModBlocks.DARKDWELLER_PLANKS);
            event.accept(ModBlocks.DARKDWELLER_SLAB);
            event.accept(ModBlocks.DARKDWELLER_STAIRS);
            event.accept(ModBlocks.DARKDWELLER_FENCE);
            event.accept(ModBlocks.DARKDWELLER_FENCE_GATE);
            event.accept(ModBlocks.DARKDWELLER_DOOR);
            event.accept(ModBlocks.DARKDWELLER_TRAPDOOR);
            event.accept(ModBlocks.DARKDWELLER_BUTTON);
            event.accept(ModBlocks.DARKDWELLER_PRESSURE_PLATE);
            //event.accept(ModBlocks.DARKDWELLER_SIGN);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            
        }
    }
}
