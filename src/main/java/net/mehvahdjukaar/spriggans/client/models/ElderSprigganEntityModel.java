package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class ElderSprigganEntityModel<T extends SprigganEntity> extends SprigganEntityModel<T> {
    public ModelRenderer heart;

    public ElderSprigganEntityModel() {
        super();
        hat.visible = false;

        heart = new ModelRenderer(this);
        heart.texOffs(24, 0).addBox(-1.0F, 2.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.01F, false);
        head.texOffs(38, 15).addBox(-8.0F, -17.0F, 0.0F, 7.0F, 9.0F, 0.0F, 0.0F, false);
        head.texOffs(36, 36).addBox(1.0F, -17.0F, 0.0F, 7.0F, 9.0F, 0.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(-5.0F, 2.0F, 0.0F);
        leftArm.texOffs(34, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setPos(5.0F, 2.0F, 0.0F);
        rightArm.texOffs(26, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(2.0F, 12.0F, 0.0F);
        leftLeg.texOffs(14, 32).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.01F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(-2.0F, 12.0F, 0.0F);
        rightLeg.texOffs(2, 27).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.01F, false);
    }

    private float ageInTicks = 0;

    @Override
    public void setupAnim(T spriggan, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(spriggan, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.ageInTicks = ageInTicks;

    }


    @Override
    public void translateToHand(HandSide handSide, MatrixStack matrixStack) {
        ModelRenderer modelrenderer = this.getArm(handSide);

        float f = 13F * (float) (handSide == HandSide.RIGHT ? 0.5 : -0.5);
        modelrenderer.x += f;
        modelrenderer.translateAndRotate(matrixStack);
        modelrenderer.x -= f;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder builder, int light, int overlay, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

        this.headParts().forEach((p_228228_8_) -> {
            p_228228_8_.render(matrixStack, builder, light, overlay, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        });
        this.bodyParts().forEach((p_228227_8_) -> {
            p_228227_8_.render(matrixStack, builder, light, overlay, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);

        });


        /*
        matrixStack.pushPose();
        float aa = MathHelper.sin(ageInTicks * 0.2f) * 0.05F;
        float f = 1 + aa;
        matrixStack.translate(0,4/16f,0);
        matrixStack.scale(f,f,f);
        matrixStack.translate(0,-4/16f,0);
        heart.render(matrixStack, builder, light, overlay, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        matrixStack.popPose();

        */
    }
}

