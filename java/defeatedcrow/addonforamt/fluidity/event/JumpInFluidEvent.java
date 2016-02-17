package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class JumpInFluidEvent {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.entity;
		if (entity != null && entity instanceof EntityPlayer) {

			if (entity.worldObj.isRemote && FluidityCore.proxy.isJumpKeyDown()) {
				FluidityCore.proxy.onJumpInFluid();
			}

		}
	}

}
