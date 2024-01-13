package cc.bluebits.hongtaiyang.event;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.entity.client.ModModelLayers;
import cc.bluebits.hongtaiyang.registries.block.ModBlockEntities;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Class for registering client-side event handlers
 */
@Mod.EventBusSubscriber(modid = HongTaiyang.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
	/**
	 * Event handler for the EntityRenderersEvent.RegisterLayerDefinitions event
	 * @param event The event to handle
	 */
	@SubscribeEvent
	public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.DARKDWELLER_BOAT_LAYER, BoatModel::createBodyModel);
		event.registerLayerDefinition(ModModelLayers.DARKDWELLER_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
	}
	
	/**
	 * Event handler for the EntityRenderersEvent.RegisterRenderers event
	 * @param event The event to handle
	 */
	@SubscribeEvent
	public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
	}
}
