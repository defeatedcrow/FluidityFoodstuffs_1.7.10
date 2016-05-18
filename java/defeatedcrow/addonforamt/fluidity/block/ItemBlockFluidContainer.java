package defeatedcrow.addonforamt.fluidity.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import defeatedcrow.addonforamt.fluidity.common.FFConfig;

public class ItemBlockFluidContainer extends ItemBlock implements IFluidContainerItem {

	public ItemBlockFluidContainer(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		String s = "";
		if (nbt != null) {
			if (nbt != null && nbt.hasKey("fdc.type")) {
				s = nbt.getString("fdc.type");
			}
			if (nbt != null && nbt.hasKey("fdc.amount")) {
				s += " " + nbt.getInteger("fdc.amount");
			}
			par3List.add(new String("Fluid : " + s));
		}
	}

	@Override
	public FluidStack getFluid(ItemStack cont) {
		if (cont == null || cont.getItem() == null) {
			return null;
		}
		NBTTagCompound nbt = cont.getTagCompound();
		if (nbt != null) {
			Fluid f = null;
			int amo = 0;
			if (nbt.hasKey("fdc.id")) {
				int id = nbt.getInteger("fdc.id");
				f = FluidRegistry.getFluid(id);
			}
			if (nbt != null && nbt.hasKey("fdc.amount")) {
				amo = nbt.getInteger("fdc.amount");
			}
			if (f != null) {
				return new FluidStack(f, amo);
			}
		}
		return null;
	}

	@Override
	public int getCapacity(ItemStack cont) {
		return FFConfig.sizeIBC;
	}

	@Override
	public int fill(ItemStack cont, FluidStack resource, boolean doFill) {
		if (resource == null || resource.getFluid() == null) {
			return 0;
		}

		FluidStack current = this.getFluid(cont);
		FluidStack resourceCopy = resource.copy();
		int cap = this.getCapacity(cont);
		if (current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)) {
			return 0;
		}

		int i = 0;
		if (current == null || current.amount <= 0) {
			i = Math.min(cap, resourceCopy.amount);
			if (i > 0 && doFill) {
				NBTTagCompound nbt = cont.getTagCompound();
				if (nbt == null)
					nbt = new NBTTagCompound();
				nbt.setString("fdc.type", resourceCopy.getFluid().getLocalizedName(resourceCopy));
				nbt.setInteger("fdc.id", resourceCopy.getFluid().getID());
				nbt.setInteger("fdc.amount", i);
				cont.setTagCompound(nbt);
			}
		} else {
			cap -= current.amount;
			i = Math.min(cap, resourceCopy.amount);
			if (i > 0 && doFill) {
				NBTTagCompound nbt = cont.getTagCompound();
				if (nbt == null)
					nbt = new NBTTagCompound();
				nbt.setInteger("fdc.amount", current.amount + i);
				cont.setTagCompound(nbt);
			}
		}

		return i;
	}

	@Override
	public FluidStack drain(ItemStack cont, int maxDrain, boolean doDrain) {
		if (maxDrain <= 0) {
			return null;
		}

		FluidStack current = this.getFluid(cont);
		int cap = this.getCapacity(cont);
		if (current == null || current.amount <= 0) {
			return null;
		}

		int i = 0;
		i = Math.min(maxDrain, current.amount);
		if (i > 0) {
			if (doDrain) {
				NBTTagCompound nbt = cont.getTagCompound();
				if (nbt == null)
					nbt = new NBTTagCompound();
				if (current.amount > i) {
					int j = current.amount - i;
					nbt.setInteger("fdc.amount", j);
				} else {
					nbt.removeTag("fdc.id");
					nbt.setString("fdc.type", "Empty");
					nbt.removeTag("fdc.amount");
				}
				cont.setTagCompound(nbt);
			}
			return new FluidStack(current.getFluid(), i);
		}
		return null;
	}

}
