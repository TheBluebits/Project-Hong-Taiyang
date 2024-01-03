package cc.bluebits.hongtaiyang.registries.block;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.DarkdwellerLogBlock;
import cc.bluebits.hongtaiyang.block.DarkdwellerStickBlock;
import cc.bluebits.hongtaiyang.block.DwellberryBlock;
import cc.bluebits.hongtaiyang.block.base.ModFlammableRotatedPillarBlock;
import cc.bluebits.hongtaiyang.block.base.ModSaplingBlock;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import cc.bluebits.hongtaiyang.registries.sound.ModSounds;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerTreeGrower;
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

@SuppressWarnings("unused")
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HongTaiyang.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerBlockWithItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


    // --------------------------------
    //  Chapter 1 Blocks
    // --------------------------------

    public static final RegistryObject<Block> UMBRAL_ORE = registerBlockWithItem(
            "umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(6f)
                    .noLootTable()
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );

    public static final RegistryObject<Block> DEEPSLATE_UMBRAL_ORE = registerBlockWithItem(
            "deepslate_umbral_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .strength(6f)
                    .noLootTable()
                    .requiresCorrectToolForDrops(),
                    UniformInt.of(3, 7))
    );

    public static final RegistryObject<Block> UMBRAL_BLOCK = registerBlockWithItem(
            "umbral_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK)
                    .strength(6f)
                    .noLootTable()
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> ROOTED_SCULK = registerBlockWithItem(
            "rooted_sculk",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SCULK)
                    .strength(1f)
                    .noLootTable()
                    .requiresCorrectToolForDrops())
    );


    public static final RegistryObject<Block> DARKDWELLER_ROOT = registerBlockWithItem(
            "darkdweller_root",
            () -> new ModSaplingBlock(new DarkdwellerTreeGrower(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).noLootTable(),
                    () -> Blocks.SCULK,
                    "deep_dark")
    );

    public static final RegistryObject<Block> DARKDWELLER_LOG = registerBlockWithItem(
            "darkdweller_log",
            () -> new DarkdwellerLogBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_LOG)
                            .noLootTable())
    );
    public static final RegistryObject<Block> DARKDWELLER_STICK = registerBlockWithItem(
            "darkdweller_stick",
            () -> new DarkdwellerStickBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noLootTable())
    );
    public static final RegistryObject<Block> DWELLBERRY = registerBlockWithoutItem(
            "dwellberry",
            () -> new DwellberryBlock(
                    BlockBehaviour.Properties
                            .copy(Blocks.COCOA)
                            .lightLevel(state -> 5),
                    ModBlocks.DARKDWELLER_LOG)
    );


    public static final RegistryObject<Block> DARKDWELLER_BUNDLE = registerBlockWithItem(
            "darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noLootTable())
    );
    public static final RegistryObject<Block> STRIPPED_DARKDWELLER_BUNDLE = registerBlockWithItem(
            "stripped_darkdweller_bundle",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).noLootTable())
    );

    public static final RegistryObject<Block> DARKDWELLER_PLANKS = registerBlockWithItem(
            "darkdweller_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noLootTable()) {
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

    public static final RegistryObject<Block> DARKDWELLER_SLAB = registerBlockWithItem(
            "darkdweller_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).noLootTable())
    );
    public static final RegistryObject<Block> DARKDWELLER_STAIRS = registerBlockWithItem(
            "darkdweller_stairs",
            () -> new StairBlock(() -> ModBlocks.DARKDWELLER_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).noLootTable())
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE = registerBlockWithItem(
            "darkdweller_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).noLootTable())
    );
    public static final RegistryObject<Block> DARKDWELLER_FENCE_GATE = registerBlockWithItem(
            "darkdweller_fence_gate",
            () -> new FenceGateBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).noLootTable(),
                    SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE,
                    SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN)
    );
    public static final RegistryObject<Block> DARKDWELLER_DOOR = registerBlockWithItem(
            "darkdweller_door",
            () -> new DoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion().noLootTable(),
                    BlockSetType.BAMBOO)
    );
    public static final RegistryObject<Block> DARKDWELLER_TRAPDOOR = registerBlockWithItem(
            "darkdweller_trapdoor",
            () -> new TrapDoorBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion().noLootTable(),
                    BlockSetType.BAMBOO)
    );
    public static final RegistryObject<Block> DARKDWELLER_BUTTON = registerBlockWithItem(
            "darkdweller_button",
            () -> new ButtonBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).noCollission().noLootTable(),
                    BlockSetType.BAMBOO,
                    15,
                    true)
    );
    public static final RegistryObject<Block> DARKDWELLER_PRESSURE_PLATE = registerBlockWithItem(
            "darkdweller_pressure_plate",
            () -> new PressurePlateBlock(
                    PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).noLootTable(),
                    BlockSetType.BAMBOO)
    );
    public static final RegistryObject<Block> DARKDWELLER_SIGN = registerBlockWithItem(
            "darkdweller_sign",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).noLootTable())
    );

    public static final RegistryObject<Block> RUNE = registerBlockWithoutItem("rune",
            () -> new CarpetBlock(BlockBehaviour.Properties
                    .copy(Blocks.WHITE_CARPET)
                    .noLootTable()
                    .noOcclusion()
                    .noCollission()
                    .strength(-1.0f, 4.0f)
                    .sound(ModSounds.RUNE_SOUNDS))



    );


}
