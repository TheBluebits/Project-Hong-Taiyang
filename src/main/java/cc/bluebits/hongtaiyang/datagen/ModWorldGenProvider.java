package cc.bluebits.hongtaiyang.datagen;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.registries.world.feature.ModBiomeModifiers;
import cc.bluebits.hongtaiyang.registries.world.feature.ModConfiguredFeatures;
import cc.bluebits.hongtaiyang.registries.world.feature.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

/**
 * A class to generate the world generation data pack
 */
public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
	/**
	 * The builder for the registry set
	 */
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
			.add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

	/**
	 * Constructs a {@code ModWorldGenProvider}
	 * @param output The output, passed to the super constructor
	 * @param registries The registries, passed to the super constructor
	 */
	public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Collections.singleton(HongTaiyang.MOD_ID));
	}
}
