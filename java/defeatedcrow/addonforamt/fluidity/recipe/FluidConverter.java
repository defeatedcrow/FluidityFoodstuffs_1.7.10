package defeatedcrow.addonforamt.fluidity.recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class FluidConverter {

	public static final FluidConverter instance = new FluidConverter();

	private FluidConverter() {
	}

	private static final ArrayList<Convertion> convList = new ArrayList<Convertion>();

	public void addRecipe(String name, Fluid f, int amo) {
		if (name != null && f != null && amo > 0) {
			if (getRecipe(name) != null) {
				FluidityCore.logger.info("This ore name is already registered!");
			} else {
				convList.add(new Convertion(name, f, amo));
			}
		}
	}

	public Convertion getRecipe(ItemStack item) {
		if (item == null || item.getItem() == null)
			return null;
		int[] ids = OreDictionary.getOreIDs(item);
		String[] ores = new String[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ores[i] = OreDictionary.getOreName(ids[i]);
		}

		for (Convertion c : convList) {
			if (c.ore == null)
				continue;
			for (String ore : ores) {
				if (ore == null)
					continue;
				if (ore.equalsIgnoreCase(c.ore))
					return c;
			}
		}

		return null;
	}

	public Convertion getRecipe(String name) {
		for (Convertion c : convList) {
			if (c.ore == null)
				continue;
			if (name.equalsIgnoreCase(c.ore))
				return c;
		}
		return null;
	}

	public class Convertion {

		private final String ore;
		private final Fluid fluid;
		private final int amount;

		public Convertion(String s, Fluid f, int a) {
			ore = s;
			fluid = f;
			amount = a;
		}

		public String getOreName() {
			return ore;
		}

		public FluidStack getFluidStack() {
			if (fluid != null && amount > 0) {
				return new FluidStack(fluid, amount);
			} else {
				return null;
			}
		}
	}

}
