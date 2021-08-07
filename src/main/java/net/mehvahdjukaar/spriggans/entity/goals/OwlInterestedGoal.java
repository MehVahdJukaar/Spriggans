package net.mehvahdjukaar.spriggans.entity.goals;


import net.mehvahdjukaar.spriggans.client.entity.OwlEntityRenderer;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.EnumSet;

public class OwlInterestedGoal extends Goal {
    private final OwlEntity owl;
    private PlayerEntity player;
    private final float lookDistance;
    private int lookTime;
    private final EntityPredicate begTargeting;

    public OwlInterestedGoal(OwlEntity owlEntity, float lookDistance) {
        this.owl = owlEntity;
        this.lookDistance = lookDistance;
        this.begTargeting = (new EntityPredicate()).range(lookDistance).allowInvulnerable().allowSameTeam().allowNonAttackable();
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if(!this.owl.isOnGround())return false;
        this.player = this.owl.level.getNearestPlayer(this.begTargeting, this.owl);
        return this.player != null && this.playerHoldingInteresting(this.player);
    }

    public boolean canContinueToUse(){
        if (!this.owl.isOnGround()){
            return false;
        }
        else if (!this.player.isAlive()) {
            return false;
        } else if (this.owl.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.playerHoldingInteresting(this.player);
        }
    }

    public void start() {
        this.owl.setInterested(true);
        this.lookTime = 50 + this.owl.getRandom().nextInt(40);
    }

    public void stop() {
        this.owl.setInterested(false);
        this.player = null;
    }

    public void tick() {
        this.owl.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), this.owl.getMaxHeadYRot(), (float)this.owl.getMaxHeadXRot());
        --this.lookTime;
    }

    private boolean playerHoldingInteresting(PlayerEntity player) {
        for(Hand hand : Hand.values()) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (!this.owl.isTame()){
                return this.owl.isTameFood(itemstack);
            }
            if (this.owl.isFood(itemstack)) {
                return true;
            }
        }

        return false;
    }
}