package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.custom.base.ModFlammableRotatedPillarBlock;
import cc.bluebits.hongtaiyang.block.custom.DarkdwellerStickBlock;
import cc.bluebits.hongtaiyang.block.custom.DarkdwellerLogBlock;
import cc.bluebits.hongtaiyang.block.custom.SculkSoilBlock;
import cc.bluebits.hongtaiyang.item.ModCreativeModeTab;
import cc.bluebits.hongtaiyang.item.ModItems;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.DarkdwellerTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HongTaiyang.MOD_ID);
    
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
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
                    UniformInt.of(3, 7)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    
    public static final RegistryObject<Block> DEEPSLATE_UMBRAL_ORE = registerBlock(
            "deepslate_umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7)),
            ModCreativeModeTab.CHAPTER1_TAB
    );

    public static final RegistryObject<Block> UMBRAL_BLOCK = registerBlock(
            "umbral_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(6f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.CHAPTER1_TAB
    );

    public static final RegistryObject<Block> SCULK_SOIL = registerBlock(
            "sculk_soil",
            () -> new SculkSoilBlock(BlockBehaviour.Properties.of(Material.SCULK)
                    .strength(1f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.CHAPTER1_TAB
    );

    public static final RegistryObject<Block> ROOTED_SCULK_SOIL = registerBlock(
            "rooted_sculk_soil",
            () -> new Block(BlockBehaviour.Properties.of(Material.SCULK)
                    .strength(1f)
                    .requiresCorrectToolForDrops()),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    
    
    
    public static final RegistryObject<Block> DARKDWELLER_ROOT = registerBlock(
            "darkdweller_root",
            () -> new SaplingBlock(new DarkdwellerTreeGrower(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    
    public static final RegistryObject<Block> DARKDWELLER_LOG = registerBlock(
            "darkdweller_log",
            () -> new DarkdwellerLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_STICK = registerBlock(
            "darkdweller_stick",
            () -> new DarkdwellerStickBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_BUNDLE = registerBlock(
            "darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> STRIPPED_DARKDWELLER_BUNDLE = registerBlock(
            "stripped_darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    
    
    
    public static final RegistryObject<Block> DARKDWELLER_PLANKS = registerBlock(
            "darkdweller_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
            },
            ModCreativeModeTab.CHAPTER1_TAB
    );
    
    public static final RegistryObject<Block> DARKDWELLER_SLAB = registerBlock(
            "darkdweller_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_STAIRS = registerBlock(
            "darkdweller_stairs",
            () -> new StairBlock(() -> ModBlocks.DARKDWELLER_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE = registerBlock(
            "darkdweller_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE_GATE = registerBlock(
            "darkdweller_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_DOOR = registerBlock(
            "darkdweller_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)
                    .noOcclusion()),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_TRAPDOOR = registerBlock(
            "darkdweller_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)
                    .noOcclusion()),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_BUTTON = registerBlock(
            "darkdweller_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)
                    .noCollission()),
            ModCreativeModeTab.CHAPTER1_TAB
    );
    public static final RegistryObject<Block> DARKDWELLER_PRESSURE_PLATE = registerBlock(
            "darkdweller_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)),
            ModCreativeModeTab.CHAPTER1_TAB
    );
//    public static final RegistryObject<Block> DARKDWELLER_SIGN = registerBlock(
//            "darkdweller_sign",
//            () -> new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN)),
//            ModCreativeModeTab.CHAPTER1_TAB
//    );
}
