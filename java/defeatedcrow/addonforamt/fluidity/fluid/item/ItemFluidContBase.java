package defeatedcrow.addonforamt.fluidity.fluid.item;

import java.util.Collection;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

}
