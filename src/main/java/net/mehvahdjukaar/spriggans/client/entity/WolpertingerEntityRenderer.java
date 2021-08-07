package net.mehvahdjukaar.spriggans.client.entity;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.client.models.WolpertingerEntityModel;
import net.mehvahdjukaar.spriggans.entity.WolpertingerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WolpertingerEntityRenderer extends MobRenderer<WolpertingerEntity, WolpertingerEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Spriggans.MOD_ID, "textures/entity/wolpertinger.png");
    public WolpertingerEntityRenderer(EntityRendererManager p_i47196_1_) {
        super(p_i47196_1_, new WolpertingerEntityModel(), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(WolpertingerEntity p_110775_1_) {

        return TEXTURE;
    }

}
