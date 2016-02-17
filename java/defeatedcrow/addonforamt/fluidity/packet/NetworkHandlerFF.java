package defeatedcrow.addonforamt.fluidity.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandlerFF {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("FluidityDC");

	public static void init() {
		INSTANCE.registerMessage(MessageHandlerHopperMode.class, MessageHopperMode.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageHandlerHopperFilter.class, MessageHopperFilter.class, 1, Side.SERVER);
	}

}
