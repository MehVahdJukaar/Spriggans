package net.mehvahdjukaar.spriggans.entity.AI;

import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

//from alexmobs
public class OwlMoveController extends MovementController {
    private final MobEntity parentEntity;
    private float speedGeneral;
    private boolean shouldLookAtTarget;
    private boolean needsYSupport;


    public OwlMoveController(MobEntity bird, float speedGeneral, boolean shouldLookAtTarget, boolean needsYSupport) {
        super(bird);
        this.parentEntity = bird;
        this.shouldLookAtTarget = shouldLookAtTarget;
        this.speedGeneral = speedGeneral;
        this.needsYSupport = needsYSupport;
    }

    public OwlMoveController(MobEntity bird, float speedGeneral, boolean shouldLookAtTarget) {
        this(bird, speedGeneral, shouldLookAtTarget, false);
    }

    public OwlMoveController(MobEntity bird, float speedGeneral) {
        this(bird, speedGeneral, true);
    }

    public void tick() {
        if (this.operation == MovementController.Action.MOVE_TO) {
            Vector3d vector3d = new Vector3d(this.wantedX - parentEntity.getX(), this.wantedY - parentEntity.getY(), this.wantedZ - parentEntity.getZ());
            double d0 = vector3d.length();
            if (d0 < parentEntity.getBoundingBox().getSize()) {
                this.operation = MovementController.Action.WAIT;
                parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().scale(0.5D));
            } else {

                float speed;
                if (this.mob.isOnGround()) {
                    speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                } else {
                    speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                }
                this.mob.setSpeed(speed);

                parentEntity.setDeltaMovement(parentEntity.getDeltaMovement().add(vector3d.scale(this.speedModifier * speedGeneral * 0.05D / d0)));
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
    }

}