package net.mehvahdjukaar.spriggans.datagen;

import net.mehvahdjukaar.spriggans.init.ModRegistry;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator gen, String modId, String locale) {
        super(gen, modId, locale);
    }



    @Override
    protected void addTranslations() {

        for(DyeColor color : DyeColor.values()){
            add(ModRegistry.PRESENTS.get(color).get(),format(color.toString()+"_"+ModRegistry.PRESENT_NAME));
        }
    }
    public static String format(String name){
        String[] words = name.split("_");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0,1).toUpperCase() + words[i].substring(1);
        }
        StringBuilder ret = new StringBuilder();
        for (String s : words){
            ret.append(s);
            if(!s.equals(words[words.length-1]))ret.append(" ");
        }
        return ret.toString();
    }



}
