package defeatedcrow.addonforamt.fluidity.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class BasicRecipe {
	
	private BasicRecipe(){}
	
	public static void addRecipe()
	{
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.flourBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 1),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.saltBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 2),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.sugarBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 3),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', Items.milk_bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 4),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.wheatBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 5),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.riceBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 6),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', FluidityCore.seedBucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourCont, 8, 7),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', new ItemStack(Items.paper),
	 						 'Y', Items.water_bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.flourBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodFlour",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.saltBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodSalt",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.sugarBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodSugar",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(Items.milk_bucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodMilk",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.wheatBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodWheat",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.riceBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodRice",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.seedBucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodSeed",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(Items.water_bucket, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "XYX",
	 						 "XXX",
	 						 'X', "foodWater",
	 						 'Y', Items.bucket}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidIBC, 1, 0),
	 	    	new Object[]{
	 						 "XYX",
	 						 "Y Y",
	 						 "XYX",
	 						 'X', "ingotIron",
	 						 'Y', "slimeball"}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidIBC, 1, 0),
	 	    	new Object[]{
	 						 "XYX",
	 						 "Y Y",
	 						 "XYX",
	 						 'X', "ingotCopper",
	 						 'Y', "slimeball"}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidIBC, 1, 0),
	 	    	new Object[]{
	 						 "XYX",
	 						 "Y Y",
	 						 "XYX",
	 						 'X', Items.iron_ingot,
	 						 'Y', Items.slime_ball}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidHopper, 1, 0),
	 	    	new Object[]{
	 						 "X X",
	 						 "XYX",
	 						 " X ",
	 						 'X', "ingotIron",
	 						 'Y', "slimeball"}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidHopper, 1, 0),
	 	    	new Object[]{
	 						 "X X",
	 						 "XYX",
	 						 " X ",
	 						 'X', "ingotCopper",
	 						 'Y', "slimeball"}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidHopper, 1, 0),
	 	    	new Object[]{
	 						 "X X",
	 						 "XYX",
	 						 " X ",
	 						 'X', Items.iron_ingot,
	 						 'Y', Items.slime_ball}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(FluidityCore.fluidHopperAdv, 1, 0),
	 	    	new Object[]{
	 						 "X",
	 						 "Y",
	 						 "Z",
	 						 'X', Blocks.daylight_detector,
	 						 'Y', FluidityCore.fluidHopper,
	 						 'Z', Items.comparator}));
		
		//バニラレシピの追加
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(Items.cookie, 1, 0),
	 	    	new Object[]{
	 						 "XYX",
	 						 'X', "foodFlour",
	 						 'Y', new ItemStack(Items.dye, 1, 3)}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(Items.bread, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 'X', "foodFlour"}));
		
		GameRegistry.addRecipe(
	 			new ShapedOreRecipe(
	 	    	new ItemStack(Items.cake, 1, 0),
	 	    	new Object[]{
	 						 "XXX",
	 						 "YZY",
	 						 "WWW",
	 						 'X', "foodMilk",
	 						 'Z', new ItemStack(Items.egg),
	 						 'Y', "foodSugar",
	 						 'W', "foodFlour",}));
		
		//AMT無しでは小麦粉を入手できないので、救済レシピを追加する。
		if (!Loader.isModLoaded("DCsAppleMilk"))
		{
			GameRegistry.addRecipe(
					 new ShapelessOreRecipe(
		    		  new ItemStack(FluidityCore.flourCont, 2, 0),
		    		  new Object[]{
		    			  "cropWheat",
		    			  Items.flint
						 }));
		}
		
		//MBTリセットレシピ
		GameRegistry.addRecipe(
				 new ShapelessOreRecipe(
	    		  new ItemStack(FluidityCore.fluidIBC, 1, 0),
	    		  new Object[]{
	    			  FluidityCore.fluidIBC
					 }));
	}

}
