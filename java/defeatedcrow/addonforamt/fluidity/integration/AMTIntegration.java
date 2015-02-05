package defeatedcrow.addonforamt.fluidity.integration;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import mods.defeatedcrow.api.recipe.IProsessorRecipe;
import mods.defeatedcrow.api.recipe.RecipeRegisterManager;
import mods.defeatedcrow.common.DCsAppleMilk;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import defeatedcrow.addonforamt.fluidity.recipe.CustomizeVanillaRecipe;
import defeatedcrow.addonforamt.fluidity.recipe.IMCReceptor;

public class AMTIntegration {
	
	private AMTIntegration(){}
	
	public static void load()
	{
		//除外対象
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.woodBox, 1, OreDictionary.WILDCARD_VALUE));
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.wipeBox2, 1, OreDictionary.WILDCARD_VALUE));
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.vegiBag, 1, OreDictionary.WILDCARD_VALUE));
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.mobBlock, 1, OreDictionary.WILDCARD_VALUE));
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.moromi, 1, 1));
		
		//レシピの個別追加
		GameRegistry.addRecipe(
				 new ShapelessOreRecipe(
	    		  new ItemStack(DCsAppleMilk.appleTart, 1, 0),
	    		  new Object[]{
	    			  "cropApple",
	    			  "dustSugar",
	    			  "foodFlour"
					 }));
		
		GameRegistry.addRecipe(
				 new ShapelessOreRecipe(
	    		  new ItemStack(DCsAppleMilk.appleTart, 1, 1),
	    		  new Object[]{
	    			  "cropCassis",
	    			  "dustSugar",
	    			  "foodFlour"
					 }));
		
		GameRegistry.addRecipe(
				 new ShapelessOreRecipe(
	    		  new ItemStack(FluidityCore.flourCont, 2, 0),
	    		  new Object[]{
	    			  "cropWheat",
	    			  new ItemStack(DCsAppleMilk.DCgrater, 1, 32767)
					 }));
		
		GameRegistry.addRecipe(
				 new ShapelessOreRecipe(
	    		  new ItemStack(DCsAppleMilk.moromi, 1, 1),
	    		  new Object[]{
	    			  "cropWheat",
	    			  "cropWheat",
	    			  "cropWheat",
	    			  "kouji",
	    			  "foodSugar"
					 }));
		
		//レシピ削除
		List<IProsessorRecipe> recipesP = new ArrayList<IProsessorRecipe>((List<IProsessorRecipe>)RecipeRegisterManager.prosessorRecipe.getRecipes());
		ArrayList<IProsessorRecipe> remove = new ArrayList<IProsessorRecipe>();
		
		for (IProsessorRecipe recipe : recipesP)
		{
			if (CustomizeVanillaRecipe.itemMatches(recipe.getOutput(), new ItemStack(DCsAppleMilk.mincedFoods, 1, 3), false))
			{
				remove.add(recipe);
			}
		}
		
		RecipeRegisterManager.prosessorRecipe.getRecipes().removeAll(remove);
		
		//レシピ追加
		RecipeRegisterManager.prosessorRecipe.addRecipe(new ItemStack(DCsAppleMilk.mincedFoods, 3, 3), true, null,
				new Object[]{"cropWheat", "cropWheat", "cropWheat"});
		
		RecipeRegisterManager.prosessorRecipe.addRecipe(new ItemStack(FluidityCore.flourCont, 3, 0), true, null,
				new Object[]{"cropWheat"});
	}

}
