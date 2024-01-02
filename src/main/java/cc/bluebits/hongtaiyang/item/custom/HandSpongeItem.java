package cc.bluebits.hongtaiyang.item.custom;

import cc.bluebits.hongtaiyang.registries.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HandSpongeItem extends Item {

    private final List<Fluid> wettingFluids;

    public HandSpongeItem(Properties pProperties, List<Fluid> wettingFluids) {
        super(pProperties);
        this.wettingFluids = wettingFluids;
    }


    public static InteractionResult getCauldronInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack) {

        if (!level.isClientSide) {

            Item item = itemStack.getItem();
            player.setItemInHand(interactionHand, new ItemStack(ModItems.WET_HANDSPONGE.get()));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));

            LayeredCauldronBlock.lowerFillLevel(blockState, level, blockPos);
            level.playSound(null, blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);

        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {

        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        } else {
            if (blockhitresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = blockhitresult.getBlockPos();
                if (!pLevel.mayInteract(pPlayer, blockpos)) {
                    return InteractionResultHolder.pass(itemStack);
                }
                FluidState fluidState = pLevel.getFluidState(blockpos);
                BlockState blockState = pLevel.getBlockState(blockpos);
                for (Fluid wettingFluid : wettingFluids) {
                    if (fluidState.is(wettingFluid)) {


                        return InteractionResultHolder.sidedSuccess(new ItemStack(ModItems.WET_HANDSPONGE.get()), pLevel.isClientSide());
                    }

                }
            }

            return InteractionResultHolder.pass(itemStack);


        }
    }
}

   /*@Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        System.out.println("help");
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockpos);
        FluidState fluidState = level.getFluidState(blockpos);
        Player player = pContext.getPlayer();
        InteractionHand interactionHand = pContext.getHand();
        ItemStack itemStack;


        if (player != null) {
            itemStack = player.getItemInHand(interactionHand);
            for (Fluid wettingFluid : wettingFluids) {
                if (fluidState.is(wettingFluid)) {


                    return getInteractionResult(blockState, level, blockpos, player, interactionHand, itemStack, false);
                }

            }
        }


        return InteractionResult.FAIL;
    }*/






