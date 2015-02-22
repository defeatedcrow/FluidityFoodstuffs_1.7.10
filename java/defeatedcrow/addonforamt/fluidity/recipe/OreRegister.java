package defeatedcrow.addonforamt.fluidity.recipe;

import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreRegister {
	
	private OreRegister(){}
	
	public static void load()
	{
		OreDictionary.registerOre("foodFlour", new ItemStack(FluidityCore.flourCont, 1, 0));
		OreDictionary.registerOre("foodSalt", new ItemStack(FluidityCore.flourCont, 1, 1));
		OreDictionary.registerOre("foodSugar", new ItemStack(FluidityCore.flourCont, 1, 2));
		OreDictionary.registerOre("foodMilk", new ItemStack(FluidityCore.flourCont, 1, 3));
		OreDictionary.registerOre("foodWheat", new ItemStack(FluidityCore.flourCont, 1, 4));
		OreDictionary.registerOre("foodRice", new ItemStack(FluidityCore.flourCont, 1, 5));
		OreDictionary.registerOre("foodSeed", new ItemStack(FluidityCore.flourCont, 1, 6));
		OreDictionary.registerOre("foodWater", new ItemStack(FluidityCore.flourCont, 1, 7));
		
		OreDictionary.registerOre("bucketFlour", new ItemStack(FluidityCore.flourBucket, 1, 0));
		OreDictionary.registerOre("bucketSalt", new ItemStack(FluidityCore.saltBucket, 1, 0));
		OreDictionary.registerOre("bucketSugar", new ItemStack(FluidityCore.sugarBucket, 1, 0));
		OreDictionary.registerOre("bucketWheat", new ItemStack(FluidityCore.wheatBucket, 1, 0));
		OreDictionary.registerOre("bucketRice", new ItemStack(FluidityCore.riceBucket, 1, 0));
		OreDictionary.registerOre("bucketSeed", new ItemStack(FluidityCore.seedBucket, 1, 0));
		
		//vanilla items
		OreDictionary.registerOre("bucketMilk", new ItemStack(Items.milk_bucket, 1, 0));
		OreDictionary.registerOre("bucketWater", new ItemStack(Items.water_bucket, 1, 0));
		OreDictionary.registerOre("cropWheat", new ItemStack(Items.wheat, 1, 0));
		OreDictionary.registerOre("foodSugar", new ItemStack(Items.sugar, 1, 0));
		OreDictionary.registerOre("foodSeed", new ItemStack(Items.wheat_seeds, 1, 0));
	}

}
