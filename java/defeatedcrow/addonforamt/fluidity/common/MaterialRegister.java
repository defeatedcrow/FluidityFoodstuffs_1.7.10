package defeatedcrow.addonforamt.fluidity.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import defeatedcrow.addonforamt.fluidity.fluid.fluid.FluidFlourBase;
import defeatedcrow.addonforamt.fluidity.fluid.fluid.FluidMilkBase;
import defeatedcrow.addonforamt.fluidity.fluid.item.*;

public class MaterialRegister {
	
	private MaterialRegister(){}
	
	private static Fluid registerFlour;
	private static Fluid registerSalt;
	private static Fluid registerSugar;
	private static Fluid registerMilk;
	private static Fluid registerWheat;
	private static Fluid registerRice;
	private static Fluid registerSeed;
	
	public static void addItem()
	{	
		FluidityCore.flourCont = new ItemFlourContainer()
		.setUnlocalizedName("fluiditydc.sack")
		.setCreativeTab(FluidityCore.fluidity);
		
		GameRegistry.registerItem(FluidityCore.flourCont, "fluiditydc.sack");
	}
	
	public static void addFluid()
	{
		registerFlour = new Fluid("fluid_" + "flour").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerFlour);
		FluidityCore.flourFluid = FluidRegistry.getFluid("fluid_" + "flour");
		
		registerSalt = new Fluid("fluid_" + "salt").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerSalt);
		FluidityCore.saltFluid = FluidRegistry.getFluid("fluid_" + "salt");
		
		registerSugar = new Fluid("fluid_" + "sugar").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerSugar);
		FluidityCore.sugarFluid = FluidRegistry.getFluid("fluid_" + "sugar");
		
		registerWheat = new Fluid("fluid_" + "wheat").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerWheat);
		FluidityCore.wheatFluid = FluidRegistry.getFluid("fluid_" + "wheat");
		
		registerRice = new Fluid("fluid_" + "rice").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerRice);
		FluidityCore.riceFluid = FluidRegistry.getFluid("fluid_" + "rice");
		
		registerSeed = new Fluid("fluid_" + "seed").setDensity(1200).setViscosity(2000);
		FluidRegistry.registerFluid(registerSeed);
		FluidityCore.seedFluid = FluidRegistry.getFluid("fluid_" + "seed");
		
		registerMilk = new Fluid("fluid_" + "milk").setDensity(1032).setViscosity(1000);
		FluidRegistry.registerFluid(registerMilk);
		FluidityCore.milkFluid = FluidRegistry.getFluid("fluid_" + "milk");
		
		if (FluidityCore.flourFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_flour", 100),
					new ItemStack(FluidityCore.flourCont), new ItemStack(Items.paper));
			
			FluidityCore.flourBlock = new FluidFlourBase(FluidityCore.flourFluid, Material.sand, "flour" + "_still")
			.setBlockName("fluiditydc.block_" + "flour").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.flourBlock, "block_" + "flour");
			FluidityCore.flourFluid.setBlock(FluidityCore.flourBlock);
			
			if (FluidityCore.flourBlock != null)
			{
				FluidityCore.flourBucket = new ItemFlourBucket(FluidityCore.flourBlock, "flour")
				.setUnlocalizedName("fluiditydc.bucket_flour")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.flourBucket, "fluiditydc.bucket_flour");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_flour", 1000),
						new ItemStack(FluidityCore.flourBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.saltFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_salt", 100),
					new ItemStack(FluidityCore.flourCont, 1, 1), new ItemStack(Items.paper));
			
			FluidityCore.saltBlock = new FluidFlourBase(FluidityCore.saltFluid, Material.sand, "salt" + "_still")
			.setBlockName("fluiditydc.block_" + "salt").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.saltBlock, "block_" + "salt");
			FluidityCore.saltFluid.setBlock(FluidityCore.saltBlock);
			
			if (FluidityCore.saltBlock != null)
			{
				FluidityCore.saltBucket = new ItemFlourBucket(FluidityCore.saltBlock, "salt")
				.setUnlocalizedName("fluiditydc.bucket_salt")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.saltBucket, "fluiditydc.bucket_salt");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_salt", 1000),
						new ItemStack(FluidityCore.saltBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.sugarFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_sugar", 100),
					new ItemStack(FluidityCore.flourCont, 1, 2), new ItemStack(Items.paper));
			
			FluidityCore.sugarBlock = new FluidFlourBase(FluidityCore.sugarFluid, Material.sand, "sugar" + "_still")
			.setBlockName("fluiditydc.block_" + "sugar").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.sugarBlock, "block_" + "sugar");
			FluidityCore.sugarFluid.setBlock(FluidityCore.sugarBlock);
			
			if (FluidityCore.sugarBlock != null)
			{
				FluidityCore.sugarBucket = new ItemFlourBucket(FluidityCore.sugarBlock, "sugar")
				.setUnlocalizedName("fluiditydc.bucket_sugar")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.sugarBucket, "fluiditydc.bucket_sugar");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_sugar", 1000),
						new ItemStack(FluidityCore.sugarBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.wheatFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_wheat", 100),
					new ItemStack(FluidityCore.flourCont, 1, 4), new ItemStack(Items.paper));
			
			FluidityCore.wheatBlock = new FluidFlourBase(FluidityCore.wheatFluid, Material.sand, "wheat" + "_still")
			.setBlockName("fluiditydc.block_" + "wheat").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.wheatBlock, "block_" + "wheat");
			FluidityCore.wheatFluid.setBlock(FluidityCore.wheatBlock);
			
			if (FluidityCore.wheatBlock != null)
			{
				FluidityCore.wheatBucket = new ItemFlourBucket(FluidityCore.wheatBlock, "wheat")
				.setUnlocalizedName("fluiditydc.bucket_wheat")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.wheatBucket, "fluiditydc.bucket_wheat");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_wheat", 1000),
						new ItemStack(FluidityCore.wheatBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.riceFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_rice", 100),
					new ItemStack(FluidityCore.flourCont, 1, 5), new ItemStack(Items.paper));
			
			FluidityCore.riceBlock = new FluidFlourBase(FluidityCore.riceFluid, Material.sand, "rice" + "_still")
			.setBlockName("fluiditydc.block_" + "rice").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.riceBlock, "block_" + "rice");
			FluidityCore.riceFluid.setBlock(FluidityCore.riceBlock);
			
			if (FluidityCore.riceBlock != null)
			{
				FluidityCore.riceBucket = new ItemFlourBucket(FluidityCore.riceBlock, "rice")
				.setUnlocalizedName("fluiditydc.bucket_rice")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.riceBucket, "fluiditydc.bucket_rice");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_rice", 1000),
						new ItemStack(FluidityCore.riceBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.seedFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_seed", 100),
					new ItemStack(FluidityCore.flourCont, 1, 6), new ItemStack(Items.paper));
			
			FluidityCore.seedBlock = new FluidFlourBase(FluidityCore.seedFluid, Material.sand, "seed" + "_still")
			.setBlockName("fluiditydc.block_" + "seed").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.seedBlock, "block_" + "seed");
			FluidityCore.seedFluid.setBlock(FluidityCore.seedBlock);
			
			if (FluidityCore.seedBlock != null)
			{
				FluidityCore.seedBucket = new ItemFlourBucket(FluidityCore.seedBlock, "seed")
				.setUnlocalizedName("fluiditydc.bucket_seed")
				.setCreativeTab(FluidityCore.fluidity);
				GameRegistry.registerItem(FluidityCore.seedBucket, "fluiditydc.bucket_seed");
				
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_seed", 1000),
						new ItemStack(FluidityCore.seedBucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
		
		if (FluidityCore.milkFluid != null)
		{
			FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_milk", 100),
					new ItemStack(FluidityCore.flourCont, 1, 3), new ItemStack(Items.paper));
			
			FluidityCore.milkBlock = new FluidMilkBase(FluidityCore.milkFluid, Material.water, "milk" + "_still")
			.setBlockName("fluiditydc.block_" + "milk").setCreativeTab(FluidityCore.fluidity);
			GameRegistry.registerBlock(FluidityCore.milkBlock, "block_" + "milk");
			FluidityCore.milkFluid.setBlock(FluidityCore.milkBlock);
			
			if (FluidityCore.milkBlock != null)
			{
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("fluid_milk", 1000),
						new ItemStack(Items.milk_bucket, 1, 0), new ItemStack(Items.bucket));
			}
		}
	}

}
