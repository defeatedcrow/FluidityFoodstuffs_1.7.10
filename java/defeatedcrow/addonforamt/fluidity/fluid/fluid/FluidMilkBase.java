package defeatedcrow.addonforamt.fluidity.fluid.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidMilkBase extends BlockFluidClassic{
	
	@SideOnly(Side.CLIENT)
	protected IIcon baseIcon;
	
	protected final String TEXNAME;

	public FluidMilkBase(Fluid fluid, Material material, String name) {
		super(fluid, material);
		this.TEXNAME = name;
		this.displacements.put(Blocks.water,true);
		this.displacements.put(Blocks.lava,true);
		this.setQuantaPerBlock(3);
		this.setDensity(1032);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return this.baseIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.baseIcon = par1IconRegister.registerIcon("fluiditydc:fluid/" + TEXNAME);
	}

}
