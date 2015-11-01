package defeatedcrow.addonforamt.fluidity.common;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import defeatedcrow.addonforamt.fluidity.event.BucketFillEvent;
import defeatedcrow.addonforamt.fluidity.event.CrickBucketBeforeFill;
import defeatedcrow.addonforamt.fluidity.event.CrickSackEvent;
import defeatedcrow.addonforamt.fluidity.event.DispenserIBCEvent;
import defeatedcrow.addonforamt.fluidity.integration.AMTIntegration;
import defeatedcrow.addonforamt.fluidity.recipe.BasicRecipe;
import defeatedcrow.addonforamt.fluidity.recipe.CustomizeVanillaRecipe;
import defeatedcrow.addonforamt.fluidity.recipe.OreGetter;
import defeatedcrow.addonforamt.fluidity.recipe.OreRegister;

@Mod(modid = "FluidityDC", name = "FluidityFoodstuffs", version = "1.7.10_1.4a",
		dependencies = "required-after:Forge@[10.13.2.1291,);after:DCsAppleMilk")
public class FluidityCore {

	@SidedProxy(clientSide = "defeatedcrow.addonforamt.fluidity.client.ClientProxyFF",
			serverSide = "defeatedcrow.addonforamt.fluidity.common.CommonProxyFF")
	public static CommonProxyFF proxy;

	// インスタンスの生成
	@Instance("FluidityDC")
	public static FluidityCore instance;

	public static Logger logger = LogManager.getLogger("FluidityDC");

	public static final CreativeTabs fluidity = new CreativeTabFF("fluidity");

	// foodstaffs
	public static Item flourCont;
	public static Item emptySack;

	public static Fluid flourFluid;
	public static Fluid saltFluid;
	public static Fluid sugarFluid;
	public static Fluid milkFluid;
	public static Fluid wheatFluid;
	public static Fluid riceFluid;
	public static Fluid seedFluid;

	public static Fluid ffmMilk;

	public static Block flourBlock;
	public static Block saltBlock;
	public static Block sugarBlock;
	public static Block milkBlock;
	public static Block wheatBlock;
	public static Block riceBlock;
	public static Block seedBlock;

	public static Item saltBucket;
	public static Item sugarBucket;
	public static Item flourBucket;
	public static Item wheatBucket;
	public static Item riceBucket;
	public static Item seedBucket;
	public static Item milkBucket;

	// gadgets
	public static Block fluidIBC;
	public static Block fluidHopper;
	public static Block fluidHopperAdv;

	public static int renderIBC;
	public static int renderFHopper;

	public static int guiFHopper;
	public static int guiAdvFHopper;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// logger.info("Now PreInit");
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		FFConfig.config(cfg);
		if (FFConfig.allowReplace) {
			FFConfig.addReplaceList();
		}

		MaterialRegister.addItem();
		MaterialRegister.addBlock();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MaterialRegister.addFluid();
		proxy.registerFluidTex();

		// logger.info("Now Init");
		MinecraftForge.EVENT_BUS.register(new BucketFillEvent());
		MinecraftForge.EVENT_BUS.register(new CrickBucketBeforeFill());
		MinecraftForge.EVENT_BUS.register(new CrickSackEvent());
		OreRegister.load();
		BasicRecipe.addRecipe();
		BasicRecipe.addConvertion();

		GameRegistry.registerFuelHandler((IFuelHandler) flourCont);

		// render
		renderIBC = proxy.getRenderID();
		renderFHopper = proxy.getRenderID();
		proxy.registerRenderers();
		proxy.registerTileEntity();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		DispenserIBCEvent.setIBC();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// logger.info("Now PostInit");

		// integration
		if (Loader.isModLoaded("DCsAppleMilk")) {
			try {
				AMTIntegration.load();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		// ore handling
		OreGetter.listSetUp();
		OreGetter.registerListItems();
		OreGetter.integrateOreDic();

		logger.info("Get food items in OreDictionary");
		logger.info("Flour : " + OreGetter.getList(0).size());
		logger.info("Salt : " + OreGetter.getList(1).size());
		logger.info("Sugar : " + OreGetter.getList(2).size());
		logger.info("Milk : " + OreGetter.getList(3).size());
		logger.info("Wheat : " + OreGetter.getList(4).size());
		logger.info("Rice : " + OreGetter.getList(5).size());
		logger.info("Seed : " + OreGetter.getList(6).size());
		logger.info("Water : " + OreGetter.getList(7).size());

		CustomizeVanillaRecipe.initCustomize();
	}

	@EventHandler
	public void postInit(FMLLoadCompleteEvent event) {

	}

	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 4;
	}

	public String getRivision() {
		return "a";
	}

	public String getModName() {
		return "FluidityFoodstuffs";
	}

	public String getModID() {
		return "FluidityDC";
	}

}
