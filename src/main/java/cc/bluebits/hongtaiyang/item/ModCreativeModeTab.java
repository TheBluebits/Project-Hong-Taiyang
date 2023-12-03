package cc.bluebits.hongtaiyang.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab CHAPTER1_TAB = new CreativeModeTab("chapter1_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.SOUL_CORE.get());
        }
    };
}
