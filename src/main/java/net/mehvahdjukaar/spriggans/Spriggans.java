package net.mehvahdjukaar.spriggans;

import net.mehvahdjukaar.spriggans.common.Events;
import net.mehvahdjukaar.spriggans.init.ClientSetup;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.mehvahdjukaar.spriggans.init.ModSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Spriggans.MOD_ID)
public class Spriggans {
    public static final String MOD_ID = "spriggans";

    private static final Logger LOGGER = LogManager.getLogger();

    public Spriggans() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(Events.class);

        ModRegistry.init(bus);

        bus.addListener(ModSetup::init);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::init));
    }


}
