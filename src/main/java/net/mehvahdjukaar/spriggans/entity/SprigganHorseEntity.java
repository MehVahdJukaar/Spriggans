package net.mehvahdjukaar.spriggans.entity;

import net.mehvahdjukaar.spriggans.common.OwlType;
import net.mehvahdjukaar.spriggans.common.SprigganType;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.SoundType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class SprigganHorseEntity extends AbstractHorseEntity {
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    //skins
    private static final DataParameter<Integer> SKIN_ID = EntityDataManager.defineId(SprigganHorseEntity.class, DataSerializers.INT);
    protected BasicParticleType particle;
    private boolean hasColor = true;


    public SprigganHorseEntity(EntityType<? extends AbstractHorseEntity> horse, World world) {
        super(horse, world);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("SprigganType", this.getSprigganType().ordinal());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setSprigganType(compoundNBT.getInt("SprigganType"));
    }

    public SprigganType getSprigganType() {
        return SprigganType.values()[this.entityData.get(SKIN_ID)];
    }

    public void setSprigganType(int index) {
        this.entityData.set(SKIN_ID, index);
    }

    public void setSprigganType(SprigganType type) {
        this.setSprigganType(type.ordinal());
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData data, @Nullable CompoundNBT compound) {
        data = super.finalizeSpawn(world, difficulty, spawnReason, data, compound);
        //assign type
        Biome biome = world.getBiome(this.blockPosition());
        this.setSprigganType(SprigganType.getHorseType(biome).ordinal());

        return data;
    }


    @Override
    protected void playGallopSound(SoundType p_190680_1_) {
        super.playGallopSound(p_190680_1_);
        if (this.random.nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, p_190680_1_.getVolume() * 0.6F, p_190680_1_.getPitch());
        }

        ItemStack stack = this.inventory.getItem(1);
        if (isArmor(stack)) stack.onHorseArmorTick(level, this);
    }

    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }


    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    protected void playSwimSound(float p_203006_1_) {
        if (this.onGround) {
            super.playSwimSound(0.3F);
        } else {
            super.playSwimSound(Math.min(0.1F, p_203006_1_ * 25.0F));
        }

    }


    @Override
    protected void randomizeAttributes() {
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(0.7+random.nextFloat()*0.3);
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.HORSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        super.getHurtSound(p_184601_1_);
        return SoundEvents.HORSE_HURT;
    }

    @Nullable
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity entity) {
        SprigganHorseEntity horse = ModRegistry.SPRIGGAN_HORSE.get().create(world);
        horse.setSprigganType(random.nextBoolean()? this.getSprigganType() : ((SprigganHorseEntity)entity).getSprigganType());
        return horse;
    }

    /*
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {

        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isTamed()) {
            return ActionResultType.PASS;
        } else if (this.isBaby()) {
            return super.mobInteract(player, hand);
        } else if (player.isSecondaryUseActive()) {
            this.openInventory(player);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (this.isVehicle()) {
            return super.mobInteract(player, hand);
        } else {
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.SADDLE && !this.isSaddled()) {
                    this.openInventory(player);
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }

                ActionResultType actionresulttype = itemstack.interactLivingEntity(player, this, hand);
                if (actionresulttype.consumesAction()) {
                    return actionresulttype;
                }
            }

            this.doPlayerRide(player);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }
    }
    */

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {

        if (this.isTamed() && p_230254_1_.isSecondaryUseActive()) {
            this.openInventory(p_230254_1_);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        }

        if (this.isVehicle()) {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }


        this.doPlayerRide(p_230254_1_);
        return ActionResultType.sidedSuccess(this.level.isClientSide);

    }


    private BasicParticleType getGetParticle(){
        if(this.particle==null){
            this.particle = this.getSprigganType().getParticle();
            this.hasColor = this.getSprigganType().hasBiomeColors();
        }
        return this.particle;
    }


    @Override
    public void aiStep() {
        super.aiStep();
        //TODO: improve particle spawning
        if (this.level.isClientSide) {

            float c = this.gallopSoundCounter>0 ? 0.1f : 0.02f;




            float r = this.random.nextFloat();
            //this.xo != this.getX() || this.zo != this.getZ()
            if (r < 0.008 || (r < c && (this.animationSpeed>0.05))) {
                int p = 1+random.nextInt(1);
                BasicParticleType particle = this.getGetParticle();
                int col = this.hasColor? this.level.getBiome(this.blockPosition()).getFoliageColor() : -1;

                Vector3d d = this.getDeltaMovement().normalize();
                for (int i = 0; i < p; ++i) {
                    this.level.addParticle(particle, this.getRandomX(0.3D) - 0.25 * d.x, this.getY(random.nextFloat() * 0.7) + this.getBbHeight() * 0.3, this.getRandomZ(0.3D) - 0.25 * d.z, ColorHelper.PackedColor.red(col) / 255f, ColorHelper.PackedColor.green(col) / 255f, ColorHelper.PackedColor.blue(col) / 255f);
                }
            }
        }
    }

    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 0.1875D;
    }

}
