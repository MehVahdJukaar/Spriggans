package net.mehvahdjukaar.spriggans.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class BarkArmorModel extends BipedModel<LivingEntity> {

    public static final BarkArmorModel innerModel = new BarkArmorModel( 0.25f);
    public static final BarkArmorModel outerModel = new BarkArmorModel( 0.5f);

    private static final String overlay1 = "spriggans:textures/models/armor/bark_layer_e1.png";
    private static final String overlay2 = "spriggans:textures/models/armor/bark_layer_e2.png";

    public BarkArmorModel(float p_i1148_1_) {
        super(p_i1148_1_);
        texWidth = 64;
        texHeight = 32;

        head = new ModelRenderer(this);

        head = new ModelRenderer(this);

        head.setPos(0.0F, 0.0F, 0.0F);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i1148_1_, false);
        head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i1148_1_, false);
        head.texOffs(24, 0).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 8.0F, 0.0F, p_i1148_1_, false);

        hat.visible = false;
    }

    @Override
    public void setupAnim(LivingEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
    }

    @Override
    public void setAllVisible(boolean p_178719_1_) {
        super.setAllVisible(p_178719_1_);
    }
    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float r, float g, float b, float a) {
        if(r==1 && b==1 && g == 1){

            int u = light & '\uffff';
            int v = light >> 16 & '\uffff';


            long time = System.currentTimeMillis();
            int gg = (int) (time % 1000);
            float t = gg /1000f;


            int aa = Math.max(u,(int) ( (1 + MathHelper.sin((float) (Math.PI*t))) * 120));




            light = v << 16 | aa;
        }

        super.renderToBuffer(matrixStack, vertexBuilder, light, overlay, 1, 1, 1, 1);



    }

}
