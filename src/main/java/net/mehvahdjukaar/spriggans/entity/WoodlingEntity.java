package net.mehvahdjukaar.spriggans.entity;

import net.mehvahdjukaar.spriggans.common.CommonUtil;
import net.mehvahdjukaar.spriggans.common.SprigganType;
import net.mehvahdjukaar.spriggans.entity.goals.FollowSpriggansGoal;
import net.mehvahdjukaar.spriggans.entity.goals.HurtByTargetAlertGoal;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class WoodlingEntity extends TameableEntity {
    //skins
    protected static final DataParameter<Integer> SKIN_ID = EntityDataManager.defineId(WoodlingEntity.class, DataSerializers.INT);
    protected static final DataParameter<Byte> EARS = EntityDataManager.defineId(WoodlingEntity.class, DataSerializers.BYTE);


    public enum EarsType{
        STICK,LEAVES,HORNS;
    }

    public int followCooldown = this.isTame()?1000:0;

    public WoodlingEntity(EntityType<? extends TameableEntity> p_i48574_1_, World p_i48574_2_) {
        super(p_i48574_1_, p_i48574_2_);
        this.xpReward = 2;
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_ID, 0);
        this.entityData.define(EARS, (byte) 0);
    }

    public SprigganType getSprigganType() {
        return SprigganType.values()[this.entityData.get(SKIN_ID)];
    }

    public void setSprigganType(int index) {
        this.entityData.set(SKIN_ID, index);
    }

    public EarsType getEarsType() {
        return EarsType.values()[this.entityData.get(EARS)];
    }

    public void setEarsType(byte index) {
        this.entityData.set(EARS, index);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ItemTags.SAPLINGS), false));
        this.goalSelector.addGoal(4, new FollowSpriggansGoal(this, 0.75D));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1,0.003F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, AnimalEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(ModRegistry.BRANCH.get()));
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData data, @Nullable CompoundNBT compound) {
        data = super.finalizeSpawn(world, difficulty, spawnReason, data, compound);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        //this.reassessWeaponGoal();
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());
        if (this.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
            LocalDate localdate = LocalDate.now();
            int i = localdate.get(ChronoField.DAY_OF_MONTH);
            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
            if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
                this.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
                this.armorDropChances[EquipmentSlotType.HEAD.getIndex()] = 0.0F;
            }
        }
        //assign type
        Biome biome = world.getBiome(this.blockPosition());
        this.setSprigganType(SprigganType.getWoodlingType(biome).ordinal());
        this.setEarsType((byte) random.nextInt(3));

        return data;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("SprigganType", this.getSprigganType().ordinal());
        compoundNBT.putInt("Ears", this.getEarsType().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setSprigganType(compoundNBT.getInt("SprigganType"));
        this.setSprigganType(compoundNBT.getInt("Ears"));
    }



    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22)
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.ARMOR, 0);
    }

    @Override
    public ItemStack getMainHandItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSensitiveToWater() {
        //TODO: this makes so water bottles hurt them.
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        if(damageSource==DamageSource.DROWN)return true;
        return super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if(damageSource == DamageSource.DROWN){
            if(this.getHealth() < this.getMaxHealth() && !this.hurtMarked && this.random.nextFloat()<0.01){
                this.level.broadcastEntityEvent(this, (byte)11);
                this.heal(amount);
            }
            return false;
        }
        return super.hurt(damageSource, amount);
    }


    @Override
    public void handleEntityEvent(byte b) {
         if (b == 11) {
             for(int i = 0; i < 3; ++i) {
                 double d2 = random.nextGaussian() * 0.02D;
                 double d3 = random.nextGaussian() * 0.02D;
                 double d4 = random.nextGaussian() * 0.02D;

                 this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d2, d3, d4);

             }
        } else {
            super.handleEntityEvent(b);
        }
    }

    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.UNDEFINED;
    }


    @Override
    public void aiStep() {
        super.aiStep();

        if (this.followCooldown > 0) {
            --this.followCooldown;
        }
    }


    @Override
    public void tick() {
        super.tick();
    }

    public boolean isFood(ItemStack stack) {
        return stack.getItem() == ModRegistry.BARK.get();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (this.level.isClientSide) {
            if (this.isTame() && this.isOwnedBy(player)) {
                return ActionResultType.SUCCESS;
            } else {
                return !this.isFood(itemstack) || !(this.getHealth() < this.getMaxHealth()) && this.isTame() ? ActionResultType.PASS : ActionResultType.SUCCESS;
            }
        } else {
            if (this.isTame()) {
                if (this.isOwnedBy(player)) {

                    if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.usePlayerItem(player, itemstack);
                        this.heal(0.5f);
                        return ActionResultType.CONSUME;
                    }

                    ActionResultType actionresulttype = super.mobInteract(player, hand);
                    if (!actionresulttype.consumesAction() || this.isBaby()) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                    }
                    return actionresulttype;
                }


            }
            ActionResultType actionresulttype1 = super.mobInteract(player, hand);
            if (actionresulttype1.consumesAction()) {
                this.setPersistenceRequired();
            }
            return actionresulttype1;
        }
    }

}
