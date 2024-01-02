package cc.bluebits.hongtaiyang.item.custom;

import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class ChalkItem extends Item {
    public ChalkItem(Properties pProperties){
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {

        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockpos);
        BlockPos runePos = blockpos.above();
        Block rune = ModBlocks.RUNE.get();
        Player player = pContext.getPlayer();

        if (!blockState.isFaceSturdy(level, blockpos, pContext.getClickedFace())){
            return InteractionResult.FAIL;
        }
        if (!level.isEmptyBlock(runePos)){
            return InteractionResult.FAIL;
        }
        if (player == null){
            return InteractionResult.FAIL;
        }

        level.setBlock(runePos, rune.defaultBlockState(), 3);
        level.gameEvent(pContext.getPlayer(), GameEvent.BLOCK_PLACE, runePos);


        pContext.getItemInHand().hurtAndBreak(1, player, playerHand -> playerHand.broadcastBreakEvent(player.getUsedItemHand())) ;

        return InteractionResult.SUCCESS;
    }



}
