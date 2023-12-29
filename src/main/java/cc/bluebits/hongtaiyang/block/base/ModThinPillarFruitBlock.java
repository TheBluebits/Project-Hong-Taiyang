package cc.bluebits.hongtaiyang.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModThinPillarFruitBlock extends HorizontalDirectionalBlock implements BonemealableBlock {
	public static final int MAX_AGE = 2;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;



	protected final Supplier<Block> SURVIVES_ON;
	protected final VoxelShape[] SHAPES = makeShapes();

	/**
	 * Generates all possible shapes.
	 * @return An array containing all possible shapes organized by age first, then facing
	 */
	protected VoxelShape[] makeShapes() { return new VoxelShape[] {}; }

	

	public ModThinPillarFruitBlock(Properties pProperties, Supplier<Block> survivesOn) {
		super(pProperties);
		this.SURVIVES_ON = survivesOn;
		
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(FACING, Direction.NORTH)
				.setValue(AGE, 0));
	}
	
	

	public static int getShapeIndex(int age, Direction facing) {
		int nShapesPerAge = (int) Direction.Plane.HORIZONTAL.stream().count();
		int ageOffset = nShapesPerAge * age;
		
		int dirIndex = 0;
		for(Direction dir : Direction.Plane.HORIZONTAL) {
			if(dir == facing) break;
			dirIndex++;
		}
		
		return ageOffset + dirIndex;
	}

	public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
		return SHAPES[getShapeIndex(pState.getValue(AGE), pState.getValue(FACING))];
	}



	/**
	 * @return whether this block needs random ticking.
	 */
	public boolean isRandomlyTicking(BlockState pState) {
		return pState.getValue(AGE) < 2;
	}

	/**
	 * Performs a random tick on a block.
	 */
	public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
		int i = pState.getValue(AGE);
		if (i < 2 && ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pLevel.random.nextInt(5) == 0)) {
			pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 3);
			ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
		}
	}

	public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
		BlockState blockstate = pLevel.getBlockState(pPos.relative(pState.getValue(FACING)));
		return blockstate.is(SURVIVES_ON.get());
	}
	
	

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		BlockState blockState = this.defaultBlockState();
		LevelReader levelReader = pContext.getLevel();
		BlockPos pos = pContext.getClickedPos();

		for(Direction direction : pContext.getNearestLookingDirections()) {
			if (direction.getAxis().isHorizontal()) {
				blockState = blockState.setValue(FACING, direction);
				if (blockState.canSurvive(levelReader, pos)) {
					
					return blockState;
				}
			}
		}

		return null;
	}

	/**
	 * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific direction passed in.
	 */
	public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
		return pFacing == pState.getValue(FACING) && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
	}

	/**
	 * @return whether bone meal can be used on this block
	 */
	public boolean isValidBonemealTarget(@NotNull LevelReader pLevel, @NotNull BlockPos pPos, BlockState pState, boolean pIsClient) {
		return pState.getValue(AGE) < MAX_AGE;
	}

	public boolean isBonemealSuccess(@NotNull Level pLevel, @NotNull RandomSource pRandom, @NotNull BlockPos pPos, @NotNull BlockState pState) {
		return true;
	}

	public void performBonemeal(ServerLevel pLevel, @NotNull RandomSource pRandom, @NotNull BlockPos pPos, BlockState pState) {
		pLevel.setBlock(pPos, pState.setValue(AGE, pState.getValue(AGE) + 1), 3);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(FACING, AGE);
	}

	public boolean isPathfindable(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull PathComputationType pType) {
		return false;
	}
}
