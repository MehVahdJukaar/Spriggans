package net.mehvahdjukaar.spriggans.datagen;

import net.mehvahdjukaar.spriggans.blocks.PresentBlock;
import net.mehvahdjukaar.spriggans.init.ModRegistry;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for(DyeColor color :DyeColor.values()){
            makePresentBlock(color);
        }
    }



    private void makePresentBlock(DyeColor color){

        getVariantBuilder(ModRegistry.PRESENTS.get(color).get()).forAllStatesExcept(state -> {
            String baseName = ModRegistry.PRESENT_NAME;

            ModelFile model = presentModel(color, baseName,state.getValue(PresentBlock.OPEN)?"opened": "closed");

            return ConfiguredModel.builder().modelFile(model)
                    .build();
        }, PresentBlock.WATERLOGGED);

    }

    private ModelFile presentModel(DyeColor color, String baseName, String type){
        return models().getBuilder(baseName+"_"+type+"_"+color.getName())
                .parent(new ModelFile.UncheckedModelFile(modLoc("block/"+baseName+"_"+type+"_template")))
                .texture("particle", "blocks/presents/present_side_"+color.getName())
                .texture("side", "blocks/presents/present_side_"+color.getName())
                .texture("top", "blocks/presents/present_top_"+color.getName())
                .texture("bottom", "blocks/presents/present_bottom_"+color.getName())
                .texture("inside", "blocks/presents/present_inside_"+color.getName());
    }

}
