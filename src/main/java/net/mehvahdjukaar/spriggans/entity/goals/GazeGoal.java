package net.mehvahdjukaar.spriggans.entity.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

import java.util.EnumSet;

public class GazeGoal extends Goal {
    protected final MobEntity mob;
    protected Entity lookAt;
    protected final float lookDistance;
    private final int minLookTime;
    private int lookTime;
    protected final float probability;
    protected final Class<? extends LivingEntity> lookAtType;
    protected final EntityPredicate lookAtContext;

    public GazeGoal(MobEntity user, Class<? extends LivingEntity> target, float distance, int lookTime) {
        this(user, target, distance, lookTime, 0.02F);
    }

    public GazeGoal(MobEntity user, Class<? extends LivingEntity> target, float distance, int lookTime, float probability) {
        this.mob = user;
        this.minLookTime = lookTime;
        this.lookAtType = target;
        this.lookDistance = distance;
        this.probability = probability;
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        if (target == PlayerEntity.class) {
            this.lookAtContext = (new EntityPredicate()).range(distance).allowSameTeam().allowInvulnerable().allowNonAttackable().selector((p_220715_1_) -> EntityPredicates.notRiding(user).test(p_220715_1_));
        } else {
            this.lookAtContext = (new EntityPredicate()).range(distance).allowSameTeam().allowInvulnerable().allowNonAttackable();
        }

    }

    public boolean canUse() {
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTarget() != null) {
                this.lookAt = this.mob.getTarget();
            }

            if (this.lookAtType == PlayerEntity.class) {
                this.lookAt = this.mob.level.getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                this.lookAt = this.mob.level.getNearestLoadedEntity(this.lookAtType, this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.mob.getBoundingBox().inflate(this.lookDistance, 3.0D, this.lookDistance));
            }

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
