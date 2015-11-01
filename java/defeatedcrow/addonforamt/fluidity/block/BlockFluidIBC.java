package defeatedcrow.addonforamt.fluidity.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class BlockFluidIBC extends BlockContainer {

	protected Random rand = new Random();

	public BlockFluidIBC() {
		super(Material.gourd);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(1.0F);
		this.setResistance(15.0F);
	}

	// プレイヤーの右クリック処理
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer,
			int par6, float par7, float par8, float par9) {
		/* 手持ちアイテム */
		ItemStack itemstack = par5EntityPlayer.inventory.getCurrentItem();
		/* このブロックのTileEntity */
		TileFluidIBC tile = (TileFluidIBC) par1World.getTileEntity(par2, par3, par4);

		/*
		 * ちなみに、この段階ではworld.isRemoteの判定を行っていない。
		 * 意図的にサーバ・クライアントで同じ処理がそれぞれ行われるようにしている。
		 */
		if (tile != null) {
			// TileEntityの液体タンクに入っている液体を取得
			FluidStack fluid = tile.productTank.getFluid();

			if (itemstack == null)// 素手
			{
				// 表示用の文字列をとりあえず作成
				String s = "";

				if (fluid != null && fluid.getFluid() != null) {
					s = "Fluid current in the tank : " + fluid.getFluid().getLocalizedName(fluid) + " " + fluid.amount
							+ "/" + tile.productTank.getCapacity() + ", " + tile.getFluidGauge();
				} else {
					s = "No fluid in the tank";
				}

				/*
				 * チャット表示時はリモートワールドの判定を挟む。
				 * そうしないと、サーバ・クライアントで二重にメッセージが出てしまう。
				 */
				if (!par1World.isRemote)
					par5EntityPlayer.addChatMessage(new ChatComponentText(s));

				return true;
			} else {
				// このメソッドにより、手持ちのアイテムが液体容器に登録されたアイテムかどうか、及び入っている液体を取得する。
				FluidStack fluid2 = FluidContainerRegistry.getFluidForFilledItem(itemstack);

				// 満たされた液体コンテナが手持ちの場合
				if (fluid2 != null && fluid2.getFluid() != null) {
					/*
					 * fillメソッドの第二引数にfalseを入れた場合、実際に液体をタンクに入れるのではなく、
					 * タンクに投入可能な液体の量をシュミレートして値を返す。
					 */
					int put = tile.fill(ForgeDirection.UNKNOWN, fluid2, false);

					// 全量投入可能なときのみ
					if (put == fluid2.amount) {
						// 今度は液体を液体タンクに入れるので、第二引数はtrueにする。
						tile.fill(ForgeDirection.UNKNOWN, fluid2, true);

						// 液体容器を空にして、空容器を得るメソッド。
						ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(itemstack);
						if (emptyContainer != null) {
							if (!par5EntityPlayer.inventory.addItemStackToInventory(emptyContainer.copy())) {
								par5EntityPlayer.entityDropItem(emptyContainer.copy(), 1);
							}
						}

						// プレイヤーの手持ちアイテムを減らす処理
						if (!par5EntityPlayer.capabilities.isCreativeMode && (itemstack.stackSize--) <= 0) {
							par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem,
									(ItemStack) null);
						}

						// 更新を伝える処理
						// TileEntityを更新した場合、このように更新処理を挟まないと見た目に反映しない。
						tile.markDirty();
						par5EntityPlayer.inventory.markDirty();
						par1World.markBlockForUpdate(par2, par3, par4);

						// 効果音の発生
						par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);

						return true;
					}
				} else {
					// 液体タンクに何かしら入っている時
					if (fluid != null && fluid.getFluid() != null) {
						/*
						 * このメソッドにより、手持ちのアイテムを空容器として指定した液体を入れた「液体で満たされた容器アイテム」を取得している。
						 * 液体容器に登録された液体の量も判定されるため、とりあえず1000mB（バケツの容量）で判定。
						 */
						if (FluidContainerRegistry.isEmptyContainer(itemstack)) {
							ItemStack get = FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid.getFluid(),
									1000), itemstack.copy());
							if (get == null)
								return false;

							int amount = FluidContainerRegistry.getContainerCapacity(get);
							/*
							 * タンクの液体の減少処理
							 * タンク容量 > amount であることをチェックしてから行う
							 */
							int canDrain = tile.drain(ForgeDirection.UNKNOWN, amount, false).amount;
							if (canDrain < amount)
								return false;
							else {
								tile.drain(ForgeDirection.UNKNOWN, amount, true);

								// プレイヤーに、先に取得した「液体で満たされた容器アイテム」を与える処理
								if (!par5EntityPlayer.inventory.addItemStackToInventory(get.copy())) {
									par5EntityPlayer.entityDropItem(get.copy(), 1);
								}

								// プレイヤーの手持ちアイテムを減らす処理
								if (!par5EntityPlayer.capabilities.isCreativeMode
										&& (par5EntityPlayer.inventory.getCurrentItem().stackSize--) <= 0) {
									par5EntityPlayer.inventory.setInventorySlotContents(
											par5EntityPlayer.inventory.currentItem, (ItemStack) null);
								}
							}

							// 更新を伝える処理
							// TileEntityを更新した場合、このように更新処理を挟まないと見た目に反映しない。
							tile.markDirty();
							par5EntityPlayer.inventory.markDirty();
							par1World.markBlockForUpdate(par2, par3, par4);

							// 効果音の発生
							par1World.playSoundAtEntity(par5EntityPlayer, "random.pop", 0.4F, 1.8F);

							return true;
						}
					} else {
						// アイテムが液体入り容器でなく、かつタンクが空だった場合は何もしない
						return true;
					}
				}
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int a) {

		return new TileFluidIBC();
	}

	/* === Entityの接触判定 === */

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/* 破壊時の情報保持 */

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase,
			ItemStack par6ItemStack) {
		Fluid fluid = null;
		int amount = 0;

		if (par6ItemStack.getItem() == Item.getItemFromBlock(this)) {
			if (par6ItemStack.hasTagCompound() && !par6ItemStack.getTagCompound().hasNoTags()) {
				NBTTagCompound fluidNBT = par6ItemStack.getTagCompound();
				if (fluidNBT != null && fluidNBT.hasKey("fdc.id")) {
					int s = fluidNBT.getInteger("fdc.id");
					fluid = FluidRegistry.getFluid(s);
				}
				if (fluidNBT != null && fluidNBT.hasKey("fdc.amount")) {
					amount = fluidNBT.getInteger("fdc.amount");
				}
			}
		}

		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		if (tile != null && tile instanceof TileFluidIBC) {
			if (fluid != null && amount > 0) {
				FluidStack fs = new FluidStack(fluid, amount);
				((TileFluidIBC) tile).fill(ForgeDirection.UNKNOWN, fs, true);
				par1World.markBlockForUpdate(par2, par3, par4);
			}
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return null;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileFluidIBC tile = (TileFluidIBC) par1World.getTileEntity(par2, par3, par4);

		if (tile != null) {
			FluidStack fluid = tile.productTank.getFluid();
			ItemStack block = new ItemStack(this, 1, 0);

			float a = this.rand.nextFloat() * 0.8F + 0.1F;
			float a1 = this.rand.nextFloat() * 0.8F + 0.1F;
			float a2 = this.rand.nextFloat() * 0.8F + 0.1F;
			EntityItem drop = new EntityItem(par1World, par2 + a, par3 + a1, par4 + a2, block);

			if (fluid != null) {
				NBTTagCompound tag1 = new NBTTagCompound();

				tag1.setInteger("fdc.amount", fluid.amount);
				tag1.setInteger("fdc.id", fluid.getFluid().getID());
				tag1.setString("fdc.type", fluid.getLocalizedName());
				drop.getEntityItem().setTagCompound(tag1);
			}

			float a3 = 0.05F;
			drop.motionX = (float) this.rand.nextGaussian() * a3;
			drop.motionY = (float) this.rand.nextGaussian() * a3 + 0.2F;
			drop.motionZ = (float) this.rand.nextGaussian() * a3;
			par1World.spawnEntityInWorld(drop);

			par1World.func_147453_f(par2, par3, par4, par5);
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	/* === レンダー関係 === */

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	// 追加したブロックレンダークラスのIDを入れる
	@Override
	public int getRenderType() {
		return FluidityCore.renderIBC;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("hopper_inside");// ここで登録するテクスチャは破壊時のエフェクトくらいにしか使用されない。なのでバニラ木材を流用。
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int light = 0;
		if (tile instanceof TileFluidIBC) {
			Fluid fluid = ((TileFluidIBC) tile).productTank.getFluidType();
			if (fluid != null) {
				light = fluid.getLuminosity();
			}
		}

		return light;
	}

}
