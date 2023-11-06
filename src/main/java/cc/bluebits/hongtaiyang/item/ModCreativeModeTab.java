package cc.bluebits.hongtaiyang.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab HONGTAIYANG_TAB = new CreativeModeTab("hongtaiyangtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.UMBRAL_GEM.get());
        }
    };
}
