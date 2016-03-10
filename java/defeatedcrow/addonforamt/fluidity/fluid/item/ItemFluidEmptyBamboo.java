package defeatedcrow.addonforamt.fluidity.fluid.item;

import net.minecraft.item.Item;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class ItemFluidEmptyBamboo extends ItemFluidEmptyBase {

	@Override
	protected Item getFill() {
		return FluidityCore.filledBamboo;
	}

}
