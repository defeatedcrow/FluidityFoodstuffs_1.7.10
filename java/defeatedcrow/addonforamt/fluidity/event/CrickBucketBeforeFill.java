package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.init.Items;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import defeatedcrow.addonforamt.fluidity.api.event.*;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class CrickBucketBeforeFill {
	
	@SubscribeEvent
	public void onBucketCrick(FlourBucketCrickEvent event) {
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
			
			boolean flag = false;
			if (current.getItem() == FluidityCore.seedBucket){
				int x = target.blockX;
				int y = target.blockY;
				int z = target.blockZ;
				
				for (int i = -1 ; i < 2 ; i++)
				{
					for (int j = -1 ; j < 2 ; j++)
					{
						if (world.getBlock(x + i, y, z + j).canSustainPlant(world, x + i, y, z + j, ForgeDirection.UP, (IPlantable)Items.wheat_seeds)
								&& world.isAirBlock(x + i, y + 1, z + j))
						{
							world.setBlock(x + i, y + 1, z + j, ((IPlantable)Items.wheat_seeds).getPlant(world, x + i, y, z + j));
							flag = true;
						}
					}
				}
				
				if (flag)
				{
					return new ItemStack(Items.bucket);
				}
			}
		}
		return null;
	}

}
