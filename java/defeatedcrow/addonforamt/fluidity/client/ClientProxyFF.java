package defeatedcrow.addonforamt.fluidity.client;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.common.CommonProxyFF;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class ClientProxyFF extends CommonProxyFF {
	
	@Override
	public void registerFluidTex()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Post event)
	{
		FluidityCore.flourFluid.setIcons(FluidityCore.flourBlock.getIcon(0, 0));
		FluidityCore.saltFluid.setIcons(FluidityCore.saltBlock.getIcon(0, 0));
		FluidityCore.sugarFluid.setIcons(FluidityCore.sugarBlock.getIcon(0, 0));
		FluidityCore.wheatFluid.setIcons(FluidityCore.wheatBlock.getIcon(0, 0));
		FluidityCore.riceFluid.setIcons(FluidityCore.riceBlock.getIcon(0, 0));
		FluidityCore.seedFluid.setIcons(FluidityCore.seedBlock.getIcon(0, 0));
		FluidityCore.milkFluid.setIcons(FluidityCore.milkBlock.getIcon(0, 0));
	}

}
