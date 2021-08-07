package net.mehvahdjukaar.spriggans.common;


import com.google.common.collect.ImmutableMap;
import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;

public enum SprigganType {

    OAK("oak"),
    BIRCH("birch"),
    SPRUCE("spruce"),
    JUNGLE("jungle"),
    ACACIA("acacia"),
    DARK_OAK("dark_oak"),
    SWAMP("swamp"),
    FLOWER("flower",ModRegistry.FLOWER_PARTICLE),
    SNOWY("snowy"),
    FROZEN("frozen",ModRegistry.SNOWFLAKE_PARTICLE),
    KELP("kelp", ()->ParticleTypes.FALLING_WATER),
    CORAL("coral",()->ParticleTypes.FALLING_WATER),
    CACTUS("cactus"),
    DRIED("dried"),
    MUSHROOM("mushroom",ModRegistry.SPORE_PARTICLE),
    WARPED("warped",ModRegistry.WARPED_PARTICLE),
    CRIMSON("crimson",ModRegistry.CRIMSON_PARTICLE),
    CHORUS("chorus",ModRegistry.CHORUS_PARTICLE),
    MELON("melon"),
    HAY("hay"),
    BEEHIVE("beehive");


    private final int glowColor;
    private final ResourceLocation texture;
    private final ResourceLocation eyesTexture;
    private final ResourceLocation elderTexture;
    private final ResourceLocation elderEyesTexture;
    private final ResourceLocation horseTexture;
    private final ResourceLocation horseEyesTexture;
    private final ResourceLocation woodlingTexture;
    private final String name;
    private final Supplier<BasicParticleType> particle;
    private final boolean hasBiomeColors;

    SprigganType(String name, int glowColor, Supplier<BasicParticleType> particle){
        this.name = name;
        this.texture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/spriggan/"+name+".png");
        this.eyesTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/spriggan/"+name+"_e.png");
        this.elderTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/elder_spriggan/"+name+".png");
        this.elderEyesTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/elder_spriggan/"+name+"_e.png");
        this.horseTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/horse/"+name+".png");
        this.horseEyesTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/horse/"+name+"_e.png");
        this.woodlingTexture = new ResourceLocation(Spriggans.MOD_ID+":textures/entity/woodling/"+name+".png");
        this.glowColor = glowColor;
        this.particle = particle;
        this.hasBiomeColors = particle == ModRegistry.LEAF_PARTICLE;
    }
    SprigganType(String name, Supplier<BasicParticleType> particle){
        this(name, -1, particle);
    }
    SprigganType(String name){
        this(name,-1, ModRegistry.LEAF_PARTICLE);
    }

    public BasicParticleType getParticle() {
        return particle.get();
    }

    public ResourceLocation getWoodlingTexture() {
        return woodlingTexture;
    }

    public ResourceLocation getHorseTexture() {
        return horseTexture;
    }

    public ResourceLocation getHorseEyesTexture() {
        return horseEyesTexture;
    }

    public ResourceLocation getEyesTexture() {
        return eyesTexture;
    }

    public ResourceLocation getElderEyesTexture() {
        return elderEyesTexture;
    }

    public ResourceLocation getElderTexture() {
        return elderTexture;
    }

    public boolean hasBiomeColors() {
        return hasBiomeColors;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getGlowColor() {
        return glowColor;
    }

    public String getName() {
        return name;
    }

    public static final List<String> birch_biomes = Arrays.asList("birch","aspen","autumnal");
    public static final List<String> flower_biomes = Arrays.asList("flower","flowering","blooming","cherry","sakura");

    public static final Map<Biome, SprigganType> BIOME_TO_TYPE_MAP = new HashMap<>();

    public static final Map<Biome, SprigganType> HORSE_BIOME_TO_TYPE_MAP = new HashMap<>();

    public static final Map<Biome, SprigganType> WOODLING_BIOME_TO_TYPE_MAP = new HashMap<>();

    public static SprigganType getType(Biome b){
        SprigganType type = BIOME_TO_TYPE_MAP.get(b);
        if(type!=null)return type;

        type = findType(b);

        BIOME_TO_TYPE_MAP.put(b,type);
        return type;
    }

    public static SprigganType getHorseType(Biome b){
        SprigganType type = HORSE_BIOME_TO_TYPE_MAP.get(b);
        if(type!=null)return type;

        type = findHorseType(b);

        HORSE_BIOME_TO_TYPE_MAP.put(b,type);
        return type;
    }


    public static SprigganType getWoodlingType(Biome b){
        SprigganType type = WOODLING_BIOME_TO_TYPE_MAP.get(b);
        if(type!=null)return type;

        type = findWoodlingType(b);

        WOODLING_BIOME_TO_TYPE_MAP.put(b,type);
        return type;
    }


    private static SprigganType findType(Biome biome){
        ResourceLocation biomeName = biome.getRegistryName();
        String name = biomeName.getPath();
        for(String s : flower_biomes){
            if(name.contains(s))return FLOWER;
        }
        for(String z : birch_biomes){
            if(name.contains(z))return BIRCH;
        }
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, biomeName);
        if(key == Biomes.WARPED_FOREST)return WARPED;

        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if(types.contains(BiomeDictionary.Type.END))return CHORUS;
        if(types.contains(BiomeDictionary.Type.NETHER))return CRIMSON;
        if(types.contains(BiomeDictionary.Type.SAVANNA))return ACACIA;
        if(types.contains(BiomeDictionary.Type.JUNGLE))return JUNGLE;
        if(types.contains(BiomeDictionary.Type.SWAMP))return SWAMP;
        if(types.contains(BiomeDictionary.Type.MESA))return DRIED;
        if(types.contains(BiomeDictionary.Type.SANDY))return CACTUS;
        boolean snow = types.contains(BiomeDictionary.Type.SNOWY);
        boolean forest = types.contains(BiomeDictionary.Type.FOREST);
        if(types.contains(BiomeDictionary.Type.MUSHROOM))return MUSHROOM;
        if(types.contains(BiomeDictionary.Type.SPOOKY))return DARK_OAK;
        if(types.contains(BiomeDictionary.Type.MAGICAL))return SWAMP;
        if(types.contains(BiomeDictionary.Type.CONIFEROUS)) {
            if(snow)return SNOWY;
            else return SPRUCE;
        }
        if(snow){
            return forest ? SPRUCE : FROZEN;
        }
        if(forest){
            if(name.contains("taiga"))return SPRUCE;
            return OAK;
        }
        if(types.contains(BiomeDictionary.Type.DEAD))return DRIED;
        if(types.contains(BiomeDictionary.Type.WATER)){
            if(name.contains("warm")||name.contains("tropical"))return CORAL;
            return KELP;
        }
        if(types.contains(BiomeDictionary.Type.BEACH))return KELP;
        if(types.contains(BiomeDictionary.Type.HOT))return CACTUS;
        if(types.contains(BiomeDictionary.Type.COLD))return SPRUCE;
        if(types.contains(BiomeDictionary.Type.MOUNTAIN))return SPRUCE;
        if(types.contains(BiomeDictionary.Type.WET))return SWAMP;
        return OAK;
    }

    private static SprigganType findHorseType(Biome biome){
        ResourceLocation biomeName = biome.getRegistryName();
        String name = biomeName.getPath();
        for(String z : birch_biomes){
            if(name.contains(z))return BIRCH;
        }
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, biomeName);


        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if(types.contains(BiomeDictionary.Type.JUNGLE))return JUNGLE;
        if(types.contains(BiomeDictionary.Type.SWAMP))return OAK;
        if(types.contains(BiomeDictionary.Type.WET))return JUNGLE;
        if(types.contains(BiomeDictionary.Type.HOT))return ACACIA;
        boolean snow = types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD);
        boolean forest = types.contains(BiomeDictionary.Type.FOREST);
        if(types.contains(BiomeDictionary.Type.SPOOKY))return DARK_OAK;
        if(types.contains(BiomeDictionary.Type.MAGICAL))return DARK_OAK;
        if(types.contains(BiomeDictionary.Type.CONIFEROUS) || snow) return SPRUCE;
        if(forest){
            if(name.contains("taiga"))return SPRUCE;
            return OAK;
        }
        if(types.contains(BiomeDictionary.Type.DEAD))return ACACIA;
        if(types.contains(BiomeDictionary.Type.MOUNTAIN))return SPRUCE;
        return OAK;
    }

    private static SprigganType findWoodlingType(Biome biome){
        ResourceLocation biomeName = biome.getRegistryName();
        String name = biomeName.getPath();
        for(String z : birch_biomes){
            if(name.contains(z))return BIRCH;
        }
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, biomeName);
        if(key == Biomes.WARPED_FOREST)return WARPED;

        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if(types.contains(BiomeDictionary.Type.WATER)){
            return KELP;
        }
        if(types.contains(BiomeDictionary.Type.BEACH))return KELP;



        if(types.contains(BiomeDictionary.Type.END))return CHORUS;
        if(types.contains(BiomeDictionary.Type.NETHER))return CRIMSON;
        if(types.contains(BiomeDictionary.Type.MUSHROOM))return MUSHROOM;

        if(types.contains(BiomeDictionary.Type.JUNGLE))return JUNGLE;
        if(types.contains(BiomeDictionary.Type.SWAMP))return OAK;
        if(types.contains(BiomeDictionary.Type.WET))return JUNGLE;
        if(types.contains(BiomeDictionary.Type.HOT))return ACACIA;
        boolean snow = types.contains(BiomeDictionary.Type.SNOWY) || types.contains(BiomeDictionary.Type.COLD);
        boolean forest = types.contains(BiomeDictionary.Type.FOREST);
        if(types.contains(BiomeDictionary.Type.SPOOKY))return DARK_OAK;
        if(types.contains(BiomeDictionary.Type.MAGICAL))return DARK_OAK;
        if(types.contains(BiomeDictionary.Type.CONIFEROUS) || snow) return SPRUCE;
        if(forest){
            if(name.contains("taiga"))return SPRUCE;
            return OAK;
        }
        if(types.contains(BiomeDictionary.Type.DEAD))return ACACIA;
        if(types.contains(BiomeDictionary.Type.MOUNTAIN))return SPRUCE;
        return OAK;
    }

    public static final Map<Block, SprigganType>woodlingConversionMap = new HashMap<Block, SprigganType>(){{
        put(Blocks.HAY_BLOCK,SprigganType.HAY);
        put(Blocks.MELON,SprigganType.MELON);
        put(Blocks.OAK_LOG,SprigganType.OAK);
        put(Blocks.SPRUCE_LOG,SprigganType.SPRUCE);
        put(Blocks.BIRCH_LOG,SprigganType.BIRCH);
        put(Blocks.ACACIA_LOG,SprigganType.ACACIA);
        put(Blocks.JUNGLE_LOG,SprigganType.JUNGLE);
        put(Blocks.DARK_OAK_LOG,SprigganType.DARK_OAK);
        put(Blocks.CRIMSON_STEM,SprigganType.CRIMSON);
        put(Blocks.WARPED_STEM,SprigganType.WARPED);
        put(Blocks.DRIED_KELP_BLOCK,SprigganType.KELP);
        put(Blocks.MUSHROOM_STEM,SprigganType.MUSHROOM);
        put(Blocks.RED_MUSHROOM_BLOCK,SprigganType.MUSHROOM);
        put(Blocks.BROWN_MUSHROOM_BLOCK,SprigganType.MUSHROOM);
        put(Blocks.BEE_NEST,SprigganType.BEEHIVE);
    }};





}
