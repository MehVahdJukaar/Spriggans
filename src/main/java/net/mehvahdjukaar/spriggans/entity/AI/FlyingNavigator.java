package net.mehvahdjukaar.spriggans.entity.AI;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.pavocado.exoticbirds.entity.EntityOwl;
import net.pavocado.exoticbirds.entity.EntityRobin;
import org.jetbrains.annotations.Nullable;


//from ender zoo
public class FlyingNavigator extends GroundPathNavigator {

    private int totalTicks;
    private int ticksAtLastPos;
    private Vector3d lastPosCheck = new Vector3d(0.0D, 0.0D, 0.0D);

    private boolean forceFlying = false;

    public FlyingNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    public boolean isForceFlying() {
        return forceFlying && !isDone();
    }

    public void setForceFlying(boolean forceFlying) {
        this.forceFlying = forceFlying;
    }



    @Override
    protected Vector3d getTempMobPos() {
        int y = (int) (mob.getBoundingBox().minY + 0.5D);
        return new Vector3d(mob.getX(), y, mob.getZ());
    }

    /*

    public boolean tryFlyToXYZ(double x, double y, double z, double speedIn) {
        Path pathentity = getPathToPos(new BlockPos((double) MathHelper.floor(x), (double) ((int) y), (double) MathHelper.floor(z)));
        return setPath(pathentity, speedIn, true);
    }

    public boolean tryFlyToPos(double x, double y, double z, double speedIn) {
        Path pathentity = getPathToXYZ(x, y, z);
        return setPath(pathentity, speedIn, true);
    }

    public boolean tryFlyToEntityLiving(Entity entityIn, double speedIn) {
        Path pathentity = getPathToEntityLiving(entityIn);
        return pathentity != null ? setPath(pathentity, speedIn, true) : false;
    }*/


    public boolean moveTo(Path path, double speed, boolean forceFlying) {
        if (super.moveTo(path, speed)) {
            // String str = "FlyingPathNavigate.setPath:";
            // for (int i = 0; i < path.getCurrentPathLength(); i++) {
            // PathPoint pp = path.getPathPointFromIndex(i);
            // str += " [" + pp + "]";
            // }
            // Log.info(str);
            ticksAtLastPos = totalTicks;
            lastPosCheck = getTempMobPos();
            this.forceFlying = forceFlying;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveTo(Path path, double speed) {
        return moveTo(path, speed, false);
    }

    @Override
    public void recomputePath() {
        ++totalTicks;
        if (!isDone()) { // if we have a path
            // theEntity.onGround = false;
            // theEntity.isAirBorne = true;
            followThePath(); // follow it
            if (!isDone()) { // if we haven't finished, then set the new move point
                Vector3d targetPos = path.getNextEntityPos(mob);
                if (targetPos == null) {
                    return;
                }
                double y = targetPos.y;
                if (forceFlying) {
                    double aboveBlock = y - (int) y;
                    if (aboveBlock < 0.10) {
                        y = (int) y + 0.10;
                    }
                }
                mob.getMoveControl().setWantedPosition(targetPos.x, y, targetPos.z, speedModifier);
            }
        }

    }

    @Override
    protected void followThePath() {

        Vector3d entPos = getTempMobPos();
        float entWidthSq = mob.getBbWidth() * mob.getBbWidth();
        if (path.getNextNodeIndex() == path.getNodeCount() - 1 && mob.isOnGround()) {
            entWidthSq = 0.01f; // we need to be right on top of the last point if on
            // the ground so we don't hang on ledges
        }

        Vector3d targetPos = path.getEntityPosAtNode(mob, path.getNextNodeIndex());

        double distToCurrTargSq = entPos.distanceToSqr(targetPos);
        if (distToCurrTargSq < entWidthSq) {
            path.advance();
        }
        // starting six points ahead (or the end point) see if we can go directly
        // there
        int i = 6;
        for (int j = Math.min(path.getNextNodeIndex() + i, path.getNodeCount() - 1); j > path.getNextNodeIndex(); --j) {
            targetPos = path.getEntityPosAtNode(mob, j);
            if (targetPos.distanceToSqr(entPos) <= 36.0D && canMoveDirectly(entPos, targetPos, 0, 0, 0)) {
                path.setNextNodeIndex(j);
                break;
            }
        }
        doStuckDetection(entPos);
    }

/*
    @Override
    protected boolean canMoveDirectly(Vector3d startPos, Vector3d endPos, int sizeX, int sizeY, int sizeZ) {

        Vector3d target = new Vector3d(endPos.x, endPos.y + entity.height * 0.5D, endPos.z);
        if (!isClear(startPos, target)) {
            return false;
        }
        AxisAlignedBB bb = mob.getBoundingBox();
        startPos = new Vector3d(bb.maxX, bb.maxY, bb.maxZ);
        if (!isClear(startPos, target)) {
            return false;
        }
        return true;

    }

    private boolean isClear(Vector3d startPos, Vector3d target) {
        RayTraceResult hit = level.clipWithInteractionOverride(startPos, target, true, true, false);
        return hit == null || hit.getType() == RayTraceResult.Type.MISS;
    }
*/
    @Override
    protected void doStuckDetection(Vector3d positionVec3) {

        if (totalTicks - ticksAtLastPos > 10 && positionVec3.distanceToSqr(lastPosCheck) < 0.0625) {
            //clearPathEntity();
            ticksAtLastPos = totalTicks;
            lastPosCheck = positionVec3;
            return;
        }

        if (totalTicks - ticksAtLastPos > 50) {
            if (positionVec3.distanceToSqr(lastPosCheck) < 2.25D) {
                //clearPathEntity();
            }

            ticksAtLastPos = totalTicks;
            lastPosCheck = positionVec3;
        }
    }



}
