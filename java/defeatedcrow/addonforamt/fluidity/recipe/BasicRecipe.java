package defeatedcrow.addonforamt.fluidity.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
	}

}
