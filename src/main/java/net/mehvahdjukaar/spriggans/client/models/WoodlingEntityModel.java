package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.mehvahdjukaar.spriggans.entity.WoodlingEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WoodlingEntityModel<T extends WoodlingEntity> extends BipedModel<T> {

    private final ModelRenderer stick;
    private final ModelRenderer stick_leaf;

    private final ModelRenderer right_ear;
    private final ModelRenderer left_ear;

    private final ModelRenderer right_leaf;
    private final ModelRenderer left_leaf;


    public WoodlingEntityModel() {
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

        texWidth = 32;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 16.0F, 0.0F);
        body.texOffs(0, 10).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);
        body.texOffs(12, 12).addBox(-1.5F, 3.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 15.0F, 0.0F);
        head.texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        right_ear = new ModelRenderer(this);
        right_ear.setPos(1.75F, -4.5F, 0.0F);
        head.addChild(right_ear);


        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-1.75F, 5.5F, 1.0F);
        right_ear.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.3927F);
        cube_r1.texOffs(0, 23).addBox(0.0F, -9.5F, -1.0F, 3.0F, 3.0F, 0.0F, 0.0F, false);
        cube_r1.texOffs(8, 16).addBox(-1.0F, -10.0F, -1.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(-1.75F, 5.5F, 1.0F);
        right_ear.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.7854F);
        cube_r2.texOffs(16, 19).addBox(7.0F, -6.0F, -1.5F, 1.0F, 2.0F, 1.0F, -0.01F, false);

        left_ear = new ModelRenderer(this);
        left_ear.setPos(-1.75F, -4.5F, 0.0F);
        head.addChild(left_ear);


        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(1.75F, 5.5F, 1.0F);
        left_ear.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, 0.7854F);
        cube_r3.texOffs(16, 16).addBox(-8.0F, -6.0F, -1.5F, 1.0F, 2.0F, 1.0F, -0.01F, false);

        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(1.75F, 5.5F, 1.0F);
        left_ear.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.3927F);
        cube_r4.texOffs(12, 16).addBox(0.0F, -10.0F, -1.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        stick = new ModelRenderer(this);
        stick.setPos(1.0F, -5.0F, 0.0F);
        head.addChild(stick);


        ModelRenderer cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-1.0F, 6.0F, 1.0F);
        stick.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, 0.3927F);
        cube_r5.texOffs(8, 16).addBox(-2.0F, -10.0F, -1.5F, 1.0F, 5.0F, 1.0F, 0.0F, false);

        ModelRenderer cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(-1.0F, 6.0F, 1.0F);
        stick.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, -0.7854F);
        cube_r6.texOffs(16, 29).addBox(6.0F, -6.0F, -1.5F, 1.0F, 2.0F, 1.0F, -0.01F, false);


        stick_leaf = new ModelRenderer(this);
        stick_leaf.setPos(0.0F, -2.0F, 0.0F);
        stick.addChild(stick_leaf);
        setRotationAngle(stick_leaf, 0.0F, 0.0F, -0.1745F);
        stick_leaf.texOffs(0, 27).addBox(-4.3737F, -4.2951F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);


        right_leaf = new ModelRenderer(this);
        right_leaf.setPos(2.0F, -5.0F, 0.0F);
        head.addChild(right_leaf);
        right_leaf.texOffs(22, 27).addBox(-1.0F, -4.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);

        left_leaf = new ModelRenderer(this);
        left_leaf.setPos(-2.0F, -5.0F, 0.0F);
        head.addChild(left_leaf);
        left_leaf.texOffs(0, 27).addBox(-4.0F, -4.0F, 0.0F, 5.0F, 5.0F, 0.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setPos(0.0F, 16.0F, 0.0F);
        rightArm.texOffs(4, 16).addBox(-3.0F, -1.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(0.0F, 16.0F, 0.0F);
        leftArm.texOffs(0, 16).addBox(2.0F, -1.0F, -0.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        rightLeg = new ModelRenderer(this);
        rightLeg.setPos(-1.0F, 21.0F, 0.0F);
        rightLeg.texOffs(15, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        leftLeg = new ModelRenderer(this);
        leftLeg.setPos(1.0F, 21.0F, 0.0F);
        leftLeg.texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @Override
    public void translateToHand(HandSide handSide, MatrixStack matrixStack) {
        ModelRenderer modelrenderer = this.getArm(handSide);

        float f = 1F * (float) (handSide == HandSide.RIGHT ? 0.5 : -0.5);
        modelrenderer.x += f;
        modelrenderer.translateAndRotate(matrixStack);
        modelrenderer.x -= f;
    }


    public void setupAnim(T spriggan, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(spriggan, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        leftLeg.y = 21;
        rightLeg.y = 21;
        head.y = 15;
        body.y = 16;
        leftArm.setPos(0.0F, 16.0F, 0.0F);
        rightArm.setPos(0.0F, 16.0F, 0.0F);

        WoodlingEntity.EarsType ears = spriggan.getEarsType();

        float health = ((spriggan.getMaxHealth() - spriggan.getHealth()) /spriggan.getMaxHealth())*0.9f;

        switch (ears){
            case HORNS:
                this.left_ear.visible = true;
                this.right_ear.visible = true;
                this.stick.visible = false;
                this.right_leaf.visible = false;
                this.left_leaf.visible = false;

                this.left_ear.zRot = -health;
                this.right_ear.zRot = -left_ear.zRot;


                break;
            case LEAVES:
                this.left_ear.visible = false;
                this.right_ear.visible = false;
                this.stick.visible = false;
                this.right_leaf.visible = true;
                this.left_leaf.visible = true;
                this.left_leaf.zRot = -health*2.1f;
                this.right_leaf.zRot = -left_leaf.zRot;
                break;
            case STICK:
                this.left_ear.visible = false;
                this.right_ear.visible = false;
                this.stick.visible = true;
                this.right_leaf.visible = false;
                this.left_leaf.visible = false;
                this.stick_leaf.zRot = -health;
                break;
        }
    }
}

