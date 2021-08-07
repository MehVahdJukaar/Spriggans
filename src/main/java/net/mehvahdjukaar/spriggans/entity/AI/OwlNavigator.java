package net.mehvahdjukaar.spriggans.entity.AI;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

public class OwlNavigator extends GroundPathNavigator {

    private MobEntity mob;

    public OwlNavigator(MobEntity mob, World world) {
        super(mob, world);
        this.mob = mob;
    }

    public void tick() {
        ++this.tick;
    }

    public boolean moveTo(double x, double y, double z, double speedIn) {
        mob.getMoveControl().setWantedPosition(x, y, z, speedIn);
        return true;
    }

    public boolean moveTo(Entity entityIn, double speedIn) {
        mob.getMoveControl().setWantedPosition(entityIn.getX(), entityIn.getY(), entityIn.getZ(), speedIn);
        return true;
    }

}