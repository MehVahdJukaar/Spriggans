package net.mehvahdjukaar.spriggans.entity;

import net.mehvahdjukaar.spriggans.common.CommonUtil;
import net.mehvahdjukaar.spriggans.common.SprigganType;
import net.mehvahdjukaar.spriggans.entity.goals.HurtByTargetAlertGoal;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
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

public class SprigganEntity extends CreatureEntity implements IAngerable {
    //skins
    protected static final DataParameter<Integer> SKIN_ID = EntityDataManager.defineId(SprigganEntity.class, DataSerializers.INT);
    protected BasicParticleType particle;
    private boolean hasColor = true;
    //spell
    protected static final DataParameter<Byte> DATA_SPELL_CASTING_ID = EntityDataManager.defineId(SprigganEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<BlockPos> TARGET_DIRT = EntityDataManager.defineId(SprigganEntity.class, DataSerializers.BLOCK_POS);

    //anger
    private static final RangedInteger PERSISTENT_ANGER_TIME = TickRangeConverter.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    //look goal
    private int remainingCooldownBeforeLocatingNewPlant = 0;
    private BlockPos savedPlantPos = null;


    //turn dirt goal
    private int turnDirtCooldown = 0;



    public SprigganEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 5;
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
    }

    @Override
    protected Brain.BrainCodec<?> brainProvider() {
        return super.brainProvider();
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SKIN_ID, 0);
        this.entityData.define(DATA_SPELL_CASTING_ID, (byte)0);
        this.entityData.define(TARGET_DIRT, BlockPos.ZERO);
    }

    public SprigganType getSprigganType() {
        return SprigganType.values()[this.entityData.get(SKIN_ID)];
    }

    public void setSprigganType(int index) {
        this.entityData.set(SKIN_ID, index);
    }

    public BlockPos getTargetDirt() {
        return this.entityData.get(TARGET_DIRT);
    }

    public void setTargetDirt(BlockPos pos) {
        this.entityData.set(TARGET_DIRT, pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 2.2, false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ItemTags.SAPLINGS), false));
        this.goalSelector.addGoal(4, new LookAtPlantGoal());
        this.goalSelector.addGoal(5, new CastGrowSpellGoal(CommonUtil.GRASS_PREDICATE,CommonUtil.TURN_TO_GRASS));
        this.goalSelector.addGoal(6, new CastGrowSpellGoal(CommonUtil.GROWABLE_PREDICATE,CommonUtil.USE_BONEMEAL));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1,0.003F));
        this.goalSelector.addGoal(8, new FindPlantsGoal());
        this.goalSelector.addGoal(10, new LookAtGoal(this, AnimalEntity.class, 8.0F));
        this.goalSelector.addGoal(11, new LookRandomlyGoal(this));



        //TODO: water ai
        this.targetSelector.addGoal(1, (new HurtByTargetAlertGoal(this, SprigganEntity.class)).setCallForHelp(WolfEntity.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetAngerGoal<>(this, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, RavagerEntity.class, true));
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
        this.setSprigganType(SprigganType.getType(biome).ordinal());

        return data;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("SprigganType", this.getSprigganType().ordinal());
        this.addPersistentAngerSaveData(compoundNBT);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setSprigganType(compoundNBT.getInt("SprigganType"));
        if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
            this.readPersistentAngerSaveData((ServerWorld)this.level, compoundNBT);
    }
    //ai stuff
    public boolean hasSavedPlantPos() {
        return savedPlantPos != null;
    }

    public boolean wantsToGoToPlant() {
        return remainingCooldownBeforeLocatingNewPlant == 0;
    }

    public boolean canTurnDirt() {
        return turnDirtCooldown == 0;
    }

    public boolean isCastingSpell() {
        return this.entityData.get(DATA_SPELL_CASTING_ID) > 0;
    }

    public void setIsCastingSpell(byte type) {
        this.entityData.set(DATA_SPELL_CASTING_ID, type);
    }

    private boolean closerThan(BlockPos pos, double radius) {
        return pos.closerThan(this.blockPosition(), (double)radius);
    }

    private boolean isTooFarAway(BlockPos pos) {
        return !this.closerThan(pos, 32);
    }

    private boolean isValidPlant(BlockPos pos) {
        return this.level.isLoaded(pos) && CommonUtil.SPRIGGAN_INTEREST_PREDICATE.test(this.level,pos);
    }


    //anger stuff
    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.randomValue(this.random));
    }
    @Override
    public void setRemainingPersistentAngerTime(int p_230260_1_) {
        this.remainingPersistentAngerTime = p_230260_1_;
    }
    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }
    @Override
    public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
        this.persistentAngerTarget = p_230259_1_;
    }
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }


    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 0.5);
    }

    @Override
    public ItemStack getMainHandItem() {
        if(!this.isCastingSpell())
            return super.getMainHandItem();
        else return ItemStack.EMPTY;
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

        if (this.level.isClientSide) {
            float r = this.random.nextFloat();
            //this.xo != this.getX() || this.zo != this.getZ()
            if (r < 0.008 || (r < 0.02 && (this.animationSpeed>0.05))) {
                int p = 1+random.nextInt(1);
                BasicParticleType particle = this.getGetParticle();
                int col = this.hasColor? this.level.getBiome(this.blockPosition()).getFoliageColor() : -1;

                Vector3d d = this.getDeltaMovement().normalize();
                for (int i = 0; i < p; ++i) {
                    this.level.addParticle(particle, this.getRandomX(0.5D) - 0.25 * d.x, this.getY(random.nextFloat() * 0.7) + this.getBbHeight() * 0.3, this.getRandomZ(0.5D) - 0.25 * d.z, ColorHelper.PackedColor.red(col) / 255f, ColorHelper.PackedColor.green(col) / 255f, ColorHelper.PackedColor.blue(col) / 255f);
                }
            }
        }

        if (this.remainingCooldownBeforeLocatingNewPlant > 0) {
            --this.remainingCooldownBeforeLocatingNewPlant;
        }

        if (this.turnDirtCooldown > 0) {
            --this.turnDirtCooldown;
        }

        if (!this.level.isClientSide) { //fix for spawner bug (?)
            this.updatePersistentAnger((ServerWorld)this.level, true);
        }
    }

    private BasicParticleType getGetParticle(){
        if(this.particle==null){
            this.particle = this.getSprigganType().getParticle();
            this.hasColor = this.getSprigganType().hasBiomeColors();
        }
        return this.particle;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide && this.isCastingSpell()) {
            this.yBodyRot = this.yHeadRot;

            if (random.nextFloat() < 0.45) {
                //int col = this.level.getBiome(this.blockPosition()).getFoliageColor();
                double d0 = 10 / 255f;//ColorHelper.PackedColor.red(col) / 255f;
                double d1 = 165 / 255f;//ColorHelper.PackedColor.green(col) / 255f;
                double d2 = 40 / 255f;//ColorHelper.PackedColor.blue(col) / 255f;
                float f = this.yBodyRot * ((float) Math.PI / 180F) + 0.475f + MathHelper.sin(this.tickCount * (0.3f / 2f)) * 0.1F;
                float dy = -MathHelper.sin(this.tickCount * (0.3f)) * 0.06F;
                float f2 = MathHelper.cos(f);
                float f1 = MathHelper.sin(f);


                this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() - (double) f1 * 0.75, this.getY() + 1.175D + dy, this.getZ() + (double) f2 * 0.75D, d0, d1, d2);
            }
        }

    }



    public class FindPlantsGoal extends Goal {
        //ticks that has been travelling

        private int travellingTicks = SprigganEntity.this.level.random.nextInt(10);

        FindPlantsGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            //only start when not on cooldown
            if (!SprigganEntity.this.wantsToGoToPlant()) {
                return false;
            } else if (SprigganEntity.this.random.nextFloat() < 0.7F) {
                return false;
            } else {
                Optional<BlockPos> optional = this.findNearbyPlant();
                if (optional.isPresent()) {
                    SprigganEntity.this.savedPlantPos = optional.get();
                    //SprigganEntity.this.navigation.moveTo((double)BeeEntity.this.savedFlowerPos.getX() + 0.5D, (double)BeeEntity.this.savedFlowerPos.getY() + 0.5D, (double)BeeEntity.this.savedFlowerPos.getZ() + 0.5D, (double)1.2F);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return SprigganEntity.this.hasSavedPlantPos() &&
                    //for leash
                    !SprigganEntity.this.hasRestriction() &&
                    SprigganEntity.this.wantsToGoToPlant() &&
                    !SprigganEntity.this.closerThan(SprigganEntity.this.savedPlantPos, 1);
        }

        @Override
        public void start() {
            this.travellingTicks = 0;
            super.start();
        }

        @Override
        public void stop() {
            this.travellingTicks = 0;
            SprigganEntity.this.navigation.stop();
            SprigganEntity.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        @Override
        public void tick() {

            ++this.travellingTicks;
            if (this.travellingTicks > 600) {
                //give up
                SprigganEntity.this.savedPlantPos = null;
            } else if (!SprigganEntity.this.navigation.isInProgress()) {
                //reset plant if I get too far away
                if (SprigganEntity.this.isTooFarAway(SprigganEntity.this.savedPlantPos)) {
                    SprigganEntity.this.savedPlantPos = null;
                } else {
                    Vector3d vector3d = Vector3d.atBottomCenterOf(SprigganEntity.this.savedPlantPos);
                    SprigganEntity.this.navigation.moveTo(vector3d.x, vector3d.y, vector3d.z, 1.0D);
                    //SprigganEntity.this.pathfindRandomlyTowards(SprigganEntity.this.savedPlantPos);
                }
            }
        }

        private Optional<BlockPos> findNearbyPlant() {
            return CommonUtil.findNearestBlock(SprigganEntity.this, 6, 0,CommonUtil.SPRIGGAN_INTEREST_PREDICATE);
        }

    }


    class LookAtPlantGoal extends Goal {

        private int staringTicks = 0;

        LookAtPlantGoal() {
            //goal type? seems prevent moving. seems to prevent other goals with same type
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            //only start when not on cooldown
            if (!SprigganEntity.this.wantsToGoToPlant()) {
                return false;
            }
            else if (!SprigganEntity.this.hasSavedPlantPos()) {
                return false;
            }
            //only start when close to flower
            else if(!SprigganEntity.this.closerThan(SprigganEntity.this.savedPlantPos, SprigganEntity.this.random.nextInt(1)+2)){
                return false;
            }
            else return !(SprigganEntity.this.random.nextFloat() < 0.7F);
        }

        @Override
        public boolean canContinueToUse() {
            if (!SprigganEntity.this.hasSavedPlantPos()) {
                return false;
            }
            else if (this.hasLookedLongEnough()) {
                return SprigganEntity.this.random.nextFloat() < 0.2F;
            }
            //checks every 1 sec if block is valid
            else if (SprigganEntity.this.tickCount % 20 == 0 && !SprigganEntity.this.isValidPlant(SprigganEntity.this.savedPlantPos)) {
                SprigganEntity.this.savedPlantPos = null;
                return false;
            }
            return true;
        }

        private boolean hasLookedLongEnough() {
            return this.staringTicks > 400;
        }



        public void start() {
            this.staringTicks = 0;
        }

        public void stop() {
            SprigganEntity.this.navigation.stop();
            SprigganEntity.this.remainingCooldownBeforeLocatingNewPlant = 500+SprigganEntity.this.random.nextInt(200);
            SprigganEntity.this.savedPlantPos = null;
        }

        public void tick() {
            ++this.staringTicks;
            if (this.staringTicks > 600) {
                SprigganEntity.this.savedPlantPos = null;
            } else {
                Vector3d vector3d = Vector3d.atBottomCenterOf(SprigganEntity.this.savedPlantPos).add(0.0D, (double)0.6F, 0.0D);
                SprigganEntity.this.getLookControl().setLookAt(vector3d.x(), vector3d.y(), vector3d.z());
            }
        }
    }


    class CastGrowSpellGoal extends Goal {

        private final BiPredicate<World,BlockPos> targetPredicate;
        private final BiFunction<World,BlockPos,Boolean> action;
        private BlockPos targetPos;
        private int animationTicks = 0;

        CastGrowSpellGoal(BiPredicate<World,BlockPos> target, BiFunction<World,BlockPos,Boolean> action) {
            //can't move and can't change look dir
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
            this.targetPredicate = target;
            this.action = action;

        }

        @Override
        public boolean canUse() {
            //only start when not on cooldown
            if (!SprigganEntity.this.canTurnDirt()) {
                return false;
            } else if (SprigganEntity.this.random.nextFloat() < 0.7F) {
                return false;
            }
            else {
                Optional<BlockPos> optional = CommonUtil.findNearestBlock(SprigganEntity.this,2,1.1f,this.targetPredicate);
                if (optional.isPresent()) {
                    targetPos = optional.get();
                    SprigganEntity.this.setTargetDirt(targetPos);
                    SprigganEntity.this.setIsCastingSpell((byte)1);
                    //SprigganEntity.this.navigation.moveTo((double)BeeEntity.this.savedFlowerPos.getX() + 0.5D, (double)BeeEntity.this.savedFlowerPos.getY() + 0.5D, (double)BeeEntity.this.savedFlowerPos.getZ() + 0.5D, (double)1.2F);
                    return true;
                }
            }
            return false;
        }


        @Override
        public boolean canContinueToUse() {
            if (this.targetPos==null) {
                return false;
            }
            //checks every 1 sec if block is valid
            else if (SprigganEntity.this.tickCount % 20 == 0 && !this.isValidTarget(this.targetPos)) {
                return false;
            }
            return true;
        }

        private boolean isValidTarget(BlockPos pos){
            return targetPredicate.test(SprigganEntity.this.level,pos);
        }

        private boolean hasFinishedAnimation() {
            return this.animationTicks > 100;
        }

        public void start() {
            this.animationTicks = 0;
        }

        public void stop() {
            //SprigganEntity.this.navigation.stop();
            SprigganEntity.this.turnDirtCooldown = 1000;
            SprigganEntity.this.setIsCastingSpell((byte)0);
            this.targetPos = null;
        }

        public void tick() {
            ++this.animationTicks;
            if (this.hasFinishedAnimation()) {
                if(this.isValidTarget(this.targetPos)){
                    this.action.apply(SprigganEntity.this.level,this.targetPos);
                }

                targetPos = null;
            } else {
                Vector3d vector3d = Vector3d.atBottomCenterOf(this.targetPos).add(0.0D, (double)0.6F, 0.0D);
                SprigganEntity.this.getLookControl().setLookAt(vector3d.x(), vector3d.y(), vector3d.z());
                //SprigganEntity.this.
            }
        }
    }






    public static void angerNearbySpriggans(PlayerEntity player, boolean p_234478_1_) {
        List<SprigganEntity> list = player.level.getEntitiesOfClass(SprigganEntity.class, player.getBoundingBox().inflate(14.0D));
        list.stream().filter((e) -> !p_234478_1_ || BrainUtil.canSee(e, player)).forEach((entity) -> {
            if (entity.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                setAngerTargetToNearestTargetablePlayerIfFound(entity, player);
            } else {
                setAngerTarget(entity, player);
            }

        });
    }

    private static boolean isAttackAllowed(LivingEntity p_234506_0_) {
        return EntityPredicates.ATTACK_ALLOWED.test(p_234506_0_);
    }

    protected static void setAngerTarget(SprigganEntity spriggan, LivingEntity target) {
        if (isAttackAllowed(target)) {
            spriggan.setTarget(target);
            /*
            spriggan.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            spriggan.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);

            if (target.getType() == EntityType.PLAYER && spriggan.level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                spriggan.getBrain().setMemoryWithExpiry(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
            */

        }
    }

    private static void setAngerTargetToNearestTargetablePlayerIfFound(SprigganEntity spriggan, LivingEntity target) {
        Optional<PlayerEntity> optional = getNearestVisibleTargetablePlayer(spriggan);
        if (optional.isPresent()) {
            setAngerTarget(spriggan, optional.get());
        } else {
            setAngerTarget(spriggan, target);
        }

    }
    public static Optional<PlayerEntity> getNearestVisibleTargetablePlayer(SprigganEntity p_241432_0_) {
        return p_241432_0_.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) ? p_241432_0_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) : Optional.empty();
    }




}
