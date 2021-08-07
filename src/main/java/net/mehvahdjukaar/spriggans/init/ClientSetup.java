package net.mehvahdjukaar.spriggans.init;

import net.mehvahdjukaar.spriggans.client.entity.*;
import net.mehvahdjukaar.spriggans.client.particle.SprigganLeafParticle;
import net.mehvahdjukaar.spriggans.client.tile.FlowerBoxBlockTileRenderer;
import net.mehvahdjukaar.spriggans.client.tile.TankarcBlockTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.SPRIGGAN.get(), SprigganEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.ELDER_SPRIGGAN.get(), ElderSprigganEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.SPRIGGAN_HORSE.get(), SprigganHorseEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.WOODLING.get(), WoodlingEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.THORNS.get(), SprigganThornsEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.OWL.get(), OwlEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModRegistry.WOLPERTINGER.get(), WolpertingerEntityRenderer::new);

        ItemModelsProperties.register(ModRegistry.SPRIGGAN_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getUseItem() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemModelsProperties.register(ModRegistry.SPRIGGAN_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isUsingItem() && p_239428_2_.getUseItem() == p_239428_0_ ? 1.0F : 0.0F);


        ClientRegistry.bindTileEntityRenderer(ModRegistry.TANKARD_TILE.get(), TankarcBlockTileRenderer::new);

        ClientRegistry.bindTileEntityRenderer(ModRegistry.FLOWER_BOX_TILE.get(), FlowerBoxBlockTileRenderer::new);
    }

    //particles
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleManager particleManager = Minecraft.getInstance().particleEngine;
        particleManager.register(ModRegistry.LEAF_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.SPORE_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.FLOWER_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.WARPED_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.CRIMSON_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.SNOWFLAKE_PARTICLE.get(), SprigganLeafParticle.Factory::new);
        particleManager.register(ModRegistry.CHORUS_PARTICLE.get(), SprigganLeafParticle.Factory::new);
    }
}
