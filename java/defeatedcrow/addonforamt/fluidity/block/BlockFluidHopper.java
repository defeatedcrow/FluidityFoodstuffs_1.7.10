package defeatedcrow.addonforamt.fluidity.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class BlockFluidHopper extends BlockContainer {
	
	protected Random rand = new Random();
	
	public BlockFluidHopper()
	{
		super(Material.clay);
		this.setStepSound(Block.soundTypeMetal);
		this.setHardness(1.0F);
		this.setResistance(15.0F);
	}
	
	//プレイヤーの右クリック処理
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		/*手持ちアイテム*/
        ItemStack itemstack = player.inventory.getCurrentItem();
        /*このブロックのTileEntity*/
        TileFluidHopper tile = (TileFluidHopper) world.getTileEntity(x, y, z);
        if (tile == null || world.isRemote) return true;
        
        /*真上の情報*/
        Block ue = world.getBlock(x, y + 1, z);
        TileEntity ueTile = world.getTileEntity(x, y + 1, z);
        
        FluidStack fluid = tile.productTank.getFluid();
		
        //スニークで流量変更
        if (player.isSneaking())
        {
        	int mode = tile.getMode();
        	int next = mode + 1;
        	tile.setMode(next);
        	FluidityCore.logger.info("Mode : " + next);
        }
		else
		{
			player.openGui(FluidityCore.instance, FluidityCore.instance.guiFHopper, world, x, y, z);
    		return true;
		}
        
        return true;
    }
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 3);
        }

        if (l == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 3);
        }

        if (l == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 3);
        }

        if (l == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 3);
        }
    }
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		TileFluidHopper tile = (TileFluidHopper) par1World.getTileEntity(par2, par3, par4);
		
		if (tile != null)
		{
			
			for (int j1 = 0; j1 < tile.getSizeInventory(); ++j1)
			{
				ItemStack itemstack = tile.getStackInSlot(j1);
				
				if (itemstack != null)
				{
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
	 
					while (itemstack.stackSize > 0)
					{
						int k1 = this.rand.nextInt(21) + 10;
	 
						if (k1 > itemstack.stackSize)
						{
							k1 = itemstack.stackSize;
						}
	 
						itemstack.stackSize -= k1;
						EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
	 
						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
	 
						float f3 = 0.05F;
						entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
						entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
						par1World.spawnEntityInWorld(entityitem);
					}
				}
			}
	 
			par1World.func_147453_f(par2, par3, par4, par5);
		}
	 
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int a) {
		
		return new TileFluidHopper();
	}
	
	/*=== レンダー関係  ===*/
	
	@Override
	public boolean isOpaqueCube()
    {
        return false;
    }

	//追加したブロックレンダークラスのIDを入れる
	@Override
    public int getRenderType()
    {
        return FluidityCore.renderFHopper;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon("hopper_inside");//ここで登録するテクスチャは破壊時のエフェクトくらいにしか使用されない
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
    {
        this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        float f = 0.375F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.5F, 1.0F - f);
        super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
        this.setBlockBoundsForItemRender();
    }
	
	//中身のFluidの持つ明るさを取得する
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
		TileEntity tile = world.getTileEntity(x, y, z);
		int light = 0;
		if (tile instanceof TileFluidHopper)
		{
			Fluid fluid = ((TileFluidHopper)tile).productTank.getFluidType();
			if (fluid != null)
			{
				light = fluid.getLuminosity();
			}
		}
		
		return light;
    }

}
