package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.entity.SprigganHorseEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class SprigganHorseModel extends EntityModel<SprigganHorseEntity> {

    private final ModelRenderer headParts;

    public final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer front_left_leg;
    private final ModelRenderer front_right_leg;
    private final ModelRenderer back_left_leg;
    private final ModelRenderer back_right_leg;


    private final ModelRenderer[] saddleParts;
    private final ModelRenderer[] ridingParts;

    public SprigganHorseModel() {
        texWidth = 128;
        texHeight = 128;

        ModelRenderer head = new ModelRenderer(this);
        head.setPos(0.0F, 2.0F, -9.0F);
        head.texOffs(22, 50).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);

        ModelRenderer mouth1 = new ModelRenderer(this);
        mouth1.setPos(0.0F, 2.0F, -9.0F);
        mouth1.texOffs(24, 62).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, 0.0F, false);

        ModelRenderer right_ear = new ModelRenderer(this);
        right_ear.setPos(2.0F, -11.0F, 2.0F);
        head.addChild(right_ear);
        right_ear.yRot = 0.7854F;
        right_ear.texOffs(83, 15).addBox(-0.3284F, -9.0F, -1.6669F, 0.0F, 9.0F, 17.0F, 0.0F, false);

        ModelRenderer left_ear = new ModelRenderer(this);
        left_ear.setPos(-2.0F, -11.0F, 2.0F);
        head.addChild(left_ear);
        left_ear.yRot = -0.7854F;
        left_ear.texOffs(83, 15).addBox(0.3284F, -9.0F, -1.6669F, 0.0F, 9.0F, 17.0F, 0.0F, false);


        right_ear.texOffs(83, 15).addBox(-0.3284F, -9.0F, -1.6669F, 0.0F, 9.0F, 17.0F, 0.0F, false);

        ModelRenderer neck = new ModelRenderer(this);
        neck.setPos(0.0F, 2.0F, -8.9F);
        neck.texOffs(0, 41).addBox(-2.0F, -6.0F, -2.099F, 4.0F, 12.0F, 7.0F, 0.0F, false);


        this.headParts = new ModelRenderer(this, 0, 35);
        this.headParts.texOffs(0, 41).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
        this.headParts.xRot = ((float)Math.PI / 6F);
        ModelRenderer modelrenderer = new ModelRenderer(this, 0, 13);
        modelrenderer.texOffs(22, 50).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F);
        ModelRenderer mane = new ModelRenderer(this, 56, 36);
        mane.texOffs(41, 71).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 1.0F, -0.01F, false);
        ModelRenderer mouth = new ModelRenderer(this, 0, 25);
        mouth.texOffs(24, 62).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F);
        this.headParts.addChild(modelrenderer);
        this.headParts.addChild(mane);
        this.headParts.addChild(mouth);
        this.headParts.addChild(right_ear);
        this.headParts.addChild(left_ear);


        body = new ModelRenderer(this);
        body.setPos(0.0F, 11.0F, 5.0F);
        //body.texOffs(60, 50).addBox(-1.0F, -3.0F, -14.0F, 4.0F, 3.0F, 6.0F, 0.05F, false);
        body.texOffs(0, 0).addBox(-5.0F, -8.001F, -17.0F, 10.0F, 10.0F, 11.0F, 0.0F, false);
        body.texOffs(0, 21).addBox(-4.0F, -8.001F, -6.0F, 8.0F, 9.0F, 11.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, -7.0F, 5.0F);
        body.addChild(tail);
        tail.texOffs(62, 62).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 14.0F, 2.0F, -0.01F, false);
        //tail.xRot = ((float)Math.PI / 6F);
        this.body.addChild(this.tail);

        this.back_left_leg = new ModelRenderer(this, 48, 21);
        this.back_left_leg.mirror = true;
        this.back_left_leg.texOffs(68, 34).addBox(-3.0F, -1.01F, -1.0F, 3.0F, 12.0F, 3.0F);
        this.back_left_leg.setPos(4.0F, 13.0F, 8.0F);
        this.back_right_leg = new ModelRenderer(this, 48, 21);
        this.back_right_leg.texOffs(68, 0).addBox(-0.0F, -1.01F, -1.0F, 3.0F, 12.0F, 3.0F);
        this.back_right_leg.setPos(-4.0F, 13.0F, 8.0F);

        this.front_left_leg = new ModelRenderer(this, 48, 21);
        this.front_left_leg.mirror = true;
        this.front_left_leg.texOffs(24, 72).addBox(-2.0F, -1.01F, -1.9F, 3.0F, 11.0F, 3.0F);
        this.front_left_leg.setPos(4.0F, 6.0F, -12.0F);
        this.front_right_leg = new ModelRenderer(this, 48, 21);
        this.front_right_leg.texOffs(71, 15).addBox(-1.0F, -1.01F, -1.9F, 3.0F, 11.0F, 3.0F);
        this.front_right_leg.setPos(-4.0F, 6.0F, -12.0F);


        ModelRenderer right_rein = new ModelRenderer(this, 32, 2);
        right_rein.texOffs(27, 5).addBox(3.1F, -6.25F, -6.0F, 0.0F, 3.0F, 16.0F, 0.0F, false);
        right_rein.xRot = (-(float)Math.PI / 6F);

        ModelRenderer left_rein = new ModelRenderer(this, 32, 2);
        left_rein.texOffs(27, 8).addBox(-3.1F, -6.25F, -6.0F, 0.0F, 3.0F, 16.0F, 0.0F, false);
        left_rein.xRot = (-(float)Math.PI / 6F);



        ModelRenderer saddle = new ModelRenderer(this, 26, 0);
        saddle.texOffs(29, 32).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.1F);
        this.body.addChild(saddle);


        ModelRenderer left_bit = new ModelRenderer(this, 29, 5);
        left_bit.texOffs(0, 4).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F);
        this.headParts.addChild(left_bit);
        ModelRenderer right_bit = new ModelRenderer(this, 29, 5);
        right_bit.texOffs(0, 0).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F);
        this.headParts.addChild(right_bit);

        this.headParts.addChild(right_rein);
        this.headParts.addChild(left_rein);
        ModelRenderer modelrenderer8 = new ModelRenderer(this, 1, 1);
        modelrenderer8.texOffs(42, 0).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
        this.headParts.addChild(modelrenderer8);
        ModelRenderer modelrenderer9 = new ModelRenderer(this, 19, 0);
        modelrenderer9.texOffs(31, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
        this.headParts.addChild(modelrenderer9);
        this.saddleParts = new ModelRenderer[]{saddle, left_bit, right_bit, modelrenderer8, modelrenderer9};
        this.ridingParts = new ModelRenderer[]{right_rein, left_rein};

    }





    @Override
    public void setupAnim(SprigganHorseEntity horse, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = horse.isSaddled();
        boolean flag1 = horse.isVehicle();

        for(ModelRenderer modelrenderer : this.saddleParts) {
            modelrenderer.visible = flag;
        }

        for(ModelRenderer modelrenderer1 : this.ridingParts) {
            modelrenderer1.visible = flag1 && flag;
        }

        this.body.y = 11.0F;
        //tail.xRot = 0;
        tail.setPos(0.0F, -7.0F, 5.0F);

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

        body.render(matrixStack, buffer, packedLight, packedOverlay);
        //tail.render(matrixStack, buffer, packedLight, packedOverlay);
        front_left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        front_right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        back_left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
        back_right_leg.render(matrixStack, buffer, packedLight, packedOverlay);

        this.headParts.render(matrixStack,buffer,packedLight,packedOverlay);
    }

    public void prepareMobModel(SprigganHorseEntity horse, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(horse, p_212843_2_, p_212843_3_, p_212843_4_);
        float f = MathHelper.rotlerp(horse.yBodyRotO, horse.yBodyRot, p_212843_4_);
        float f1 = MathHelper.rotlerp(horse.yHeadRotO, horse.yHeadRot, p_212843_4_);
        float f2 = MathHelper.lerp(p_212843_4_, horse.xRotO, horse.xRot);
        float f3 = f1 - f;
        float f4 = f2 * ((float)Math.PI / 180F);
        if (f3 > 20.0F) {
            f3 = 20.0F;
        }

        if (f3 < -20.0F) {
            f3 = -20.0F;
        }

        if (p_212843_3_ > 0.2F) {
            f4 += MathHelper.cos(p_212843_2_ * 0.4F) * 0.15F * p_212843_3_;
        }

        float f5 = horse.getEatAnim(p_212843_4_);
        float f6 = horse.getStandAnim(p_212843_4_);
        float f7 = 1.0F - f6;
        float f8 = horse.getMouthAnim(p_212843_4_);
        boolean flag = horse.tailCounter != 0;
        float f9 = (float)horse.tickCount + p_212843_4_;
        this.headParts.y = 4.0F;
        this.headParts.z = -12.0F;
        this.body.xRot = 0.0F;
        this.headParts.xRot = ((float)Math.PI / 6F) + f4;
        this.headParts.yRot = f3 * ((float)Math.PI / 180F);
        float f10 = horse.isInWater() ? 0.2F : 1.0F;
        float f11 = MathHelper.cos(f10 * p_212843_2_ * 0.6662F + (float)Math.PI);
        float f12 = f11 * 0.8F * p_212843_3_;
        float f13 = (1.0F - Math.max(f6, f5)) * (((float)Math.PI / 6F) + f4 + f8 * MathHelper.sin(f9) * 0.05F);
        this.headParts.xRot = f6 * (0.2617994F + f4) + f5 * (2.1816616F + MathHelper.sin(f9) * 0.05F) + f13;
        this.headParts.yRot = f6 * f3 * ((float)Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * this.headParts.yRot;
        this.headParts.y = f6 * -4.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.headParts.y;
        this.headParts.z = f6 * -4.0F + f5 * -12.0F + (1.0F - Math.max(f6, f5)) * this.headParts.z;
        this.body.xRot = f6 * (-(float)Math.PI / 4F) + f7 * this.body.xRot;
        float f14 = 0.2617994F * f6;
        float f15 = MathHelper.cos(f9 * 0.6F + (float)Math.PI);
        this.front_left_leg.y = 2.0F * f6 + 14.0F * f7;
        this.front_left_leg.z = -6.0F * f6 - 10.0F * f7;
        this.front_right_leg.y = this.front_left_leg.y;
        this.front_right_leg.z = this.front_left_leg.z;
        float f16 = ((-(float)Math.PI / 3F) + f15) * f6 + f12 * f7;
        float f17 = ((-(float)Math.PI / 3F) - f15) * f6 - f12 * f7;
        this.back_left_leg.xRot = f14 - f11 * 0.5F * p_212843_3_ * f7;
        this.back_right_leg.xRot = f14 + f11 * 0.5F * p_212843_3_ * f7;
        this.front_left_leg.xRot = f16;
        this.front_right_leg.xRot = f17;
        this.tail.xRot = ((float)Math.PI / 6F) + p_212843_3_ * 0.75F;
        this.tail.y = -5.0F + p_212843_3_;
        this.tail.z = 2.0F + p_212843_3_ * 2.0F;
        if (flag) {
            this.tail.yRot = MathHelper.cos(f9 * 0.7F);
            this.tail.xRot = p_212843_3_ * 0.75F + (1+MathHelper.sin(f9 * 0.7F))*0.3f;
        } else {
            this.tail.yRot = 0.0F;
            this.tail.xRot = 0;
        }

        this.body.y =  0.0F;
    }


}
