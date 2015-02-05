package defeatedcrow.addonforamt.fluidity.fluid.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemEmptyContainer extends Item{
	
	public ItemEmptyContainer() {
		super();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon("fluiditydc:sack_empty");
	}

}
