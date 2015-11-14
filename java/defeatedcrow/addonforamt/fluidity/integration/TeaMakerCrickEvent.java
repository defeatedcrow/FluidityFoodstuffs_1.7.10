package defeatedcrow.addonforamt.fluidity.integration;

import mods.defeatedcrow.api.appliance.ITeaMaker;
import mods.defeatedcrow.api.events.TeamakerRightClickEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class TeaMakerCrickEvent {

	@SubscribeEvent
	public void teamakerEvent(TeamakerRightClickEvent event) {
		TileEntity tile = event.teaMaker;
		ItemStack current = event.player.inventory.getCurrentItem();

		if (tile == null || current == null || !(tile instanceof ITeaMaker))
			return;

		ITeaMaker maker = (ITeaMaker) tile;

		if (event.currentRecpe == null && current.getItem() == Items.paper) {
			ItemStack ret = new ItemStack(FluidityCore.flourCont, 1, 7);

			if (!event.player.capabilities.isCreativeMode && --current.stackSize <= 0) {
				event.player.inventory.setInventorySlotContents(event.player.inventory.currentItem, (ItemStack) null);
			}

			if (!event.player.inventory.addItemStackToInventory(ret)) {
				if (!event.player.worldObj.isRemote)
					event.player.entityDropItem(ret, 0.5F);
			}

			event.player.worldObj.playSoundAtEntity(event.player, "random.pop", 0.4F, 1.8F);
			event.setResult(Result.ALLOW);
			event.setCanceled(true);

		}

		boolean milk = false;
		ItemStack ret = null;
		if (current.getItem() == FluidityCore.flourCont) {
			if (current.getItemDamage() == 3 || current.getItemDamage() == 9) {
				milk = true;
				ret = new ItemStack(Items.paper);
			}
		} else if (current.getItem() == FluidityCore.milkBucket) {
			milk = true;
			ret = new ItemStack(Items.bucket);
		}

		if (milk) {
			boolean f = false;

			if (event.currentRecpe == null && maker.canSetRecipe(new ItemStack(Items.milk_bucket))) {
				maker.setRecipe(new ItemStack(Items.milk_bucket));
				f = true;
			} else if (!maker.getMilked()) {
				maker.setMilk(true);
				f = true;
			}
			if (f) {
				if (tile.getWorldObj().isRemote) {
					event.setResult(Result.ALLOW);
					event.setCanceled(true);
					return;
					// 右クリック処理をキャンセルしておく
				}

				if (!event.player.capabilities.isCreativeMode && --current.stackSize <= 0) {
					event.player.inventory.setInventorySlotContents(event.player.inventory.currentItem,
							(ItemStack) null);
				}
				if (ret != null) {
					EntityItem drop = new EntityItem(tile.getWorldObj(), event.player.posX, event.player.posY,
							event.player.posZ, ret);
					tile.getWorldObj().spawnEntityInWorld(drop);
				}
				tile.getWorldObj().playSoundAtEntity(event.player, "random.pop", 0.4F, 1.8F);
				tile.getWorldObj().markBlockForUpdate(event.x, event.y, event.z);

			}
		}

	}

}
