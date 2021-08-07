package net.mehvahdjukaar.spriggans.entity.goals;


import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.ShoulderRidingEntity;

public class LandOnEntityShoulderGoal extends Goal {
    private final ShoulderRidingEntity entity;
    private CreatureEntity owner;
    private boolean isSittingOnShoulder;

    public LandOnEntityShoulderGoal(ShoulderRidingEntity me) {
        this.entity = me;
    }

    @Override
    public boolean canUse() {
        return !entity.isTame() && this.entity.canSitOnShoulder() && !this.entity.isOrderedToSit();
    }

    @Override
    public boolean isInterruptable() {
        return !this.isSittingOnShoulder;
    }

    public void start() {
        this.isSittingOnShoulder = false;
    }

    public void tick() {
        if (!this.isSittingOnShoulder && !this.entity.isInSittingPose() && !this.entity.isLeashed()) {
            if (this.entity.getBoundingBox().intersects(this.owner.getBoundingBox())) {
                //this.isSittingOnShoulder = this.entity.setEntityOnShoulder(this.owner);
            }

        }
    }
}
