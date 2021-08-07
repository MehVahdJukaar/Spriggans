package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.entity.ElderSprigganEntity;
import net.mehvahdjukaar.spriggans.entity.SprigganHorseEntity;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class HorseHeartLayer extends LayerRenderer<SprigganHorseEntity, SprigganHorseModel> {



    private static final ModelRenderer heart = new ModelRenderer(32, 32, 0, 0);

    static {
        //heart.setPos(0.0F, 11.0F, 6.0F);
        heart.texOffs(0, 0).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 3.0F, 6.0F, 0.05F, false);
    }

    public HorseHeartLayer(IEntityRenderer<SprigganHorseEntity, SprigganHorseModel> p_i50926_1_) {
        super(p_i50926_1_);

    }

    private static final ResourceLocation baseTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/horse/heart.png");

    private static final ResourceLocation emissiveTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/horse/heart_e.png");

    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, SprigganHorseEntity entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {

        matrixStack.pushPose();
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
        //matrixStack.translate(0.5, 0, 0.5);
        //matrixStack.scale(3.75f,3.75f,3.75f);
        //matrixStack.translate(0, 0.125, 0);
        //heart.xRot = this.getParentModel().body.xRot;
        this.getParentModel().body.translateAndRotate(matrixStack);
        //matrixStack.mulPose(Vector3f.XP.rotation(this.getParentModel().body.xRot));
        matrixStack.translate(1/16f, -0.125, -0.5-2/16f);
        matrixStack.scale(f,f,f);





        heart.render(matrixStack,buffer.getBuffer(RenderType.entityCutout(baseTexture)),combinedLightIn, OverlayTexture.NO_OVERLAY);

        heart.render(matrixStack,buffer.getBuffer(RenderType.eyes(emissiveTexture)),15728640, OverlayTexture.NO_OVERLAY);

        //matrixStack.translate(1,0,0);


        matrixStack.popPose();
    }

}
