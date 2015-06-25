package defeatedcrow.addonforamt.fluidity.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankFF extends FluidTank{

	public FluidTankFF(int capacity) {
		super(capacity);
	}
	
	public FluidTankFF(FluidStack stack, int capacity) {
		super(stack, capacity);
	}
	
	public FluidTankFF(Fluid fluid, int amount, int capacity) {
		super(fluid, amount, capacity);
	}
	
	//判定処理の短縮用のemptyフラグ
	public boolean isEmpty() {
	    return (getFluid() == null) || getFluid().getFluid() == null || (getFluid().amount <= 0);
	}

	//判定処理の短縮用の満タンフラグ
	public boolean isFull() {
	    return (getFluid() != null) && (getFluid().amount >= getCapacity());
	}

	//Fluid型で中身を得る
	public Fluid getFluidType() {
	    return getFluid() != null ? getFluid().getFluid() : null;
	}

	//翻訳された液体名を得るメソッド
	public String getFluidName()
	{
	    return (this.fluid != null) && (this.fluid.getFluid() != null) ? this.fluid.getFluid().getLocalizedName(this.fluid): "Empty";
	}
	
	//同期処理用
	@SideOnly(Side.CLIENT)
	public void setAmount(int par1)
	{
		if (this.fluid != null && this.fluid.getFluid() != null)
		{
			par1 = Math.min(par1, capacity);
			this.fluid.amount = par1;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setFluidById(int par1)
	{
		Fluid f = FluidRegistry.getFluid(par1);
		if (f != null){
			this.fluid = new FluidStack(f, this.getFluidAmount());
		}
		else{
			this.fluid = (FluidStack)null;
		}
	}
}
