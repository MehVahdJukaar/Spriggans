package net.mehvahdjukaar.spriggans.entity.goals;

import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.GhastEntity;

import java.util.EnumSet;
import java.util.Random;

public class RandomHoverGoal extends Goal {
    private final OwlEntity ghast;

    public RandomHoverGoal(OwlEntity p_i45836_1_) {
        this.ghast = p_i45836_1_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean canUse() {
        MovementController movementcontroller = this.ghast.getMoveControl();
        if (!movementcontroller.hasWanted()) {
            return true;
        } else {
            double d0 = movementcontroller.getWantedX() - this.ghast.getX();
            double d1 = movementcontroller.getWantedY() - this.ghast.getY();
            double d2 = movementcontroller.getWantedZ() - this.ghast.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }

    public boolean canContinueToUse() {
        return false;
    }

    public void start() {
        Random random = this.ghast.getRandom();
        double d0 = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.ghast.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
    }
}