package net.mehvahdjukaar.spriggans.entity;

import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpellcastingIllagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ElderSprigganEntity extends SprigganEntity {

    public ElderSprigganEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
        this.xpReward = 8;
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, 16.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(2, new AttackSpellGoal());
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {
        super.populateDefaultEquipmentSlots(p_180481_1_);
        this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData data, @Nullable CompoundNBT compound) {
        if (world.getRandom().nextInt(10) == 0)  {
            SprigganHorseEntity horse = ModRegistry.SPRIGGAN_HORSE.get().create(this.level);
            horse.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
            horse.finalizeSpawn(world, difficulty, SpawnReason.JOCKEY, (ILivingEntityData)null, (CompoundNBT)null);
            this.startRiding(horse);
            world.addFreshEntity(horse);
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, data, compound);
    }



    class AttackSpellGoal extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;
        private AttackSpellGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingentity = ElderSprigganEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                if (ElderSprigganEntity.this.isCastingSpell()) {
                    return false;
                } else {
                    return ElderSprigganEntity.this.tickCount >= this.nextAttackTickCount;
                }
            } else {
                return false;
            }
        }

        @Override
        public void stop() {
            super.stop();
            ElderSprigganEntity.this.setIsCastingSpell((byte)0);
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = ElderSprigganEntity.this.getTarget();
            return livingentity != null && livingentity.isAlive() && this.attackWarmupDelay > 0;
        }

        public void start() {
            this.attackWarmupDelay = 200;
            //SprigganEntity.this.spellCastingTickCount = this.getCastingTime();
            //this.nextAttackTickCount = SprigganEntity.this.tickCount + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            if (soundevent != null) {
                ElderSprigganEntity.this.playSound(soundevent, 1.0F, 1.0F);
            }

            ElderSprigganEntity.this.setIsCastingSpell((byte)2);
        }

        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performSpellCasting();
                //SprigganEntity.this.playSound(SprigganEntity.this.getCastingSoundEvent(), 1.0F, 1.0F);
            }

        }

        protected int getCastingTime() {
            return 40;
        }

        protected int getCastingInterval() {
            return 100;
        }

        protected void performSpellCasting() {
            LivingEntity livingentity = ElderSprigganEntity.this.getTarget();
            double d0 = Math.min(livingentity.getY(), ElderSprigganEntity.this.getY());
            double d1 = Math.max(livingentity.getY(), ElderSprigganEntity.this.getY()) + 1.0D;
            float f = (float)MathHelper.atan2(livingentity.getZ() - ElderSprigganEntity.this.getZ(), livingentity.getX() - ElderSprigganEntity.this.getX());
            if (ElderSprigganEntity.this.distanceToSqr(livingentity) < 9.0D) {
                for(int i = 0; i < 5; ++i) {
                    float f1 = f + (float)i * (float)Math.PI * 0.4F;
                    this.createSpellEntity(ElderSprigganEntity.this.getX() + (double)MathHelper.cos(f1) * 1.5D, ElderSprigganEntity.this.getZ() + (double)MathHelper.sin(f1) * 1.5D, d0, d1, f1, 0);
                }

                for(int k = 0; k < 8; ++k) {
                    float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
                    this.createSpellEntity(ElderSprigganEntity.this.getX() + (double)MathHelper.cos(f2) * 2.5D, ElderSprigganEntity.this.getZ() + (double)MathHelper.sin(f2) * 2.5D, d0, d1, f2, 3);
                }
            } else {
                for(int l = 0; l < 16; ++l) {
                    double d2 = 1.25D * (double)(l + 1);
                    int j = 1 * l;
                    this.createSpellEntity(ElderSprigganEntity.this.getX() + (double)MathHelper.cos(f) * d2, ElderSprigganEntity.this.getZ() + (double)MathHelper.sin(f) * d2, d0, d1, f, j);
                }
            }

        }

        private void createSpellEntity(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
            BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
            boolean flag = false;
            double d0 = 0.0D;

            do {
                BlockPos blockpos1 = blockpos.below();
                BlockState blockstate = ElderSprigganEntity.this.level.getBlockState(blockpos1);
                if (blockstate.isFaceSturdy(ElderSprigganEntity.this.level, blockpos1, Direction.UP)) {
                    if (!ElderSprigganEntity.this.level.isEmptyBlock(blockpos)) {
                        BlockState blockstate1 = ElderSprigganEntity.this.level.getBlockState(blockpos);
                        VoxelShape voxelshape = blockstate1.getCollisionShape(ElderSprigganEntity.this.level, blockpos);
                        if (!voxelshape.isEmpty()) {
                            d0 = voxelshape.max(Direction.Axis.Y);
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.below();
            } while(blockpos.getY() >= MathHelper.floor(p_190876_5_) - 1);

            if (flag) {
                ElderSprigganEntity.this.level.addFreshEntity(new SprigganThornsEntity(ElderSprigganEntity.this.level, p_190876_1_, (double)blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, ElderSprigganEntity.this));
            }

        }

        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOKER_PREPARE_ATTACK;
        }

        protected SpellcastingIllagerEntity.SpellType getSpell() {
            return SpellcastingIllagerEntity.SpellType.FANGS;
        }
    }


}
