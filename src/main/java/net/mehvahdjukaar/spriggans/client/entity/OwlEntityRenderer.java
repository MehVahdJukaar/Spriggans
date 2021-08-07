package net.mehvahdjukaar.spriggans.client.entity;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.client.models.OwlEyesLayer;
import net.mehvahdjukaar.spriggans.client.models.OwlHeldItemLayer;
import net.mehvahdjukaar.spriggans.client.models.OwlModel;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OwlEntityRenderer extends MobRenderer<OwlEntity, OwlModel> {

    public OwlEntityRenderer(EntityRendererManager manager) {
        super(manager, new OwlModel(), 0.3F);
        this.addLayer(new OwlEyesLayer(this));
        this.addLayer(new OwlHeldItemLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(OwlEntity entity) {
        return entity.isSleeping() ? entity.getOwlType().getSleepingTexture() : entity.getOwlType().getTexture();
    }


}