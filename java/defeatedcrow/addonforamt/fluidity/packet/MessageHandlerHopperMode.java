package defeatedcrow.addonforamt.fluidity.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import defeatedcrow.addonforamt.fluidity.block.TileAdvFluidHopper;
import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;

public class MessageHandlerHopperMode implements IMessageHandler<MessageHopperMode, IMessage> {

	@Override
	// IMessageHandlerのメソッド
	public IMessage onMessage(MessageHopperMode message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		if (player != null) {
			World world = player.worldObj;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileFluidHopper) {
				TileFluidHopper box = (TileFluidHopper) tile;
				int mode = box.getMode();
				mode++;
				box.setMode(mode);
			} else if (tile != null && tile instanceof TileAdvFluidHopper) {
				TileAdvFluidHopper box = (TileAdvFluidHopper) tile;
				int mode = box.getMode().getId();
				mode++;
				box.setMode(mode);
			}
		}
		return null;
	}
}
