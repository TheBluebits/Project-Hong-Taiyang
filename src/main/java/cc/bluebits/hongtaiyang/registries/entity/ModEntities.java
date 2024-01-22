package cc.bluebits.hongtaiyang.registries.entity;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.entity.ModBoatEntity;
import cc.bluebits.hongtaiyang.entity.ModChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering entities
 */
@SuppressWarnings({"MissingJavadoc", "unused"})
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the entities
	 * @param eventBus The event bus to register to
	 */
	public static void register(IEventBus eventBus) {
		ENTITY_TYPES.register(eventBus);
	}
	
	
	
	public static final RegistryObject<EntityType<ModBoatEntity>> MOD_BOAT = ENTITY_TYPES.register(
			"mod_boat",
			() -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
					.sized(1.375F, 0.5625F)
					.build("mod_boat"));
	public static final RegistryObject<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT = ENTITY_TYPES.register(
			"mod_chest_boat",
			() -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
					.sized(1.375F, 0.5625F)
					.build("mod_chest_boat"));
}
