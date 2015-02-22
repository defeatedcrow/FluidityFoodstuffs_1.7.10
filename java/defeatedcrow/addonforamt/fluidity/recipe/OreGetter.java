package defeatedcrow.addonforamt.fluidity.recipe;

import java.util.*;

import defeatedcrow.addonforamt.fluidity.common.FFConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;

public class OreGetter {
	
	private OreGetter(){}
	
	private static ArrayList<ItemStack> flours = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> salts = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> sugars = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> milks = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> wheats = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> rices = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> seeds = new ArrayList<ItemStack>();
	private static ArrayList<ItemStack> waters = new ArrayList<ItemStack>();
	
	private static Map<Integer, ArrayList<ItemStack>> lists = new HashMap<Integer, ArrayList<ItemStack>>();
	
	public static void listSetUp()
	{
		lists.put(0, flours);
		lists.put(1, salts);
		lists.put(2, sugars);
		lists.put(3, milks);
		lists.put(4, wheats);
		lists.put(5, rices);
		lists.put(6, seeds);
		lists.put(7, waters);
	}
	
	public static ArrayList<ItemStack> getList(int i)
	{
		MathHelper.clamp_int(i, 0, 7);
		return lists.get(i);
	}
	
	public static void registerListItems()
	{
		registerOres(0, FFConfig.flourList);
		registerOres(1, FFConfig.saltList);
		registerOres(2, FFConfig.sugarList);
		registerOres(3, FFConfig.milkList);
		registerOres(4, FFConfig.wheatList);
		registerOres(5, FFConfig.riceList);
		registerOres(6, FFConfig.seedList);
		registerOres(7, FFConfig.waterList);
	}
	
	public static void integrateOreDic()
	{
		registerIntegrateDic(flours, FFConfig.flourList);
		registerIntegrateDic(salts, FFConfig.saltList);
		registerIntegrateDic(sugars, FFConfig.sugarList);
		registerIntegrateDic(milks, FFConfig.milkList);
		registerIntegrateDic(wheats, FFConfig.wheatList);
		registerIntegrateDic(rices, FFConfig.riceList);
		registerIntegrateDic(seeds, FFConfig.seedList);
		registerIntegrateDic(waters, FFConfig.waterList);
	}
	
	private static void registerOres(int i, String[] names)
	{
		ArrayList<ItemStack> l = getList(i);
		for (String name : names)
		{
			List<ItemStack> items = OreDictionary.getOres(name);
			for (ItemStack item : items)
			{
				boolean match = false;
				for (ItemStack check : l)
				{
					if (matches(check, item)) match = true;
				}
				if (!match) l.add(item);
			}
		}
	}
	
	private static void registerIntegrateDic(List<ItemStack> list, String[] names)
	{
		if (list == null || list.isEmpty() || names.length < 0) return;
		
		for (ItemStack item : list)
		{
			for (String ore : names)
			{
				OreDictionary.registerOre(ore, item);
			}
		}
	}
	
	static boolean matches(ItemStack item, ItemStack target)
	{
		if (item == null || target == null) return false;
		
		return item.getItem() == target.getItem() && item.getItemDamage() == target.getItemDamage();
	}

}
