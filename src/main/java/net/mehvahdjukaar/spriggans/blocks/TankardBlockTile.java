package net.mehvahdjukaar.spriggans.blocks;

import net.mehvahdjukaar.selene.fluids.SoftFluidHolder;
import net.mehvahdjukaar.spriggans.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.Constants;

public class TankardBlockTile extends TileEntity {

    public SoftFluidHolder fluidHolder;

    public TankardBlockTile() {
        super(ModRegistry.TANKARD_TILE.get());
        int CAPACITY = 1;
        this.fluidHolder = new SoftFluidHolder(CAPACITY);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void setChanged() {
        if(this.level==null)return;
        //TODO: only call after you finished updating your tile so others can react properly (faucets)
        this.level.updateNeighborsAt(worldPosition,this.getBlockState().getBlock());
        int light = this.fluidHolder.getFluid().getLuminosity();
        if(light!=this.getBlockState().getValue(TankardBlock.LIGHT_LEVEL)){
            this.level.setBlock(this.worldPosition,this.getBlockState().setValue(TankardBlock.LIGHT_LEVEL,light),2);
        }
        this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        super.setChanged();
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.getBlockState(),pkt.getTag());
    }

    // does all the calculation for handling player interaction.
    public boolean handleInteraction(PlayerEntity player, Hand hand) {

        //interact with fluid holder
        if (this.fluidHolder.interactWithPlayer(player, hand, level, worldPosition)) {
            return true;
        }
        //empty hand: eat food
        if(!player.isShiftKeyDown()) {
            //from drink
            return this.fluidHolder.tryDrinkUpFluid(player, this.level, hand);
        }
        return false;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.fluidHolder.load(compound);
        this.onLoad();
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        this.fluidHolder.save(compound);
        return compound;
    }
}