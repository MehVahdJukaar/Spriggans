package net.mehvahdjukaar.spriggans.entity.goals;

import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;

public class OwlLookAtPreyGoal extends Goal {
    protected final MobEntity mob;
    protected Entity lookAt;
    protected final float lookDistance;
    private final int minLookTime;
    private int lookTime;
    protected final float probability;
    protected final EntityPredicate lookAtContext;

    public OwlLookAtPreyGoal(MobEntity user, float distance, int lookTime, float probability) {
        this.mob = user;
        this.minLookTime = lookTime;
        this.lookDistance = distance;
        this.probability = probability;
        this.setFlags(EnumSet.of(Flag.LOOK));

        this.lookAtContext = (new EntityPredicate()).range(distance).allowSameTeam()
                .allowInvulnerable().allowNonAttackable().selector(e -> OwlEntity.TAMED_PREY_SELECTOR.test(e) || OwlEntity.PREY_SELECTOR.test(e));


    }

    public boolean canUse() {
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            }

            this.lookAt = this.mob.level.getNearestLoadedEntity(CreatureEntity.class, this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.mob.getBoundingBox().inflate(this.lookDistance, 3.0D, this.lookDistance));

            return this.lookAt != null;
        }
    }

    public boolean canContinueToUse() {
        if (!this.lookAt.isAlive()) {
            return false;
        } else if (this.mob.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0;
        }
    }

    public void start() {
        this.lookTime = this.minLookTime + this.mob.getRandom().nextInt(10);
    }

    public void stop() {
        this.lookAt = null;
    }

    public void tick() {
        this.mob.getLookControl().setLookAt(this.lookAt.getX(), this.lookAt.getEyeY(), this.lookAt.getZ());
        --this.lookTime;
    }
}
