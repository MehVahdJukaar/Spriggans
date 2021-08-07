package net.mehvahdjukaar.spriggans.items;

import net.mehvahdjukaar.spriggans.client.models.BarkArmorModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;

public class BarkArmorItem extends ArmorItem implements IDyeableArmorItem {

    private static final String texture1 = "spriggans:textures/models/armor/bark_layer1.png";
    private static final String texture2 = "spriggans:textures/models/armor/bark_layer2.png";
    private static final String overlay1 = "spriggans:textures/models/armor/bark_layer1_e.png";
    private static final String overlay2 = "spriggans:textures/models/armor/bark_layer2_e.png";

    public BarkArmorItem(IArmorMaterial material, EquipmentSlotType slotType, Properties properties) {
        super(material, slotType, properties);
    }

    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) (slot == EquipmentSlotType.LEGS ? BarkArmorModel.innerModel : BarkArmorModel.outerModel);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if(type == null || type.isEmpty()) return slot == EquipmentSlotType.LEGS ? texture2 : texture1;
        return slot == EquipmentSlotType.LEGS ? overlay2 : overlay1;
    }

    @Override
    public boolean hasCustomColor(ItemStack p_200883_1_) {
        return false;
    }

    @Override
    public int getColor(ItemStack p_200886_1_) {
        return 0x00ff00;
    }

    @Override
    public void clearColor(ItemStack p_200884_1_) {

    }

    @Override
    public void setColor(ItemStack p_200885_1_, int p_200885_2_) {

    }
}
