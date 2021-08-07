package net.mehvahdjukaar.spriggans.client.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.entity.WolpertingerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WolpertingerEntityModel extends EntityModel<WolpertingerEntity> {
    private final ModelRenderer head;
    private final ModelRenderer antlers;
    private final ModelRenderer nose;
    private final ModelRenderer left_ear;
    private final ModelRenderer right_ear;
    private final ModelRenderer body;
    private final ModelRenderer left_arm;
    private final ModelRenderer right_arm;
    private final ModelRenderer left_thigh;
    private final ModelRenderer right_thigh;
    private final ModelRenderer left_foot;
    private final ModelRenderer right_foot;
    private final ModelRenderer tail;
    private final ModelRenderer right_wing;
    private final ModelRenderer left_wing;
    private float jumpRotation;

    public WolpertingerEntityModel() {
        texWidth = 64;
        texHeight = 32;

        this.left_foot = new ModelRenderer(this, 26, 24);
        this.left_foot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.left_foot.setPos(3.0F, 17.5F, 3.7F);
        this.left_foot.mirror = true;
        this.setRotation(this.left_foot, 0.0F, 0.0F, 0.0F);
        this.right_foot = new ModelRenderer(this, 8, 24);
        this.right_foot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.right_foot.setPos(-3.0F, 17.5F, 3.7F);
        this.right_foot.mirror = true;
        this.setRotation(this.right_foot, 0.0F, 0.0F, 0.0F);
        this.left_thigh = new ModelRenderer(this, 30, 15);
        this.left_thigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.left_thigh.setPos(3.0F, 17.5F, 3.7F);
        this.left_thigh.mirror = true;
        this.setRotation(this.left_thigh, -0.34906584F, 0.0F, 0.0F);
        this.right_thigh = new ModelRenderer(this, 16, 15);
        this.right_thigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.right_thigh.setPos(-3.0F, 17.5F, 3.7F);
        this.right_thigh.mirror = true;
        this.setRotation(this.right_thigh, -0.34906584F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F);
        this.body.setPos(0.0F, 19.0F, 8.0F);
        this.body.mirror = true;
        this.setRotation(this.body, -0.34906584F, 0.0F, 0.0F);
        this.left_arm = new ModelRenderer(this, 8, 15);
        this.left_arm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.left_arm.setPos(3.0F, 17.0F, -1.0F);
        this.left_arm.mirror = true;
        this.setRotation(this.left_arm, -0.17453292F, 0.0F, 0.0F);
        this.right_arm = new ModelRenderer(this, 0, 15);
        this.right_arm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.right_arm.setPos(-3.0F, 17.0F, -1.0F);
        this.right_arm.mirror = true;
        this.setRotation(this.right_arm, -0.17453292F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 32, 0);
        this.head.addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F);
        this.head.setPos(0.0F, 16.0F, -1.0F);
        this.head.mirror = true;
        this.setRotation(this.head, 0.0F, 0.0F, 0.0F);
        this.right_ear = new ModelRenderer(this, 52, 0);
        this.right_ear.addBox(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F);
        this.right_ear.setPos(0.0F, 16.0F, -1.0F);
        this.right_ear.mirror = true;
        this.setRotation(this.right_ear, 0.0F, -0.2617994F, 0.0F);
        this.left_ear = new ModelRenderer(this, 58, 0);
        this.left_ear.addBox(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F);
        this.left_ear.setPos(0.0F, 16.0F, -1.0F);
        this.left_ear.mirror = true;
        this.setRotation(this.left_ear, 0.0F, 0.2617994F, 0.0F);
        this.tail = new ModelRenderer(this, 52, 6);
        this.tail.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F);
        this.tail.setPos(0.0F, 20.0F, 7.0F);
        this.tail.mirror = true;
        this.setRotation(this.tail, -0.3490659F, 0.0F, 0.0F);


        antlers = new ModelRenderer(this);
        antlers.setPos(0.0F, -0.5F, 2.5F);
        head.addChild(antlers);
        antlers.texOffs(0, 0).addBox(-2.5F, -7.5F, -5.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);

        nose = new ModelRenderer(this);
        nose.setPos(0.0F, 16.5F, -1.0F);
        nose.texOffs(22, 8).addBox(-1.0F, -2.0F, -5.5F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        nose.texOffs(22, 0).addBox(-1.0F, -1.0F, -5.25F, 2.0F, 1.0F, 0.0F, 0.0F, false);

        right_wing = new ModelRenderer(this);
        right_wing.setPos(1.0F, -2.0F, -8.5F);
        body.addChild(right_wing);
        right_wing.xRot = 0.1309F;
        right_wing.zRot = -0.1745F;
        right_wing.texOffs(44, 25).addBox(-4.8321F, -0.9583F, -0.2515F, 4.0F, 1.0F, 6.0F, 0.0F, false);

        left_wing = new ModelRenderer(this);
        left_wing.setPos(-1.0F, -2.0F, -8.5F);
        body.addChild(left_wing);
        left_wing.xRot = 0.1309F;
        left_wing.zRot = 0.1745F;
        left_wing.texOffs(44, 25).addBox(0.8321F, -0.9583F, -0.2515F, 4.0F, 1.0F, 6.0F, 0.0F, false);

    }


    private void setRotation(ModelRenderer modelRenderer, float p_178691_2_, float p_178691_3_, float p_178691_4_) {
        modelRenderer.xRot = p_178691_2_;
        modelRenderer.yRot = p_178691_3_;
        modelRenderer.zRot = p_178691_4_;
    }

    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int p_225598_3_, int p_225598_4_, float partialTicks, float p_225598_6_, float p_225598_7_, float p_225598_8_) {


        if (this.young) {
            float f = 1.5F;
            matrixStack.pushPose();
            matrixStack.scale(0.56666666F, 0.56666666F, 0.56666666F);
            matrixStack.translate(0.0D, 1.375D, 0.125D);
            ImmutableList.of(this.head, this.left_ear, this.right_ear, this.nose).forEach((p_228292_8_) -> {
                p_228292_8_.render(matrixStack, builder, p_225598_3_, p_225598_4_, partialTicks, p_225598_6_, p_225598_7_, p_225598_8_);
            });
            matrixStack.popPose();
            matrixStack.pushPose();
            matrixStack.scale(0.4F, 0.4F, 0.4F);
            matrixStack.translate(0.0D, 2.25D, 0.0D);
            ImmutableList.of(this.right_wing, this.left_wing,this.left_foot, this.right_foot, this.left_arm, this.right_arm, this.body, this.left_thigh, this.right_thigh, this.tail).forEach((p_228291_8_) -> {
                p_228291_8_.render(matrixStack, builder, p_225598_3_, p_225598_4_, partialTicks, p_225598_6_, p_225598_7_, p_225598_8_);
            });
            matrixStack.popPose();
        } else {
            matrixStack.pushPose();
            matrixStack.scale(0.6F, 0.6F, 0.6F);
            matrixStack.translate(0.0D, 1.0D, 0.0D);
            ImmutableList.of(this.left_foot, this.right_foot, this.left_arm, this.right_arm, this.body, this.left_thigh, this.right_thigh, this.head, this.left_ear, this.right_ear, this.tail, this.nose).forEach((p_228290_8_) -> {
                p_228290_8_.render(matrixStack, builder, p_225598_3_, p_225598_4_, partialTicks, p_225598_6_, p_225598_7_, p_225598_8_);
            });
            matrixStack.popPose();
        }

    }

    public void setupAnim(WolpertingerEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        float f = p_225597_4_ - (float) entity.tickCount;
        this.nose.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.head.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.right_ear.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.left_ear.xRot = p_225597_6_ * ((float) Math.PI / 180F);
        this.nose.yRot = p_225597_5_ * ((float) Math.PI / 180F);
        this.head.yRot = p_225597_5_ * ((float) Math.PI / 180F);
        this.right_ear.yRot = this.nose.yRot - 0.2617994F;
        this.left_ear.yRot = this.nose.yRot + 0.2617994F;
        this.jumpRotation = MathHelper.sin(entity.getJumpCompletion(f) * (float) Math.PI);
        this.left_thigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.right_thigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.left_foot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.right_foot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.left_arm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.right_arm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);

        float f2 = MathHelper.lerp(f, entity.oFlap, entity.flap);
        float f1 = MathHelper.lerp(f, entity.oFlapSpeed, entity.flapSpeed);
        float b = (MathHelper.sin(f2) + 1.0F) * f1;

        this.left_wing.xRot =  0.1309F + b/3;
        this.right_wing.xRot = 0.1309F + b/3;

        this.left_wing.yRot = b/5f;
        this.right_wing.yRot = -b/5f;
    }

    public void prepareMobModel(WolpertingerEntity p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
        this.jumpRotation = MathHelper.sin(p_212843_1_.getJumpCompletion(p_212843_4_) * (float) Math.PI);
    }
}