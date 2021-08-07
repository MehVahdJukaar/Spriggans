package net.mehvahdjukaar.spriggans.init;

import net.mehvahdjukaar.selene.fluids.FluidTextures;
import net.mehvahdjukaar.selene.fluids.SoftFluid;
import net.mehvahdjukaar.selene.fluids.SoftFluidRegistry;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.common.OwlType;
import net.mehvahdjukaar.spriggans.common.SprigganType;
import net.mehvahdjukaar.spriggans.entity.OwlEntity;
import net.mehvahdjukaar.spriggans.entity.WoodlingEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.UUID;


public class ModSetup {

    public static final SoftFluid HEART_JUICE;

    static{
        HEART_JUICE = new SoftFluid(new SoftFluid.Builder(FluidTextures.POTION_TEXTURE, FluidTextures.POTION_TEXTURE_FLOW,"heart_juice")
                .color(0x00ee00)
                .luminosity(12)
                .emptyHandContainerItem(ModRegistry.SPRIGGAN_HEART.get(),1)
                .food(Items.GOLDEN_APPLE,8)
                .translationKey("fluid.spriggans.heart_juice"));
    }

    public static void init(final FMLCommonSetupEvent event) {

        SoftFluidRegistry.register(HEART_JUICE);

    }


}
