package cc.bluebits.hongtaiyang.datagen;


import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.item.ModItems;
import cc.bluebits.hongtaiyang.util.DataGenUtil;
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

/**
 * A class that generates item models for all items in the mod
 */
@SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "unused"})
public class ModItemModelProvider extends ItemModelProvider {
	/**
	 * Constructor for the class
	 * @param output Passed to the super constructor
	 * @param existingFileHelper Passed to the super constructor
	 * @see ItemModelProvider   
	 */
	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, HongTaiyang.MOD_ID, existingFileHelper);
	}


	/**
	 * Get subdirectory for paths depending on type of item
	 * @param item The RegistryObject of the item that is being checked for the type
	 * @param forceItem If set to {@code true} it will always return {@code "item/"}
	 * @return {@code "item/"} if {@code item} is of type {@code Item} and {@code "block/"} if {@code item} is of type {@code Block}. If the type cannot be determined returns an empty string.
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 */
	private <T extends ItemLike> String getPathDirectory(RegistryObject<T> item, boolean forceItem) {
		if(item.get() instanceof Item || forceItem) return "item/";
		if(item.get() instanceof Block) return "block/";
		return "";
	}

	/**
	 * Get subdirectory for paths depending on type of item
	 * @param item The {@code RegistryObject} of the item that is being checked for the type
	 * @return {@code "item/"} if {@code item} is of type {@code Item} and {@code "block/"} if {@code item} is of type {@code Block}
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 */
	private <T extends ItemLike> String getPathDirectory(RegistryObject<T> item) {
		return getPathDirectory(item, false);
	}

	/**
	 * Pulls the name of an item from the corresponding registry
	 * @param item The {@code RegistryObject} of the item of which the name is pulled
	 * @return The name of the specified item
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 */
	private <T extends ItemLike> String getName(RegistryObject<T> item) {
		if(item.get() instanceof Item) return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(((Item) item.get()))).getPath();
		if(item.get() instanceof Block) return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey((Block) item.get())).getPath();
		return "";
	}

	/**
	 * Decides which namespace to use for {@code ResourceLocations}
	 * @param isModded Flag to determine which namespace to use
	 * @return The mod id if {@code isModded} is {@code true} and {@code "minecraft"} otherwise
	 * @see ResourceLocation
	 */
	private String getNamespace(boolean isModded) {
		return isModded ? HongTaiyang.MOD_ID : "minecraft";
	}


	/**
	 * Creates a {@code ItemModelBuilder} with the specified parent and texture
	 * @param item The {@code RegistryObject} of the item for which the model is created
	 * @param parent The parent model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param textureName The name of the texture file
	 * @param isTextureModded Flag to determine which namespace to use for the texture
	 * @param textureKey The key of the texture specified in the parent model
	 * @param forceItem If set to {@code true} the namespace for the parent and texture will always be {@code "item/"}
	 * @return The {@code ItemModelBuilder} with the specified parent and texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded, String textureKey, boolean forceItem) {
		return withExistingParent(getName(item), new ResourceLocation(getNamespace(isParentModded), getPathDirectory(item, forceItem) + parent))
				.texture(textureKey, new ResourceLocation(getNamespace(isTextureModded), getPathDirectory(item, forceItem) + textureName));
	}

	/**
	 * Creates a {@code ItemModelBuilder} with the specified parent and texture
	 * @param item The {@code RegistryObject} of the item for which the model is created
	 * @param parent The parent model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param textureName The name of the texture file
	 * @param isTextureModded Flag to determine which namespace to use for the texture
	 * @param textureKey The key of the texture specified in the parent model
	 * @return The {@code ItemModelBuilder} with the specified parent and texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded, String textureKey) {
		return parentedItem(item, parent, isParentModded, textureName, isTextureModded, textureKey, false);
	}

	/**
	 * Creates a {@code ItemModelBuilder} with the specified parent and texture. The texture key will default to {@code "layer0"}
	 * @param item The {@code RegistryObject} of the item for which the model is created
	 * @param parent The parent model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param textureName The name of the texture file
	 * @param isTextureModded Flag to determine which namespace to use for the texture
	 * @return The {@code ItemModelBuilder} with the specified parent and texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName, boolean isTextureModded) {
		return parentedItem(item, parent, isParentModded, textureName, true, "layer0");
	}

	/**
	 * Creates a {@code ItemModelBuilder} with the specified parent and texture. The texture key will default to {@code "layer0"} and the texture namespace will default to the modded namespace
	 * @param item The {@code RegistryObject} of the item for which the model is created
	 * @param parent The parent model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param textureName The name of the texture file
	 * @return The {@code ItemModelBuilder} with the specified parent and texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded, String textureName) {
		return parentedItem(item, parent, isParentModded, textureName, true);
	}

	/**
	 * Creates a {@code ItemModelBuilder} with the specified parent and texture. The texture key will default to {@code "layer0"}, the texture namespace will default to the modded namespace and the texture name will default to the name of the item
	 * @param item The {@code RegistryObject} of the item for which the model is created
	 * @param parent The parent model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @return The {@code ItemModelBuilder} with the specified parent and texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedItem(RegistryObject<T> item, String parent, boolean isParentModded) {
		return parentedItem(item, parent, isParentModded, getName(item));
	}


	/**
	 * Creates an {@code ItemModelBuilder} for blocks with the specified parent
	 * @param block The {@code RegistryObject} of the block for which the item model is created
	 * @param parent The parent block model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @return The {@code ItemModelBuilder} with the specified block model parent
	 * @param <T> Extends {@code Block} and specifies the type used for {@code block}
	 */
	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block, String parent, boolean isParentModded) {
		return withExistingParent(getName(block), new ResourceLocation(getNamespace(isParentModded), getPathDirectory(block) + parent));
	}

	/**
	 * Creates an {@code ItemModelBuilder} for blocks with the specified parent. The parent namespace will default to the modded namespace
	 * @param block The {@code RegistryObject} of the block for which the item model is created
	 * @param parent The parent block model
	 * @return The {@code ItemModelBuilder} with the specified block model parent
	 * @param <T> Extends {@code Block} and specifies the type used for {@code block}
	 */
	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block, String parent) {
		return blockItem(block, parent, true);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for blocks with the specified parent. The parent will default to {@code "block/" + block name}
	 * @param block The {@code RegistryObject} of the block for which the item model is created
	 * @return The {@code ItemModelBuilder} with the specified block model parent
	 * @param <T> Extends {@code Block} and specifies the type used for {@code block}
	 */
	private <T extends Block> ItemModelBuilder blockItem(RegistryObject<T> block) {
		return blockItem(block, getName(block));
	}


	/**
	 * Creates an {@code ItemModelBuilder} for items with the specified parent and a texture consisting of multiple layers
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param parent The parent item model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param layerTextures A {@code Map} of texture names and their corresponding {@code isModded} flag, that determines which namespace to use for the texture, for each layer
	 * @return The {@code ItemModelBuilder} with the specified parent and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 */
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

	/**
	 * Creates an {@code ItemModelBuilder} for items with the specified parent and a texture consisting of multiple layers. The texture namespace will default to the modded namespace
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param parent The parent item model
	 * @param isParentModded Flag to determine which namespace to use for the parent
	 * @param layerTextures A {@code List} of texture names for each layer
	 * @return The {@code ItemModelBuilder} with the specified parent and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder parentedLayeredItem(RegistryObject<T> item, String parent, boolean isParentModded, List<String> layerTextures) {
		Map<String, Boolean> layerTexturesMap = new LinkedHashMap<>();
		
		for(String texture : layerTextures) {
			layerTexturesMap.put(texture, true);
		}
		
		return parentedLayeredItem(item, parent, isParentModded, layerTexturesMap);
	}


	/**
	 * Creates an {@code ItemModelBuilder} for items with a custom model. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} that contain the texture key, texture name and {@code isModded} flag, which determines which namespace to use, for each texture
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textures The textures as described above
	 * @param model The name of the custom model file
	 * @param isModelModded Flag to determine which namespace to use for the custom model
	 * @return The {@code ItemModelBuilder} with the specified custom model and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see Tuple
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, Map<Tuple<String, String>, Boolean> textures, String model, boolean isModelModded) {
		String pathDirectory = getPathDirectory(item);
		ItemModelBuilder builder = withExistingParent(getName(item),
				new ResourceLocation(getNamespace(isModelModded), pathDirectory + model));

		for (Map.Entry<Tuple<String, String>, Boolean> texture : textures.entrySet()) {
			String textureKey = texture.getKey().getA();
			String textureName = texture.getKey().getB();
			boolean isTextureModded = texture.getValue();
			
			builder = builder.texture(textureKey, new ResourceLocation(getNamespace(isTextureModded), pathDirectory + textureName));
		}

		return builder;
	}

	/**
	 * Creates an {@code ItemModelBuilder} for items with a custom model. The model namespace will default to the modded namespace. The model name will default to {@code "base/" + item name}. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} that contain the texture key, texture name and {@code isModded} flag, which determines which namespace to use, for each texture
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textures The textures as described above
	 * @return The {@code ItemModelBuilder} with the specified custom model and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see Tuple
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, Map<Tuple<String, String>, Boolean> textures) {
		return customModelItem(item, textures, "base/" + getName(item), true);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for items with a custom model. The texture namespace will default to the modded namespace. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textures The textures as described above
	 * @param model The name of the custom model file
	 * @param isModelModded Flag to determine which namespace to use for the custom model
	 * @return The {@code ItemModelBuilder} with the specified custom model and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see Tuple
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, List<Tuple<String, String>> textures, String model, boolean isModelModded) {
		Map<Tuple<String, String>, Boolean> texturesMap = DataGenUtil.convertTextureListToFlaggedTextureMap(textures);
		return customModelItem(item, texturesMap, model, isModelModded);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for items with a custom model. The texture namespace will default to the modded namespace. The model name will default to {@code "base/" + item name}. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textures The textures as described above
	 * @return The {@code ItemModelBuilder} with the specified custom model and textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see Tuple
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder customModelItem(RegistryObject<T> item, List<Tuple<String, String>> textures) {
		return customModelItem(item, textures, "base/" + getName(item), true);
	}


	/**
	 * Creates an {@code ItemModelBuilder} for simple items with the parent set to {@code "item/generated"} and a single texture
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textureName The name of the texture file
	 * @param isTextureModded Flag to determine which namespace to use for the texture
	 * @return The {@code ItemModelBuilder} with the specified texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item, String textureName, boolean isTextureModded) {
		return parentedItem(item, "generated", false, textureName, isTextureModded, "layer0", true);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for simple items with the parent set to {@code "item/generated"} and a single texture. The texture namespace will default to the modded namespace
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param textureName The name of the texture file
	 * @return The {@code ItemModelBuilder} with the specified texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item, String textureName) {
		return simpleItem(item, textureName, true);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for simple items with the parent set to {@code "item/generated"} and a single texture. The texture name will default to the name of the item
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @return The {@code ItemModelBuilder} with the specified texture
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleItem(RegistryObject<T> item) {
		return simpleItem(item, getName(item), true);
	}


	/**
	 * Creates an {@code ItemModelBuilder} for simple items with the parent set to {@code "item/generated"} and a texture consisting of multiple layers. Textures are encoded in a {@code Map} of texture names and their corresponding {@code isModded} flag, which determines which namespace to use, for each layer
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param layerTextures The textures as described above
	 * @return The {@code ItemModelBuilder} with the specified textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleLayeredItem(RegistryObject<T> item, Map<String, Boolean> layerTextures) {
		return parentedLayeredItem(item, "generated", false, layerTextures);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for simple items with the parent set to {@code "item/generated"} and a texture consisting of multiple layers. The texture namespace will default to the modded namespace
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @param layerTextures A {@code List} of texture names for each layer
	 * @return The {@code ItemModelBuilder} with the specified textures
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleLayeredItem(RegistryObject<T> item, List<String> layerTextures) {
		return parentedLayeredItem(item, "generated", false, layerTextures);
	}

	/**
	 * Creates an {@code ItemModelBuilder} for simple items with a custom model. The model will default to {@code "base/" + item name} int the modded namespace
	 * @param item The {@code RegistryObject} of the item for which the item model is created
	 * @return The {@code ItemModelBuilder} with the specified custom model
	 * @param <T> Extends {@code ItemLike} and specifies the type used for {@code item}
	 * @see ItemLike
	 * @see RegistryObject
	 * @see ItemModelBuilder
	 */
	private <T extends ItemLike> ItemModelBuilder simpleCustomModelItem(RegistryObject<T> item) {
		return parentedItem(item, "base/" + getName(item), true);
	}


	/**
	 * Registers all item models
	 */
	@Override
	protected void registerModels() {
		// --------------------------------
		//  Pure Items
		// --------------------------------
		
		// ========[ Chapter 1 ]========
		
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
		
		
		
		// --------------------------------
		//  Block Items
		// --------------------------------

		// ========[ Chapter 1 ]========
		
		blockItem(ModBlocks.DARKDWELLER_BUNDLE);
		parentedItem(ModBlocks.DARKDWELLER_BUTTON, "button_inventory", false, "placeholder", true, "texture");
		simpleItem(ModBlocks.DARKDWELLER_DOOR, "placeholder");
		parentedItem(ModBlocks.DARKDWELLER_FENCE, "fence_inventory", false, "placeholder", true, "texture");
		blockItem(ModBlocks.DARKDWELLER_FENCE_GATE);
		blockItem(ModBlocks.DARKDWELLER_LOG, "darkdweller_log_inventory");
		blockItem(ModBlocks.DARKDWELLER_PLANKS);
		blockItem(ModBlocks.DARKDWELLER_PRESSURE_PLATE);
		// Darkdweller Sign
		simpleItem(ModBlocks.DARKDWELLER_ROOT, "placeholder");
		blockItem(ModBlocks.DARKDWELLER_SLAB);
		blockItem(ModBlocks.DARKDWELLER_STAIRS);
		simpleItem(ModBlocks.DARKDWELLER_STICK, "placeholder");
		blockItem(ModBlocks.DARKDWELLER_TRAPDOOR, getName(ModBlocks.DARKDWELLER_TRAPDOOR) + "_bottom");
		blockItem(ModBlocks.DEEPSLATE_UMBRAL_ORE);
		blockItem(ModBlocks.ROOTED_SCULK);
		blockItem(ModBlocks.STRIPPED_DARKDWELLER_BUNDLE);
		blockItem(ModBlocks.UMBRAL_BLOCK);
		blockItem(ModBlocks.UMBRAL_ORE);
	}
}