package cc.bluebits.hongtaiyang.item;

import cc.bluebits.hongtaiyang.registries.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.List;

public class HandSpongeItem extends Item {
    private final List<Block> affectedBlocks;
    private final List<Fluid> wettingFluids;
    private final int barColor;
    private final boolean forceBar;


    public HandSpongeItem(Properties pProperties, List<Block> affectedBlocks, List<Fluid> wettingFluids, int barColor, boolean forceBar) {
        super(pProperties);
        this.affectedBlocks = affectedBlocks;
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



    @SuppressWarnings("unused")
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
    
    
    
    public @NotNull InteractionResult fillSponge(ItemStack pItemStack, Level pLevel, Player pPlayer) {
        if (pItemStack.getDamageValue() <= 0) {
            return InteractionResult.PASS;
        }

        BlockHitResult blockHitResult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = blockHitResult.getBlockPos();
                if (!pLevel.mayInteract(pPlayer, blockpos)) {
                    return InteractionResult.PASS;
                }

                FluidState fluidState = pLevel.getFluidState(blockpos);

                for (Fluid wettingFluid : wettingFluids) {
                    if (fluidState.is(wettingFluid)) {
                        pItemStack.setDamageValue(0);
                        sheeeesh(pPlayer, pLevel);

                        return InteractionResult.sidedSuccess(pLevel.isClientSide());
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }
    
    public @NotNull InteractionResult breakRune(UseOnContext pContext, ItemStack itemStack, Level level, Player player) {
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        
        if (getDamage(itemStack) >= getMaxDamage(itemStack)) {
            return InteractionResult.PASS;
        }
        
        for(Block block : affectedBlocks) {
            if (clickedState.is(block)) {
                level.destroyBlock(clickedPos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, clickedPos);
                level.playSeededSound(null, clickedPos.getX(), clickedPos.getY(), clickedPos.getZ(), ModSounds.HANDSPONGE_SCRUB.get(), SoundSource.PLAYERS, 1f, 1f, 0);

                pContext.getItemInHand().hurt(1, player.getRandom(), null);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        if (player == null) { return InteractionResult.PASS; }
        ItemStack itemStack = player.getItemInHand(pContext.getHand());

        InteractionResult result = fillSponge(itemStack, level, player);
        if(result != InteractionResult.PASS) return result;
        
        return breakRune(pContext, itemStack, level, player);
    }

    @SuppressWarnings("unused")
    public void sheeeesh(Entity entity, Level level) {
        Vec3 handPos = entity.getRopeHoldPosition(0f);
        //Vec3 playerPos = player.position();
        Direction entityDirection = entity.getDirection();
        Quaternionf entityRotation = entityDirection.getRotation();

        Vec3 entityForward = entity.getForward();
        double particlePosX = handPos.x + 0.2d * (double) entityRotation.x;
        double particlePosY = handPos.y - 0.2d;
        double particlePosZ = handPos.z + 0.2d * (double) entityRotation.z;
        level.addParticle(ParticleTypes.FALLING_WATER, particlePosX, particlePosY, particlePosZ, 0d, 0d, 0d);


    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pIsSelected) {
            return;

        }
        if (pStack.getDamageValue() == pStack.getMaxDamage()) {
            return;
        }
        sheeeesh(pEntity, pLevel);

    }
}