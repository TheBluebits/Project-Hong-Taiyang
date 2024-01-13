package cc.bluebits.hongtaiyang.entity.client;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.entity.ModBoatEntity;
import cc.bluebits.hongtaiyang.entity.ModChestBoatEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

/**
 * A custom boat renderer
 */
public class ModBoatRenderer extends BoatRenderer {
	private final Map<ModBoatEntity.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

	/**
	 * Constructor for the custom boat renderer
	 * @param pContext The entity renderer provider context
	 * @param pChestBoat Whether the boat is a chest boat
	 */
	public ModBoatRenderer(EntityRendererProvider.Context pContext, boolean pChestBoat) {
		super(pContext, pChestBoat);
		boatResources = Stream.of(ModBoatEntity.Type.values()).collect(
				ImmutableMap.toImmutableMap(
						type -> type,
						type -> Pair.of(
								new ResourceLocation(HongTaiyang.MOD_ID, getTextureLocation(type, pChestBoat)),
								createBoatModel(pContext, type, pChestBoat))));
	}

	
	
	private ListModel<Boat> createBoatModel(EntityRendererProvider.Context pContext, ModBoatEntity.Type pType, boolean pChestBoat) {
		ModelLayerLocation modellayerlocation = pChestBoat ? ModBoatRenderer.createChestBoatModelName(pType) : ModBoatRenderer.createBoatModelName(pType);
		ModelPart modelpart = pContext.bakeLayer(modellayerlocation);
		return pChestBoat ? new ChestBoatModel(modelpart) : new BoatModel(modelpart);
	}

	private static String getTextureLocation(ModBoatEntity.Type pType, boolean pChestBoat) {
		return pChestBoat ? "textures/entity/chest_boat/" + pType.getName() + ".png" : "textures/entity/boat/" + pType.getName() + ".png";
	}
	
	
	
	@SuppressWarnings("SameParameterValue")
	private static ModelLayerLocation createLocation(String pPath, String pModel) {
		return new ModelLayerLocation(new ResourceLocation(HongTaiyang.MOD_ID, pPath), pModel);
	}

	/**
	 * Creates a model layer location for a boat
	 * @param pType The boat type
	 * @return The created model layer location
	 */
	public static ModelLayerLocation createBoatModelName(ModBoatEntity.Type pType) {
		return createLocation("boat/" + pType.getName(), "main");
	}

	/**
	 * Creates a model layer location for a chest boat
	 * @param pType The boat type
	 * @return The created model layer location
	 */
	public static ModelLayerLocation createChestBoatModelName(ModBoatEntity.Type pType) {
		return createLocation("chest_boat/" + pType.getName(), "main");
	}
	
	@SuppressWarnings("DataFlowIssue")
	public @NotNull Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@NotNull Boat boat) {
		if (boat instanceof ModBoatEntity modBoat) {
			return boatResources.get(modBoat.getModVariant());
		} else if (boat instanceof ModChestBoatEntity modChestBoat) {
			return boatResources.get(modChestBoat.getModVariant());
		} else {
			return null;
		}
	}
}
