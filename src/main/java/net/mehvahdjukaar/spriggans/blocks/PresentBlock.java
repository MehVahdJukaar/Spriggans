package net.mehvahdjukaar.spriggans.blocks;


import net.mehvahdjukaar.selene.blocks.WaterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

public class PresentBlock extends WaterBlock {

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    private final DyeColor color;

    public PresentBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }
}
