package defeatedcrow.addonforamt.fluidity.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FFConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileFluidIBC extends TileEntity implements IFluidHandler{
	
	//このTileEntityに持たせる液体タンク。引数は最大容量。
	public FluidTankFF productTank = new FluidTankFF(FFConfig.sizeIBC);
	
	private int lastAmount = 0;

    /*=== NBT、パケットの読み書き部分 ===*/
	
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.productTank = new FluidTankFF(FFConfig.sizeIBC);
		if (par1NBTTagCompound.hasKey("productTank")) {
		    this.productTank.readFromNBT(par1NBTTagCompound.getCompoundTag("productTank"));
		}
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        
        NBTTagCompound tank = new NBTTagCompound();
		this.productTank.writeToNBT(tank);
		par1NBTTagCompound.setTag("productTank", tank);
    }
    
    @Override
	public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
	}
 
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }
	
	//見た目更新用
	public void updateEntity()
	{
		if (productTank.getFluid() != null && productTank.getFluidAmount() > productTank.getCapacity()){
			productTank.setAmount(productTank.getCapacity());
		}
		
		if (!worldObj.isRemote)
		{
			this.onServerUpdate();
		}
	}
	
	private void onServerUpdate()
	{
		int count = 0;
		if (!this.productTank.isEmpty())
		{
			count += this.productTank.getFluidAmount();
		}
		
		if (lastAmount != count)
		{
			lastAmount = count;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	/*=== レンダー用に用意したメソッド。使わない方法もあると思う ===*/
	
	@SideOnly(Side.CLIENT)
	public int getMetadata()
    {
    	return this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }
	
	//TileEntityの特殊レンダークラスで使う液体のアイコンを取得。
	@SideOnly(Side.CLIENT)
	public IIcon getFluidIcon()
    {
		Fluid fluid = this.productTank.getFluidType();
    	return fluid != null ? fluid.getIcon() : null;
    }
	
	public short getFluidGauge()
    {
		int max = productTank.getCapacity() / 100;
    	return (short) (productTank.getFluidAmount() / max);
    }
    
	/*====== IFluidHandlerの実装部分 ======*/

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (resource == null) {
			return null;
		}
		if (productTank.getFluidType() == resource.getFluid()) {
			return productTank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.productTank.drain(maxDrain, doDrain);
	}
	
	//
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource == null || resource.getFluid() == null){
			return 0;
		}
		
		FluidStack current = this.productTank.getFluid();
		FluidStack resourceCopy = resource.copy();
		if (current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)){
			return 0;
		}
		
		int i = 0;
		int used = this.productTank.fill(resourceCopy, doFill);
		resourceCopy.amount -= used;
		i += used;
		
		return i;
	}

	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (fluid == null || this.productTank.isFull()) return false;
		
		if (this.productTank.isEmpty()) return true;
		else{
			return this.productTank.getFluidType() == fluid;
		}
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return !this.productTank.isEmpty();
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{productTank.getInfo()};
	}

}
