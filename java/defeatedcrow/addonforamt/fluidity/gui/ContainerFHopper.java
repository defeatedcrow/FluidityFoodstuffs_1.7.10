package defeatedcrow.addonforamt.fluidity.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class ContainerFHopper extends Container {
	
	private TileFluidHopper tile;
	
	private int lastUpper;
	private int lastUnder;
	private int lastFluidID;
	
	public ContainerFHopper(EntityPlayer player, TileFluidHopper hopper)
	{
		this.tile = hopper;
		//input
		this.addSlotToContainer(new Slot(this.tile, 0, 100, 15));
		//output
		this.addSlotToContainer(new SlotFurnace(player, this.tile, 1, 100, 56));
		
		//player
		int i;
		for (i = 0; i < 3; ++i)
		{
			for (int h = 0; h < 9; ++h)
			{
				this.addSlotToContainer(new Slot(player.inventory, h + i * 9 + 9, 8 + h * 18, 84 + i * 18));
			}
		}
		
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		if (this.tile.productTank.getFluid() != null)
		{
			par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.productTank.getFluid().getFluid().getID());
			par1ICrafting.sendProgressBarUpdate(this, 1, tile.getUnder());
			par1ICrafting.sendProgressBarUpdate(this, 2, tile.getUpper());
		}
		else
		{
			par1ICrafting.sendProgressBarUpdate(this, 0, 0);
			par1ICrafting.sendProgressBarUpdate(this, 1, 0);
			par1ICrafting.sendProgressBarUpdate(this, 2, 0);
		}
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
 
		for (int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
			
			if (this.tile.productTank.getFluid() != null)
			{
				if (this.lastFluidID != this.tile.productTank.getFluid().getFluid().getID())
				{
					icrafting.sendProgressBarUpdate(this, 0, this.tile.productTank.getFluid().getFluid().getID());
				}
				if (this.lastUnder!= this.tile.getUnder())
				{
					icrafting.sendProgressBarUpdate(this, 1, this.tile.getUnder());
				}
				if (this.lastUpper != this.tile.getUpper())
				{
					icrafting.sendProgressBarUpdate(this, 2, this.tile.getUpper());
				}
				
				this.lastUnder = this.tile.getUnder();
				this.lastUpper = this.tile.getUpper();
				this.lastFluidID = this.tile.productTank.getFluid().getFluid().getID();
			}
			else
			{
				if (this.lastFluidID != 0)
				{
					icrafting.sendProgressBarUpdate(this, 0, 0);
				}
				if (this.lastUnder != 0)
				{
					icrafting.sendProgressBarUpdate(this, 1, 0);
				}
				if (this.lastUpper != 0)
				{
					icrafting.sendProgressBarUpdate(this, 2, 0);
				}
				
				this.lastUnder = 0;
				this.lastUpper = 0;
				this.lastFluidID = 0;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0 || par1 == 1 || par1 == 2)
		{
			this.tile.getGuiFluidUpdate(par1, par2);
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.tile.isUseableByPlayer(par1EntityPlayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(i);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (i == 1)
			{
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return null;
				}
 
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (i > 1)
			{
				if (this.tile.isItemValidForSlot(0, itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
			}
			else if (!this.mergeItemStack(itemstack1, 2, 38, false))
			{
				return null;
			}
 
			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}
 
			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
 
			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		
		return itemstack;
	}

}
