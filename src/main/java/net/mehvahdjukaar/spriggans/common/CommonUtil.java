package net.mehvahdjukaar.spriggans.common;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class CommonUtil {
    private static final Random random = new Random();

    public static final BiPredicate<World, BlockPos> SPRIGGAN_INTEREST_PREDICATE = (world,pos) ->
            world.getBlockState(pos).getBlock().is(ModTags.SPRIGGAN_INTEREST);

    public static final BiPredicate<World, BlockPos> GRASS_PREDICATE = (world,pos) -> {
        BlockState state = world.getBlockState(pos);
        if(state.getBlock() == Blocks.DIRT){
            return canBeGrass(state,world,pos);
        }
        return false;
    };

    public static final BiFunction<World, BlockPos, Boolean> TURN_TO_GRASS = (world, pos) -> {
        world.setBlockAndUpdate(pos,Blocks.GRASS_BLOCK.defaultBlockState());
        return true;
    };

    public static final BiPredicate<World, BlockPos> GROWABLE_PREDICATE = (world,pos) -> {
        BlockState state = world.getBlockState(pos);
        Block b = state.getBlock();
        return b instanceof IGrowable && (b.is(BlockTags.SAPLINGS) || b instanceof CropsBlock || b == Blocks.SWEET_BERRY_BUSH);
    };

    public static final BiFunction<World, BlockPos, Boolean> USE_BONEMEAL = (world, pos) -> {
        BlockState state = world.getBlockState(pos);
        ((IGrowable)state.getBlock()).performBonemeal((ServerWorld) world,random,pos,state);
        return true;
    };

    private static boolean canBeGrass(BlockState state, IWorldReader world, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = world.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(world, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(world, blockpos));
            return i < world.getMaxLightLevel();
        }
    }

    public static Optional<BlockPos> findNearestBlock(Entity e, int maxRadius, float minRadius, BiPredicate<World,BlockPos> predicate){
        return findNearestBlock(e.blockPosition(),e.level,maxRadius,minRadius,predicate);
    }

    public static Optional<BlockPos> findNearestBlock(BlockPos center, World world, int maxRadius, float minRadius, BiPredicate<World,BlockPos> predicate){

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; (double)i <= maxRadius; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < maxRadius; ++j) {
                for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        mutable.setWithOffset(center, k, i - 1, l);
                        if (center.closerThan(mutable, maxRadius+0.5) && !center.closerThan(mutable, minRadius) && predicate.test(world,mutable)){
                            return Optional.of(mutable);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
