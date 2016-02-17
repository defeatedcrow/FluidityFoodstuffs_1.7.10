package defeatedcrow.addonforamt.fluidity.gui;

import java.util.ArrayList;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import defeatedcrow.addonforamt.fluidity.packet.MessageHopperMode;
import defeatedcrow.addonforamt.fluidity.packet.NetworkHandlerFF;

public class GuiFHopper extends GuiContainer {

	private TileFluidHopper tile;

	public GuiFHopper(EntityPlayer player, TileFluidHopper hopper) {
		super(new ContainerFHopper(player, hopper));
		this.tile = hopper;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// 文字
		String s = this.tile.hasCustomInventoryName() ? this.tile.getInventoryName() : I18n.format(
				this.tile.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2,
				4210752);

		boolean b1 = this.func_146978_c(67, 59, 16, 16, par1, par2);
		if (b1) {
			int rate = this.tile.extractAmount();
			ArrayList<String> list1 = new ArrayList<String>();
			if (rate == 0) {
				list1.add("Valve is closed.");
			} else {
				list1.add("Flow Rate : " + rate + " mB/t");
			}

			this.drawHoveringText(list1, par1 - this.guiLeft, par2 - this.guiTop, fontRendererObj);
		}

		boolean b2 = this.func_146978_c(67, 15, 16, 40, par1, par2);
		if (b2) {
			ArrayList<String> list2 = new ArrayList<String>();
			list2.add("Fluid : " + this.tile.productTank.getFluidName());
			list2.add("Amount : " + this.tile.productTank.getFluidAmount());
			this.drawHoveringText(list2, par1 - this.guiLeft, par2 - this.guiTop, fontRendererObj);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("fluiditydc", "textures/gui/fluidhopper_gui.png"));

		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		if (this.tile.getMode() == 0) {
			this.drawTexturedModalRect(k + 67, l + 59, 176, 0, 16, 16);
		}

		drawFluid(this.tile.productTank.getFluid(), this.tile.getFluidAmountScaled(40), k + 67, l + 15, 16, 40);
	}

	/**
	 * Original code was made by Shift02.
	 */
	private void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}

		ResourceLocation res = null;
		if (fluid.getFluid().getSpriteNumber() == 0) {
			res = TextureMap.locationBlocksTexture;
		} else {
			res = TextureMap.locationItemsTexture;
		}
		mc.getTextureManager().bindTexture(res);

		IIcon icon = fluid.getFluid().getIcon(fluid);
		if (icon == null)
			return;
		mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		setGLColorFromInt(fluid.getFluid().getColor(fluid));

		int widR = width;
		int heiR = level;
		int yR = y + (height - heiR);

		int widL = 0;
		int heiL = 0;

		for (int i = 0; i < widR; i += 16) {
			for (int j = 0; j < heiR; j += 16) {
				widL = Math.min(widR - i, 16);
				heiL = Math.min(heiR - j, 16);
				this.drawTexturedModelRectFromIcon(x + i, yR + j, icon, widL, heiL);
			}
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);

	}

	public static void setGLColorFromInt(int color) {
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GL11.glColor4f(red, green, blue, 1.0F);
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		int ix = x - k;
		int iy = y - l;
		String s = "x:" + ix + ", y:" + iy;
		boolean flag = false;
		if (ix > 0 && ix < this.xSize && iy > 0 && iy < this.ySize / 2) {
			FluidityCore.logger.info(s);
			boolean b = false;
			if (ix > 67 && ix < 82 && iy > 59 && iy < 74) {
				b = true;
			}
			if (b) {
				MessageHopperMode message = new MessageHopperMode(tile.xCoord, tile.yCoord, tile.zCoord);
				NetworkHandlerFF.INSTANCE.sendToServer(message);
				this.mc.getSoundHandler().playSound(
						PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			}
		}
		super.mouseClicked(x, y, button);
	}
}
