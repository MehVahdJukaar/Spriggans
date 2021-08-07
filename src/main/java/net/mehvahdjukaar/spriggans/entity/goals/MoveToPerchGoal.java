package net.mehvahdjukaar.spriggans.entity.goals;

import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import java.util.List;

public class MoveToPerchGoal extends MoveToBlockGoal {
    private final OwlEntity owl;
    private final int minPerchHeight;
    private final int maxPerchHeight;
    private final int searchRange = 8;
    private final int searchAttempts = 30;
    private boolean reachedTarget2 = false;

    public MoveToPerchGoal(OwlEntity owl, int minPerchHeight, int maxPerchHeight) {
        super(owl, 1, 8);
        this.owl = owl;
        this.minPerchHeight = minPerchHeight;
        this.maxPerchHeight = maxPerchHeight;
    }

    @Override
    public boolean canUse() {
        if(this.owl.isTame() && this.owl.isOrderedToSit())return false;
        if(owl.isSleeping())return false;
        if(!owl.isOnGround()&&owl.isBaby())return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(this.reachedTarget2 && owl.isOnGround() && this.owl.getDeltaMovement().length()<0.1 && owl.getRandom().nextFloat()<0.07)return false;
        return this.tryTicks <= 300 && this.isValidTarget(this.mob.level, this.blockPos);
    }

    @Override
    public double acceptedDistance() {
        return 0.8D;
    }

    @Override
    public void start() {
        super.start();
        this.owl.setInSittingPose(false);
    }

    @Override
    public void stop() {
        super.stop();
        if(owl.level.isDay())
            owl.setSleeping(this.reachedTarget2);
        //this.owl.setInSittingPose(this.isReachedTarget());
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 30 == 0;
    }

    @Override
    public void tick() {
        BlockPos blockpos = this.getMoveToTarget();
        if (!blockpos.closerThan(this.mob.position().add(0,0.5,0), this.acceptedDistance())) {
            this.reachedTarget2 = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                PathNavigator navigator = this.mob.getNavigation();
                boolean baby = owl.isBaby();
                navigator.moveTo(navigator.createPath((double)((float)blockpos.getX()) + 0.5, blockpos.getY()+0.5f, (double)((float)blockpos.getZ()) + 0.5f,0), this.speedModifier*(baby?0.45f:1));
                //this.mob.getNavigation().moveTo((double)((float)blockpos.getX()) + 0.5D, blockpos.getY()+1, (double)((float)blockpos.getZ()) + 0.5D, this.speedModifier);
            }
        } else {
            this.reachedTarget2 = true;
            --this.tryTicks;
        }
    }

    //idk why I need this damn
    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos.above(1);
    }

    @Override
    protected boolean findNearestBlock() {
        BlockPos targetPos = findRandomPerchRecursive();
        if (targetPos != null) {
            //List<CreatureEntity> others = owl.level.getLoadedEntitiesOfClass(CreatureEntity.class, new AxisAlignedBB(targetPos).inflate(2));
            //if (others.size() != 0) eturn false;

            this.blockPos = targetPos;
            return true;
        }
        return false;
    }


    @Override
    protected boolean isValidTarget(IWorldReader world, BlockPos pos) {
        BlockState feetState = world.getBlockState(pos);
        if (!feetState.getMaterial().isSolid() || feetState.getCollisionShape(world, pos).isEmpty()) {
            return false;
        }
        BlockPos above = pos.above();
        return world.getBlockState(above).getCollisionShape(world, above).isEmpty();
    }



    private BlockPos findRandomPerchRecursive() {
        for (int i = 0; i < searchAttempts; i++) {
            BlockPos res = findRandomPerch();
            if (res != null) {
                  return res;
            }
        }
        return null;
    }


    private BlockPos findRandomPerch() {

        BlockPos entityPos = mob.blockPosition();
        World world = mob.level;

        int range = searchRange - (this.owl.isBaby()?4:0);

        int x = entityPos.getX()  - range + (world.random.nextInt(range + 1) * 2);
        int z = entityPos.getZ() - range + (world.random.nextInt(range + 1) * 2);

        int entityY = entityPos.getY()-1;
        int y = Math.min(entityY + maxPerchHeight +1, world.getHeight(Heightmap.Type.MOTION_BLOCKING,x,z));

        int minY = entityY + minPerchHeight;

        boolean canLand = false;
        BlockPos.Mutable pos = new BlockPos.Mutable(x,y,z);
        while (!canLand) {
            --y;
            if (y < minY) {
                return null;
            }
            pos.set(x, y, z);
            canLand = this.isValidTarget(world, pos) && this.owl.isWithinRestriction(pos);
        }
        world.setBlockAndUpdate(pos,Blocks.EMERALD_BLOCK.defaultBlockState());
        return pos;
    }

}
