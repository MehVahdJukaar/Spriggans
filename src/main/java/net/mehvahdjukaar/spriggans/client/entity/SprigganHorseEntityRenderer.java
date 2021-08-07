package net.mehvahdjukaar.spriggans.client.entity;

import net.mehvahdjukaar.spriggans.client.models.HorseHeartLayer;
import net.mehvahdjukaar.spriggans.client.models.SprigganHorseEyesLayer;
import net.mehvahdjukaar.spriggans.client.models.SprigganHorseModel;
import net.mehvahdjukaar.spriggans.entity.SprigganHorseEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class SprigganHorseEntityRenderer extends MobRenderer<SprigganHorseEntity, SprigganHorseModel> {

    public SprigganHorseEntityRenderer(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_, new SprigganHorseModel(), 0);
        this.addLayer(new SprigganHorseEyesLayer(this));
        this.addLayer(new HorseHeartLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SprigganHorseEntity entity) {
        return entity.getSprigganType().getHorseTexture();
    }
}
