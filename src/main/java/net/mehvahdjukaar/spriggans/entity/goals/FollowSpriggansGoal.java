package net.mehvahdjukaar.spriggans.entity.goals;

import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.mehvahdjukaar.spriggans.entity.WoodlingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

import java.util.EnumSet;
import java.util.List;

public class FollowSpriggansGoal extends Goal {

    private final WoodlingEntity woodling;
    private SprigganEntity parent;
    private final double speedModifier;
    private int timeToRecalcPath;
    private int followDuration;

    public FollowSpriggansGoal(WoodlingEntity woodlingEntity, double speed) {
        this.woodling = woodlingEntity;
        this.speedModifier = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));

    }


    @Override
    public boolean canUse() {
        if(woodling.followCooldown==0 && woodling.getRandom().nextFloat()<0.3 && !woodling.isTame()) {


            List<SprigganEntity> list = this.woodling.level.getEntitiesOfClass(SprigganEntity.class, this.woodling.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            SprigganEntity animalentity = null;
            double d0 = Double.MAX_VALUE;

            for (SprigganEntity animalentity1 : list) {
                double d1 = this.woodling.distanceToSqr(animalentity1);
                if (!(d1 > d0)) {
                    d0 = d1;
                    animalentity = animalentity1;
                }
            }

            if (animalentity == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parent = animalentity;
                return true;
            }
        }
        return false;
    }

    public boolean canContinueToUse() {
        if(followDuration <= 0){
            return false;
        }
        if (!this.parent.isAlive() || (this.parent).isAngry()) {
            return false;
        } else {
            double d0 = this.woodling.distanceToSqr(this.parent);
            boolean stop = !(d0 > 32.0D);
            return stop;
        }
    }

    public void start() {
         this.followDuration = 600+woodling.getRandom().nextInt(500);
        this.timeToRecalcPath = 0;
    }

    public void stop() {
        this.woodling.followCooldown = 1800+woodling.getRandom().nextInt(1000);
        this.parent = null;
    }

    public void tick() {

        double d0 = this.woodling.distanceToSqr(this.parent);
        if(d0 < 7.0D){
            this.woodling.getLookControl().setLookAt(this.parent.getX(), this.parent.getEyeY(), this.parent.getZ());
            //this.woodling.lookAt(parent, 30, 30);
        }
        else if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            this.woodling.getNavigation().moveTo(this.parent, this.speedModifier);
        }

        this.followDuration--;

    }
}