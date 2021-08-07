package net.mehvahdjukaar.spriggans.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.client.models.HorseHeartLayer;
import net.mehvahdjukaar.spriggans.client.models.SprigganEntityModel;
import net.mehvahdjukaar.spriggans.client.models.SprigganEyesLayer;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class SprigganEntityRenderer extends BipedRenderer<SprigganEntity, SprigganEntityModel<SprigganEntity>> {


    public SprigganEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SprigganEntityModel<>(), 0.5F);
        this.addLayer(new SprigganEyesLayer<>(this, false));

        this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel(0.5F, true), new SkeletonModel(1.0F, true)));
    }

    protected void scale(SprigganEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        p_225620_2_.scale(0.95f, 0.95f, 0.95f);

    }


    @Override
    public ResourceLocation getTextureLocation(SprigganEntity entity) {
        return entity.getSprigganType().getTexture();
    }
}
