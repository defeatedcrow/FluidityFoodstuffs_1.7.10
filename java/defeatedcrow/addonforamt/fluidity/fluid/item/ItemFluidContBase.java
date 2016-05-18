package defeatedcrow.addonforamt.fluidity.fluid.item;

import java.util.Collection;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import defeatedcrow.addonforamt.fluidity.integration.SS2FluidIntegration;

/**
 * 汎用FluidContのベースクラス。全液体に対応する。
 */
public class ItemFluidContBase extends Item implements IFuelHandler {

	public ItemFluidContBase(Item item, String name) {
		super();
		this.setContainerItem(item);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setTextureName("fluiditydc:bottle_" + name);
		this.setUnlocalizedName("fluiditydc.filled_" + name);
	}

	@Override
	public int getMetadata(int par1) {
		return par1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		Collection<Integer> ids = FluidRegistry.getRegisteredFluidIDsByFluid().values();
		for (int i : ids) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel != null) {
			int lava = FluidRegistry.getFluidID("lava");
			if (fuel.getItemDamage() == lava)
				return 1200;
		}

		return 0;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (stack != null) {
			Fluid fluid = FluidRegistry.getFluid(stack.getItemDamage());
			if (fluid != null) {
				String name = fluid.getLocalizedName(new FluidStack(fluid, 1000));
				if (name != null)
					list.add(EnumChatFormatting.AQUA + name + " 200mB");
			}
		}
	}

	public static Fluid getFluidByMeta(int meta) {
		Fluid fluid = FluidRegistry.getFluid(meta);
		if (fluid != null) {
			return fluid;
		}
		return null;
	}

	private ForgeDirection directionFromMOP(int i) {
		ForgeDirection[] dirs = {
				ForgeDirection.DOWN,
				ForgeDirection.UP,
				ForgeDirection.NORTH,
				ForgeDirection.SOUTH,
				ForgeDirection.WEST,
				ForgeDirection.EAST };
		if (i >= 0 && i < dirs.length) {
			return dirs[i];
		} else {
			return ForgeDirection.UP;
		}
	}

	// 右クリック使用
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (mop == null || mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
			player.setItemInUse(item, this.getMaxItemUseDuration(item));
			return item;
		} else if (item != null && item.stackSize >= 5) {
			ForgeDirection dir = this.directionFromMOP(mop.sideHit);
			int i = MathHelper.floor_double(mop.blockX);
			int j = MathHelper.floor_double(mop.blockY);
			int k = MathHelper.floor_double(mop.blockZ);
			// FluidityCore.logger.info("mop: " + i + ", " + j + ", " + k + ", " + dir + "/" +
			// mop.sideHit);
			if (player.canPlayerEdit(i, j, k, mop.sideHit, item)) {
				Block block = world.getBlock(i, j, k);
				int l = world.getBlockMetadata(i, j, k);
				ItemStack drop = this.getContainerItem(item);
				if (drop != null)
					drop.stackSize = 5;
				boolean f = false;
				if (!block.isReplaceable(world, i, j, k)) {
					i += dir.offsetX;
					j += dir.offsetY;
					k += dir.offsetZ;
				}
				Fluid fluid = this.getFluidByMeta(item.getItemDamage());
				// water
				if (fluid != null) {
					if (fluid.canBePlacedInWorld() && fluid.getBlock() != null) {
						f = world.setBlock(i, j, k, fluid.getBlock(), 0, 3);
					}
				}

				if (f && drop != null) {
					if (!player.capabilities.isCreativeMode) {
						item.stackSize -= 5;
						if (item.stackSize <= 0) {
							item = drop;
						} else {
							int x = MathHelper.floor_double(player.posX);
							int y = MathHelper.floor_double(player.posY);
							int z = MathHelper.floor_double(player.posZ);
							EntityItem ent = new EntityItem(world, x + 0.5D, y + 0.25D, z + 0.5D, drop);
							if (!world.isRemote)
								world.spawnEntityInWorld(ent);
						}
					}

					return item;
				}
			}
		}
		return super.onItemRightClick(item, world, player);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 16;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player) {
		if (!world.isRemote && item != null) {
			int meta = item.getItemDamage();
			Fluid fluid = this.getFluidByMeta(meta);
			if (fluid != null) {
				if (this.drinkMilk(world, item, fluid, player)) {
					if (!player.capabilities.isCreativeMode) {
						ItemStack drop = this.getContainerItem(item);
						item.stackSize--;
						if (item == null || item.stackSize <= 0) {
							item = drop;
						} else {
							int x = MathHelper.floor_double(player.posX);
							int y = MathHelper.floor_double(player.posY);
							int z = MathHelper.floor_double(player.posZ);
							EntityItem ent = new EntityItem(world, x + 0.5D, y + 0.25D, z + 0.5D, drop);
							if (!world.isRemote)
								world.spawnEntityInWorld(ent);
						}
					}
				}
			}
		}

		return item;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		if (stack != null && stack.getItem() == FluidityCore.filledBamboo)
			return new ItemStack(FluidityCore.emptyBamboo);
		else
			return new ItemStack(FluidityCore.emptyBottle);
	}

	public boolean drinkMilk(World world, ItemStack item, Fluid fluid, EntityPlayer player) {
		if (fluid != null && fluid.getName().contains("milk")) {
			player.clearActivePotions();
			return true;
		} else if (Loader.isModLoaded("SextiarySector")) {
			if (fluid == FluidRegistry.getFluid("water")) {
				SS2FluidIntegration.addStatus(2, 1.0F, player);
				player.addPotionEffect(new PotionEffect(Potion.hunger.id, 60, 0));
				return true;
			} else if (fluid == FluidRegistry.getFluid("drinkingwater")
					|| fluid == FluidRegistry.getFluid("springwater")) {
				SS2FluidIntegration.addStatus(3, 1.0F, player);
				return true;
			}
		}
		return false;
	}

}
