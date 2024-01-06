package cc.bluebits.hongtaiyang.datagen;


import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "unused"})
public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, HongTaiyang.MOD_ID, existingFileHelper);
	}

	private <T extends ItemLike> String getPathDirectory(RegistryObject<T> t, boolean forceItem) {
		if(t.get() instanceof Item || forceItem) return "item/";
		if(t.get() instanceof Block) return "block/";
		return "";
	}

	private <T extends ItemLike> String getPathDirectory(RegistryObject<T> t) {
		return getPathDirectory(t, false);
	}
	
	private <T extends ItemLike> String getName(RegistryObject<T> t) {
		if(t.get() instanceof Item) return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(((Item) t.get()))).getPath();
		if(t.get() instanceof Block) return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey((Block) t.get())).getPath();
		return "";
	}
	
	private String getNamespace(boolean isModded) {
		return isModded ? HongTaiyang.MOD_ID : "minecraft";
	}
	
	
	
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded, String textureKey, boolean forceItem) {
		return withExistingParent(getName(item), new ResourceLocation(getNamespace(isParentModded), getPathDirectory(item, forceItem) + parent))
				.texture(textureKey, new ResourceLocation(getNamespace(isTextureModded), getPathDirectory(item, forceItem) + textureName));
	}

	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded, String textureKey) {
		return parentedItem(item, parent, isParentModded, textureName, isTextureModded, textureKey, false);
	}

	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded) {
		return parentedItem(item, parent, isParentModded, textureName, true, "layer0");
	}

	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName) {
		return parentedItem(item, parent, isParentModded, textureName, true);
	}
	
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded) {
		return parentedItem(item, parent, isParentModded, getName(item));
	}

	
	
	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block, String parent, boolean isParentModded) {
		return withExistingParent(getName(block), new ResourceLocation(getNamespace(isParentModded), getPathDirectory(block) + parent));
	}

	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block, String parent) {
		return blockItem(block, parent, true);
	}

	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block) {
		return blockItem(block, getName(block));
	}

	
	
	private <T extends ItemLike> ItemModelBuilder parentedLayeredItem(RegistryObject<T> item, String parent, boolean isParentModded, Map<String, Boolean> layerTextures) {
		ItemModelBuilder builder = withExistingParent(getName(item), new ResourceLocation(getNamespace(isParentModded), getPathDirectory(item) + parent));

		int i = 0;
		for (Map.Entry<String, Boolean> layerTexture : layerTextures.entrySet()) {
			String textureName = layerTexture.getKey();
			boolean isTextureModded = layerTexture.getValue();
			
			builder = builder.texture("layer" + i++, new ResourceLocation(getNamespace(isTextureModded), getPathDirectory(item) + textureName));
		}

		return builder;
	}

	private <T extends ItemLike> ItemModelBuilder parentedLayeredItem(RegistryObject<T> item, String parent, boolean isParentModded, List<String> layerTextures) {
		Map<String, Boolean> layerTexturesMap = new LinkedHashMap<>();
		
		for(String texture : layerTextures) {
			layerTexturesMap.put(texture, true);
		}
		
		return parentedLayeredItem(item, parent, isParentModded, layerTexturesMap);
	}

	
	
	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, Map<Tuple<String, String>, Boolean> textures, String parent, boolean isParentModded) {
		String pathDirectory = getPathDirectory(item);
		ItemModelBuilder builder = withExistingParent(getName(item),
				new ResourceLocation(getNamespace(isParentModded), pathDirectory + parent));

		for (Map.Entry<Tuple<String, String>, Boolean> texture : textures.entrySet()) {
			String textureKey = texture.getKey().getA();
			String textureName = texture.getKey().getB();
			boolean isTextureModded = texture.getValue();
			
			builder = builder.texture(textureKey, new ResourceLocation(getNamespace(isTextureModded), pathDirectory + textureName));
		}

		return builder;
	}

	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, Map<Tuple<String, String>, Boolean> textures) {
		return customModelItem(item, textures, "base/" + getName(item), true);
	}

	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, List<Tuple<String, String>> textures, String parent, boolean isParentModded) {
		Map<Tuple<String, String>, Boolean> texturesMap = new LinkedHashMap<>();
		
		for(Tuple<String, String> texture : textures) {
			texturesMap.put(texture, true);
		}
		
		return customModelItem(item, texturesMap, parent, isParentModded);
	}

	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, List<Tuple<String, String>> textures) {
		return customModelItem(item, textures, "base/" + getName(item), true);
	}
	
	
	
	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item, String textureName, boolean isTextureModded) {
		return parentedItem(item, "generated", false, textureName, isTextureModded, "layer0", true);
	}

	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item, String textureName) {
		return simpleItem(item, textureName, true);
	}

	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item) {
		return simpleItem(item, getName(item), true);
	}

	
	
	private <T extends ItemLike> ItemModelBuilder simpleLayeredItem(RegistryObject<T> item, Map<String, Boolean> layerTextures) {
		return parentedLayeredItem(item, "generated", false, layerTextures);
	}

	private <T extends ItemLike> ItemModelBuilder simpleLayeredItem(RegistryObject<T> item, List<String> layerTextures) {
		return parentedLayeredItem(item, "generated", false, layerTextures);
	}

	private <T extends ItemLike> ItemModelBuilder simpleCustomModelItem(RegistryObject<T> item) {
		return parentedItem(item, "base/" + getName(item), true);
	}
	
	

	@Override
	protected void registerModels() {
		// Pure Items
		simpleLayeredItem(ModItems.CRACKED_SOUL_CORE, List.of("soul_core_base", "soul_core_animation_fast", "soul_core_top_cracked"));
		simpleItem(ModItems.DARKDWELLER_BOAT, "placeholder");
		simpleItem(ModItems.DWELLBERRY, "placeholder");
		simpleItem(ModItems.DWELLBERRY_SEEDS, "placeholder");
		simpleItem(ModItems.LOGBOOK);
		simpleCustomModelItem(ModItems.MAGIC_CHALK);
		simpleItem(ModItems.SONAR_COMPASS, "placeholder");
		simpleLayeredItem(ModItems.SOUL_CORE, List.of("soul_core_base", "soul_core_animation", "soul_core_top"));
		simpleLayeredItem(ModItems.TOME_OF_UNIVERSE, List.of("logbook", "galaxy"));
		simpleItem(ModItems.TUNING_FORK, "placeholder");
        parentedLayeredItem(ModItems.UMBRAL_AXE, "handheld", false, List.of("umbral_axe_base", "umbral_axe_pulse"));
		simpleItem(ModItems.UMBRAL_ESSENCE);
		simpleItem(ModItems.UMBRAL_GEM);
		customModelItem(ModItems.UMBRAL_LOCATOR, List.of(
				new Tuple<>("back", "umbral_locator_back"),
				new Tuple<>("front", "umbral_locator_front"))
		);
		parentedLayeredItem(ModItems.UMBRAL_HOE, "handheld", false, List.of("umbral_hoe_base", "umbral_hoe_pulse"));
		parentedLayeredItem(ModItems.UMBRAL_PICKAXE, "handheld", false, List.of("umbral_pickaxe_base", "umbral_pickaxe_pulse"));
		parentedLayeredItem(ModItems.UMBRAL_SHOVEL, "handheld", false, List.of("umbral_shovel_base", "umbral_shovel_pulse"));
		//handheldLayeredItem(ModItems.UMBRAL_SWORD, "handheld", false, List.of("umbral_sword_base", "umbral_sword_pulse"));
		simpleItem(ModItems.UMBRAL_SWORD, "placeholder");
		simpleCustomModelItem(ModItems.WARDLING_ANTLER);
		simpleItem(ModItems.WARDLING_HELMET, "placeholder");
		simpleItem(ModItems.WARDLING_CHESTPLATE, "placeholder");
		simpleItem(ModItems.WARDLING_LEGGINGS, "placeholder");
		simpleItem(ModItems.WARDLING_BOOTS, "placeholder");
		simpleItem(ModItems.WRITINGS);
		
		// Block Items
		blockItem(ModBlocks.DARKDWELLER_BUNDLE);
		parentedItem(ModBlocks.DARKDWELLER_BUTTON, "button_inventory", false, "placeholder", true, "texture");
		simpleItem(ModBlocks.DARKDWELLER_DOOR, "placeholder");
		parentedItem(ModBlocks.DARKDWELLER_FENCE, "fence_inventory", false, "placeholder", true, "texture");
		blockItem(ModBlocks.DARKDWELLER_FENCE_GATE);
		blockItem(ModBlocks.DARKDWELLER_PLANKS);
		blockItem(ModBlocks.DARKDWELLER_PRESSURE_PLATE);
		blockItem(ModBlocks.DARKDWELLER_SLAB);
		blockItem(ModBlocks.DARKDWELLER_STAIRS);
		blockItem(ModBlocks.DARKDWELLER_TRAPDOOR, getName(ModBlocks.DARKDWELLER_TRAPDOOR) + "_bottom");
		blockItem(ModBlocks.DEEPSLATE_UMBRAL_ORE);
		blockItem(ModBlocks.ROOTED_SCULK);
		blockItem(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE);
		blockItem(ModBlocks.UMBRAL_BLOCK);
		blockItem(ModBlocks.UMBRAL_ORE);
	}
}
