package net.mehvahdjukaar.spriggans.common;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public enum OwlType {
    HORNED("horned"),
    SNOW("snow"),
    BARN("barn"),
    LITTLE("little"),
    BARRED("barred"),
    FISHER("fisher"),
    EAGLE("eagle"),
    MOON("moon"),
    DUO("duo");

    private String name;
    private ResourceLocation texture;
    private ResourceLocation sleepingTexture;
    private ResourceLocation eyesTexture;

    OwlType(String name){
        this.name = name;
        this.texture = new ResourceLocation(Spriggans.MOD_ID, "textures/entity/owl/"+name+".png");
        this.sleepingTexture = new ResourceLocation(Spriggans.MOD_ID, "textures/entity/owl/"+name+"_sleep.png");
        this.eyesTexture = new ResourceLocation(Spriggans.MOD_ID, "textures/entity/owl/"+name+"_e.png");

    }



    public String getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ResourceLocation getSleepingTexture() {
        return sleepingTexture;
    }

    public ResourceLocation getEyesTexture() {
        return eyesTexture;
    }

    public static OwlType fromNBT(byte b){
        return OwlType.values()[b%OwlType.values().length];
    }

    public static OwlType getOwlType(Biome biome, Random rand){
        return OwlType.values()[rand.nextInt(OwlType.values().length)];
    }

}
