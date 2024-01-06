package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, HongTaiyang.MOD_ID, exFileHelper);
	}
	
	private void texturedBlock(Block block, ResourceLocation texture) {
		ModelFile placeholderModel = models().cubeAll(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(), texture);
		simpleBlock(block, placeholderModel);
	}
	
	private void placeholderBlock(Block block) {
		texturedBlock(block, modLoc("block/placeholder"));
	}
	
	@Override
	protected void registerStatesAndModels() {
		ResourceLocation darkdwellerPlanksLocation = blockTexture(ModBlocks.DARKDWELLER_PLANKS.get());
		ResourceLocation placeholderLocation = modLoc("block/placeholder");

		axisBlock((RotatedPillarBlock) ModBlocks.DARKDWELLER_BUNDLE.get(), placeholderLocation, placeholderLocation);
		buttonBlock(((ButtonBlock) ModBlocks.DARKDWELLER_BUTTON.get()), placeholderLocation);
		doorBlockWithRenderType(((DoorBlock) ModBlocks.DARKDWELLER_DOOR.get()), placeholderLocation, placeholderLocation, "cutout");
		fenceBlock((FenceBlock) ModBlocks.DARKDWELLER_FENCE.get(), placeholderLocation);
		fenceGateBlock((FenceGateBlock) ModBlocks.DARKDWELLER_FENCE_GATE.get(), placeholderLocation);
		// Darkdweller Log
		placeholderBlock(ModBlocks.DARKDWELLER_PLANKS.get());
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.DARKDWELLER_PRESSURE_PLATE.get()), placeholderLocation);
		// Darkdweller Root
		// Darkdweller Sign
		slabBlock(((SlabBlock) ModBlocks.DARKDWELLER_SLAB.get()), darkdwellerPlanksLocation, placeholderLocation); // First location is a block model, second one is a texture
		stairsBlock(((StairBlock) ModBlocks.DARKDWELLER_STAIRS.get()), placeholderLocation);
		// Darkdweller Sticks
		trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DARKDWELLER_TRAPDOOR.get(), placeholderLocation, true, "cutout");
		texturedBlock(ModBlocks.DEEPSLATE_UMBRAL_ORE.get(), mcLoc("block/deepslate"));
		// Dwellberry
		simpleBlock(ModBlocks.ROOTED_SCULK.get());
		axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get(), placeholderLocation, placeholderLocation);
		// Stripped Darkdweller Log
		// Stripped Darkdweller Stick
		placeholderBlock(ModBlocks.UMBRAL_BLOCK.get());
		texturedBlock(ModBlocks.UMBRAL_ORE.get(), mcLoc("block/stone"));
	}
}
