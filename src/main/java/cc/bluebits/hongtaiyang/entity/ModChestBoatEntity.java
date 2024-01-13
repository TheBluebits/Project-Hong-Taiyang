package cc.bluebits.hongtaiyang.entity;

import cc.bluebits.hongtaiyang.registries.entity.ModEntities;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * A custom chest boat entity
 */
public class ModChestBoatEntity extends ChestBoat {
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModChestBoatEntity.class, EntityDataSerializers.INT);

	/**
	 * Constructs a new ModChestBoatEntity
	 * @param pEntityType The entity type of the boat, passed to super
	 * @param pLevel The level, passed to super
	 */
	public ModChestBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	/**
	 * Constructs a new ModChestBoatEntity
	 * @param pLevel The level, passed to super
	 * @param pX The x position
	 * @param pY The y position
	 * @param pZ The z position
	 */
	public ModChestBoatEntity(Level pLevel, double pX, double pY, double pZ) {
		this(ModEntities.MOD_CHEST_BOAT.get(), pLevel);
		setPos(pX, pY, pZ);
		xo = pX;
		yo = pY;
		zo = pZ;
	}



	@Override
	public @NotNull Item getDropItem() {
		return switch (getModVariant()) {
			case DARKDWELLER -> ModItems.DARKDWELLER_CHEST_BOAT.get();
		};
	}



	/**
	 * Sets the boat variant to the given type
	 * @param pVariant The type to set the boat variant to
	 */
	public void setVariant(ModBoatEntity.Type pVariant) {
		this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
	}

	/**
	 * Gets the mod boat variant
	 * @return The mod boat variant
	 */
	public ModBoatEntity.Type getModVariant() {
		return ModBoatEntity.Type.byId(this.entityData.get(DATA_ID_TYPE));
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE, ModBoatEntity.Type.DARKDWELLER.ordinal());
	}

	protected void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putString("Type", this.getModVariant().getSerializedName());
	}

	protected void readAdditionalSaveData(CompoundTag pCompound) {
		if (pCompound.contains("Type", 8)) {
			this.setVariant(ModBoatEntity.Type.byName(pCompound.getString("Type")));
		}
	}
}
