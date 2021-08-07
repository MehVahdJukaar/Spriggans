package net.mehvahdjukaar.spriggans.client.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.mehvahdjukaar.spriggans.client.models.SprigganEntityModel;
import net.mehvahdjukaar.spriggans.client.models.SprigganEyesLayer;
import net.mehvahdjukaar.spriggans.client.models.WoodlingEntityModel;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.mehvahdjukaar.spriggans.entity.WoodlingEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.SkeletonModel;
import net.minecraft.util.ResourceLocation;

public class WoodlingEntityRenderer extends BipedRenderer<WoodlingEntity, WoodlingEntityModel<WoodlingEntity>> {


    public WoodlingEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager, new WoodlingEntityModel<>(), 0.25F);

    }



    @Override
    public ResourceLocation getTextureLocation(WoodlingEntity entity) {
        return entity.getSprigganType().getWoodlingTexture();
    }
}
