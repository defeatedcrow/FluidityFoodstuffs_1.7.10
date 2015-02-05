package defeatedcrow.addonforamt.fluidity.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class IMCReceptor {
	
	private IMCReceptor(){}
	
	private static ArrayList<ItemStack> exclusionAltList = new ArrayList<ItemStack>();
	
	private static Map<ItemStack, String> replaceAltTable = new HashMap<ItemStack, String>();
	
	public static ArrayList<ItemStack> getExclusionList()
	{
		return exclusionAltList;
	}
	
	public static Map<ItemStack, String> getReplaceTable()
	{
		return replaceAltTable;
	}
	
	public static void receiveIMC(IMCEvent event)
	{
		for (IMCMessage message : event.getMessages())
		{
			if (message.key.equals("Exclusion"))
			{
				receiveExclusion(event, message);
			}
			else if (message.key.equals("Replace"))
			{
				receiveReplaceItem(event, message);
			}
			else
			{
				FluidityCore.logger.warn("Received IMC message with unknown key : " + message.key);
			}
		}
	}
	
	private static void receiveExclusion(IMCEvent event, IMCMessage message)
	{
		boolean flag = false;
		if (message.isNBTMessage())
		{
			NBTTagCompound tag = message.getNBTValue();
			if (tag.hasKey("item", 10))
			{
				ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"));
				if (input != null && input.getItem() != null)
				{
					exclusionAltList.add(input);
					FluidityCore.logger.trace("Register customize recipe exclusion : " + input.getDisplayName() + " by " + message.getSender());
				}
			}
		}
	}
	
	private static void receiveReplaceItem(IMCEvent event, IMCMessage message)
	{
		boolean flag = false;
		if (message.isNBTMessage())
		{
			NBTTagCompound tag = message.getNBTValue();
			if (tag.hasKey("item", 10) && tag.hasKey("oredic"))
			{
				ItemStack input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"));
				String name = tag.getString("oredic");
				
				if (input != null && input.getItem() != null && name != null)
				{
					replaceAltTable.put(input, name);
					FluidityCore.logger.trace("Register recipe customize elements : " + input.getDisplayName() + " -> " + name + " by " + message.getSender());
				}
			}
		}
	}

}
