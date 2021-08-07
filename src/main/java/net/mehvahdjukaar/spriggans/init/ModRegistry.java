package net.mehvahdjukaar.spriggans.init;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.blocks.*;
import net.mehvahdjukaar.spriggans.client.items.SprigganHearthItemRenderer;
import net.mehvahdjukaar.spriggans.entity.*;
import net.mehvahdjukaar.spriggans.items.BarkArmorItem;
import net.mehvahdjukaar.spriggans.items.BarkArmorMaterial;
import net.mehvahdjukaar.spriggans.items.BurnableItem;
import net.mehvahdjukaar.spriggans.items.SprigganBow;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Spriggans.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Spriggans.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Spriggans.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Spriggans.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Spriggans.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Spriggans.MOD_ID);


    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILES.register(bus);
        ENTITIES.register(bus);
        PARTICLES.register(bus);
        SOUNDS.register(bus);
    }

    //creative tab

    public static final ItemGroup MOD_TAB = new ItemGroup("spriggans") {
                @Override
                public ItemStack makeIcon() {
                    return new ItemStack(ModRegistry.SPRIGGAN_HEART.get());
                }
                public boolean hasSearchBar() {
                    return false;
                }
            };


    private static RegistryObject<Item> regItem(String name, Supplier<? extends Item> sup) {
        return ITEMS.register(name, sup);
    }

    protected static RegistryObject<Item> regBlockItem(RegistryObject<Block> blockSup, ItemGroup group) {
        return regItem(blockSup.getId().getPath(), ()-> new BlockItem(blockSup.get(), (new Item.Properties()).tab(group)));
    }

    public static final String SPRIGGAN_HEART_NAME = "spriggan_heart";
    public static final RegistryObject<Item> SPRIGGAN_HEART = ITEMS.register(SPRIGGAN_HEART_NAME, ()->new Item(
            new Item.Properties().tab(MOD_TAB).setISTER(()->SprigganHearthItemRenderer::new)));

    public static final String BARK_NAME = "bark";
    public static final RegistryObject<Item> BARK = ITEMS.register(BARK_NAME, ()->new BurnableItem(
            new Item.Properties().tab(MOD_TAB),150));

    public static final BarkArmorMaterial armorMaterial = new BarkArmorMaterial();

    public static final String BARK_ARMOR_HELMET_NAME = "bark_helmet";
    public static final RegistryObject<Item> BARK_ARMOR_HELMET = ITEMS.register(BARK_ARMOR_HELMET_NAME,
            () -> new BarkArmorItem(armorMaterial, EquipmentSlotType.HEAD, new Item.Properties().tab(MOD_TAB)));

    public static final String BARK_ARMOR_CHEST_NAME = "bark_chestplate";
    public static final RegistryObject<Item> BARK_ARMOR_CHEST = ITEMS.register(BARK_ARMOR_CHEST_NAME,
            () -> new BarkArmorItem(armorMaterial, EquipmentSlotType.CHEST, new Item.Properties().tab(MOD_TAB)));

    public static final String BARK_ARMOR_LEGGINGS_NAME = "bark_leggings";
    public static final RegistryObject<Item> BARK_ARMOR_LEGGINGS = ITEMS.register(BARK_ARMOR_LEGGINGS_NAME,
            ()-> new BarkArmorItem(armorMaterial, EquipmentSlotType.LEGS, new Item.Properties().tab(MOD_TAB)));

    public static final String BARK_ARMOR_BOOTS_NAME = "bark_boots";
    public static final RegistryObject<Item> BARK_ARMOR_BOOTS = ITEMS.register(BARK_ARMOR_BOOTS_NAME,
            () -> new BarkArmorItem(armorMaterial, EquipmentSlotType.FEET, new Item.Properties().tab(MOD_TAB)));



    public static final String SPRIGGAN_BOW_NAME = "spriggan_bow";
    public static final RegistryObject<Item> SPRIGGAN_BOW = ITEMS.register(SPRIGGAN_BOW_NAME, ()->new SprigganBow( new Item.Properties().tab(MOD_TAB)));

    private static RegistryObject<Item> makeSpawnEgg(EntityType<?> type_raw,String name, int outerColor, int innerColor){
        return ITEMS.register(name+"_spawn_egg",()-> new SpawnEggItem(type_raw,  outerColor, innerColor,
                new Item.Properties().tab(MOD_TAB)));
    }


    public static final String SPRIGGAN_NAME = "spriggan";
    private static final EntityType<SprigganEntity> SPRIGGAN_TYPE_RAW = (EntityType.Builder.of(SprigganEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(0.6f, 2*0.95f))
            .build(SPRIGGAN_NAME);

    public static final RegistryObject<EntityType<SprigganEntity>> SPRIGGAN = ENTITIES.register(SPRIGGAN_NAME,()->SPRIGGAN_TYPE_RAW);

    public static final RegistryObject<Item> SPRIGGAN_SPAWN_EGG_ITEM = makeSpawnEgg(SPRIGGAN_TYPE_RAW,SPRIGGAN_NAME,0x4d3c1a,0x22a728);

    public static final String ELDER_SPRIGGAN_NAME = "elder_spriggan";
    private static final EntityType<ElderSprigganEntity> ELDER_SPRIGGAN_TYPE_RAW = (EntityType.Builder.of(ElderSprigganEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(0.6f, 2f))
            .build(ELDER_SPRIGGAN_NAME);

    public static final RegistryObject<EntityType<ElderSprigganEntity>> ELDER_SPRIGGAN = ENTITIES.register(ELDER_SPRIGGAN_NAME,()->ELDER_SPRIGGAN_TYPE_RAW);

    public static final RegistryObject<Item> ELDER_SPRIGGAN_SPAWN_EGG_ITEM = makeSpawnEgg(ELDER_SPRIGGAN_TYPE_RAW,ELDER_SPRIGGAN_NAME,0x4d3c1a,0x19DE2B);


    public static final String THORNS_NAME = "spriggan_thorns";
    public static final RegistryObject<EntityType<SprigganThornsEntity>> THORNS = ENTITIES.register(THORNS_NAME,()->(
            EntityType.Builder.<SprigganThornsEntity>of(SprigganThornsEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory(SprigganThornsEntity::new)
                    .sized(0.5F, 0.8F)
                    .clientTrackingRange(6).
                    updateInterval(2))
            .build(THORNS_NAME));


    public static final String BRANCH_NAME = "spriggan_branch";
    public static final RegistryObject<Item> BRANCH = ITEMS.register(BRANCH_NAME,()-> new SwordItem(
            ItemTier.STONE, 4, -2.8F, (new Item.Properties()).tab(MOD_TAB)));



    public static final String WOLPERTINGER_NAME = "wolpertinger";
    private static final EntityType<WolpertingerEntity> WOLPERTINGER_TYPE_RAW = (EntityType.Builder.of(WolpertingerEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(0.6f, 0.6f))
            .build(WOLPERTINGER_NAME);

    public static final RegistryObject<EntityType<WolpertingerEntity>> WOLPERTINGER = ENTITIES.register(WOLPERTINGER_NAME,()->WOLPERTINGER_TYPE_RAW);

    public static final RegistryObject<Item> WOLPERTINGER_SPAWN_EGG_ITEM = makeSpawnEgg(WOLPERTINGER_TYPE_RAW,WOLPERTINGER_NAME,0x4d3c1a,0xffee00);




    public static final String OWL_NAME = "owl";
    private static final EntityType<OwlEntity> OWL_TYPE_RAW = (EntityType.Builder.of(OwlEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(0.6f, 15/16f))
            .build(OWL_NAME);

    public static final RegistryObject<EntityType<OwlEntity>> OWL = ENTITIES.register(OWL_NAME,()->OWL_TYPE_RAW);

    public static final RegistryObject<Item> OWL_SPAWN_EGG_ITEM = makeSpawnEgg(OWL_TYPE_RAW,OWL_NAME,0x32211a,0xa4935d);

    public static final String SPRIGGAN_HORSE_NAME = "spriggan_horse";
    private static final EntityType<SprigganHorseEntity> SPRIGGAN_HORSE_TYPE_RAW = (EntityType.Builder.of(SprigganHorseEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(10)
            .sized(1.3964844F, 1.6F))
            .build(SPRIGGAN_HORSE_NAME);


    public static final RegistryObject<EntityType<SprigganHorseEntity>> SPRIGGAN_HORSE = ENTITIES.register(SPRIGGAN_HORSE_NAME,()->SPRIGGAN_HORSE_TYPE_RAW);

    public static final RegistryObject<Item> SPRIGGAN_HORSE_SPAWN_EGG_ITEM = makeSpawnEgg(SPRIGGAN_HORSE_TYPE_RAW,SPRIGGAN_HORSE_NAME,0x4d1c1a,0x22a128);




    public static final String WOODLING_NAME = "woodling";
    private static final EntityType<WoodlingEntity> WOODLING_TYPE_RAW = (EntityType.Builder.of(WoodlingEntity::new, EntityClassification.CREATURE)
            .setTrackingRange(10)
            .sized(0.5f, 1F))
            .build(WOODLING_NAME);


    public static final RegistryObject<EntityType<WoodlingEntity>> WOODLING = ENTITIES.register(WOODLING_NAME,()->WOODLING_TYPE_RAW);

    public static final RegistryObject<Item> WOODLING_SPAWN_EGG_ITEM = makeSpawnEgg(WOODLING_TYPE_RAW,WOODLING_NAME,0x4eec1a,0x21a1f8);




    //flower box
    public static final String FLOWER_BOX_NAME = "flower_box";
    public static final RegistryObject<Block> FLOWER_BOX = BLOCKS.register(FLOWER_BOX_NAME,()-> new FlowerBoxBlock(
            AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR))
    );
    public static final RegistryObject<Item> FLOWER_BOX_ITEM = regBlockItem(FLOWER_BOX, MOD_TAB);

    public static final RegistryObject<TileEntityType<FlowerBoxBlockTile>> FLOWER_BOX_TILE = TILES.register(FLOWER_BOX_NAME,()-> TileEntityType.Builder.of(
            FlowerBoxBlockTile::new, FLOWER_BOX.get()).build(null));

    public static final String TANKARD_NAME = "tankard";
    public static final RegistryObject<Block> TANKARD = BLOCKS.register(TANKARD_NAME,()-> new TankardBlock(
            AbstractBlock.Properties.copy(Blocks.SPRUCE_TRAPDOOR)));

    public static final RegistryObject<Item> GOBLET_ITEM = regBlockItem(TANKARD, MOD_TAB);

    public static final RegistryObject<TileEntityType<TankardBlockTile>> TANKARD_TILE = TILES.register(TANKARD_NAME,()-> TileEntityType.Builder.of(
            TankardBlockTile::new, TANKARD.get()).build(null));



    public static final RegistryObject<BasicParticleType> LEAF_PARTICLE = PARTICLES
            .register("spriggan_leaf", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SPORE_PARTICLE = PARTICLES
            .register("spriggan_spore", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> CRIMSON_PARTICLE = PARTICLES
            .register("spriggan_crimson", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> WARPED_PARTICLE = PARTICLES
            .register("spriggan_warped", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> CHORUS_PARTICLE = PARTICLES
            .register("spriggan_chorus", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> FLOWER_PARTICLE = PARTICLES
            .register("spriggan_flower", ()-> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> SNOWFLAKE_PARTICLE = PARTICLES
            .register("spriggan_snow", ()-> new BasicParticleType(true));

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event){
        event.put(ModRegistry.SPRIGGAN.get(), SprigganEntity.setCustomAttributes().build());
        event.put(ModRegistry.ELDER_SPRIGGAN.get(), SprigganEntity.setCustomAttributes().build());
        event.put(ModRegistry.SPRIGGAN_HORSE.get(), SprigganHorseEntity.setCustomAttributes().build());
        event.put(ModRegistry.OWL.get(), OwlEntity.setCustomAttributes().build());
        event.put(ModRegistry.WOLPERTINGER.get(),WolpertingerEntity.createAttributes().build());
        event.put(ModRegistry.WOODLING.get(),WoodlingEntity.setCustomAttributes().build());
    }


    public static final String PRESENT_NAME = "present";
    public static final Map<DyeColor, RegistryObject<Block>> PRESENTS = makePresents(PRESENT_NAME);


    //presents
    public static Map<DyeColor, RegistryObject<Block>> makePresents(String baseName){
        Map<DyeColor, RegistryObject<Block>> map = new HashMap<>();

        for(DyeColor color : DyeColor.values()){
            String name = baseName+"_"+color.getName();
            map.put(color, BLOCKS.register(name, ()-> new PresentBlock(color,
                            AbstractBlock.Properties.of(Material.WOOD, color.getMaterialColor())
                                    .strength(1.0F)
                                    .noCollission()
                                    .sound(SoundType.WOOD)
                    )
            ));
        }
        return map;
    }


}
