package cc.bluebits.hongtaiyang.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Class for generating recipes
 */
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
	/**
	 * Constructs a {@code ModRecipeProvider}
	 * @param pOutput The pack output, passed to the super constructor
	 */
	public ModRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {

	}
}