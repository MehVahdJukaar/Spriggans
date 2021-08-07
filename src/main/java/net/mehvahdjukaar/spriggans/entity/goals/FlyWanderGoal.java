package net.mehvahdjukaar.spriggans.entity.goals;

import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class FlyWanderGoal extends WaterAvoidingRandomWalkingGoal
{
    private final OwlEntity dragon;

    public FlyWanderGoal(OwlEntity dragon, double speed, float probability)
    {
        super(dragon, speed, probability);
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));

        this.dragon = dragon;
    }

    public FlyWanderGoal(OwlEntity dragon, double speed)
    {
        this(dragon, speed, 0.001f);
    }

    @Override
    public boolean canUse()
    {
        if (dragon.isInSittingPose()) return false;
        if (dragon.canBeControlledByRider()) return false;
        if (!dragon.isFlying()) return false;
        Vector3d vec3d = getPosition();
        if (vec3d != null)
        {
            this.wantedX = vec3d.x;
            this.wantedY = vec3d.y;
            this.wantedZ = vec3d.z;
            this.forceTrigger = false;
            return true;
        }

        return false;
    }

    public double getAltitude()
    {
        BlockPos.Mutable pos = dragon.blockPosition().mutable().move(0, -1, 0);
        while (pos.getY() > 0 && !dragon.level.getBlockState(pos).canOcclude()) pos.setY(pos.getY() - 1);
        return dragon.getY() - pos.getY();
    }

    @Override
    public Vector3d getPosition()
    {
        Vector3d position = null;

        if (dragon.isFlying() || (!dragon.isLeashed() && dragon.getRandom().nextFloat() <= probability + 0.02))
        {
            if ((!dragon.level.isDay()) || dragon.getRandom().nextFloat() <= probability)
                position = RandomPositionGenerator.getLandPos(dragon, 20, 25);
            else
            {
                Vector3d vec3d = dragon.getLookAngle();
                if (!dragon.isWithinRestriction())
                    vec3d = Vector3d.atLowerCornerOf(dragon.getRestrictCenter()).subtract(dragon.position()).normalize();

                int yOffset = getAltitude() > 40? 10 : 0;
                position = RandomPositionGenerator.getAboveLandPos(dragon, 50, 30, vec3d, (float) (Math.PI / 2), 10, yOffset);
            }
            if (position != null && position.y > dragon.getY() + dragon.getBbHeight() && !dragon.isFlying()){} //dragon.setFlying(true);
        }

        return position == null? super.getPosition() : position;
    }
}
