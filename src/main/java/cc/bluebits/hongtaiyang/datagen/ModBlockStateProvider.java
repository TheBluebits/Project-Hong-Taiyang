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

@SuppressWarnings("ALL")
public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, HongTaiyang.MOD_ID, exFileHelper);
	}
	
	
	
	private void simpleBlock(Block block, ResourceLocation texture) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		ModelFile model = models().cubeAll(name, texture);
		simpleBlock(block, model);
	}
	
	private void crossBlock(Block block, ResourceLocation texture) {
		String name = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
		ModelFile model = models().cross(name, texture).renderType("cutout");
		simpleBlock(block, model);
	}
	
	private void crossBlock(Block block) {
		ResourceLocation texture = blockTexture(block);
		crossBlock(block, texture);
	}
	
	
	
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
		// Darkdweller Log
		simpleBlock(ModBlocks.DARKDWELLER_PLANKS.get(), placeholderLocation);
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.DARKDWELLER_PRESSURE_PLATE.get()), placeholderLocation);
		crossBlock(ModBlocks.DARKDWELLER_ROOT.get(), placeholderLocation);
		// Darkdweller Sign
		slabBlock(((SlabBlock) ModBlocks.DARKDWELLER_SLAB.get()), darkdwellerPlanksLocation, placeholderLocation); // First location is a block model, second one is a texture
		stairsBlock(((StairBlock) ModBlocks.DARKDWELLER_STAIRS.get()), placeholderLocation);
		// Darkdweller Sticks
		trapdoorBlockWithRenderType((TrapDoorBlock) ModBlocks.DARKDWELLER_TRAPDOOR.get(), placeholderLocation, true, "cutout");
		simpleBlock(ModBlocks.DEEPSLATE_UMBRAL_ORE.get(), mcLoc("block/deepslate"));
		// Dwellberry
		simpleBlock(ModBlocks.ROOTED_SCULK.get());
		axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_DARKDWELLER_BUNDLE.get(), placeholderLocation, placeholderLocation);
		// Stripped Darkdweller Log
		// Stripped Darkdweller Stick
		simpleBlock(ModBlocks.UMBRAL_BLOCK.get(), placeholderLocation);
		simpleBlock(ModBlocks.UMBRAL_ORE.get(), mcLoc("block/stone"));
	}
}
