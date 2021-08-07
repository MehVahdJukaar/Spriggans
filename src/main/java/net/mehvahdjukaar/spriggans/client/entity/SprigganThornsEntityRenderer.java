package net.mehvahdjukaar.spriggans.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.client.models.SprigganThornsEntityModel;
import net.mehvahdjukaar.spriggans.entity.SprigganThornsEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SprigganThornsEntityRenderer extends EntityRenderer<SprigganThornsEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Spriggans.MOD_ID,"textures/entity/spriggan_thorns.png");
    private final SprigganThornsEntityModel<SprigganThornsEntity> model = new SprigganThornsEntityModel<>();

    public SprigganThornsEntityRenderer(EntityRendererManager p_i47208_1_) {
        super(p_i47208_1_);
    }

    public void render(SprigganThornsEntity thornsEntity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        float f = thornsEntity.getAnimationProgress(p_225623_3_);
        if (f != 0.0F) {
            float f1 = 2.0F;
            if (f > 0.9F) {
                f1 = (float)((double)f1 * ((1.0D - (double)f) / (double)0.1F));
            }

            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F - thornsEntity.yRot));
            matrixStack.scale(-f1, -f1, f1);
            float f2 = 0.03125F;
            matrixStack.translate(0.0D, (double)-0.626F, 0.0D);
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            this.model.setupAnim(thornsEntity, f, 0.0F, 0.0F, thornsEntity.yRot, thornsEntity.xRot);
            IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            this.model.renderToBuffer(matrixStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.popPose();
            super.render(thornsEntity, p_225623_2_, p_225623_3_, matrixStack, p_225623_5_, p_225623_6_);
        }
    }

    public ResourceLocation getTextureLocation(SprigganThornsEntity p_110775_1_) {
        return TEXTURE_LOCATION;
    }
}