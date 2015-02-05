package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import defeatedcrow.addonforamt.fluidity.api.event.*;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class CrickSackEvent {
	
	@SubscribeEvent
	public void onSackCrick(FlourSackCrickEvent event) {
		ItemStack result = crickResult(event.current, event.world, event.target);

		if (result == null) {
			return;
		}

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack crickResult(ItemStack current, World world, MovingObjectPosition target) {
		if (current == null || target == null || target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
			return null;
		}
		else {
			
			int meta = current.getItemDamage();
			if (current.getItem() == FluidityCore.flourCont && meta == 6){
				int x = target.blockX;
				int y = target.blockY;
				int z = target.blockZ;
				
				if (world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable)Items.wheat_seeds)
						&& world.isAirBlock(x, y + 1, z))
				{
					world.setBlock(x, y + 1, z, ((IPlantable)Items.wheat_seeds).getPlant(world, x, y, z));
					return new ItemStack(Items.paper);
				}
			}
		}
		return null;
	}

}