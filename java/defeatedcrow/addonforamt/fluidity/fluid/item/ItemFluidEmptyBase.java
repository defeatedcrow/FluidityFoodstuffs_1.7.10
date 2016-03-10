package defeatedcrow.addonforamt.fluidity.fluid.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

public abstract class ItemFluidEmptyBase extends Item {

	public ItemFluidEmptyBase() {
		super();
	}

	protected abstract Item getFill();

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (movingobjectposition == null) {
			return item;
		} else if (item != null && item.stackSize >= 5) {
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;

				if (player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {

					Block block = world.getBlock(i, j, k);
					int l = world.getBlockMetadata(i, j, k);
					ItemStack drop = null;
					boolean f = false;

					if (block == Blocks.water && l == 0) {
						int id = FluidRegistry.getFluidID("water");
						drop = new ItemStack(getFill(), 5, id);
						f = true;
					} else if (block == Blocks.lava && l == 0) {
						int id = FluidRegistry.getFluidID("lava");
						drop = new ItemStack(getFill(), 5, id);
						f = true;
					} else if (block instanceof IFluidBlock) {
						IFluidBlock fb = (IFluidBlock) block;
						if (fb != null && fb.canDrain(world, i, j, k)) {
							FluidStack drain = fb.drain(world, i, j, k, false);
							if (drain != null && drain.amount == 1000) {
								int id = FluidRegistry.getFluidID(drain.getFluid());
								drop = new ItemStack(getFill(), 5, id);
								f = true;
							}
						}
					} else if (block instanceof BlockLiquid) {
						Fluid target = FluidRegistry.lookupFluidForBlock(block);
						if (target != null && l == 0) {
							int id = FluidRegistry.getFluidID(target);
							drop = new ItemStack(getFill(), 5, id);
							f = true;
						}
					}

					if (f && drop != null) {
						int x = MathHelper.floor_double(player.posX);
						int y = MathHelper.floor_double(player.posY);
						int z = MathHelper.floor_double(player.posZ);
						EntityItem ent = new EntityItem(world, x + 0.5D, y + 0.25D, z + 0.5D, drop);
						world.setBlockToAir(i, j, k);

						if (!player.capabilities.isCreativeMode) {
							item.stackSize -= 5;
							if (item.stackSize <= 0) {
								item = drop;
							} else {
								if (!world.isRemote)
									world.spawnEntityInWorld(ent);
							}
						}

						return item;
					}

				}
			} else {
				return super.onItemRightClick(item, world, player);
			}
		}
		return super.onItemRightClick(item, world, player);
	}

}
