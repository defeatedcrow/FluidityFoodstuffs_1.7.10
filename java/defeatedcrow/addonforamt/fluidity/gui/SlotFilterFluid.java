package defeatedcrow.addonforamt.fluidity.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import defeatedcrow.addonforamt.fluidity.block.TileAdvFluidHopper;
import defeatedcrow.addonforamt.fluidity.packet.MessageHopperFilter;
import defeatedcrow.addonforamt.fluidity.packet.NetworkHandlerFF;

public class SlotFilterFluid extends Slot {

	public SlotFilterFluid(IInventory inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}

	/*
	 * このアイテムは動かせない、つかめないようにする。
	 */
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	/*
	 * ItemStackは入れられず、かわりにフィルター設定を変更
	 */
	@Override
	public boolean isItemValid(ItemStack item) {
		if (item != null && item.getItem() != null && FluidContainerRegistry.isFilledContainer(item)) {
			FluidStack fillCont = FluidContainerRegistry.getFluidForFilledItem(item);
			if (fillCont != null && fillCont.getFluid() != null) {
				if (this.inventory != null && this.inventory instanceof TileAdvFluidHopper) {
					TileAdvFluidHopper hopper = (TileAdvFluidHopper) this.inventory;
					if (hopper.getWorldObj().isRemote) {
						MessageHopperFilter message = new MessageHopperFilter(fillCont.getFluid().getID(),
								hopper.xCoord, hopper.yCoord, hopper.zCoord);
						NetworkHandlerFF.INSTANCE.sendToServer(message);
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
	}

	@Override
	public void putStack(ItemStack p_75215_1_) {
	}

	@Override
	public void onSlotChange(ItemStack item, ItemStack slot) {
	}

	@Override
	public boolean getHasStack() {
		return false;
	}

}
