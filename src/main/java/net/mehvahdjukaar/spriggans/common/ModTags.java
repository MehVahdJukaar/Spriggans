package net.mehvahdjukaar.spriggans.common;

import net.mehvahdjukaar.spriggans.Spriggans;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static final Tags.IOptionalNamedTag<Block> SPRIGGAN_INTEREST = blockTag("spriggan_interest");
    public static final Tags.IOptionalNamedTag<Block> GUARDED_BY_SPRIGGANS = blockTag("guarded_by_spriggans");

    private static Tags.IOptionalNamedTag<Item> itemTag(String name) {
        return ItemTags.createOptional(new ResourceLocation(Spriggans.MOD_ID, name));
    }
    private static Tags.IOptionalNamedTag<Block> blockTag(String name) {
        return BlockTags.createOptional(new ResourceLocation(Spriggans.MOD_ID, name));
    }
    private static Tags.IOptionalNamedTag<EntityType<?>> entityTag(String name) {
        return EntityTypeTags.createOptional(new ResourceLocation(Spriggans.MOD_ID, name));
    }
}
