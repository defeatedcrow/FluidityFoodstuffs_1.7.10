package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import defeatedcrow.addonforamt.fluidity.block.TileFluidIBC;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class DispenserIBCEvent {

	public static DispenserIBCEvent instance = new DispenserIBCEvent();

	private DispenserIBCEvent() {
	}

	public static void setIBC() {

		BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(FluidityCore.fluidIBC),
				new BehaviorDefaultDispenseItem() {
					private boolean flag = true;

					@Override
					protected ItemStack dispenseStack(IBlockSource block, ItemStack item) {
						EnumFacing enumfacing = BlockDispenser.func_149937_b(block.getBlockMetadata());
						World world = block.getWorld();
						int i = block.getXInt() + enumfacing.getFrontOffsetX();
						int j = block.getYInt() + enumfacing.getFrontOffsetY();
						int k = block.getZInt() + enumfacing.getFrontOffsetZ();

						if (j < 5 || j > 254) {
							this.flag = false;
						} else if (world.isAirBlock(i, j, k)) {

							world.setBlock(i, j, k, FluidityCore.fluidIBC);
							if (item.hasTagCompound() && world.getTileEntity(i, j, k) != null
									&& world.getTileEntity(i, j, k) instanceof TileFluidIBC) {
								TileFluidIBC tile = (TileFluidIBC) world.getTileEntity(i, j, k);
								NBTTagCompound tag = item.getTagCompound();
								Fluid fluid = null;
								int amount = 0;
								if (tag.hasKey("fdc.id")) {
									int s = tag.getInteger("fdc.id");
									fluid = FluidRegistry.getFluid(s);
								}
								if (tag.hasKey("fdc.amount")) {
									amount = tag.getInteger("fdc.amount");
								}
								if (fluid != null && amount > 0) {
									FluidStack fs = new FluidStack(fluid, amount);
									tile.fill(ForgeDirection.UNKNOWN, fs, true);
									world.markBlockForUpdate(i, j, k);
								}
							}

							item.stackSize--;
							this.flag = true;
							return item;
						} else {
							this.flag = false;
						}

						return super.dispenseStack(block, item);
					}

					/**
					 * Play the dispense sound from the specified block.
					 */
					@Override
					protected void playDispenseSound(IBlockSource block) {
						if (this.flag) {
							block.getWorld().playAuxSFX(1000, block.getXInt(), block.getYInt(), block.getZInt(), 0);
						} else {
							block.getWorld().playAuxSFX(1001, block.getXInt(), block.getYInt(), block.getZInt(), 0);
						}
					}
				});

	}

}
