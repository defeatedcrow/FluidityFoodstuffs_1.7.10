package defeatedcrow.addonforamt.fluidity.client;

import mods.defeatedcrow.handler.KeyConfigHelper;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import defeatedcrow.addonforamt.fluidity.block.TileAdvFluidHopper;
import defeatedcrow.addonforamt.fluidity.block.TileFluidHopper;
import defeatedcrow.addonforamt.fluidity.block.TileFluidIBC;
import defeatedcrow.addonforamt.fluidity.common.CommonProxyFF;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class ClientProxyFF extends CommonProxyFF {

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public int getRenderID() {
		return RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerBlockHandler(new RenderBlockIBC());
		RenderingRegistry.registerBlockHandler(new RenderBlockDummy());

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FluidityCore.fluidIBC), new RenderItemIBC());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FluidityCore.fluidHopper),
				new RenderItemFHopper());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FluidityCore.fluidHopperAdv),
				new RenderItemAdvFHopper());
	}

	@Override
	public void registerTileEntity() {
		ClientRegistry.registerTileEntity(TileFluidIBC.class, "tileFluidIBC", new RenderTileIBC());
		ClientRegistry.registerTileEntity(TileFluidHopper.class, "tileFluidHopper", new RenderTileFHopper());
		ClientRegistry.registerTileEntity(TileAdvFluidHopper.class, "tileAdvFluidHopper", new RenderTileFHopperAdv());
	}

	@Override
	public int addArmor(String armor) {
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}

	@Override
	public void registerFluidTex() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTextureStitch(TextureStitchEvent.Post event) {
		FluidityCore.flourFluid.setIcons(FluidityCore.flourBlock.getIcon(0, 0));
		FluidityCore.saltFluid.setIcons(FluidityCore.saltBlock.getIcon(0, 0));
		FluidityCore.sugarFluid.setIcons(FluidityCore.sugarBlock.getIcon(0, 0));
		FluidityCore.wheatFluid.setIcons(FluidityCore.wheatBlock.getIcon(0, 0));
		FluidityCore.riceFluid.setIcons(FluidityCore.riceBlock.getIcon(0, 0));
		FluidityCore.seedFluid.setIcons(FluidityCore.seedBlock.getIcon(0, 0));
		FluidityCore.milkFluid.setIcons(FluidityCore.milkBlock.getIcon(0, 0));
	}

	@Override
	public boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	@Override
	public boolean isJumpKeyDown() {
		return Keyboard.isKeyDown(KeyConfigHelper.getJumpKey());
	}

}
