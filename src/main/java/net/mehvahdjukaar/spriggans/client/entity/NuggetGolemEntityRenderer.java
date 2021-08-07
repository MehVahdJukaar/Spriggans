package net.mehvahdjukaar.spriggans.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolenFlowerLayer;
import net.minecraft.client.renderer.entity.model.IronGolemModel;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class NuggetGolemEntityRenderer extends MobRenderer<IronGolemEntity, IronGolemModel<IronGolemEntity>> {
    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(Spriggans.MOD_ID, "textures/entity/nugget_golem.png");

    public NuggetGolemEntityRenderer(EntityRendererManager p_i46133_1_) {
        super(p_i46133_1_, new IronGolemModel<>(), 0.7F);
        this.addLayer(new IronGolenFlowerLayer(this));
    }

    public ResourceLocation getTextureLocation(IronGolemEntity p_110775_1_) {
        return GOLEM_LOCATION;
    }

    protected void setupRotations(IronGolemEntity p_225621_1_, MatrixStack p_225621_2_, float p_225621_3_, float p_225621_4_, float p_225621_5_) {
        super.setupRotations(p_225621_1_, p_225621_2_, p_225621_3_, p_225621_4_, p_225621_5_);
        if (!((double)p_225621_1_.animationSpeed < 0.01D)) {
            float f = 13.0F;
            float f1 = p_225621_1_.animationPosition - p_225621_1_.animationSpeed * (1.0F - p_225621_5_) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));

        }
    }
}