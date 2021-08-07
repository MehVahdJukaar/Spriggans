package net.mehvahdjukaar.spriggans.client.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SprigganHearthItemRenderer extends ItemStackTileEntityRenderer {
    private static final ModelRenderer heart = new ModelRenderer(16, 16, 0, 0);



    private static final ResourceLocation baseTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/spriggan_heart.png");

    private static final ResourceLocation overlayTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/spriggan_heart_e.png");

    static {
        heart.texOffs(0, 0).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        matrixStackIn.pushPose();
        long time = System.currentTimeMillis();
        int g = (int) (time % 1000);
        float t = g /1000f;

        float aa = 0;
        if(g<1000f/5){
            aa = MathHelper.sin((float) (Math.PI*t*5)) * 0.1F;
        }

        int u = combinedLightIn & '\uffff';
        int v = combinedLightIn >> 16 & '\uffff';


        v = 100;

        int overlayLight = v << 16 | v;


        float f = 1 + aa;
        matrixStackIn.translate(0.5, 0.5, 0.5);
        matrixStackIn.scale(3.75f,3.75f,3.75f);
        matrixStackIn.translate(0, 0.125, 0);
        matrixStackIn.scale(f,f,f);
        matrixStackIn.translate(0, -0.125, 0);


        heart.render(matrixStackIn,bufferIn.getBuffer(RenderType.entityCutout(baseTexture)),combinedLightIn,combinedOverlayIn);

        heart.render(matrixStackIn,bufferIn.getBuffer(RenderType.eyes(overlayTexture)),15728640, OverlayTexture.NO_OVERLAY);

        matrixStackIn.translate(1,0,0);


        matrixStackIn.popPose();

    }
}
