package net.mehvahdjukaar.spriggans.client.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.mehvahdjukaar.spriggans.blocks.TankardBlockTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;


public class TankarcBlockTileRenderer extends TileEntityRenderer<TankardBlockTile> {

    public TankarcBlockTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    public static void renderFluid(float h, int color, int luminosity, ResourceLocation texture, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int light, int combinedOverlayIn, boolean shading){
        matrixStackIn.pushPose();
        float opacity = 1;//tile.liquidType.opacity;
        if(luminosity!=0) light = light & 15728640 | luminosity << 4;
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(texture);
        // TODO:remove breaking animation
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.translucentMovingBlock());
        matrixStackIn.translate(0.5, 0.0625, 0.5);

        float w = 0.25f;


        int lu = light & '\uffff';
        int lv = light >> 16 & '\uffff'; // ok
        float atlasscaleU = sprite.getU1() - sprite.getU0();
        float atlasscaleV = sprite.getV1() - sprite.getV0();
        float minu = sprite.getU0();
        float minv = sprite.getV0();
        float maxu = minu + atlasscaleU * w;
        float maxv = minv + atlasscaleV * h;
        float maxv2 = minv + atlasscaleV * w;

        float r = (float) ((color >> 16 & 255)) / 255.0F;
        float g = (float) ((color >> 8 & 255)) / 255.0F;
        float b = (float) ((color & 255)) / 255.0F;



        float hw = w / 2f;

        addQuadTop(builder, matrixStackIn, -hw, h, hw, hw, h, -hw, minu, minv, maxu, maxv2, r, g, b, opacity, lu, lv, 0, 1, 0);

        matrixStackIn.popPose();
    }

    public static void addQuadTop(IVertexBuilder builder, MatrixStack matrixStackIn, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, float r, float g,
                                  float b, float a, int lu, int lv, float nx, float ny, float nz) {
        addVert(builder, matrixStackIn, x0, y0, z0, u0, v1, r, g, b, a, lu, lv, nx, ny, nz);
        addVert(builder, matrixStackIn, x1, y0, z0, u1, v1, r, g, b, a, lu, lv, nx, ny, nz);
        addVert(builder, matrixStackIn, x1, y1, z1, u1, v0, r, g, b, a, lu, lv, nx, ny, nz);
        addVert(builder, matrixStackIn, x0, y1, z1, u0, v0, r, g, b, a, lu, lv, nx, ny, nz);
    }


    public static void addVert(IVertexBuilder builder, MatrixStack matrixStackIn, float x, float y, float z, float u, float v, float r, float g,
                               float b, float a, int lu, int lv, float nx, float ny, float nz) {
        builder.vertex(matrixStackIn.last().pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lu, lv)
                .normal(matrixStackIn.last().normal(), nx, ny, nz).endVertex();
    }


    @Override
    public void render(TankardBlockTile tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tile.fluidHolder.isEmpty()){

            renderFluid(7/16f, tile.fluidHolder.getTintColor(tile.getLevel(),tile.getBlockPos()), tile.fluidHolder.getFluid().getLuminosity(),
                    tile.fluidHolder.getFluid().getStillTexture(), matrixStackIn,bufferIn,combinedLightIn,combinedOverlayIn,true);
        }
    }
}

