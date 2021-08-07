package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OwlHeldItemLayer extends LayerRenderer<OwlEntity, OwlModel> {
    public OwlHeldItemLayer(IEntityRenderer<OwlEntity, OwlModel> renderer) {
        super(renderer);
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int p_225628_3_, OwlEntity owl, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {

        boolean isBaby = owl.isBaby();
        boolean isFlying = owl.isFlying();

        matrixStack.pushPose();

        this.getParentModel().translateToFeet(matrixStack);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        //matrixStack.translate(0,14/16f,0);


        if (isBaby) {
            float f = 0.75F;
            matrixStack.scale(0.75F, 0.75F, 0.75F);
            matrixStack.translate(0.0D, 0.5D, 0.209375F);
        }

        //matrixStack.translate((this.getParentModel()).head.x / 16.0F, (this.getParentModel()).head.y / 16.0F, (double)((this.getParentModel()).head.z / 16.0F));
        //float f1 = owl.getHeadRollAngle(p_225628_7_);
        //matrixStack.mulPose(Vector3f.ZP.rotation(f1));
        //matrixStack.mulPose(Vector3f.YP.rotationDegrees(p_225628_9_));
        //matrixStack.mulPose(Vector3f.XP.rotation(owl.isFlying()?0.8f:0f));
        if (isBaby) {
            matrixStack.translate(0.06F, 0.26F, -0.5D);
        }
        else {
            //matrixStack.translate(0.06F, 0.27F, -0.5D);
        }

        //matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));


        ItemStack itemstack = owl.getItemBySlot(EquipmentSlotType.MAINHAND);
        Minecraft.getInstance().getItemInHandRenderer().renderItem(owl, itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStack, buffer, p_225628_3_);
        matrixStack.popPose();
    }
}