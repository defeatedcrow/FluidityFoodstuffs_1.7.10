package defeatedcrow.addonforamt.fluidity.packet;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageHopperMode implements IMessage {

	public int x;
	public int y;
	public int z;

	public MessageHopperMode() {
	}

	public MessageHopperMode(int posx, int posy, int posz) {
		x = posx;
		y = posy;
		z = posz;
	}

	// read
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	// write
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
}
