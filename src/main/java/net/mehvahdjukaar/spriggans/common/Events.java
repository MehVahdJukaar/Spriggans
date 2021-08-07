package net.mehvahdjukaar.spriggans.common;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.mehvahdjukaar.spriggans.entity.WoodlingEntity;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

public class Events {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if(itemStack.getItem() == ModRegistry.SPRIGGAN_HEART.get()) {
            BlockPos pos = event.getPos();
            World world = event.getWorld();
            Block block = world.getBlockState(pos).getBlock();
            SprigganType type = SprigganType.woodlingConversionMap.get(block);
            if(type!=null){
                WoodlingEntity woodling = ModRegistry.WOODLING.get().create(world);
                if(woodling!=null) {
                    woodling.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0.0F);
                    if(!world.isClientSide) {
                        PlayerEntity player = event.getPlayer();
                        woodling.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0.0F);

                        woodling.setSprigganType(type.ordinal());
                        woodling.setEarsType((byte) world.random.nextInt(3));
                        UUID uuid = event.getPlayer().getUUID();
                        woodling.setOwnerUUID(uuid);
                        woodling.setTame(true);
                        world.addFreshEntity(woodling);
                        woodling.lookAt(player, 40, 40);
                        woodling.yRot = -player.yHeadRot;
                        woodling.yHeadRot = -player.yHeadRot;
                        woodling.yHeadRotO = woodling.yHeadRot;
                        woodling.yRotO = woodling.yHeadRot;
                        world.destroyBlock(pos, false);
                        if (!player.isCreative()) {
                            itemStack.shrink(1);
                        }
                    /*
                    for(int i = 0; i < 8; ++i) {
                        double d2 = world.random.nextGaussian() * 0.5D;
                        double d3 = world.random.nextGaussian() * 0.5D;
                        double d4 = world.random.nextGaussian() * 0.5D;

                        world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d2, d3, d4);

                    }
                    */
                    }
                    else{
                        double r = 10 / 255f;//ColorHelper.PackedColor.red(col) / 255f;
                        double g = 165 / 255f;//ColorHelper.PackedColor.green(col) / 255f;
                        double b = 40 / 255f;//ColorHelper.PackedColor.blue(col) / 255f;

                        for (int i = 0; i < 20; ++i) {
                            world.addParticle(ParticleTypes.ENTITY_EFFECT, woodling.getRandomX(0.5D), woodling.getRandomY(), woodling.getRandomZ(0.5D), r, g, b);
                        }
                    }
                    event.setCancellationResult(ActionResultType.sidedSuccess(world.isClientSide));
                    event.setCanceled(true);
                }
            }
        }
    }


}
