package net.mehvahdjukaar.spriggans.entity;

import its_meow.betteranimalsplus.common.entity.EntityLammergeier;
import its_meow.betteranimalsplus.common.entity.ai.LammerMoveHelper;
import net.mehvahdjukaar.spriggans.common.OwlType;
import net.mehvahdjukaar.spriggans.entity.AI.FlyingNavigator;
import net.mehvahdjukaar.spriggans.entity.AI.LamMoveController;
import net.mehvahdjukaar.spriggans.entity.AI.OwlMoveController;
import net.mehvahdjukaar.spriggans.entity.AI.OwlNavigator;
import net.mehvahdjukaar.spriggans.entity.goals.*;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.pavocado.exoticbirds.entity.EntityRobin;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class OwlEntity extends TameableEntity implements IFlyingAnimal {
    private static final DataParameter<Byte> DATA_VARIANT_ID = EntityDataManager.defineId(OwlEntity.class, DataSerializers.BYTE);

    private static final DataParameter<Byte> DATA_ACTION = EntityDataManager.defineId(OwlEntity.class, DataSerializers.BYTE);


    public static final Predicate<LivingEntity> PREY_SELECTOR = (e) -> {
        EntityType<?> type = e.getType();
        return type == EntityType.RABBIT ;
    };

    public static final Predicate<LivingEntity> TAMED_PREY_SELECTOR = (e) -> {
        EntityType<?> type = e.getType();
        return type == EntityType.SPIDER || type == EntityType.SILVERFISH || type == EntityType.ENDERMITE;
    };

    private static final Ingredient TAME_FOOD = Ingredient.of(Items.RABBIT_FOOT, Items.RABBIT, Items.COOKED_RABBIT);

    public float interestedAngle;
    public float interestedAngleO;
    private boolean interestedSide;
    public float flap;
    public float flapSpeed = 0;
    public float oFlapSpeed = 0;
    public float oFlap;
    private float flapping = 1.0F;

    public OwlEntity(EntityType<? extends OwlEntity> entityType, World world) {
        super(entityType, world);
        //this.moveControl = new LamMoveController(this);
        this.moveControl = new LamMoveController(this);
        //this.moveControl = new FlyingMovementController(this, 140, false);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.COCOA, -1.0F);

    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance instance, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT nbt) {
        this.setOwlType(OwlType.getOwlType(world.getBiome(this.blockPosition()),world.getRandom()));
        if (data == null) {
            data = new AgeableEntity.AgeableData(false);
        }
        this.setState((byte) 0);
        this.populateDefaultEquipmentSlots(instance);
        return super.finalizeSpawn(world, instance, reason, data, nbt);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
        //this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    protected void registerGoals() {
        goalSelector.addGoal(8, new FlyWanderGoal(this, 1));
        /*
        this.goalSelector.addGoal(0, new SwimGoal(this));

        this.goalSelector.addGoal(1, new OwlSleepGoal(this));
        this.goalSelector.addGoal(2, new SitGoal(this));

        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 15.0F, 6F, true));

        //this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));


        this.goalSelector.addGoal(3, new MoveToPerchGoal(this,3,10));

        this.goalSelector.addGoal(5, new FollowParentFlyingGoal(this, 1D, 20,5));


        this.goalSelector.addGoal(3, new DiveAtTargetGoal(this, 0.4F));

        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        //this.goalSelector.addGoal(5, new OcelotAttackGoal(this));

        this.goalSelector.addGoal(5, new OwlInterestedGoal(this, 8.0F));
        this.goalSelector.addGoal(6, new OwlLookAtPreyGoal(this, 8.0F, 60, 0.015f));
        this.goalSelector.addGoal(7, new GazeGoal(this, PlayerEntity.class, 8.0F, 15, 0.01f));
        this.goalSelector.addGoal(7, new GazeGoal(this, MobEntity.class, 8.0F, 15, 0.008f));

        //this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));

        this.targetSelector.addGoal(5, new NonTamedTargetGoal<>(this, AnimalEntity.class, false, PREY_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 10, false, false, TAMED_PREY_SELECTOR));
        */

    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.FLYING_SPEED, 2.1F)
                .add(Attributes.MOVEMENT_SPEED, 0.2F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected PathNavigator createNavigation(World p_175447_1_) {

        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, p_175447_1_);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        //flyingpathnavigator.setCanPassDoors(true);
        //flyingpathnavigator.setForceFlying(true);
        //PathNavigator flyingpathnavigator = new OwlNavigator(this, level);
        return flyingpathnavigator;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, (byte)0);
        this.entityData.define(DATA_ACTION,(byte)0);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putByte("Variant", (byte) this.getOwlType().ordinal());
        nbt.putByte("State", this.getState());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        this.setOwlType(OwlType.fromNBT(nbt.getByte("Variant")));
        this.setState(nbt.getByte("State"));

    }

    public OwlType getOwlType() {
        return OwlType.values()[this.entityData.get(DATA_VARIANT_ID) % 7];
    }

    public void setOwlType(OwlType variant) {
        this.entityData.set(DATA_VARIANT_ID, (byte) variant.ordinal());
    }

    public void setState(byte state) {
        this.entityData.set(DATA_ACTION, state);
    }

    public byte getState() {
        return this.entityData.get(DATA_ACTION);
    }

    public void setInterested(boolean interested) {
        this.setState(interested?(byte)1:0);
    }

    public boolean isInterested() {
        return this.getState()==1;
    }

    public void setDiving(boolean diving) {
        this.setState(diving?(byte)2:0);
    }

    public boolean isDiving() {
        return this.getState()==2;
    }

    public void setSleeping(boolean sleeping) {
        this.setState(sleeping?(byte)3:0);
    }

    public boolean isSleeping() {
        return this.getState()==3;
    }


    private void setFlag(int id, boolean value) {
        if (value) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) | id));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(this.entityData.get(DATA_FLAGS_ID) & ~id));
        }
    }


    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize size) {
        return size.height * 0.8F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }

    @Override
    public void tick() {
        super.tick();
        this.interestedAngleO = this.interestedAngle;
        if (this.isInterested()) {
            if(this.random.nextFloat()<0.02 && Math.abs(this.interestedAngleO)>0.8)this.interestedSide = !interestedSide;

            this.interestedAngle += (this.interestedSide?-1:1 * 1.0F - this.interestedAngle) * 0.3F;
            this.interestedAngle = MathHelper.clamp(this.interestedAngle,-1,1);
        } else {
            this.interestedAngle += (0.0F - this.interestedAngle) * 0.4F;
        }
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround && !this.isPassenger() ? 2.5f : -1) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.2F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9D);
        Vector3d vector3d = this.getDeltaMovement();
        if (!this.onGround && vector3d.y < 0.0D) {
            this.setDeltaMovement(vector3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
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

                    if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                        this.usePlayerItem(player, itemstack);
                        this.heal((float)item.getFoodProperties().getNutrition());
                        return ActionResultType.CONSUME;
                    }

                    ActionResultType actionresulttype = super.mobInteract(player, hand);
                    if (!actionresulttype.consumesAction() || this.isBaby()) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                    }
                    return actionresulttype;


                }
            } else if (isTameFood(itemstack)) {
                this.usePlayerItem(player, itemstack);
                this.setSleeping(false);
                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                this.setPersistenceRequired();
                return ActionResultType.CONSUME;
            }

            ActionResultType actionresulttype1 = super.mobInteract(player, hand);
            if (actionresulttype1.consumesAction()) {
                this.setPersistenceRequired();
            }

            return actionresulttype1;
        }
    }


    public boolean isTameFood(ItemStack stack) {
        return TAME_FOOD.test(stack);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        Item item = stack.getItem();
        return item.isEdible() && item.getFoodProperties().isMeat();
    }

    public static boolean checkParrotSpawnRules(EntityType<OwlEntity> p_223317_0_, IWorld p_223317_1_, SpawnReason p_223317_2_, BlockPos p_223317_3_, Random p_223317_4_) {
        BlockState blockstate = p_223317_1_.getBlockState(p_223317_3_.below());
        return (blockstate.is(BlockTags.LEAVES) || blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(BlockTags.LOGS) || blockstate.is(Blocks.AIR)) && p_223317_1_.getRawBrightness(p_223317_3_, 0) > 8;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    protected void checkFallDamage(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
    }

    @Override
    public boolean canMate(AnimalEntity entity) {
        if (entity == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(entity instanceof OwlEntity)) {
            return false;
        } else {
            OwlEntity owlEntity = (OwlEntity)entity;
            if (!owlEntity.isTame()) {
                return false;
            } else if (owlEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && owlEntity.isInLove();
            }
        }
    }

    @Nullable
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity father) {
        OwlEntity owl = ModRegistry.OWL.get().create(world);
        OwlType variant = random.nextBoolean()? this.getOwlType() : ((OwlEntity)father).getOwlType();
        owl.setOwlType(variant);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            owl.setOwnerUUID(uuid);
            owl.setTame(true);
        }
        return owl;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = entity.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttackDamage()));
        if (flag) {
            this.doEnchantDamageEffects(this, entity);
        }
        return flag;
    }


    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.PARROT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos p_180429_1_, BlockState p_180429_2_) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    @Override
    protected float playFlySound(float p_191954_1_) {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        return p_191954_1_ + this.flapSpeed / 2.0F;
    }

    @Override
    protected boolean makeFlySound() {
        return true;
    }

    @Override
    protected float getVoicePitch() {
        return getPitch(this.random);
    }

    public static float getPitch(Random p_192000_0_) {
        return (p_192000_0_.nextFloat() - p_192000_0_.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public SoundCategory getSoundSource() {
        return SoundCategory.NEUTRAL;
    }

    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float p_70097_2_) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.setOrderedToSit(false);
            this.setSleeping(false);
            return super.hurt(source, p_70097_2_);
        }
    }

    public boolean isFlying() {
        return !this.onGround;
    }

    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isFlying()? 35 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isFlying()? super.getMaxHeadYRot() : 130;
    }

    @Override
    public int getHeadRotSpeed() {
        return 24;
    }


    public boolean isOnTree(int minHeight){
        if(this.onGround) {
            int x = this.blockPosition().getX();
            int y = this.blockPosition().getY();
            int z = this.blockPosition().getZ();
            return y-this.level.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,x,z)>=minHeight;
        }
        return false;
    }


    public int getFlightTime(){
        return this.fallFlyTicks;
    }
}
