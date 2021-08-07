package net.mehvahdjukaar.spriggans.entity.goals;

import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;

import java.util.EnumSet;
import java.util.List;

public class FollowParentFlyingGoal  extends Goal {
    private final AnimalEntity childAnimal;
    private AnimalEntity parentAnimal;
    private final double speedModifier;
    private int timeToRecalcPath;
    private float range = 8F;
    private float minDist = 3F;
    public FollowParentFlyingGoal(AnimalEntity child, double speed, float range, float minDist) {
        this.childAnimal = child;
        this.speedModifier = speed;
        this.range = range;
        this.minDist = minDist;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.childAnimal.getAge() >= 0) {
            return false;
        } else {
            List<AnimalEntity> entityList = this.childAnimal.level.getEntitiesOfClass(this.childAnimal.getClass(), this.childAnimal.getBoundingBox().inflate(range, range * 0.5D, range));
            AnimalEntity parent = null;
            double d0 = Double.MAX_VALUE;

            for (AnimalEntity a : entityList) {
                if (a.getAge() >= 0) {
                    double distance = this.childAnimal.distanceToSqr(a);
                    if (distance <= d0) {
                        d0 = distance;
                        parent = a;
                    }
                }
            }

            if (parent == null) {
                return false;
            } else if (d0 < minDist * minDist) {
                return false;
            } else {
                this.parentAnimal = parent;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.childAnimal.getAge() >= 0) {
            return false;
        } else if (!this.parentAnimal.isAlive()) {
            return false;
        } else {
            double dist = this.childAnimal.distanceToSqr(this.parentAnimal);
            return dist >= minDist * minDist && dist <= range * range;
        }
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.parentAnimal = null;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            PathNavigator nav = this.childAnimal.getNavigation();
            Path path = nav.createPath(this.parentAnimal, 2);
            if(path != null) nav.moveTo(path, this.speedModifier);
        }
    }
}