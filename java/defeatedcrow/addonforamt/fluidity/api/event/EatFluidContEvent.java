package defeatedcrow.addonforamt.fluidity.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
@Event.HasResult
public class EatFluidContEvent extends PlayerEvent {
	public final ItemStack current;
	public final World world;
	public final Fluid fluid;
	public ItemStack container = null;

	public EatFluidContEvent(World world, ItemStack current, EntityPlayer player, Fluid fluid) {
		super(player);
		this.current = current;
		this.world = world;
		this.fluid = fluid;
	}
}
