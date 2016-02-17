package defeatedcrow.addonforamt.fluidity.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import defeatedcrow.addonforamt.fluidity.block.TileAdvFluidHopper;

public class MessageHandlerHopperFilter implements IMessageHandler<MessageHopperFilter, IMessage> {

	@Override
	// IMessageHandlerのメソッド
	public IMessage onMessage(MessageHopperFilter message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		if (player != null) {
			World world = player.worldObj;
			int id = message.id;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && tile instanceof TileAdvFluidHopper) {
				TileAdvFluidHopper box = (TileAdvFluidHopper) tile;
				box.setFilterID(id);
			}
		}
		return null;
	}
}
