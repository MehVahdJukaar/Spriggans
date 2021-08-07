package net.mehvahdjukaar.spriggans.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.client.renderer.entity.model.*;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OwlModel extends AgeableModel<OwlEntity> {
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer shoulderLeft;
    private final ModelRenderer shoulderRight;
    private final ModelRenderer head;
    private final ModelRenderer neck;
    private final ModelRenderer legLeft;
    private final ModelRenderer legRight;


    private final ModelRenderer wingLeft;
    private final ModelRenderer wingRight;
    private final ModelRenderer body2;

    public OwlModel() {

        super(true,  14.375f, 0, 2.25F, 2.0F, 24.0F);

        texWidth = 64;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);


        body2 = new ModelRenderer(this);
        body2.setPos(0.0F, -9.5F, 0.0F);
        body.addChild(body2);
        body2.texOffs(0, 13).addBox(-4.0F, -0.5F, -3.5F, 8.0F, 8.0F, 7.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, 6.55F, 3.25F);
        body2.addChild(tail);
        setRotationAngle(tail, 0.4363F, 0.0F, 0.0F);
        tail.texOffs(0, 28).addBox(-2.5F, -0.5F, -0.5F, 5.0F, 3.0F, 1.0F, 0.0F, false);

        legRight = new ModelRenderer(this);
        legRight.setPos(-2.0F, 7.5F, 0.0F);
        body2.addChild(legRight);
        legRight.texOffs(14, 28).addBox(-0.5F, 2.0F, -1.5F, 1.0F, 0.0F, 1.0F, 0.0F, false);
        legRight.texOffs(14, 29).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        legLeft = new ModelRenderer(this);
        legLeft.setPos(2.0F, 7.5F, 0.0F);
        body2.addChild(legLeft);
        legLeft.texOffs(19, 29).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        legLeft.texOffs(19, 28).addBox(-0.5F, 2.0F, -1.5F, 1.0F, 0.0F, 1.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setPos(0.0F, 14.0F, 0.0F);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        neck.addChild(head);
        head.texOffs(0, 0).addBox(-0.5F, -2.0F, -4.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(-4.0F, -6.0F, -3.5F, 8.0F, 6.0F, 7.0F, 0.01F, false);
        head.texOffs(24, 30).addBox(-5.0F, -7.0F, -3.5F, 10.0F, 2.0F, 0.0F, 0.0F, false);

        /*
        head = new ModelRenderer(this);
        head.setPos(0.0F, 14.0F, 0.0F);
        //body.addChild(head);
        head.texOffs(0, 0).addBox(-0.5F, -2.0F, -4.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(-4.0F, -6.0F, -3.5F, 8.0F, 6.0F, 7.0F, 0.01F, false);
        head.texOffs(24, 30).addBox(-5.0F, -7.0F, -3.5F, 10.0F, 2.0F, 0.0F, 0.0F, false);
        */



        shoulderLeft = new ModelRenderer(this);
        shoulderLeft.setPos(4.5F, -9.5F, 0.0F);
        body.addChild(shoulderLeft);
        setRotationAngle(shoulderLeft, -0.2618F, 0.0F, 0.0F);


        wingLeft = new ModelRenderer(this);
        wingLeft.setPos(-0.25F, 0.25F, 0.5F);
        shoulderLeft.addChild(wingLeft);
        setRotationAngle(wingLeft, 0.4363F, 0.0F, 0.0F);
        wingLeft.texOffs(35, 10).addBox(-0.25F, -0.93F, -3.3F, 1.0F, 8.0F, 6.0F, 0.0F, false);


        shoulderRight = new ModelRenderer(this);
        shoulderRight.setPos(-4.5F, -9.5F, 0.0F);
        body.addChild(shoulderRight);
        setRotationAngle(shoulderRight, -0.2618F, 0.0F, 0.0F);


        wingRight = new ModelRenderer(this);
        wingRight.setPos(0.25F, 0.25F, 0.5F);
        shoulderRight.addChild(wingRight);
        setRotationAngle(wingRight, 0.4363F, 0.0F, 0.0F);
        wingRight.texOffs(50, 10).addBox(-0.75F, -0.93F, -3.3F, 1.0F, 8.0F, 6.0F, 0.0F, false);
        /*
        shoulderRight = new ModelRenderer(this);
        shoulderRight.setPos(-4.5F, -9.5F, 0.0F);
        body.addChild(shoulderRight);
        setRotationAngle(shoulderRight, 0.1745F, 0.0F, 0.0F);


        wingRight = new ModelRenderer(this);
        wingRight.setPos(0.5F, -0.5F, 0.0F);
        shoulderRight.addChild(wingRight);
        wingRight.texOffs(50, 10).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 8.0F, 6.0F, 0.0F, false);
        */

    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.neck);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }


    private float getHeadRollAngle(OwlEntity owl, float partialTicks) {
        return MathHelper.lerp(partialTicks, owl.interestedAngleO, owl.interestedAngle) * 0.15F * (float)Math.PI;
    }

    private float getWingRotation(OwlEntity entity, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, entity.oFlap, entity.flap);
        float f1 = MathHelper.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        return (MathHelper.sin(f*0.3f) + 1.0F) * f1;
    }

    public void prepareMobModel(OwlEntity p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        //this.prepare(getState(p_212843_1_));
    }

    @Override
    public void setupAnim(OwlEntity owl, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float partialTicks = ageInTicks-((int)ageInTicks);

        State state = getState(owl);
        float wingFlap = getWingRotation(owl,partialTicks);
        float headRoll = getHeadRollAngle(owl,partialTicks);

        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.neck.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.neck.zRot = headRoll*0.65f;
        //this.head.x = 0.0F;
        //this.body.x = 0.0F;
        //this.tail.x = 0.0F;
        //this.shoulderRight.x = -1.5F;
        //this.shoulderLeft.x = 1.5F;

        this.body.xRot = 0;
        this.body.y=24;
        this.body.z=0;

        this.neck.y=14;
        this.neck.z=0;

        this.shoulderRight.xRot = -0.2618F;
        this.shoulderLeft.xRot = -0.2618F;

        if(state == State.SITTING){
            this.legLeft.y = 6.5F;
            this.legRight.y = 6.5F;
            this.body.y = 25.0F;
            this.neck.y = 15.0F;
            this.legLeft.yRot = -0.25f;
            this.legRight.yRot = 0.25f;
        }
        else{
            this.legLeft.y = 7.5F;
            this.legRight.y = 7.5F;
            this.legLeft.yRot = 0f;
            this.legRight.yRot = 0f;
        }

        switch(state) {
            case BABY_SLEEP:
                this.neck.xRot = 0;

                this.body2.xRot = 0;
                this.body.xRot = (float) (Math.PI/2f);
                this.body.y=20.5f;
                this.body.z=10;
                this.neck.y=21.75f;
                this.neck.z=-3;

                this.shoulderRight.xRot = -0.35F;
                this.shoulderLeft.xRot = -0.35F;

                this.legLeft.xRot = -0.8f;
                this.legRight.xRot = -0.8f;

                this.tail.xRot = 0.1463F ;

                this.wingLeft.zRot = 0;

                this.wingRight.zRot = 0;
                break;
            case SITTING:
                this.neck.xRot = 0;

                this.body2.xRot = 0;

                this.legLeft.xRot = 0;
                this.legRight.xRot = 0;

                this.tail.xRot = 0.4363F + MathHelper.cos(limbSwing * 0.8F) * 0.4F * limbSwingAmount;

                this.wingLeft.zRot = -wingFlap;

                this.wingRight.zRot = wingFlap;

                break;
            default:
            case STANDING:
                this.neck.xRot = 0;

                this.body2.xRot = 0;
                this.legLeft.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
                this.legRight.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

                this.tail.xRot = 0.4363F + MathHelper.cos(limbSwing * 0.8F) * 0.4F * limbSwingAmount;

                this.wingLeft.zRot = -wingFlap;

                this.wingRight.zRot = wingFlap;
                break;
            case DIVING:
            case FLYING:
                this.neck.xRot = 0.35f;

                this.legLeft.xRot = -0.6f;
                this.legRight.xRot = -0.6f;

                this.body2.xRot = 0.8f+ MathHelper.cos(ageInTicks * 0.09F) * 0.03F ;

                this.tail.xRot = 0.4363F + MathHelper.cos(limbSwing * 0.8F) * 0.4F * limbSwingAmount;

                this.wingLeft.zRot = -wingFlap;

                this.wingRight.zRot = wingFlap;

                break;
        }

    }


    private static State getState(OwlEntity entity) {
        if(entity.isBaby() && entity.isSleeping()){
            return State.BABY_SLEEP;
        }
        else if (entity.isInSittingPose() || entity.isSleeping()) {
            return State.SITTING;
        }
        else if(entity.isDiving()){
            return State.DIVING;
        }
        return entity.isFlying() ? State.FLYING : State.STANDING;
    }


    public void translateToFeet(MatrixStack matrixStack) {
        this.body.translateAndRotate(matrixStack);
        this.body2.translateAndRotate(matrixStack);
        this.legRight.translateAndRotate(matrixStack);

        matrixStack.translate(0.125,0.125,0);
        //matrixStack.translate(0,0.75,0);

        matrixStack.mulPose(Vector3f.XP.rotation(-body2.xRot-legRight.xRot));

        matrixStack.translate(0,0.3126,0);
    }

    @OnlyIn(Dist.CLIENT)
    public static enum State {
        FLYING,
        STANDING,
        SITTING,
        BABY_SLEEP,
        DIVING;
    }
}