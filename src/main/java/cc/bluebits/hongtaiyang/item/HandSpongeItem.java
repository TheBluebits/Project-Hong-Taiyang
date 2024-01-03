package cc.bluebits.hongtaiyang.item;

import cc.bluebits.hongtaiyang.registries.block.ModBlocks;
import cc.bluebits.hongtaiyang.registries.sound.ModSounds;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
    private final int barColor;
    private final boolean forceBar;


    public HandSpongeItem(Properties pProperties, List<Fluid> wettingFluids, int barColor, boolean forceBar) {
        super(pProperties);
        this.wettingFluids = wettingFluids;
        this.barColor = barColor;
        this.forceBar = forceBar;
    }


    @Override
    public int getBarColor(@NotNull ItemStack pStack) {
        return barColor;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack pStack) {
        return forceBar;
    }



    public static InteractionResult getCauldronInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (!level.isClientSide) {
            Item item = itemStack.getItem();

            if (itemStack.getDamageValue() <= 0) {
                return InteractionResult.PASS;
            }

            itemStack.setDamageValue(0);
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

        if (itemStack.getDamageValue() <= 0) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (blockhitresult.getType() != HitResult.Type.MISS) {
            if (blockhitresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = blockhitresult.getBlockPos();

                if (!pLevel.mayInteract(pPlayer, blockpos)) {
                    return InteractionResultHolder.pass(itemStack);
                }

                FluidState fluidState = pLevel.getFluidState(blockpos);

                for (Fluid wettingFluid : wettingFluids) {
                    if (fluidState.is(wettingFluid)) {
                        //TODO: no work wif cretif mod plz fix thx xoxo
                        ItemStack newItemStack = new ItemStack(itemStack.getItem());
                        newItemStack.setDamageValue(0);
                        return InteractionResultHolder.sidedSuccess(newItemStack, pLevel.isClientSide());
                    }
                }
            }
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block clickedBlock = blockState.getBlock();
        Player player = pContext.getPlayer();
        Block rune = ModBlocks.RUNE.get();

        if (player == null) {
            return InteractionResult.PASS;
        }
        ItemStack itemStack = player.getItemInHand(pContext.getHand());

        if (getDamage(itemStack) >= getMaxDamage(itemStack)) {
            return InteractionResult.PASS;
        }
        if (clickedBlock != rune) {
            return InteractionResult.PASS;
        }

        level.destroyBlock(blockPos, false);
        level.gameEvent(player, GameEvent.BLOCK_DESTROY, blockPos);
        level.playSeededSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), ModSounds.HANDSPONGE_SCRUB.get(), SoundSource.PLAYERS, 1f, 1f, 0);

        pContext.getItemInHand().hurt(1, player.getRandom(), null);
        return InteractionResult.SUCCESS;
    }
}