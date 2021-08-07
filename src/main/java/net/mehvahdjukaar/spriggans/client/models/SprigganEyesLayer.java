package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class SprigganEyesLayer<T extends SprigganEntity, M extends SprigganEntityModel<T>> extends AbstractEyesLayer<T, M> {
    private final boolean elder;
    public SprigganEyesLayer(IEntityRenderer<T, M> p_i50921_1_, boolean elder) {
        super(p_i50921_1_);
        this.elder = elder;
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T spriggan, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        IVertexBuilder ivertexbuilder = p_225628_2_.getBuffer(RenderType.eyes(this.getTextureLocation(spriggan)));
        this.getParentModel().renderToBuffer(p_225628_1_, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public RenderType renderType() {
        return null;
    }

    @Override
    protected ResourceLocation getTextureLocation(T spriggan) {
        if(elder)return spriggan.getSprigganType().getElderEyesTexture();
        return spriggan.getSprigganType().getEyesTexture();
    }
}