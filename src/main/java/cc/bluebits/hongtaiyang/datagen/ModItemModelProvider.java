package cc.bluebits.hongtaiyang.datagen;


import cc.bluebits.hongtaiyang.HongTaiyang;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, HongTaiyang.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HongTaiyang.MOD_ID, "item/" + item.getId().getPath()));

    }
}
