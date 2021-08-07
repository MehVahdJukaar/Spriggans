package net.mehvahdjukaar.spriggans.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SprigganBow extends BowItem {
    public SprigganBow(Properties properties) {
        super(properties);
    }
    public int getEnchantmentValue() {
        return 2;
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        boolean flag = !p_77659_2_.getProjectile(itemstack).isEmpty();

        if(!flag && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemstack) > 0)flag = true;

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_77659_1_, p_77659_2_, p_77659_3_, flag);
        if (ret != null) return ret;

        if (!p_77659_2_.abilities.instabuild && !flag) {
            return ActionResult.fail(itemstack);
        } else {
            p_77659_2_.startUsingItem(p_77659_3_);
            return ActionResult.consume(itemstack);
        }
    }


    public int getDefaultProjectileRange() {
        return 16;
    }


    public int getUseDuration(ItemStack p_77626_1_) {
        return 66000;
    }


    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 300;
    }
}
