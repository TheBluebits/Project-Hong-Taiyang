package cc.bluebits.hongtaiyang.entity;

import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.entity.ModEntities;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

/**
 * A custom boat entity
 */
public class ModBoatEntity extends Boat {
	private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(ModBoatEntity.class, EntityDataSerializers.INT);
	
	/**
	 * Constructs a new ModBoatEntity
	 * @param pEntityType The entity type of the boat, passed to super
	 * @param pLevel The level, passed to super
	 */
	public ModBoatEntity(EntityType<? extends Boat> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	/**
	 * Constructs a new ModBoatEntity
	 * @param pLevel The level, passed to super
	 * @param pX The x position
	 * @param pY The y position
	 * @param pZ The z position
	 */
	public ModBoatEntity(Level pLevel, double pX, double pY, double pZ) {
		this(ModEntities.MOD_BOAT.get(), pLevel);
		setPos(pX, pY, pZ);
		xo = pX;
		yo = pY;
		zo = pZ;
	}
	
	

	@Override
	public @NotNull Item getDropItem() {
		return switch (getModVariant()) {
			case DARKDWELLER -> ModItems.DARKDWELLER_BOAT.get();
		};
	}
	
	

	/**
	 * Sets the boat variant to the given type
	 * @param pVariant The type to set the boat variant to
	 */
	public void setVariant(Type pVariant) {
		this.entityData.set(DATA_ID_TYPE, pVariant.ordinal());
	}

	/**
	 * Gets the mod boat variant
	 * @return The mod boat variant
	 */
	public Type getModVariant() {
		return Type.byId(this.entityData.get(DATA_ID_TYPE));
	}
	
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_ID_TYPE, Type.DARKDWELLER.ordinal());
	}
	
	protected void addAdditionalSaveData(CompoundTag pCompound) {
		pCompound.putString("Type", this.getModVariant().getSerializedName());
	}
	
	protected void readAdditionalSaveData(CompoundTag pCompound) {
		if (pCompound.contains("Type", 8)) {
			this.setVariant(Type.byName(pCompound.getString("Type")));
		}
	}
	
	

	/**
	 * A custom type enum for the boat, copied from {@link net.minecraft.world.entity.vehicle.Boat.Type} and modified, since it cannot be modified directly
	 */
	@SuppressWarnings({"MissingJavadoc", "deprecation"})
	public enum Type implements StringRepresentable {
		DARKDWELLER(ModBlocks.DARKDWELLER_PLANKS.get(), "darkdweller");

		private final String name;
		private final Block planks;
		public static final StringRepresentable.EnumCodec<ModBoatEntity.Type> CODEC = StringRepresentable.fromEnum(ModBoatEntity.Type::values);
		private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

		Type(Block pPlanks, String pName) {
			this.name = pName;
			this.planks = pPlanks;
		}

		public @NotNull String getSerializedName() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}

		@SuppressWarnings("unused")
		public Block getPlanks() {
			return this.planks;
		}

		public String toString() {
			return this.name;
		}

		/**
		 * Get a boat type by its enum ordinal
		 */
		public static ModBoatEntity.Type byId(int pId) {
			return BY_ID.apply(pId);
		}

		public static ModBoatEntity.Type byName(String pName) {
			return CODEC.byName(pName, DARKDWELLER);
		}
	}
}
