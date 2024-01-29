package cc.bluebits.hongtaiyang.item;

import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class ModLogBookItem extends WrittenBookItem {


    public ModLogBookItem(Properties pProperties) {
        super(pProperties);


    }

    public void openItemGui(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (pPlayer instanceof ServerPlayer sPlayer) {
            if (resolveBookComponents(pStack, sPlayer.createCommandSourceStack(), sPlayer)) {
                sPlayer.containerMenu.broadcastChanges();
            }
            sPlayer.connection.send(new ClientboundOpenBookPacket(pHand));
        }
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        openItemGui(pPlayer, itemstack, pHand);
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
