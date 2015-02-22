package defeatedcrow.addonforamt.fluidity.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemBlockFluidContainer extends ItemBlock{

	public ItemBlockFluidContainer(Block block) {
		super(block);
	}
	
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		String s = "";
		if (nbt != null)
		{
			if (nbt != null && nbt.hasKey("fdc.type"))
			{
				s = nbt.getString("fdc.type");
			}
			if (nbt != null && nbt.hasKey("fdc.amount"))
			{
				s += " " + nbt.getInteger("fdc.amount");
			}
			par3List.add(new String("Fluid : " + s));
		}
	}

}
