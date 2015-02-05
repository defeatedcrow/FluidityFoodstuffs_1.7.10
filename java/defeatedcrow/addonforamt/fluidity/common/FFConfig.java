package defeatedcrow.addonforamt.fluidity.common;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class FFConfig {
	
	private FFConfig(){}
	
	public static String[] saltList = {"salt", "Salt", "cookingSalt", "dustSalt", "foodSalt"};
	public static String[] sugarList = {"sugar", "Sugar", "cookingSugar", "dustSugar", "foodSugar"};
	public static String[] milkList = {"milk", "listAllmilk", "bucketMilk", "cookingMilk", "milkBucket", "foodMilk"};
	public static String[] flourList = {"flour", "Flour", "foodFlour", "cookingFlour", "dustFlour", "dustWheat"};
	public static String[] wheatList = {"wheat", "Wheat", "cropWheat", "foodWheat"};
	public static String[] riceList = {"rice", "Rice", "cropRice", "foodRice"};
	public static String[] seedList = {"seed", "Seed", "cropSeed", "foodSeed"};
	
	private final String BR = System.getProperty("line.separator");
	
	public static void config(Configuration cfg)
	{
		
		try
		{
			cfg.load();
			
			cfg.setCategoryComment("oredictionary", "Add OreDictionary name into the integration list.");
			
			Property saltListP = cfg.get("oredictionary", "Salt", saltList);
			Property sugarListP = cfg.get("oredictionary", "Sugar", sugarList);
			Property milkListP = cfg.get("oredictionary", "Milk", milkList);
			Property flourListP = cfg.get("oredictionary", "Flour", flourList);
			Property wheatListP = cfg.get("oredictionary", "Wheat", wheatList);
			Property riceListP = cfg.get("oredictionary", "Rice", riceList);
			Property seedListP = cfg.get("oredictionary", "Seed", seedList);
			
			saltList = saltListP.getStringList();
			sugarList = sugarListP.getStringList();
			milkList = milkListP.getStringList();
			flourList = flourListP.getStringList();
			wheatList = wheatListP.getStringList();
			riceList = riceListP.getStringList();
			seedList = seedListP.getStringList();
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			cfg.save();
		}
	}

}
