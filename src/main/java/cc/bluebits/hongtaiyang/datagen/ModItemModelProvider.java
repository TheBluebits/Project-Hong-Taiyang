package cc.bluebits.hongtaiyang.datagen;


import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, HongTaiyang.MOD_ID, existingFileHelper);
	}


	private ItemModelBuilder placeholderItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
				.texture("layer0", new ResourceLocation(HongTaiyang.MOD_ID, "item/placeholder"));
	}

	private ItemModelBuilder parentedItem(RegistryObject<Item> item, String parent) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation(parent))
				.texture("layer0", new ResourceLocation(HongTaiyang.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder parentedLayeredItem(RegistryObject<Item> item, String parentLocation, List<String> layerTextureNames) {
		ItemModelBuilder builder = withExistingParent(item.getId().getPath(), new ResourceLocation(parentLocation));

		int i = 0;
		for (String textureName : layerTextureNames) {
			builder = builder.texture("layer" + i++, new ResourceLocation(HongTaiyang.MOD_ID, "item/" + textureName));
		}

		return builder;
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return parentedItem(item, "item/generated");
	}


	private ItemModelBuilder modParentedItem(RegistryObject<Item> item, String parent) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation(HongTaiyang.MOD_ID, "item/base/" + parent))
				.texture("layer0", new ResourceLocation(HongTaiyang.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleLayeredItem(RegistryObject<Item> item, List<String> layerTextureNames) {
		return parentedLayeredItem(item, "item/generated", layerTextureNames);
	}

	private ItemModelBuilder handheldLayeredItem(RegistryObject<Item> item, List<String> layerTextureNames) {
		return parentedLayeredItem(item, "item/handheld", layerTextureNames);
	}

	private ItemModelBuilder modParentedCustomModelItem(RegistryObject<Item> item, String parent, Map<String, String> textures) {
		ItemModelBuilder builder = withExistingParent(item.getId().getPath(), new ResourceLocation(HongTaiyang.MOD_ID, "item/base/" + parent));

		for (Map.Entry<String, String> texture : textures.entrySet()) {
			builder = builder.texture(texture.getKey(), new ResourceLocation(HongTaiyang.MOD_ID, "item/" + texture.getValue()));
		}

		return builder;
	}

	private ItemModelBuilder customModelItem(RegistryObject<Item> item, Map<String, String> textures) {
		return modParentedCustomModelItem(item, item.getId().getPath(), textures);
	}

	private ItemModelBuilder simpleCustomModelItem(RegistryObject<Item> item) {
		return modParentedItem(item, item.getId().getPath());
	}


	@Override
	protected void registerModels() {
		simpleLayeredItem(ModItems.CRACKED_SOUL_CORE, List.of("soul_core_base", "soul_core_animation_fast", "soul_core_top_cracked"));
		placeholderItem(ModItems.DARKDWELLER_BOAT);
		placeholderItem(ModItems.DWELLBERRY);
		placeholderItem(ModItems.DWELLBERRY_SEEDS);
		simpleItem(ModItems.LOGBOOK);
		simpleCustomModelItem(ModItems.MAGIC_CHALK);
		placeholderItem(ModItems.SONAR_COMPASS);
		simpleLayeredItem(ModItems.SOUL_CORE, List.of("soul_core_base", "soul_core_animation", "soul_core_top"));
		simpleLayeredItem(ModItems.TOME_OF_UNIVERSE, List.of("logbook", "galaxy"));
		placeholderItem(ModItems.TUNING_FORK);
        handheldLayeredItem(ModItems.UMBRAL_AXE, List.of("umbral_axe_base", "umbral_axe_pulse"));
		simpleItem(ModItems.UMBRAL_ESSENCE);
		simpleItem(ModItems.UMBRAL_GEM);
		customModelItem(ModItems.UMBRAL_LOCATOR, Map.of("back", "umbral_locator_back", "front", "umbral_locator_front"));
		handheldLayeredItem(ModItems.UMBRAL_HOE, List.of("umbral_hoe_base", "umbral_hoe_pulse"));
        handheldLayeredItem(ModItems.UMBRAL_PICKAXE, List.of("umbral_pickaxe_base", "umbral_pickaxe_pulse"));
        handheldLayeredItem(ModItems.UMBRAL_SHOVEL, List.of("umbral_shovel_base", "umbral_shovel_pulse"));
		//handheldLayeredItem(ModItems.UMBRAL_SWORD, List.of("umbral_sword_base", "umbral_sword_pulse"));
		placeholderItem(ModItems.UMBRAL_SWORD);
		simpleCustomModelItem(ModItems.WARDLING_ANTLER);
		placeholderItem(ModItems.WARDLING_HELMET);
		placeholderItem(ModItems.WARDLING_CHESTPLATE);
		placeholderItem(ModItems.WARDLING_LEGGINGS);
		placeholderItem(ModItems.WARDLING_BOOTS);
		simpleItem(ModItems.WRITINGS);
		customModelItem(ModItems.HANDSPONGE, Map.of("handle", "stripped_dark_oak_log", "sponge", "sponge"));
		modParentedCustomModelItem(ModItems.WET_HANDSPONGE, "handsponge", Map.of("handle", "stripped_dark_oak_log", "sponge", "wet_sponge"));
	}
}
