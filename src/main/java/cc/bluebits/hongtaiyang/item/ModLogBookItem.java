package cc.bluebits.hongtaiyang.item;

import cc.bluebits.hongtaiyang.data.LogBookEntries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


/**
 * A book item that can be opened by right-clicking that contains some log entries that guide the player towards the deep dark.
 */
public class ModLogBookItem extends WrittenBookItem {
    /**
     * Creates a new log book item.
     * @param pProperties The properties of the item, passed to the super constructor.
     */
    public ModLogBookItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Opens the book GUI when called on client side
     * @param pLevel The level that is used to determine if this function will be executed client side or not.
     * @param pStack The stack to open the GUI for.
     */
    public void openItemGui(Level pLevel, ItemStack pStack) {
        if(!pLevel.isClientSide) return;

        // Check if book has contents
        CompoundTag nbt = pStack.getTag();
        if(!makeSureTagIsValid(nbt)) {
            LogBookEntries.LogBookEntry entry = LogBookEntries.getRandomEntry(pLevel.getRandom());

            ListTag pagesTag = new ListTag();
            entry.pages().stream().map(StringTag::valueOf).forEach(pagesTag::add);
            if (!entry.pages().isEmpty()) {
                pStack.addTagElement("pages", pagesTag);
            }

            pStack.addTagElement("author", StringTag.valueOf(entry.author()));
            pStack.addTagElement("title", StringTag.valueOf("Old Logbook"));
        }
        
        Minecraft.getInstance().setScreen(new BookViewScreen(new BookViewScreen.WrittenBookAccess(pStack)));
    }

    /**
     * The use interaction of the book. Basically a copy of {@link WrittenBookItem#use(Level, Player, InteractionHand)} but with some minor modifications.
     * @param pLevel The level the book is used in.
     * @param pPlayer The player that uses the book.
     * @param pHand The hand the book is in.
     * @return The result of the interaction.
     */
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        openItemGui(pLevel, itemstack);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return false;
    }
}
