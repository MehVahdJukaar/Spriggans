package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.entity.ElderSprigganEntity;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;


public class HeartLayer extends LayerRenderer<ElderSprigganEntity, ElderSprigganEntityModel<ElderSprigganEntity>> {
    public HeartLayer(IEntityRenderer<ElderSprigganEntity, ElderSprigganEntityModel<ElderSprigganEntity>> p_i50949_1_) {
        super(p_i50949_1_);
    }


    private final ItemStack heart = new ItemStack(ModRegistry.SPRIGGAN_HEART.get());

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, ElderSprigganEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        IBakedModel ibakedmodel = itemRenderer.getModel(heart, entity.level, null);

        matrixStack.pushPose();

        matrixStack.translate(0.03125,0.25+this.getParentModel().body.y/16f,0);
        matrixStack.scale(0.5f,0.5f,0.5f);


        itemRenderer.render(heart, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLightIn,
                OverlayTexture.NO_OVERLAY, ibakedmodel);


        matrixStack.popPose();
    }
}
