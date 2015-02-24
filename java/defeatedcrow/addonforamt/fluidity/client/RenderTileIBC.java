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
import cpw.mods.fml.relauncher.*;
import defeatedcrow.addonforamt.fluidity.block.TileFluidIBC;
import defeatedcrow.addonforamt.fluidity.common.FFConfig;

/*
 * 桶の中身の部分を描写しているクラス。
 * アイコンはFluidが持っているものをTileEntityの液体タンクから取得している。
 * よって、Fluidとしてアイコンを登録されていれば、MODで追加される液体にも対応している。
 */
@SideOnly(Side.CLIENT)
public class RenderTileIBC extends TileEntitySpecialRenderer
{
	private static final ResourceLocation bodyTex = new ResourceLocation("fluiditydc:textures/entity/ibc_body.png");
	private static final ResourceLocation cageTex = new ResourceLocation("fluiditydc:textures/entity/ibc_cage.png");
	private static final ResourceLocation bottomTex = new ResourceLocation("textures/blocks/hopper_inside.png");
    public static RenderTileIBC renderer;
    private ModelIBC thisModel = new ModelIBC();

    public void renderTileEntityIBCAt(TileFluidIBC par1Tile, double par2, double par4, double par6, float par8)
    {
        this.setRotation(par1Tile, (float)par2, (float)par4, (float)par6);
    }

    public void setTileEntityRenderer(TileEntityRendererDispatcher par1TileEntityRenderer)
    {
        super.func_147497_a(par1TileEntityRenderer);
        renderer = this;
    }

    public void setRotation(TileFluidIBC par0Tile, float par1, float par2, float par3)
    {
    	this.bindTexture(bottomTex);
		GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par1 + 0.5F, (float)par2 + 1.5F, (float)par3 + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
        thisModel.renderBottom((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        
        this.bindTexture(cageTex);
		GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par1 + 0.5F, (float)par2 + 1.5F, (float)par3 + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
        thisModel.renderCage((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    	
    	//テセレータを使って、一枚の平面テクスチャとして表示させる。
    	Tessellator tessellator = Tessellator.instance;
    	short gauge = par0Tile.getFluidGauge();
		float g = gauge * -0.00875F;
    	
    	if (par0Tile.getFluidIcon() != null)
    	{
    		
    		GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(2.0F, 2.0F, 2.0F, 1.0F);
            GL11.glTranslatef((float)par1, (float)par2, (float)par3);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
            
            IIcon iicon = par0Tile.getFluidIcon();
            float u = iicon.getMinU();
            float U = iicon.getMaxU();
            float v = iicon.getMinV();
            float V = iicon.getMaxV();
            
            this.bindTexture(TextureMap.locationBlocksTexture);
            
            float f = 0.0625F;
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, 0.0D - f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, 0.0D - f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, 0.0D, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, 0.0D, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(0.0D + f, 0.0D, -1.0D + f, (double)U, (double)v);
            tessellator.addVertexWithUV(1.0D - f, 0.0D, -1.0D + f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, -1.0D + f, (double)u, (double)V);
            tessellator.addVertexWithUV(0.0D + f, -0.1D + g, 0.0D - f, (double)U, (double)V);
            tessellator.addVertexWithUV(0.0D + f, 0.0D, 0.0D - f, (double)U, (double)v);
            tessellator.addVertexWithUV(0.0D + f, 0.0D, -1.0D + f, (double)u, (double)v);
            tessellator.draw();
            
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, 0.0D - f, (double)u, (double)V);
            tessellator.addVertexWithUV(1.0D - f, -0.1D + g, -1.0D + f, (double)U, (double)V);
            tessellator.addVertexWithUV(1.0D - f, 0.0D, -1.0D + f, (double)U, (double)v);
            tessellator.addVertexWithUV(1.0D - f, 0.0D, 0.0D - f, (double)u, (double)v);
            tessellator.draw();
            
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
    	}
    	
    	float f = FFConfig.IBCalpha;
    	
    	this.bindTexture(bodyTex);
		GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.5F, 1.5F, 1.5F, f);
        GL11.glTranslatef((float)par1 + 0.5F, (float)par2 + 1.5F, (float)par3 + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 0.0F);
        thisModel.renderBody((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        
    }

    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityIBCAt((TileFluidIBC)par1TileEntity, par2, par4, par6, par8);
    }
}
