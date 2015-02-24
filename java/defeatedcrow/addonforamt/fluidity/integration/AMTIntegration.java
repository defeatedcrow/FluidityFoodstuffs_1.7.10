package defeatedcrow.addonforamt.fluidity.integration;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import mods.defeatedcrow.api.recipe.IProcessorRecipe;
import mods.defeatedcrow.api.recipe.RecipeRegisterManager;
import mods.defeatedcrow.common.DCsAppleMilk;
import mods.defeatedcrow.plugin.LoadModHandler;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import defeatedcrow.addonforamt.fluidity.recipe.CustomizeVanillaRecipe;
import defeatedcrow.addonforamt.fluidity.recipe.IMCReceptor;

public class AMTIntegration {
	
	private AMTIntegration(){}
	
	public static void load()
	{
		//Event登録
		MinecraftForge.EVENT_BUS.register(new TeaMakerCrickEvent());
		
		
		//除外対象
		IMCReceptor.getExclusionList().add(new ItemStack(DCsAppleMilk.moromi, 1, 1));
		
		ItemStack momijiMilk = LoadModHandler.getItem("milk180");
		if (momijiMilk != null)
		{
			IMCReceptor.getExclusionList().add(momijiMilk);
		}
		
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
	    			  "foodYeast",
	    			  new ItemStack(Items.bucket)
					 }));
		
		
		//レシピ削除
		List<IProcessorRecipe> recipesP = new ArrayList<IProcessorRecipe>((List<IProcessorRecipe>)RecipeRegisterManager.processorRecipe.getRecipes());
		ArrayList<IProcessorRecipe> remove = new ArrayList<IProcessorRecipe>();
		
		for (IProcessorRecipe recipe : recipesP)
		{
			if (CustomizeVanillaRecipe.itemMatches(recipe.getOutput(), new ItemStack(DCsAppleMilk.mincedFoods, 1, 3), false))
			{
				remove.add(recipe);
			}
		}
		
		RecipeRegisterManager.processorRecipe.getRecipes().removeAll(remove);
		
		//レシピ追加
		RecipeRegisterManager.processorRecipe.addRecipe(new ItemStack(DCsAppleMilk.mincedFoods, 3, 3), true, null,
				new Object[]{"cropWheat", "cropWheat", "cropWheat"});
		
		RecipeRegisterManager.processorRecipe.addRecipe(new ItemStack(FluidityCore.flourCont, 3, 0), true, null,
				new Object[]{"cropWheat"});
		
		//その他連携レシピ
		RecipeRegisterManager.plateRecipe.register(new ItemStack(FluidityCore.flourCont, 1, 0), new ItemStack(Items.bread), 60, true);
		
		RecipeRegisterManager.processorRecipe.addRecipe(new ItemStack(DCsAppleMilk.EXItems, 24, 7), false, null,
				new Object[]{Items.bucket});
	}

}
