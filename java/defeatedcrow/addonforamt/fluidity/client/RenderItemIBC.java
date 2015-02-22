package defeatedcrow.addonforamt.fluidity.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderItemIBC implements IItemRenderer{
	
	private static final ResourceLocation resource = new ResourceLocation("textures/blocks/hopper_inside.png");
	private ModelIBC model = new ModelIBC();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return canRendering(item, type);
	}
	
	private boolean canRendering(ItemStack item, ItemRenderType type)
	{
		switch(type)
		{
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (helper)
		{
		case INVENTORY_BLOCK:
		case ENTITY_BOBBING:
		case ENTITY_ROTATION:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (canRendering(item, type))
		{

			GL11.glPushMatrix();
			
			switch(type)
			{
			case INVENTORY:
				glMatrixForRenderInInventory(); break;
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
				glMatrixForRenderInEquipped();
				break;
			case ENTITY:
				glMatrixForRenderInEntity();
			default:
				break;
			}
			
			String innerTexPass = "fluiditydc:textures/entity/ibc_body.png";
			String cageTexPass = "fluiditydc:textures/entity/ibc_cage.png";
	        ResourceLocation innerTex = new ResourceLocation(innerTexPass);
	        ResourceLocation cageTex = new ResourceLocation(cageTexPass);
	        
	        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(innerTex);
	        model.renderBody((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	        
	        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(resource);
	        model.renderBottom((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	        
	        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(cageTex);
	        model.renderCage((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	        
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glPopMatrix();
		}
	}
	
	private void glMatrixForRenderInInventory()
	{
		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
	}
	
	private void glMatrixForRenderInEquipped()
	{
		GL11.glRotatef(-210F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-0F, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glTranslatef(1.0F, -1.5F, 0.0F);
	}
	
	private void glMatrixForRenderInEntity()
	{
		GL11.glRotatef(-180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.5F, 0.0F);
	}

}
