package defeatedcrow.addonforamt.fluidity.common;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;
import defeatedcrow.addonforamt.fluidity.block.TileFluidIBC;
import defeatedcrow.addonforamt.fluidity.gui.ContainerFHopper;
import defeatedcrow.addonforamt.fluidity.gui.GuiFHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxyFF implements IGuiHandler{
	
	public int getRenderID()
	{
		return -1;
	}
	
	public void registerRenderers(){}
	
	public int addArmor(String armor)
	{
		return 0;
	}

	public World getClientWorld() {
		
		return null;
	}
	
	public void registerFluidTex() {}

	public void registerTileEntity()
	{
		GameRegistry.registerTileEntity(TileFluidIBC.class, "tileFluidIBC");
		GameRegistry.registerTileEntity(TileFluidHopper.class, "tileFluidHopper");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileFluidHopper)
		{
			return new ContainerFHopper(player, (TileFluidHopper) tileentity);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileFluidHopper)
		{
			return new GuiFHopper(player, (TileFluidHopper) tileentity);
		}
		
		return null;
	}

}
