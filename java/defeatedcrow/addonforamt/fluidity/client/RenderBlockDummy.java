package defeatedcrow.addonforamt.fluidity.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

@SideOnly(Side.CLIENT)
public class RenderBlockDummy implements ISimpleBlockRenderingHandler{
	
	private IIcon boxIIcon;

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		
		int meta = metadata;
		this.boxIIcon = block.getBlockTextureFromSide(0);
		
		if (modelID == this.getRenderId())
		{
			
		}
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
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
		
		return false;
	}

	@Override
	public int getRenderId() {
		
		return FluidityCore.renderFHopper;
	}

}
