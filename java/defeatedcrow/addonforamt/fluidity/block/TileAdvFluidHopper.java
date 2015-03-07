package defeatedcrow.addonforamt.fluidity.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * FluidHopperと似てはいるが、アイテムスロットがない、明石制御機能があるなどの点が違う。
 * */
public class TileAdvFluidHopper extends TileEntity implements IFluidHandler{
	
	//このTileEntityに持たせる液体タンク。引数は最大容量。
	public FluidTankFF productTank = new FluidTankFF(80000);
	public final int FLOW_RATE = 400;
	
	//Mode制御用
	private Mode mode = Mode.Default;
	private int filterFluid = 0;
	
	//サバクラ更新用
	private int lastAmount = 0;
	private boolean lastActivated = false;
	private boolean active = false;
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        this.productTank = new FluidTankFF(80000);
		if (par1NBTTagCompound.hasKey("ProductTank")) {
		    this.productTank.readFromNBT(par1NBTTagCompound.getCompoundTag("ProductTank"));
		}
		
		this.filterFluid = par1NBTTagCompound.getShort("FilterID");
		this.mode = Mode.getMode(par1NBTTagCompound.getByte("Mode"));
		this.lastActivated = par1NBTTagCompound.getBoolean("Active");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        
        NBTTagCompound tank = new NBTTagCompound();
		this.productTank.writeToNBT(tank);
		par1NBTTagCompound.setTag("ProductTank", tank);
		
		par1NBTTagCompound.setShort("FilterID", (short)this.filterFluid);
		par1NBTTagCompound.setByte("Mode", (byte)this.mode.getId());
		par1NBTTagCompound.setBoolean("Active", this.lastActivated);
		
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
	
	//ゲッターとかセッターとか
    public Mode getMode()
    {
    	return this.mode;
    }
    
    public void setMode(int par1)
    {
    	if (par1 < 0 || par1 > 3) par1 = 0;
    	this.mode = Mode.getMode(par1);
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public void setFilter(Fluid fluid)
    {
    	int get = fluid == null ? 0 : fluid.getID();
    	this.filterFluid = get;
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public void setFilterID(int id)
    {
    	this.filterFluid = id;
    }
    
    public Fluid getFilterFluid()
    {
    	return FluidRegistry.isFluidRegistered(FluidRegistry.getFluidName(filterFluid))
    			? FluidRegistry.getFluid(filterFluid) : null;
    }
    
    public int getFilterID()
    {
    	return this.filterFluid;
    }
    
    public boolean isActive()
    {
    	return this.lastActivated;
    }
    
    //更新
    
    public void getGuiFluidUpdate(int id, int val)
	{
		if (id == 0)//ID
		{
			if (productTank.getFluid() == null)
			{
				productTank.setFluid(new FluidStack(val, 0));
			}
			else
			{
				productTank.getFluid().fluidID = val;
			}
		}
		else if (id == 1)//amount
		{
			if (productTank.getFluid() == null)
			{
				productTank.setFluid(new FluidStack(0, val));
			}
			else
			{
				productTank.getFluid().amount = val;
			}
		}
		else if (id == 2)//mode
		{
			this.setMode(val);
		}
		else if (id == 3)//active
		{
			if (val == 0) this.lastActivated = false;
			else this.lastActivated = true;
		}
		else if (id == 4)//filter
		{
			this.filterFluid = val;;
		}
	}
    
    @SideOnly(Side.CLIENT)
	public int getFluidAmountScaled(int par1)
	{
		return this.productTank.getFluidAmount() * par1 / this.productTank.getCapacity();
	}
    
    /* 処理部分 */
    
    public void updateEntity()
	{
		//動作可能判定
		this.active = false;
		boolean flag = false;
		if (this.canActivate())
		{
			flag = true;
		}
		
		if (!this.worldObj.isRemote)
		{
			if (flag)
			{
				//IFluidHandler
				boolean in = this.insertFluidInHopper();
				boolean out = this.extractFluidFromHopper();
				
				if (in || out) {
					this.active = true;
					this.onServerUpdate();
				}
			}
			
			
			if (this.active != this.lastActivated)
			{
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "fluiditydc:knock3", 1.0F, 1.0F);
				this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastActivated = this.active;
			}
		}
	}
    
    /* モードごとのチェック */
    private boolean canActivate()
    {
    	int id = this.mode.getId();
    	
    	if (id == 0)
    	{
    		return true;
    	}
    	else if (id == 1)//Entity
    	{
    		boolean flag = true;
    		boolean flag2 = true;
    		
    		List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, 
    				AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
    		List<Entity> entities2 = worldObj.getEntitiesWithinAABB(Entity.class, 
    				AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1, zCoord, xCoord + 1, yCoord, zCoord + 1));
    		
    		if (entities == null || entities.isEmpty()) flag = false;
    		if (entities2 == null || entities2.isEmpty()) flag2 = false;
    		return flag || flag2;
    	}
    	else if (id == 2)//fluid id
    	{
    		if (this.filterFluid == 0) return false;
    		else if (!this.productTank.isEmpty() && this.productTank.getFluidType().getID() != this.filterFluid) return false;
    	}
    	else if (id == 3)//red stone
    	{
    		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
    	}
    	
    	return true;
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
    
//    private boolean insertFromBlock()
//	{
//		Block block = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
//		if (block instanceof IFluidBlock)
//		{
//			IFluidBlock target = (IFluidBlock) block;
//			FluidStack get = target.drain(worldObj, xCoord, yCoord + 1, zCoord, false);
//			if (this.productTank.fill(get, false) == get.amount)
//			{
//				target.drain(worldObj, xCoord, yCoord + 1, zCoord, true);
//				this.productTank.fill(get, true);
//				return true;
//			}
//		}
//		else if (block instanceof BlockLiquid)
//		{
//			Fluid target = FluidRegistry.lookupFluidForBlock(block);
//			if (target != null)
//			{
//				FluidStack get = new FluidStack(target, FluidContainerRegistry.BUCKET_VOLUME);
//				if (this.productTank.fill(get, false) == get.amount)
//				{
//					worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
//					this.productTank.fill(get, true);
//					return true;
//				}
//			}
//		}
//		else if (block instanceof BlockCauldron)
//		{
//			if (worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord) == 3)
//			{
//				FluidStack get = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
//				if (this.productTank.fill(get, false) == get.amount)
//				{
//					worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 0, 3);
//					this.productTank.fill(get, true);
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
    
    private boolean insertFluidInHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, 
				AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
		int ext = FLOW_RATE;
		IFluidHandler target = null;
		
		if (tile != null)
		{
			if (tile instanceof IFluidHandler)
			{
				target = (IFluidHandler) tile;
			}
		}
		else if (entities != null && !entities.isEmpty())
		{
			
			for (Entity ent : entities)
			{
				if (ent != null && ent instanceof IFluidHandler)
				{
					target = (IFluidHandler)ent;
					break;
				}
			}
		}
		
		if (target != null)
		{
			FluidStack current = this.productTank.getFluid();
			
			if (this.mode == Mode.FluidType)
			{
				current = new FluidStack(this.filterFluid, ext);
			}
			
			if (current != null)
			{
				FluidStack drn = new FluidStack(current.getFluid(), ext);
				FluidStack drn2 = target.drain(ForgeDirection.DOWN, drn, false);
				int fillAmount = 0;
				if (drn2 != null)
				{
					int f1 = this.productTank.getCapacity() - current.amount;
					int f2 = Math.min(f1, drn2.amount);
					fillAmount = Math.min(f2, ext); 
				}
				
				if (fillAmount > 0 && target.canDrain(ForgeDirection.DOWN, drn2.getFluid()))
				{
					target.drain(ForgeDirection.DOWN, fillAmount, true);
					this.productTank.fill(drn2, true);
					return true;
				}
				
			}
			else
			{
				FluidTankInfo[] info = target.getTankInfo(ForgeDirection.DOWN);
				if (info == null) return false;; 
				FluidStack drn = null;
				int fillAmount = 0;
				for (int i = info.length - 1 ; i >= 0 ; i--)
				{
					if (info[i].fluid != null)
					{
						drn = info[i].fluid.copy();
						fillAmount = Math.min(drn.amount, ext);
						break;
					}
				}
				
				if (drn != null && fillAmount > 0 && target.canDrain(ForgeDirection.DOWN, drn.getFluid()))
				{
					target.drain(ForgeDirection.DOWN, fillAmount, true);
					this.productTank.fill(new FluidStack(drn.getFluid(), fillAmount), true);
					return true;
				}
			}
		}
		
		return false;
	}
    
    private boolean extractFluidFromHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, 
				AxisAlignedBB.getBoundingBox(xCoord, yCoord - 1.5D, zCoord, xCoord + 1, yCoord, zCoord + 1));
		IFluidHandler target = null;
		
		int ext = FLOW_RATE;
		ext = Math.min(ext, this.productTank.getFluidAmount());
		
		if (tile != null)
		{
			if (tile instanceof IFluidHandler)
			{
				target = (IFluidHandler) tile;
			}
		}
		else if (entities != null && !entities.isEmpty())
		{
			for (Entity ent : entities)
			{
				if (ent != null && ent instanceof IFluidHandler)
				{
					target = (IFluidHandler)ent;
					break;
				}
			}
		}
		
		if (target != null)
		{
			FluidStack current = this.productTank.getFluid();
			if (current != null)
			{
				FluidStack drn = new FluidStack(current.getFluid(), ext);
				int fill = target.fill(ForgeDirection.UP, drn, false);
				FluidStack drn2 = new FluidStack(current.getFluid(), fill);
				
				if (fill > 0 && target.canFill(ForgeDirection.UP, drn2.getFluid()))
				{
					target.fill(ForgeDirection.UP, drn2, true);
					this.productTank.drain(fill, true);
					return true;
				}
				
			}
		}
		return false;
	}
    
	/* IFluidHandler */
	
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
		if (from == ForgeDirection.DOWN || resource == null || resource.getFluid() == null){
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
		if (from == ForgeDirection.DOWN || fluid == null || this.productTank.isFull()) return false;
		
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
	
	//TileEntityの特殊レンダークラスで使う液体のアイコンを取得。
	@SideOnly(Side.CLIENT)
	public IIcon getFluidIcon()
    {
		Fluid fluid = this.productTank.getFluidType();
    	return fluid != null ? fluid.getIcon() : null;
    }
	
	public short getFluidGauge()
    {
    	return (short) (productTank.getFluidAmount() / 800);
    }
	
	//モード設定
	public enum Mode
	{
		Entity(1),
		FluidType(2),
		RedStone(3),
		Default(0);
		
		private final int id;
		
		private Mode(int num)
		{
			this.id = num;
		}
		
		public static final Mode[] ModeList = {Default, Entity, FluidType, RedStone};
		
		public static Mode getMode(int id)
		{
			if (id >= 0 && id < ModeList.length)
			{
				return ModeList[id];
			}
			return Default;
		}
		
		public int getId()
		{
			return id;
		}
		
	}
	
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

}
