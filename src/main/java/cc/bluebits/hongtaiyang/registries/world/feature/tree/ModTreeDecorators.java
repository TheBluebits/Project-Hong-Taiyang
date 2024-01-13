package cc.bluebits.hongtaiyang.registries.world.feature.tree;

import cc.bluebits.hongtaiyang.HongTaiyang;
import cc.bluebits.hongtaiyang.world.feature.tree.darkdweller.DarkdwellerTreeDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Class for registering tree decorators
 */
@SuppressWarnings("MissingJavadoc")
public class ModTreeDecorators {
	/**
	 * The deferred register for tree decorators
	 */
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, HongTaiyang.MOD_ID);

	/**
	 * Method for registering the tree decorators
	 * @param eventBus The event bus
	 */
	public static void register(IEventBus eventBus) {
		TREE_DECORATORS.register(eventBus);
	}


	public static final RegistryObject<TreeDecoratorType<DarkdwellerTreeDecorator>> DARKDWELLER_TREE_DECORATOR = TREE_DECORATORS.register(
			"darkdweller_tree_decorator",
			() -> new TreeDecoratorType<>(DarkdwellerTreeDecorator.CODEC)
	);
}
