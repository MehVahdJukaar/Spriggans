package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;


public class OwlEyesLayer extends AbstractEyesLayer<OwlEntity, OwlModel>  {


    public OwlEyesLayer(IEntityRenderer<OwlEntity, OwlModel> renderer) {
        super(renderer);

    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int p_225628_3_, OwlEntity owl, float p_225628_5_, float p_225628_6_, float r, float g, float b, float a) {
        if(owl.isSleeping())return;
        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.eyes(this.getTextureLocation(owl)));
        this.getParentModel().renderToBuffer(matrixStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }


    public RenderType renderType() {
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(OwlEntity entity) {
        return entity.getOwlType().getEyesTexture();
    }
}