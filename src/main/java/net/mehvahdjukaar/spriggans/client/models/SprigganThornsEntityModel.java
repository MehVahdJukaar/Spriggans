package net.mehvahdjukaar.spriggans.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class SprigganThornsEntityModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer base;
    private final ModelRenderer middle;
    private final ModelRenderer top;

    public SprigganThornsEntityModel() {
        texHeight = 32;
        texWidth = 32;
        base = new ModelRenderer(this);
        base.setPos(5.0F, 12.0F, -5.0F);
        base.texOffs(0, 0).addBox(-8.0F, 3.0F, 2.0F, 6.0F, 9.0F, 6.0F, 0.0F, false);

        middle = new ModelRenderer(this);
        middle.setPos(1.5F, 17.0F, 0.0F);
        middle.texOffs(0, 15).addBox(-3.5F, -9.0F, -2.0F, 4.0F, 9.0F, 4.0F, 0.0F, false);

        top = new ModelRenderer(this);
        top.setPos(-1.5F, 7.0F, 0.0F);
        middle.addChild(top);
        top.yRot = 0.7854F;
        top.texOffs(22, 15).addBox(-2.5F, -20.0F, 0.0F, 5.0F, 4.0F, 0.0F, 0.0F, false);
        top.texOffs(22, 15).addBox(0.0F, -20.0F, -2.5F, 0.0F, 4.0F, 5.0F, 0.0F, false);
    }

    public void setupAnim(T thornsEntity, float animationProgress, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        float f = animationProgress * 2.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        f = 0.5f- f * f * f;
        this.middle.zRot =  - f * 0.6F * (float)Math.PI;
        //this.lowerJaw.zRot = (float)Math.PI + f * 0.35F * (float)Math.PI;
        //this.lowerJaw.yRot = (float)Math.PI;
        float f1 = (animationProgress + MathHelper.sin(animationProgress * 2.7F)) * 0.6F * 12.0F;
        this.base.y = 24.0F - f1;
        this.middle.y = this.base.y+5;

    }

    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.base, this.middle);
    }
}