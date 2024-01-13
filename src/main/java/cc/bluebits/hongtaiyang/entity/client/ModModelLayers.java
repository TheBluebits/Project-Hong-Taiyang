package cc.bluebits.hongtaiyang.entity.client;

import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

/**
 * Class for handling model layers for mod entities
 */
@SuppressWarnings({"unused", "MissingJavadoc"})
public class ModModelLayers {
	public static final ModelLayerLocation DARKDWELLER_BOAT_LAYER = new ModelLayerLocation(
			new ResourceLocation(HongTaiyang.MOD_ID, "boat/darkdweller"), "main");
	public static final ModelLayerLocation DARKDWELLER_CHEST_BOAT_LAYER = new ModelLayerLocation(
			new ResourceLocation(HongTaiyang.MOD_ID, "chest_boat/darkdweller"), "main");
}
