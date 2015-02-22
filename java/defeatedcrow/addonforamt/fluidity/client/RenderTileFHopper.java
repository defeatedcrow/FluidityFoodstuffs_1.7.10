package defeatedcrow.addonforamt.fluidity.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;

@SideOnly(Side.CLIENT)
public class RenderTileFHopper extends TileEntitySpecialRenderer {

	private static final ResourceLocation thisTex = new ResourceLocation("fluiditydc:textures/entity/fluid_hopper.png");
	public static RenderTileFHopper renderer;
	private ModelFluidHopper thisModel = new ModelFluidHopper();
	
	public void renderTileEntityAt(TileFluidHopper par1Tile, double par2, double par4, double par6, float par8)
    {
        this.setRotation(par1Tile, (float)par2, (float)par4, (float)par6);
    }
	
	private void setRotation(TileFluidHopper par1Tile, float par2, float par4, float par6) {
		
		byte l = (byte) par1Tile.getBlockMetadata();
		byte mode = (byte) par1Tile.getMode();
		float round = l * 90.0F - 90.0F;
		
		float valve = 0.0F;
		if (mode == 1) valve = -30.0F;
		else if (mode == 2) valve = -60.0F;
		else if (mode == 3) valve = -90.0F;
		
		this.bindTexture(thisTex);
		
		GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2 + 0.5F, (float)par4 + 1.5F, (float)par6 + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glRotatef(round, 0.0F, 1.0F, 0.0F);
        thisModel.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        
        thisModel.renderValve((Entity)null, 0.0F, 0.0F, 0.0F, valve, 0.0F, 0.0625F);
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        
    	//テセレータを使って、一枚の平面テクスチャとして表示させる。
    	Tessellator tessellator = Tessellator.instance;
    	short gauge = par1Tile.getFluidGauge();
		float g = gauge * -0.005F;
		
		if (par1Tile.getFluidIcon() != null)
    	{
    		
    		GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(2.0F, 2.0F, 2.0F, 1.0F);
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            
            IIcon iicon = par1Tile.getFluidIcon();
            float u = iicon.getMinU();
            float U = iicon.getMaxU();
            float v = iicon.getMinV();
            float V = iicon.getMaxV();
            
            this.bindTexture(TextureMap.locationBlocksTexture);
            
            float f = 0.0625F;
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, 0.0D - f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, 0.0D - f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, -0.5D, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.5D, -1.0D + f, (double)U, (double)v);
            tessellator.addVertexWithUV(1.0D - f, -0.5D, -1.0D + f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.5D + g, 0.0D - f, (double)U, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.5D, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, -0.5D, -1.0D + f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, 0.0D - f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.5D, -1.0D + f, (double)U, (double)v);
            tessellator.addVertexWithUV(1.0D - f, -0.5D, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
    	}
		
	}

	public void setTileEntityRenderer(TileEntityRendererDispatcher par1TileEntityRenderer)
    {
        super.func_147497_a(par1TileEntityRenderer);
        renderer = this;
    }
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityAt((TileFluidHopper)par1TileEntity, par2, par4, par6, par8);
    }
}
