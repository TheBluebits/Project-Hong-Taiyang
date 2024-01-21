package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.block.base.ModModularPillarBlock;
import cc.bluebits.hongtaiyang.block.base.ModThinPillarFruitBlock;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.util.DataGenUtil;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

/**
 * A class that generates blockstates and blockmodels for all blocks in the mod
 */
@SuppressWarnings("ALL")
public class ModBlockStateProvider extends BlockStateProvider {
	/**
	 * Constructor for the class
	 * @param output Passed to the super constructor
	 * @param exFileHelper Passed to the super constructor
	 * @see BlockStateProvider   
	 */
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, HongTaiyang.MOD_ID, exFileHelper);
	}


	
	/**
	 * Pulls the name of a block from the registry and adds a suffix to it, if specified
	 * @param block The instance of the block which name is pulled
	 * @param suffix The suffix that will be added behind the name after a {@code "_"} character    
	 * @return The name of the specified block with the specified suffix
	 */
	private String getName(Block block, String suffix) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		if(suffix != null && !suffix.isBlank()) name += "_" + suffix;
		
		return name;
	}

	/**
	 * Pulls the name of a block from the registry without adding a suffix
	 * @param block The instance of the block which name is pulled
	 * @return The name of the specified block
	 */
	private String getName(Block block) {
		return getName(block, "");
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
	 * Creates a BlockState for a simple block
	 * @param block The instance of the block which BlockState is created
	 * @param texture The texture of the block
	 */
	private void simpleBlock(Block block, ResourceLocation texture) {
		ModelFile model = models().cubeAll(getName(block), texture);
		simpleBlock(block, model);
	}


	/**
	 * Creates a BlockState for a cross block
	 * @param block The instance of the block which BlockState is created
	 * @param texture The texture of the block
	 */
	private void crossBlock(Block block, ResourceLocation texture) {
		ModelFile model = models().cross(getName(block), texture).renderType("cutout");
		simpleBlock(block, model);
	}

	/**
	 * Creates a BlockState for a cross block. The texture is pulled from the block's registry name
	 * @param block The instance of the block which BlockState is created
	 */
	private void crossBlock(Block block) {
		ResourceLocation texture = blockTexture(block);
		crossBlock(block, texture);
	}

	
	
	/**
	 * Creates a BlockState for a plate block
	 * @param block The instance of the block which BlockState is created
	 * @param texture The texture of the block
	 */
	private void plateBlock(Block block, ResourceLocation texture) {
		ModelFile model = models().carpet(getName(block), texture).renderType("cutout");
		simpleBlock(block, model);
	}
	
	/**
	 * Creates a BlockState for a plate block. The texture is pulled from the block's registry name
	 * @param block The instance of the block which BlockState is created
	 */
	private void plateBlock(Block block) {
		ResourceLocation texture = blockTexture(block);
		plateBlock(block, texture);
	}


	// This is temporary until the actual rune blocks are implemented
	/**
	 * Creates a BlockState for a plate block with a dynamic amount of variating textures. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param textures The textures as described above
	 */
	private void variedPlateBlock(Block block, Map<Tuple<String, String>, Boolean> textures) {
		List<ModelFile> models = new ArrayList<>();
		ConfiguredModel.Builder modelBuilder = ConfiguredModel.builder();
		
		boolean isFirstIteration = true;
		for(Map.Entry<Tuple<String, String>, Boolean> texture : textures.entrySet()) {
			String textureKey = texture.getKey().getA();
			String textureName = texture.getKey().getB();
			boolean isTextureModded = texture.getValue();
			
			ModelFile modelFile = models().carpet(getName(block, textureName), new ResourceLocation(getNamespace(isTextureModded), "block/" + textureName)).renderType("cutout");
			
			if(!isFirstIteration) modelBuilder = modelBuilder.nextModel();
			modelBuilder.modelFile(modelFile);
			
			isFirstIteration = false;
		}
		
		VariantBlockStateBuilder stateBuilder = getVariantBuilder(block);
		stateBuilder.partialState().setModels(modelBuilder.build());
	}
	
	

	/**
	 * Creates a BlockModel based on another BlockModel. The textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockModel is created
	 * @param nameSuffix The suffix that will be added behind the name after a {@code "_"} character
	 * @param parent The name of the parent BlockModel
	 * @param textures The textures as described above
	 * @return The created BlockModel
	 * @see ModelBuilder
	 * @see Tuple
	 */
	private ModelBuilder parentedBlockModel(Block block, String nameSuffix, String parent, Map<Tuple<String, String>, Boolean> textures) {
		ModelBuilder model = null;
		
		if(parent != null && !parent.isEmpty()) {
			model = models().withExistingParent(getName(block, nameSuffix), new ResourceLocation(HongTaiyang.MOD_ID, "block/" + parent));

			for(Map.Entry<Tuple<String, String>, Boolean> texture : textures.entrySet()) {
				String textureKey = texture.getKey().getA();
				String textureName = texture.getKey().getB();
				boolean isTextureModded = texture.getValue();

				model.texture(textureKey, new ResourceLocation(getNamespace(isTextureModded), "block/" + textureName));
			}
		}
		
		return model;
	}


	/**
	 * Adds the links to the BlockState of a modular pillar block
	 * @param builder The BlockStateBuilder of the block
	 * @param primaryLink The BlockModel of the primary link
	 * @param secondaryLink The BlockModel of the secondary link
	 */
	private void modularPillarMultipart(MultiPartBlockStateBuilder builder, ModelFile primaryLink, ModelFile secondaryLink) {
		for(Direction dir : Direction.values()) {
			int xRot = 0;
			int yRot = 0;

			if(dir.get2DDataValue() >= 0) {
				yRot = (180 + (int) dir.toYRot()) % 360;
			} else {
				xRot = (180 + (dir.getAxisDirection().getStep() * 90)) % 360;
			}

			List<Direction.Axis> otherAxesList = new ArrayList<>(Arrays.asList(Direction.Axis.VALUES));
			otherAxesList.remove(dir.getAxis());
			Direction.Axis[] otherAxes = otherAxesList.toArray(new Direction.Axis[2]);

			if(primaryLink != null) {
				builder.part()
						.modelFile(primaryLink)
						.rotationX(xRot)
						.rotationY(yRot)
						.addModel()
						.useOr()
						.nestedGroup()
						.condition(ModModularPillarBlock.PROPERTY_BY_DIRECTION.get(dir), 1)
						.end()
						.nestedGroup()
						.condition(ModModularPillarBlock.NUM_PRIMARY_LINKS, 0, 1)
						.condition(ModModularPillarBlock.AXIS, dir.getAxis())
						.end()
						.end();
			}

			if(secondaryLink != null) {
				builder.part()
						.modelFile(secondaryLink)
						.rotationX(xRot)
						.rotationY(yRot)
						.addModel()
						.nestedGroup()
						.condition(ModModularPillarBlock.PROPERTY_BY_DIRECTION.get(dir), 2)
						.end()
						.nestedGroup()
						.useOr()
						.nestedGroup()
						.condition(ModModularPillarBlock.NUM_PRIMARY_LINKS, 2, 3, 4, 5, 6)
						.endNestedGroup()
						.nestedGroup()
						.condition(ModModularPillarBlock.NUM_PRIMARY_LINKS, 0, 1)
						.condition(ModModularPillarBlock.AXIS, otherAxes)
						.endNestedGroup()
						.end()
						.end();
			}
		}
	}

	/**
	 * Creates a BlockState for a modular pillar block. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures Textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures Textures for the primary link as described above
	 * @param secondaryLinkModel The name of the BlockModel of the secondary link
	 * @param secondaryLinkTextures Textures for the secondary link as described above
	 * @param inventoryModel The name of the BlockModel for the inventory model
	 * @param inventoryTextures Textures for the inventory model as described above
	 * @see MultiPartBlockStateBuilder
	 */
	private void modularPillarBlock(ModModularPillarBlock block,
									String coreModel, Map<Tuple<String, String>, Boolean> coreTextures,
									String primaryLinkModel, Map<Tuple<String, String>, Boolean> primaryLinkTextures,
									String secondaryLinkModel, Map<Tuple<String, String>, Boolean> secondaryLinkTextures,
									String inventoryModel, Map<Tuple<String, String>, Boolean> inventoryTextures) {
		ModelFile core = parentedBlockModel(block, "core", coreModel, coreTextures);
		ModelFile primaryLink = parentedBlockModel(block, "link_primary", primaryLinkModel, primaryLinkTextures);
		ModelFile secondaryLink = parentedBlockModel(block, "link_secondary", secondaryLinkModel, secondaryLinkTextures);
		ModelFile inventory = parentedBlockModel(block, "inventory", inventoryModel, inventoryTextures);
		
		MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
		
		for(Direction.Axis axis : Direction.Axis.VALUES)
			builder.part()
				.modelFile(core)
				.rotationX((axis.isHorizontal() ? 90 : 0) % 360)
				.rotationY((axis == Direction.Axis.X ? 90 : 0) % 360)
				.addModel()
				.condition(ModModularPillarBlock.AXIS, axis)
				.end();
		modularPillarMultipart(builder, primaryLink, secondaryLink);
	}

	/**
	 * Creates a BlockState for a modular pillar block. The texture namespaces default to the mod's namespace. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @param secondaryLinkModel The name of the BlockModel of the secondary link
	 * @param secondaryLinkTextures The textures for the secondary link as described above
	 * @param inventoryModel The name of the BlockModel for the inventory model
	 * @param inventoryTextures The textures for the inventory model as described above
	 * @see MultiPartBlockStateBuilder
	 */
	private void modularPillarBlock(ModModularPillarBlock block, 
									String coreModel, List<Tuple<String, String>> coreTextures,
									String primaryLinkModel, List<Tuple<String, String>> primaryLinkTextures,
									String secondaryLinkModel, List<Tuple<String, String>> secondaryLinkTextures,
									String inventoryModel, List<Tuple<String, String>> inventoryTextures) {
		Map<Tuple<String, String>, Boolean> coreTexturesMap = DataGenUtil.convertTextureListToFlaggedTextureMap(coreTextures);
		Map<Tuple<String, String>, Boolean> primaryLinkTexturesMap = DataGenUtil.convertTextureListToFlaggedTextureMap(primaryLinkTextures);
		Map<Tuple<String, String>, Boolean> secondaryLinkTexturesMap = DataGenUtil.convertTextureListToFlaggedTextureMap(secondaryLinkTextures);
		Map<Tuple<String, String>, Boolean> inventoryTexturesMap = DataGenUtil.convertTextureListToFlaggedTextureMap(inventoryTextures);

		modularPillarBlock(block, coreModel, coreTexturesMap, primaryLinkModel, primaryLinkTexturesMap, secondaryLinkModel, secondaryLinkTexturesMap, inventoryModel, inventoryTexturesMap);
	}

	/**
	 * Creates a BlockState for a modular pillar block. The texture namespaces default to the mod's namespace and the secondary link is omitted. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @param inventoryModel The name of the BlockModel for the inventory model
	 * @param inventoryTextures The textures for the inventory model as described above
	 * @see MultiPartBlockStateBuilder
	 */
	private void modularPillarBlock(ModModularPillarBlock block, 
									String coreModel, List<Tuple<String, String>> coreTextures,
									String primaryLinkModel, List<Tuple<String, String>> primaryLinkTextures,
									String inventoryModel, List<Tuple<String, String>> inventoryTextures) {
		modularPillarBlock(block, coreModel, coreTextures, primaryLinkModel, primaryLinkTextures, null, null, inventoryModel, inventoryTextures);
	}

	/**
	 * Creates a BlockState for a modular pillar block. The texture namespaces default to the mod's namespace and the secondary link is omitted. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @param inventoryModel The name of the BlockModel for the inventory model
	 * @param inventoryTextures The textures for the inventory model as described above
	 * @see MultiPartBlockStateBuilder   
	 */
	private void modularPillarBlock(ModModularPillarBlock block,
									String coreModel, Map<Tuple<String, String>, Boolean> coreTextures,
									String primaryLinkModel, Map<Tuple<String, String>, Boolean> primaryLinkTextures,
									String inventoryModel, Map<Tuple<String, String>, Boolean> inventoryTextures) {
		modularPillarBlock(block, coreModel, coreTextures, primaryLinkModel, primaryLinkTextures, null, null, inventoryModel, inventoryTextures);
	}

	/**
	 * Creates a BlockState for a modular pillar block. The texture namespaces default to the mod's namespace and the secondary link and inventory models are omitted. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @see MultiPartBlockStateBuilder
	 */
	private void modularPillarBlock(ModModularPillarBlock block,
									String coreModel, List<Tuple<String, String>> coreTextures,
									String primaryLinkModel, List<Tuple<String, String>> primaryLinkTextures) {
		modularPillarBlock(block, coreModel, coreTextures, primaryLinkModel,primaryLinkTextures, null, null);
	}

	/**
	 * Creates a BlockState for a modular pillar block. The texture namespaces default to the mod's namespace and the secondary link and inventory models are omitted. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreModel The name of the BlockModel of the core
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkModel The name of the BlockModel of the primary link
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @see MultiPartBlockStateBuilder
	 */
	private void modularPillarBlock(ModModularPillarBlock block,
									String coreModel, Map<Tuple<String, String>, Boolean> coreTextures,
									String primaryLinkModel, Map<Tuple<String, String>, Boolean> primaryLinkTextures) {
		modularPillarBlock(block, coreModel, coreTextures, primaryLinkModel,primaryLinkTextures, null, null);
	}


	/**
	 * Creates BlockModels and a BlockState for a thin pillar block, which is based on a modular pillar block and pulls the models from {@code "base/"} in the mod's namespace. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @param secondaryLinkTextures The textures for the secondary link as described above
	 * @param inventoryTextures The textures for the inventory nmodel as described above
	 * @see ModBlockStateProvider#modularPillarBlock(ModModularPillarBlock, String, Map, String, Map, String, Map, String, Map)
	 */
	private void thinPillarBlock(ModModularPillarBlock block,
								 Map<Tuple<String, String>, Boolean> coreTextures,
								 Map<Tuple<String, String>, Boolean> primaryLinkTextures,
								 Map<Tuple<String, String>, Boolean> secondaryLinkTextures,
								 Map<Tuple<String, String>, Boolean> inventoryTextures) {
		modularPillarBlock(block,
				"base/thin_pillar_core", coreTextures,
				"base/thin_pillar_link_primary", primaryLinkTextures,
				"base/thin_pillar_link_secondary", secondaryLinkTextures,
				"base/thin_pillar_inventory", inventoryTextures); 
	}

	/**
	 * Creates BlockModels and a BlockState for a thin pillar block, which is based on a modular pillar block and pulls the models from {@code "base/"} in the mod's namespace. The texture namespaces default to the mod's namespace. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @param secondaryLinkTextures The textures for the secondary link as described above
	 * @param inventoryTextures The textures for the inventory nmodel as described above
	 * @see ModBlockStateProvider#modularPillarBlock(ModModularPillarBlock, String, List, String, List, String, List, String, List)
	 */
	private void thinPillarBlock(ModModularPillarBlock block,
								 List<Tuple<String, String>> coreTextures,
								 List<Tuple<String, String>> primaryLinkTextures,
								 List<Tuple<String, String>> secondaryLinkTextures,
								 List<Tuple<String, String>> inventoryTextures) {
		modularPillarBlock(block,
				"base/thin_pillar_core", coreTextures,
				"base/thin_pillar_link_primary", primaryLinkTextures,
				"base/thin_pillar_link_secondary", secondaryLinkTextures,
				"base/thin_pillar_inventory", inventoryTextures);
	}


	/**
	 * Creates BlockModels and a BlockState for a stick pillar block, which is based on a modular pillar block and pulls the models from {@code "base/"} in the mod's namespace. Also omits the secondary link and inventory models. Textures are encoded in a {@code Map} of {@code Tuple<String, String>} and {@code Boolean} that contain the texture key, texture name and {@code isModded} flag, which determines what namespace to use, for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @see ModBlockStateProvider#modularPillarBlock(ModModularPillarBlock, String, Map, String, Map, String, Map, String, Map)
	 */
	private void stickPillarBlock(ModModularPillarBlock block, Map<Tuple<String, String>, Boolean> coreTextures, Map<Tuple<String, String>, Boolean> primaryLinkTextures) {
		modularPillarBlock(block, "base/stick_pillar_core", coreTextures, "base/stick_pillar_link_primary", primaryLinkTextures);
	}

	/**
	 * Creates BlockModels and a BlockState for a stick pillar block, which is based on a modular pillar block and pulls the models from {@code "base/"} in the mod's namespace. Also omits the secondary link and inventory models. The texture namespaces default to the mod's namespace. Textures are encoded in a {@code List} of {@code Tuple<String, String>} that contain the texture key and texture name for each texture
	 * @param block The instance of the block which BlockState is created
	 * @param coreTextures The textures for the core as described above
	 * @param primaryLinkTextures The textures for the primary link as described above
	 * @see ModBlockStateProvider#modularPillarBlock(ModModularPillarBlock, String, List, String, List, String, List, String, List)
	 */
	private void stickPillarBlock(ModModularPillarBlock block, List<Tuple<String, String>> coreTextures, List<Tuple<String, String>> primaryLinkTextures) {
		modularPillarBlock(block, "base/stick_pillar_core", coreTextures, "base/stick_pillar_link_primary", primaryLinkTextures);
	}


	/**
	 * Creates BlockModels and a BlockState for a thin pillar fruit block, which pulls the models from {@code "base/"} in the mod's namespace. Textures are encoded in a {@code List} of {@code Map<Tuple<String, String>, Boolean>} that contain the texture keys, texture names and {@code isModded} flags, that determine which namespace to use for the textures. The amount of maps in the list must be equal to the maximum age of the fruit plus one
	 * @param block The instance of the block which BlockState is created
	 * @param textures The textures for each stage of the fruit as described above
	 */
	private void thinPillarFruitBlock(ModThinPillarFruitBlock block, List<Map<Tuple<String, String>, Boolean>> textures) {
		if(textures.size() != block.MAX_AGE + 1) return;
		List<ModelFile> models = new ArrayList<>();
		
		int stage = 0;
		for(Map<Tuple<String, String>, Boolean> map : textures) {
			models.add(parentedBlockModel(block, "stage" + stage, "base/thin_pillar_fruit_stage" + stage++, map));
		}

		VariantBlockStateBuilder builder = getVariantBuilder(block);
		builder.forAllStates(state -> 
				ConfiguredModel.builder()
						.modelFile(models.get(state.getValue(ModThinPillarFruitBlock.AGE)))
						.rotationY((int) state.getValue(ModThinPillarFruitBlock.FACING).toYRot())
						.build());
	}


	/**
	 * Creates BlockModels and a BlockState for all blocks
	 */
	@Override
	protected void registerStatesAndModels() {
		// ========[ Resources ]========
		
		ResourceLocation darkdwellerPlanksLocation = blockTexture(ModBlocks.DARKDWELLER_PLANKS.get());
		ResourceLocation placeholderLocation = modLoc("block/placeholder");

		
		
		// ========[ Chapter 1 ]========

		axisBlock((RotatedPillarBlock) ModBlocks.DARKDWELLER_BUNDLE.get(), placeholderLocation, placeholderLocation);
		buttonBlock(((ButtonBlock) ModBlocks.DARKDWELLER_BUTTON.get()), placeholderLocation);
		doorBlockWithRenderType(((DoorBlock) ModBlocks.DARKDWELLER_DOOR.get()), placeholderLocation, placeholderLocation, "cutout");
		fenceBlock((FenceBlock) ModBlocks.DARKDWELLER_FENCE.get(), placeholderLocation);
		fenceGateBlock((FenceGateBlock) ModBlocks.DARKDWELLER_FENCE_GATE.get(), placeholderLocation);
		thinPillarBlock((ModModularPillarBlock) ModBlocks.DARKDWELLER_LOG.get(), List.of(
				new Tuple<>("side", "darkdweller_log_core"),
				new Tuple<>("particle", "darkdweller_log_end")
		), List.of(
				new Tuple<>("side", "darkdweller_log_link_primary"),
				new Tuple<>("end", "darkdweller_log_end")
		), List.of(
				new Tuple<>("side", "darkdweller_log_link_secondary"),
				new Tuple<>("end", "darkdweller_stick_end")	
		), List.of(
				new Tuple<>("side", "darkdweller_log_inventory"),
				new Tuple<>("end", "darkdweller_log_end")
		));
		simpleBlock(ModBlocks.DARKDWELLER_PLANKS.get(), placeholderLocation);
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.DARKDWELLER_PRESSURE_PLATE.get()), placeholderLocation);
		crossBlock(ModBlocks.DARKDWELLER_ROOT.get(), placeholderLocation);
		// Darkdweller Sign
		slabBlock(((SlabBlock) ModBlocks.DARKDWELLER_SLAB.get()), darkdwellerPlanksLocation, placeholderLocation); // First location is a block model, second one is a texture
		stairsBlock(((StairBlock) ModBlocks.DARKDWELLER_STAIRS.get()), placeholderLocation);
		stickPillarBlock((ModModularPillarBlock) ModBlocks.DARKDWELLER_STICK.get(), List.of(
				new Tuple<>("side", "darkdweller_stick_core")
		), List.of(
				new Tuple<>("side", "darkdweller_stick_link_primary"),
				new Tuple<>("end", "darkdweller_stick_end")
		));
		trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DARKDWELLER_TRAPDOOR.get(), placeholderLocation, true, "cutout");
		simpleBlock(ModBlocks.DEEPSLATE_UMBRAL_ORE.get(), mcLoc("block/deepslate"));
		thinPillarFruitBlock((ModThinPillarFruitBlock) ModBlocks.DWELLBERRY.get(), List.of(
				Map.of(
						new Tuple<>("fruit", "placeholder"), true,
						new Tuple<>("particle", "placeholder"), true),
				Map.of(
						new Tuple<>("fruit", "placeholder"), true,
						new Tuple<>("particle", "placeholder"), true),
				Map.of(
						new Tuple<>("fruit", "placeholder"), true,
						new Tuple<>("particle", "placeholder"), true)
		));
		simpleBlock(ModBlocks.ROOTED_SCULK.get());
		variedPlateBlock(ModBlocks.RUNE.get(), Map.of(
				new Tuple<>("wool", "rune1"), true,
				new Tuple<>("wool", "rune2"), true,
				new Tuple<>("wool", "rune3"), true,
				new Tuple<>("wool", "rune4"), true,
				new Tuple<>("wool", "rune5"), true,
				new Tuple<>("wool", "rune6"), true,
				new Tuple<>("wool", "rune7"), true,
				new Tuple<>("wool", "rune8"), true,
				new Tuple<>("wool", "rune9"), true,
				new Tuple<>("wool", "rune10"), true
		));
		axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get(), placeholderLocation, placeholderLocation);
		// Stripped Darkdweller Log
		// Stripped Darkdweller Stick
		simpleBlock(ModBlocks.UMBRAL_BLOCK.get(), placeholderLocation);
		simpleBlock(ModBlocks.UMBRAL_ORE.get(), mcLoc("block/stone"));
	}
}
