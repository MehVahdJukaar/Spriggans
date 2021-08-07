package net.mehvahdjukaar.spriggans.items;

import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class BarkArmorMaterial implements IArmorMaterial {


    @Override
    public int getDurabilityForSlot(EquipmentSlotType slot) {
        return new int[]{13, 15, 16, 11}[slot.getIndex()] * 18;
    }


    @Override
    public int getDefenseForSlot(EquipmentSlotType slot) {
        return new int[]{2, 7, 5, 2}[slot.getIndex()];
    }


    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModRegistry.BARK.get());
    }


    @Override
    public String getName() {
        return "bark_armor";
    }

    @Override
    public float getToughness() {
        return 1f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.05f;
    }

}
