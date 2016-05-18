package defeatedcrow.addonforamt.fluidity.integration;

import net.minecraft.entity.player.EntityPlayer;
import shift.sextiarysector.api.SextiarySectorAPI;

public class SS2FluidIntegration {

	public static void addStatus(int par1, float par2, EntityPlayer par5EntityPlayer) {
		int m = par1;
		float ms = par2;

		if (m > 0) {
			SextiarySectorAPI.addMoistureStats(par5EntityPlayer, m, ms);
		} else {
			SextiarySectorAPI.addMoistureExhaustion(par5EntityPlayer, -ms * 4.0F);
		}

	}

}
