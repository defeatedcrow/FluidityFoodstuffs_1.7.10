package defeatedcrow.addonforamt.fluidity.client;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import defeatedcrow.addonforamt.fluidity.fluid.item.ItemFluidContBase;

public class ItemRenderFluidCont implements IItemRenderer {

	private final RenderItem renderItem = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (item.getItem() instanceof ItemFluidContBase && type == ItemRenderType.INVENTORY) {
			// 通常のアイコン
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_ALPHA_TEST);

			this.renderItem.renderIcon(0, 0, item.getItem().getIconIndex(item), 16, 16);

			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);

			int meta = item.getItemDamage();
			Fluid f = ItemFluidContBase.getFluidByMeta(meta);
			if (f != null) {
				IIcon icon = f.getIcon(new FluidStack(f, 100));
				if (icon != null) {
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_ALPHA_TEST);

					float u = icon.getMinU();
					float U = icon.getMaxU();
					float v = icon.getMinV();
					float V = icon.getMaxV();

					int col = f.getColor();
					if (col != 0xFFFFFF) {
						float r = (col & 0xFF0000) * 1.2F / 0xFF0000;
						float g = (col & 0x00FF00) * 1.2F / 0x00FF00;
						float b = (col & 0x0000FF) * 1.2F / 0x0000FF;
						GL11.glColor4f(r, g, b, 1.0F);
					}

					FMLClientHandler.instance().getClient().getTextureManager()
							.bindTexture(TextureMap.locationBlocksTexture);
					this.renderItem.renderIcon(2, 6, icon, 8, 8);

					GL11.glDisable(GL11.GL_ALPHA_TEST);
					GL11.glEnable(GL11.GL_LIGHTING);

					FMLClientHandler.instance().getClient().getTextureManager()
							.bindTexture(TextureMap.locationItemsTexture);
				}
			}

		}

	}

}
