package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class SprigganEntityModel<T extends SprigganEntity> extends BipedModel<T> {


    public SprigganEntityModel() {
        super(0.0F, 0.0F, 64, 64);
        hat.visible = false;
        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        head.texOffs(38, 15).addBox(-8.0F, -17.0F, 0.0F, 7.0F, 9.0F, 0.0F, 0.0F, false);
        head.texOffs(36, 36).addBox(1.0F, -17.0F, 0.0F, 7.0F, 9.0F, 0.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 0.0F, 0.0F);
        body.texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 7.0F, 4.0F, 0.0F, false);
        body.texOffs(20, 23).addBox(-3.5F, 7.0F, -2.0F, 7.0F, 5.0F, 4.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setPos(5.0F, 2.0F, 0.0F);
        rightArm.texOffs(24, 32).addBox(-2.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(-5.0F, 2.0F, 0.0F);
        leftArm.texOffs(32, 0).addBox(-1.0F, -2.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        //TODO: mirror thing

        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(2.0F, 12.0F, 0.0F);
        leftLeg.texOffs(12, 32).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(-2.0F, 12.0F, 0.0F);
        rightLeg.texOffs(0, 27).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);

        /*
        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(1.9F, 12.0F, 0.0F);
        leftLeg.texOffs(12, 32).addBox(-1.6F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(-1.9F, 12.0F, 0.0F);
        rightLeg.texOffs(0, 27).addBox(-1.4F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);
        */
    }

    @Override
    public void translateToHand(HandSide handSide, MatrixStack matrixStack) {
        ModelRenderer modelrenderer = this.getArm(handSide);

        float f = 1F * (float) (handSide == HandSide.RIGHT ? 0.5 : -0.5);
        modelrenderer.x += f;
        modelrenderer.translateAndRotate(matrixStack);
        modelrenderer.x -= f;
    }

    @Override
    public void setupAnim(T spriggan, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(spriggan, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);

        if(spriggan.isCastingSpell()){
            this.rightArm.z = 0.0F;
            this.rightArm.x = -5;//-5.0F;
            this.rightArm.xRot = -(float) (Math.PI/2.5f) + MathHelper.sin(p_225597_4_ * 0.3F) * 0.06F;
            this.rightArm.zRot = 0;//MathHelper.cos(p_225597_4_ * 0.6662F) * 0.125F;
            this.rightArm.yRot = MathHelper.sin(p_225597_4_ * (0.3f/2f)) * 0.1F;
        }
        int y = 7;
        if(this.riding){
            this.body.y = 0+y;
            this.head.y = 0+y;
            this.rightArm.y = 2+y;
            this.leftArm.y = 2+y;
            this.leftLeg.y = 12+y;
            this.rightLeg.y = 12+y;
        }
        else{
            this.body.y = 0;
            this.head.y = 0;
            this.rightArm.y = 2;
            this.leftArm.y = 2;
            this.leftLeg.y = 12;
            this.rightLeg.y = 12;
        }
    }
}

