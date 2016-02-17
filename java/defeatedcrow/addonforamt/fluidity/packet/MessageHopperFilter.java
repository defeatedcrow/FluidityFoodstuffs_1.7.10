package defeatedcrow.addonforamt.fluidity.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageHopperFilter implements IMessage {

	public int id;
	public int x;
	public int y;
	public int z;

	public MessageHopperFilter() {
	}

	public MessageHopperFilter(int data, int posx, int posy, int posz) {
		id = data;
		x = posx;
		y = posy;
		z = posz;
	}

	// read
	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	// write
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
}
