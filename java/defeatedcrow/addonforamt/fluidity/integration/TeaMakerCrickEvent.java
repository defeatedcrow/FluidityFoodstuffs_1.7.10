package defeatedcrow.addonforamt.fluidity.integration;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import mods.defeatedcrow.api.appliance.ITeaMaker;
import mods.defeatedcrow.api.events.TeamakerRightClickEvent;

public class TeaMakerCrickEvent {
	
	@SubscribeEvent
	public void teamakerEvent(TeamakerRightClickEvent event)
	{
		TileEntity tile = event.teaMaker;
		ItemStack current = event.player.inventory.getCurrentItem();
		
		if (tile == null || current == null)
		{
			return;
		}
		
		if (tile instanceof ITeaMaker && event.currentRecpe == null && current.getItem() == Items.paper)
		{
			ItemStack ret = new ItemStack(FluidityCore.flourCont, 1, 7);
			
			if (!event.player.capabilities.isCreativeMode && --current.stackSize <= 0)
			{
				event.player.inventory.setInventorySlotContents(event.player.inventory.currentItem, (ItemStack)null);
			}
			
			if (!event.player.inventory.addItemStackToInventory(ret))
			{
				if (!event.player.worldObj.isRemote) event.player.entityDropItem(ret, 0.5F);
			}
			
			event.player.worldObj.playSoundAtEntity(event.player, "random.pop", 0.4F, 1.8F);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);
		}
		
		
	}

}
