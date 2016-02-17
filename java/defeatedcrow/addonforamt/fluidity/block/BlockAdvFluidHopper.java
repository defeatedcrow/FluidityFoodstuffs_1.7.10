package defeatedcrow.addonforamt.fluidity.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class BlockAdvFluidHopper extends BlockContainer {

	protected Random rand = new Random();

	private String[] modeName = {
			"default",
			"entity",
			"fluidtype",
			"redstone",
			"default" };

	public BlockAdvFluidHopper() {
		super(Material.clay);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(1.0F);
		this.setResistance(15.0F);
	}

	// プレイヤーの右クリック処理
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		/* 手持ちアイテム */
		ItemStack itemstack = player.inventory.getCurrentItem();
		/* このブロックのTileEntity */
		TileAdvFluidHopper tile = (TileAdvFluidHopper) world.getTileEntity(x, y, z);
		if (tile == null || world.isRemote)
			return true;

		/* 真上の情報 */
		Block ue = world.getBlock(x, y + 1, z);
		TileEntity ueTile = world.getTileEntity(x, y + 1, z);

		FluidStack fluid = tile.productTank.getFluid();

		// スニークでモード変更
		if (player.isSneaking()) {
			int mode = tile.getMode().getId();
			int next = mode + 1;
			tile.setMode(next);
			if (!world.isRemote) {
				String output = StatCollector.translateToLocal("defeatedcrow.fhopper.mode." + this.modeName[next]);
				player.addChatComponentMessage(new ChatComponentText(output));
			}
			// if (next == 2 && !tile.productTank.isEmpty())
			// {
			// Fluid tar = tile.productTank.getFluidType();
			// tile.setFilter(tar);
			// if (!world.isRemote) {
			// String output = StatCollector.translateToLocal("defeatedcrow.fhopper.filter.set")
			// + " : " + tar.getLocalizedName(new FluidStack(tar, 100));
			// player.addChatComponentMessage(new ChatComponentText(output));
			// }
			// }
			// FluidityCore.logger.info("Mode : " + next);
		} else {
			boolean flag = false;
			// if (itemstack != null && FluidContainerRegistry.isFilledContainer(itemstack)) {
			// FluidStack fillCont = FluidContainerRegistry.getFluidForFilledItem(itemstack);
			// if (fillCont != null && fillCont.getFluid() != null) {
			// tile.setFilter(fillCont.getFluid());
			// if (!world.isRemote) {
			// String output = StatCollector.translateToLocal("defeatedcrow.fhopper.filter.set") +
			// " : "
			// + fillCont.getFluid().getLocalizedName(fillCont);
			// player.addChatComponentMessage(new ChatComponentText(output));
			// }
			// flag = true;
			// }
			// } else if (itemstack != null && itemstack.getItem() == Items.bucket) {
			// tile.setFilter(null);
			// if (!world.isRemote) {
			// String output = StatCollector.translateToLocal("defeatedcrow.fhopper.filter.clear");
			// player.addChatComponentMessage(new ChatComponentText(output));
			// }
			// flag = true;
			// }

			if (!flag)
				player.openGui(FluidityCore.instance, FluidityCore.instance.guiAdvFHopper, world, x, y, z);
			return true;
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase,
			ItemStack par6ItemStack) {
		int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 3);
		}

		if (l == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 3);
		}

		if (l == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 3);
		}

		if (l == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 3);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int a) {

		return new TileAdvFluidHopper();
	}

	// 上面にレールを置けるように固形面に設定する
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	/* === レンダー関係 === */

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	// 追加したブロックレンダークラスのIDを入れる
	@Override
	public int getRenderType() {
		return FluidityCore.renderFHopper;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("hopper_inside");// ここで登録するテクスチャは破壊時のエフェクトくらいにしか使用されない
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		float f = 0.375F;
		this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.5F, 1.0F - f);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBoundsForItemRender();
	}

	// 中身のFluidの持つ明るさを取得する
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int light = 0;
		if (tile instanceof TileAdvFluidHopper) {
			Fluid fluid = ((TileAdvFluidHopper) tile).productTank.getFluidType();
			if (fluid != null) {
				light = fluid.getLuminosity();
			}
		}

		return light;
	}

}
