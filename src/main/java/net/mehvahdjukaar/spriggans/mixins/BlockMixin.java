package net.mehvahdjukaar.spriggans.mixins;

import net.mehvahdjukaar.spriggans.common.ModTags;
import net.mehvahdjukaar.spriggans.entity.SprigganEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {
    public BlockMixin(Properties p_i241196_1_) {
        super(p_i241196_1_);
    }

    @Inject(method = "playerWillDestroy", at = @At("TAIL"), cancellable = true)
    public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_, CallbackInfo ci){
        if (this.is(ModTags.GUARDED_BY_SPRIGGANS)) {
            SprigganEntity.angerNearbySpriggans(p_176208_4_, false);
        }
    }

    @Shadow
    public boolean is(ITag<Block> p_203417_1_) {
        return false;
    }


}
