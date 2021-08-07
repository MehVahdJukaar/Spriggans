package net.mehvahdjukaar.spriggans.entity;

import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class SprigganThornsEntity extends Entity {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;
    private boolean clientSideAttackStarted;
    private LivingEntity owner;
    private UUID ownerUUID;

    public SprigganThornsEntity(EntityType<? extends SprigganThornsEntity> p_i50170_1_, World p_i50170_2_) {
        super(p_i50170_1_, p_i50170_2_);
    }


    public SprigganThornsEntity(World p_i47276_1_, double p_i47276_2_, double p_i47276_4_, double p_i47276_6_, float p_i47276_8_, int p_i47276_9_, LivingEntity p_i47276_10_) {
        this(ModRegistry.THORNS.get(), p_i47276_1_);
        this.warmupDelayTicks = p_i47276_9_;
        this.setOwner(p_i47276_10_);
        this.yRot = p_i47276_8_ * (180F / (float)Math.PI);
        this.setPos(p_i47276_2_, p_i47276_4_, p_i47276_6_);
    }

    public SprigganThornsEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(ModRegistry.THORNS.get(), world);
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void defineSynchedData() { }

    public void setOwner(@Nullable LivingEntity entity) {
        this.owner = entity;
        this.ownerUUID = entity == null ? null : entity.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerWorld) {
            Entity entity = ((ServerWorld)this.level).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        this.warmupDelayTicks = compoundNBT.getInt("Warmup");
        if (compoundNBT.hasUUID("Owner")) {
            this.ownerUUID = compoundNBT.getUUID("Owner");
        }
    }

    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
            compoundNBT.putUUID("Owner", this.ownerUUID);
        }
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks == 14) {
                    for(int i = 0; i < 12; ++i) {
                        double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                        double d1 = this.getY() + 0.05D + this.random.nextDouble();
                        double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                        double d3 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        double d4 = 0.3D + this.random.nextDouble() * 0.3D;
                        double d5 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        this.level.addParticle(ParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
                    }
                }
            }
        } else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -8) {
                for(LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D))) {
                    this.dealDamageTo(livingentity);
                }
            }

            if (!this.sentSpikeEvent) {
                this.level.broadcastEntityEvent(this, (byte)4);
                this.sentSpikeEvent = true;
            }

            if (--this.lifeTicks < 0) {
                this.remove();
            }
        }

    }

    private void dealDamageTo(LivingEntity entity) {
        LivingEntity livingentity = this.getOwner();
        if (entity.isAlive() && !entity.isInvulnerable() && entity != livingentity) {
            if (livingentity == null) {
                entity.hurt(DamageSource.MAGIC, 6.0F);
            } else {
                if (livingentity.isAlliedTo(entity)) {
                    return;
                }

                entity.hurt(DamageSource.indirectMagic(this, livingentity), 6.0F);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte b) {
        super.handleEntityEvent(b);
        if (b == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.EVOKER_FANGS_ATTACK, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getAnimationProgress(float p_190550_1_) {
        if (!this.clientSideAttackStarted) {
            return 0.0F;
        } else {
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - p_190550_1_) / 20.0F;
        }
    }


}
