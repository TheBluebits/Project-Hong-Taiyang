package cc.bluebits.hongtaiyang.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab HONGTAIYANG_TAB = new CreativeModeTab("hongtaiyangtab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.SOUL_CORE.get());
        }
    };
}
