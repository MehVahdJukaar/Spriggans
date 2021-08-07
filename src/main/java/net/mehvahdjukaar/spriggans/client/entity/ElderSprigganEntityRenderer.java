package net.mehvahdjukaar.spriggans.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.client.models.ElderSprigganEntityModel;
import net.mehvahdjukaar.spriggans.client.models.HeartLayer;
import net.mehvahdjukaar.spriggans.client.models.SprigganEyesLayer;
import net.mehvahdjukaar.spriggans.entity.ElderSprigganEntity;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;

public class ElderSprigganEntityRenderer extends BipedRenderer<ElderSprigganEntity, ElderSprigganEntityModel<ElderSprigganEntity>> {


    public ElderSprigganEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ElderSprigganEntityModel<>(), 0.5F);
        this.addLayer(new SprigganEyesLayer<>(this, true));
        this.addLayer(new HeartLayer(this));
        this.addLayer(new BipedArmorLayer<>(this, new SkeletonModel(0.5F, true), new SkeletonModel(1.0F, true)));
    }

    protected void scale(SprigganEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
    }

    @Override
    public ResourceLocation getTextureLocation(ElderSprigganEntity entity) {
        return entity.getSprigganType().getElderTexture();
    }
}
