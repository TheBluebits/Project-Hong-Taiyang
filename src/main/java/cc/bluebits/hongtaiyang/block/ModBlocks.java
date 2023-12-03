package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.item.ModCreativeModeTab;
import cc.bluebits.hongtaiyang.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HongTaiyang.MOD_ID);
    
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModCreativeModeTab.HONGTAIYANG_TAB)));
    }
    
    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }



    // --------------------------------
    //  Chapter 1 Blocks
    // --------------------------------
    
    public static final RegistryObject<Block> UMBRAL_ORE = registerBlock(
            "umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );
    
    public static final RegistryObject<Block> DEEPSLATE_UMBRAL_ORE = registerBlock(
            "deepslate_umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );

    public static final RegistryObject<Block> UMBRAL_BLOCK = registerBlock(
            "umbral_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(6f)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> SCULK_SOIL = registerBlock(
            "sculk_soil",
            () -> new Block(BlockBehaviour.Properties.of(Material.SCULK)
                    .strength(1f)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> ROOTED_SCULK_SOIL = registerBlock(
            "rooted_sculk_soil",
            () -> new Block(BlockBehaviour.Properties.of(Material.SCULK)
                    .strength(1f)
                    .requiresCorrectToolForDrops())
    );
}
