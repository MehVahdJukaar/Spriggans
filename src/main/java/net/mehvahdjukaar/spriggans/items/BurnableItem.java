package net.mehvahdjukaar.spriggans.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BurnableItem extends Item {
    private final int burnTime;
    public BurnableItem(Properties builder, int burnTicks) {
        super(builder);
        this.burnTime = burnTicks;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
}
