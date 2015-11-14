package defeatedcrow.addonforamt.fluidity.event;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import defeatedcrow.addonforamt.fluidity.fluid.fluid.FluidFlourBase;

public class JumpInFluidEvent {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		Entity entity = event.entity;
		if (entity != null && entity instanceof EntityPlayerSP) {
			EntityPlayer player = (EntityPlayer) event.entity;
			boolean cre = player.capabilities.isCreativeMode;

			if (player.worldObj.isRemote && player instanceof EntityPlayerSP && FluidityCore.proxy.isJumpKeyDown()) {
				int x = MathHelper.floor_double(player.posX);
				int y = MathHelper.floor_double(player.posY);
				int z = MathHelper.floor_double(player.posZ);

				Block upper = player.worldObj.getBlock(x, y, z);
				Block under = player.worldObj.getBlock(x, y - 1, z);
				if (upper instanceof FluidFlourBase || under instanceof FluidFlourBase) {
					player.motionY = 0.4F;
				}
			}

		}
	}

}
