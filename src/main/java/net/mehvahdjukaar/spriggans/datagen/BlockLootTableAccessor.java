package net.mehvahdjukaar.spriggans.datagen;


import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootTable;
import net.minecraft.util.IItemProvider;

public class BlockLootTableAccessor extends BlockLootTables {

    public static LootTable.Builder dropping(IItemProvider item) {
        return BlockLootTables.createSingleItemTable(item);
    }

}