package defeatedcrow.addonforamt.fluidity.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
@Event.HasResult
public class FlourSackCrickEvent extends PlayerEvent
{
    public final ItemStack current;
    public final World world;
    public final MovingObjectPosition target;

    public ItemStack result;

    public FlourSackCrickEvent(EntityPlayer player, ItemStack current, World world, MovingObjectPosition target)
    {
        super(player);
        this.current = current;
        this.world = world;
        this.target = target;
    }
}
