package defeatedcrow.addonforamt.fluidity.fluid.fluid;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FluidFlourBase extends BlockFluidClassic{
	
	@SideOnly(Side.CLIENT)
	protected IIcon baseIcon;
	
	protected final String TEXNAME;

	public FluidFlourBase(Fluid fluid, Material material, String name) {
		super(fluid, material);
		this.TEXNAME = name;
		this.displacements.put(Blocks.water,true);
		this.displacements.put(Blocks.lava,true);
		this.setQuantaPerBlock(1);
		this.setTickRate(5);
	}
	
	public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return true;
    }
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return this.baseIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.baseIcon = par1IconRegister.registerIcon("fluiditydc:fluid/" + TEXNAME);
	}
	
	//真下に落ちていく
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
		if (!world.isRemote)
		{
			if (this.canDisplace(world, x, y - 1, z))
			{
				world.setBlock(x, y - 1, z, this, 0, 3);
				world.setBlockToAir(x, y, z);
			}
		}
		
		world.scheduleBlockUpdate(x, y, z, this, tickRate);
        world.notifyBlocksOfNeighborChange(x, y, z, this);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity != null)
		{
			if (entity.motionY < 0)
			{
				entity.motionY *= 0.3D;
				if (entity.motionY < 0.01D) entity.motionY = 0.0D;
			}
			entity.fallDistance = 0.0F;
		}
	}

}
