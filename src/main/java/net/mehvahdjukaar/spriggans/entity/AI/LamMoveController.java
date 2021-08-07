package net.mehvahdjukaar.spriggans.entity.AI;

import its_meow.betteranimalsplus.common.entity.ai.LammerMoveHelper;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

//from bap
public class LamMoveController extends MovementController {

    private boolean shouldLookAtTarget;
    private boolean needsYSupport;

    private final int maxTurn;

    public LamMoveController(OwlEntity lam) {

        super(lam);
        maxTurn = 180;

    }

    //vanilla parrot
    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {
            this.operation = MovementController.Action.WAIT;
            this.mob.setNoGravity(true);
            double diffX = this.wantedX - this.mob.getX();
            double diffY = this.wantedY - this.mob.getY();
            double diffZ = this.wantedZ - this.mob.getZ();
            Vector3d vector3d = new Vector3d(diffX, diffY, diffZ);
            double dist = vector3d.lengthSqr();


            if (dist < (double) 2.5000003E-7F) {
                mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5D));
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }

            float speed;
            if (this.mob.isOnGround()) {
                speed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
                speed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            }
            this.mob.setSpeed(speed);

            float wantedYaw = (float) (MathHelper.atan2(diffZ, diffX) * (double) (180F / (float) Math.PI)) - 90.0F;
            this.mob.yRot = this.rotlerp(this.mob.yRot, wantedYaw, 90.0F);

            float flapProgress = ((OwlEntity)this.mob).flapSpeed*0.011f;

            //double d4 = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            //float f2 = (float) (-(MathHelper.atan2(diffY, d4) * (double) (180F / (float) Math.PI)));
            //this.mob.xRot = this.rotlerp(this.mob.xRot, f2, (float) this.maxTurn);
            //this.mob.setYya(diffY > 0.0D ? speed*2 : -speed*2);

            mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.normalize().scale(this.speedModifier * flapProgress)));


        } else {
            this.mob.setNoGravity(false);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }
/*
    public void tick() {
        if (this.action == MovementController.Action.MOVE_TO) {
            Vector3d vector3d = new Vector3d(this.posX - parentEntity.getPosX(), this.posY - parentEntity.getPosY(), this.posZ - parentEntity.getPosZ());
            double d0 = vector3d.length();
            if (d0 < parentEntity.getBoundingBox().getAverageEdgeLength()) {
                this.action = MovementController.Action.WAIT;
                parentEntity.setMotion(parentEntity.getMotion().scale(0.5D));
            } else {
                parentEntity.setMotion(parentEntity.getMotion().add(vector3d.scale(this.speed * speedGeneral * 0.05D / d0)));
                if(needsYSupport){
                    double d1 = this.posY - parentEntity.getPosY();
                    parentEntity.setMotion(parentEntity.getMotion().add(0.0D, (double)parentEntity.getAIMoveSpeed() * speedGeneral * MathHelper.clamp(d1, -1, 1) * 0.6F, 0.0D));
                }
                if (parentEntity.getAttackTarget() == null || !shouldLookAtTarget) {
                    Vector3d vector3d1 = parentEntity.getMotion();
                    parentEntity.rotationYaw = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    parentEntity.renderYawOffset = parentEntity.rotationYaw;
                } else{
                    double d2 = parentEntity.getAttackTarget().getPosX() - parentEntity.getPosX();
                    double d1 = parentEntity.getAttackTarget().getPosZ() - parentEntity.getPosZ();
                    parentEntity.rotationYaw = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                    parentEntity.renderYawOffset = parentEntity.rotationYaw;
                }
            }

        }
    }*/

/*

    //bald eagle
    public void tick() {
        if (this.operation == Action.MOVE_TO) {
            this.mob.setNoGravity(true);

            double diffX = this.wantedX - this.mob.getX();
            double diffY = this.wantedY - this.mob.getY();
            double diffZ = this.wantedZ - this.mob.getZ();
            Vector3d vector3d = new Vector3d(diffX, diffY, diffZ);
            double dist = vector3d.length();

            if (dist < 0.3) {
                this.operation = Action.WAIT;
                mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5D));
            } else {


                float newSpeed;
                if (this.mob.isOnGround()) {
                    newSpeed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                } else {
                    newSpeed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }
                this.mob.setSpeed(newSpeed);


                //mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale(this.speedGeneral * 0.05D / dist)));


                //mob.setDeltaMovement(mob.getDeltaMovement().add(0.0D, mob.getSpeed() * speedGeneral * MathHelper.clamp(diffY, -1, 1) * 0.6F, 0.0D));
                if (this.isPathClear(vector3d, MathHelper.ceil(dist))) {
                    this.mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale( 0.05D / dist )));

                    //this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
                } else {
                    this.operation = MovementController.Action.WAIT;
                }

                //this.mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale( 0.05D / dist )));

                Vector3d vector3d1 = mob.getDeltaMovement();

                float wantedYaw = (float)(MathHelper.atan2(diffZ, diffX) * (double)(180F / (float)Math.PI)) - 90.0F;
                //this.mob.yRot = this.rotlerp(this.mob.yRot, wantedYaw, 40.0F);
                mob.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                mob.yBodyRot = mob.yRot;
                //this.mob.setYya(diffY > 0.0D ? newSpeed*3 : -newSpeed*3);

            }

        }
        else{
            this.mob.setNoGravity(false);
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }

    //lammer
    @Override
    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {
            this.mob.setNoGravity(true);

            double diffX = this.wantedX - this.mob.getX();
            double diffY = this.wantedY - this.mob.getY();
            double diffZ = this.wantedZ - this.mob.getZ();
            Vector3d vector3d = new Vector3d(diffX, diffY, diffZ);
            double dist = vector3d.length();

            vector3d = vector3d.normalize();

            float yawAngle = (float)(MathHelper.atan2(diffZ, diffX) * (double)(180F / (float)Math.PI)) - 90.0F;

            if (this.isPathClear(vector3d, MathHelper.ceil(dist))) {
                this.parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale( 0.05D / dist )));

                //this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vector3d.scale(0.1D)));
            } else {
                this.operation = MovementController.Action.WAIT;
            }


            this.parentEntity.yRot = this.rotlerp(this.parentEntity.yRot, yawAngle, 35.0F);
            double xZDist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float pitchAngle = (float)(-(MathHelper.atan2(diffY, xZDist) * (double)(180F / (float)Math.PI)));
            this.parentEntity.xRot = this.rotlerp(this.parentEntity.xRot, pitchAngle, 35.0F);
        }
    }
    */


    private boolean isPathClear(Vector3d movement, int distance) {
        AxisAlignedBB axisalignedbb = this.mob.getBoundingBox();

        for(int i = 1; i < distance; ++i) {
            axisalignedbb = axisalignedbb.move(movement);
            if (!this.mob.level.noCollision(this.mob, axisalignedbb)) {
                return false;
            }
        }

        return true;
    }
/*


    */
    /*

    //crow
    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {

            double diffX = this.wantedX - this.mob.getX();
            double diffY = this.wantedY - this.mob.getY();
            double diffZ = this.wantedZ - this.mob.getZ();
            Vector3d vector3d = new Vector3d(diffX, diffY, diffZ);
            double dist = vector3d.length();

            if (dist < parentEntity.getBoundingBox().getSize()) {
                this.operation = MovementController.Action.WAIT;
                parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().scale(0.5D));
            } else {


                float f1;
                if (this.mob.isOnGround()) {
                    f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                } else {
                    f1 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }

                this.mob.setSpeed(f1);


                parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale(this.speedModifier * speedGeneral * 0.05D / dist)));
                if(needsYSupport){
                    double d1 = this.wantedY - parentEntity.getY();
                    parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(0.0D, parentEntity.getSpeed() * speedGeneral * MathHelper.clamp(d1, -1, 1) * 0.6F, 0.0D));
                }
                if (parentEntity.getTarget() == null || !shouldLookAtTarget) {
                    Vector3d vector3d1 = parentEntity.getDeltaMovement();
                    parentEntity.yRot = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                } else{
                    double d2 = parentEntity.getTarget().getX() - parentEntity.getX();
                    double d1 = parentEntity.getTarget().getZ() - parentEntity.getZ();
                    parentEntity.yRot = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                }
                parentEntity.yBodyRot = parentEntity.yRot;
            }

        }
    }*/



}