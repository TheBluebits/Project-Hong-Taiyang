package cc.bluebits.hongtaiyang.registries.block;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.entity.ModHangingSignBlockEntity;
import cc.bluebits.hongtaiyang.block.entity.ModSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering block entities
 */
@SuppressWarnings({"unused", "MissingJavadoc", "DataFlowIssue"})
public class ModBlockEntities {
	/**
	 * The deferred register for block entities
	 */
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the block entities
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		BLOCK_ENTITIES.register(eventBus);
	}
	
	public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITIES.register(
			"mod_sign",
			() -> BlockEntityType.Builder.of(ModSignBlockEntity::new,
					ModBlocks.DARKDWELLER_SIGN.get(),
					ModBlocks.DARKDWELLER_WALL_SIGN.get()
			).build(null)
	);
	
	public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN = BLOCK_ENTITIES.register(
			"mod_hanging_sign",
			() -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
					ModBlocks.DARKDWELLER_HANGING_SIGN.get(),
					ModBlocks.DARKDWELLER_WALL_HANGING_SIGN.get()
			).build(null)
	);
}
