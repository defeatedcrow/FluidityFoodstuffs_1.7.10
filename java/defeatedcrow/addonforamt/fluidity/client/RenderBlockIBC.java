package defeatedcrow.addonforamt.fluidity.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.block.TileFluidIBC;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

/*
 * このクラスでは、桶の部分のレンダーを作っている。
 * 中身のみ、別のクラス（TileEntityの特殊描画クラス）で描画している。
 */
@SideOnly(Side.CLIENT)
public class RenderBlockIBC implements ISimpleBlockRenderingHandler{
	
	private IIcon boxIIcon;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		
		//アイコンはバニラの樫の木材
		this.boxIIcon = FluidityCore.fluidIBC.getBlockTextureFromSide(0);
		
		if (modelID == this.getRenderId())
		{
//			//底
//			renderInvCuboid(renderer, block,  0.0F/16.0F, 0.0F/16.0F, 0.0F/16.0F, 16.0F/16.0F, 2.0F/16.0F, 16.0F/16.0F,  this.boxIIcon);
//			
//			//壁面
//			renderInvCuboid(renderer, block,  0.0F/16.0F, 2.0F/16.0F, 0.0F/16.0F, 16.0F/16.0F, 16.0F/16.0F, 1.0F/16.0F,  this.boxIIcon);
//			renderInvCuboid(renderer, block,  0.0F/16.0F, 2.0F/16.0F, 15.0F/16.0F, 16.0F/16.0F, 16.0F/16.0F, 16.0F/16.0F,  this.boxIIcon);
//			renderInvCuboid(renderer, block,  0.0F/16.0F, 2.0F/16.0F, 1.0F/16.0F, 1.0F/16.0F, 16.0F/16.0F, 15.0F/16.0F,  this.boxIIcon);
//			renderInvCuboid(renderer, block,  15.0F/16.0F, 2.0F/16.0F, 1.0F/16.0F, 16.0F/16.0F, 16.0F/16.0F, 15.0F/16.0F,  this.boxIIcon);
		}
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		this.boxIIcon = FluidityCore.fluidIBC.getBlockTextureFromSide(0);
		
		if (modelId == this.getRenderId())
		{
			renderer.clearOverrideBlockTexture();
			block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.setRenderBoundsFromBlock(block);
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int a) {
		
		return true;
	}

	//コアクラスのレンダー用IDをここに入れる
	@Override
	public int getRenderId() {
		
		return FluidityCore.renderIBC;
	}
	
	private void renderInvCuboid(RenderBlocks renderer, Block block, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, IIcon icon)
	{
		Tessellator tessellator = Tessellator.instance;
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		renderer.setRenderBoundsFromBlock(block);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
	}
}
