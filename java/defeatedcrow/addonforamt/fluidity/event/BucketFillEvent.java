package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import defeatedcrow.addonforamt.fluidity.fluid.fluid.FluidFlourBase;

public class BucketFillEvent {
	
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = fillCustomBucket(event.world, event.target);

		if (result == null) {
			return;
		}

		event.result = result;
		event.setResult(Result.ALLOW);
	}
	
	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		int meta = world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
		
		ItemStack bucket = null;
		if (block instanceof FluidFlourBase)
		{
			Fluid fluid = ((FluidFlourBase)block).getFluid();
			if (fluid != null)
			{
				FluidStack f = new FluidStack(fluid, FluidContainerRegistry.BUCKET_VOLUME);
				bucket = FluidContainerRegistry.fillFluidContainer(f, new ItemStack(Items.bucket));
			}
		}
		
		if (bucket != null && meta == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return bucket.copy();
		} else {
			return null;
		}
	}

}
