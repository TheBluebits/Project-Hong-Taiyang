package cc.bluebits.hongtaiyang.block;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.custom.DarkdwellerLogBlock;
import cc.bluebits.hongtaiyang.block.custom.DarkdwellerStickBlock;
import cc.bluebits.hongtaiyang.block.custom.base.ModFlammableRotatedPillarBlock;
import cc.bluebits.hongtaiyang.block.custom.base.ModSaplingBlock;
import cc.bluebits.hongtaiyang.item.ModItems;
import cc.bluebits.hongtaiyang.world.feature.tree.custom.darkdweller.DarkdwellerTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
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
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );
    
    public static final RegistryObject<Block> DEEPSLATE_UMBRAL_ORE = registerBlock(
            "deepslate_umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(6f)
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );

    public static final RegistryObject<Block> UMBRAL_BLOCK = registerBlock(
            "umbral_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK)
                    .strength(6f)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> ROOTED_SCULK = registerBlock(
            "rooted_sculk",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SCULK)
                    .strength(1f)
                    .requiresCorrectToolForDrops())
    );
    
    
    
    public static final RegistryObject<Block> DARKDWELLER_ROOT = registerBlock(
            "darkdweller_root",
            () -> new ModSaplingBlock(new DarkdwellerTreeGrower(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING),
                    () -> Blocks.SCULK,
                    "deep_dark")
    );
    
    public static final RegistryObject<Block> DARKDWELLER_LOG = registerBlock(
            "darkdweller_log",
            () -> new DarkdwellerLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))
    );
    public static final RegistryObject<Block> DARKDWELLER_STICK = registerBlock(
            "darkdweller_stick",
            () -> new DarkdwellerStickBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))
    );
    public static final RegistryObject<Block> DARKDWELLER_BUNDLE = registerBlock(
            "darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))
    );
    public static final RegistryObject<Block> STRIPPED_DARKDWELLER_BUNDLE = registerBlock(
            "stripped_darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG))
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
            }
    );
    
    public static final RegistryObject<Block> DARKDWELLER_SLAB = registerBlock(
            "darkdweller_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))
    );
    public static final RegistryObject<Block> DARKDWELLER_STAIRS = registerBlock(
            "darkdweller_stairs",
            () -> new StairBlock(() -> ModBlocks.DARKDWELLER_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE = registerBlock(
            "darkdweller_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE_GATE = registerBlock(
            "darkdweller_fence_gate",
            () -> new FenceGateBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE),
                    SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE,
                    SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN)
    );
    public static final RegistryObject<Block> DARKDWELLER_DOOR = registerBlock(
            "darkdweller_door",
            () -> new DoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion(),
                    BlockSetType.BAMBOO)
    );
    public static final RegistryObject<Block> DARKDWELLER_TRAPDOOR = registerBlock(
            "darkdweller_trapdoor",
            () -> new TrapDoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion(),
                    BlockSetType.BAMBOO)
    );
    public static final RegistryObject<Block> DARKDWELLER_BUTTON = registerBlock(
            "darkdweller_button",
            () -> new ButtonBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).noCollission(),
                    BlockSetType.BAMBOO,
                    15,
                    true)
    );
    public static final RegistryObject<Block> DARKDWELLER_PRESSURE_PLATE = registerBlock(
            "darkdweller_pressure_plate",
            () -> new PressurePlateBlock(
                    PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE),
                    BlockSetType.BAMBOO)
    );
//    public static final RegistryObject<Block> DARKDWELLER_SIGN = registerBlock(
//            "darkdweller_sign",
//            () -> new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN))
//    );
}
