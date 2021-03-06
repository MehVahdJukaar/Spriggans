package net.mehvahdjukaar.spriggans.datagen;


import net.mehvahdjukaar.spriggans.Spriggans;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Spriggans.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    public DataGenerators(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if(true) {
            gen.addProvider(new ModBlockStateProvider(gen, Spriggans.MOD_ID, helper));
            gen.addProvider(new ModLanguageProvider(gen, Spriggans.MOD_ID, "en_us"));

        }

    }
}
