package defeatedcrow.addonforamt.fluidity.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import defeatedcrow.addonforamt.fluidity.block.TileAdvFluidHopper;

public class GuiAdvFHopper extends GuiContainer {
	
	private TileAdvFluidHopper tile;
	private String[] modeName = {"default", "entity", "fluidtype", "redstone", "default"};

	public GuiAdvFHopper(EntityPlayer player, TileAdvFluidHopper hopper) {
		super(new ContainerAdvFHopper(player, hopper));
		this.tile = hopper;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		//文字
		String s = I18n.format("fluiditydc.advfhopper.invname", new Object[0]);
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 4, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
    {
		super.drawScreen(par1, par2, par3);
		
		boolean b1 = this.func_146978_c(79, 61, 16, 16, par1, par2);
		if (b1)
		{
			int rate = this.tile.FLOW_RATE;
			int mode = this.tile.getMode().getId();
			ArrayList<String> list1 = new ArrayList<String>();
			list1.add("Current mode is " + modeName[mode]);
			if (!this.tile.isActive())
			{
				list1.add("Valve is closed.");
			}
			else
			{
				list1.add("Flow Rate : " + rate + " mB/t");
			}
			
			this.drawHoveringText(list1, par1, par2, fontRendererObj);
		}
		
		boolean b2 = this.func_146978_c(79, 17, 16, 40, par1, par2);
		if (b2)
		{
			ArrayList<String> list2 = new ArrayList<String>();
			list2.add("Fluid : " + this.tile.productTank.getFluidName());
			list2.add("Amount : " + this.tile.productTank.getFluidAmount());
			this.drawHoveringText(list2, par1, par2, fontRendererObj);
		}
		
		boolean b3 = this.func_146978_c(99, 61, 16, 16, par1, par2);
		if (b3 && tile.getFilterFluid() != null)
		{
			ArrayList<String> list3 = new ArrayList<String>();
			list3.add("Filter : " + this.tile.getFilterFluid().getLocalizedName(new FluidStack(this.tile.getFilterFluid(), 1000)));
			this.drawHoveringText(list3, par1, par2, fontRendererObj);
		}
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("fluiditydc", "textures/gui/fluidhopper_adv_gui.png"));
		
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		
		int id = this.tile.getMode().getId();
		boolean act = this.tile.isActive();
		
		int tipY = 16 * id - 16;
		int tipX = act ? 176 : 192;
		
		if (id != 0)
		{
			this.drawTexturedModalRect(k + 79, l + 61, tipX, tipY, 16, 16);
		}
		
		drawFluid(this.tile.productTank.getFluid(), this.tile.getFluidAmountScaled(40), k + 79, l + 17, 16, 40);
		
		if (this.tile.getFilterFluid() != null)
		{
			drawFluid(new FluidStack(this.tile.getFilterFluid(), 1000), 16, k + 99, l + 61, 16, 16);
		}
	}
	
	/**
	 * Original code was made by Shift02. 
	 * */
	private void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height) {
		if (fluid == null || fluid.getFluid() == null) {
			return;
		}
		
		ResourceLocation res = null;
		if (fluid.getFluid().getSpriteNumber() == 0)
		{
			res = TextureMap.locationBlocksTexture;
		}
		else
		{
			res = TextureMap.locationItemsTexture;
		}
		mc.getTextureManager().bindTexture(res);
		
		
		IIcon icon = fluid.getFluid().getIcon(fluid);
		if (icon == null)return;
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
}
