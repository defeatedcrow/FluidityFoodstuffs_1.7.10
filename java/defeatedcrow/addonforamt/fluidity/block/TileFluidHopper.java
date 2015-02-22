package defeatedcrow.addonforamt.fluidity.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileFluidHopper extends TileEntity implements IFluidHandler, ISidedInventory {
	
	//このTileEntityに持たせる液体タンク。引数は最大容量。
	public FluidTankFF productTank = new FluidTankFF(80000);
	
	public final int MAX_COOLTIME = 10;
	private int coolTime = 0;
	private int mode = 0;
	
	private int lastAmount = 0;
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 10);
		this.itemstacks = new ItemStack[this.getSizeInventory()];
 
		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
 
			if (b0 >= 0 && b0 < this.itemstacks.length)
			{
				this.itemstacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
        
        this.productTank = new FluidTankFF(80000);
		if (par1NBTTagCompound.hasKey("ProductTank")) {
		    this.productTank.readFromNBT(par1NBTTagCompound.getCompoundTag("ProductTank"));
		}
		
		this.coolTime = par1NBTTagCompound.getByte("CoolTime");
		this.mode = par1NBTTagCompound.getByte("Mode");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        
        NBTTagList nbttaglist = new NBTTagList();
		 
		for (int i = 0; i < this.itemstacks.length; ++i)
		{
			if (this.itemstacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.itemstacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		
		par1NBTTagCompound.setTag("Items", nbttaglist);
        
        NBTTagCompound tank = new NBTTagCompound();
		this.productTank.writeToNBT(tank);
		par1NBTTagCompound.setTag("ProductTank", tank);
		
		par1NBTTagCompound.setByte("CoolTime", (byte)this.coolTime);
		par1NBTTagCompound.setByte("Mode", (byte)this.mode);
		
    }
    
    public int getCoolTime()
    {
    	return this.coolTime;
    }
    
    public int getMode()
    {
    	return this.mode;
    }
    
    public void setCoolTime(int par1)
    {
    	this.coolTime = par1;
    }
    
    public void setMode(int par1)
    {
    	if (par1 < 0 || par1 > 3) par1 = 0;
    	this.mode = par1;
    	this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public int extractAmount()
    {
    	if (this.mode == 1)
    	{
    		return 1000;
    	}
    	else if (this.mode == 2)
    	{
    		return 4000;
    	}
    	else if (this.mode == 3)
    	{
    		return 16000;
    	}
    	else
    	{
    		return 0;
    	}
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
	}
	
	@SideOnly(Side.CLIENT)
	public int getFluidAmountScaled(int par1)
	{
		return this.productTank.getFluidAmount() * par1 / this.productTank.getCapacity();
	}
	
	/* 処理部分 */
	
	public void updateEntity()
	{
		if (this.coolTime > 0)
		{
			--this.coolTime;
		}
 
		if (!this.worldObj.isRemote)
		{
			if (this.coolTime == 0)
			{
				this.drainContainer();
				this.fillContainer();
				this.insertFromBlock();
				this.insertItemInHopper();
				this.extractItemFromHopper();
				
				this.coolTime = MAX_COOLTIME;
			}
			
			this.insertFluidInHopper();
			this.extractFluidFromHopper();
			
			this.onServerUpdate();
		}
	}
	
	//スロットの液体コンテナを処理する
	private void drainContainer()
	{
		ItemStack in = this.getStackInSlot(0);
		if (in != null && FluidContainerRegistry.isFilledContainer(in))
		{
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(in);
			ItemStack empty = FluidContainerRegistry.drainFluidContainer(in.copy());
			ItemStack current = this.getStackInSlot(1);
			
			boolean flag1 =  fluid.amount >= this.fill(ForgeDirection.UNKNOWN, fluid, false);
			boolean flag2 = false;
			if (empty == null || current == null) flag2 = true;
			else if (this.isItemStackable(empty, current)) flag2 = true;
			
			if (flag1 && flag2)
			{
				this.fill(ForgeDirection.UNKNOWN, fluid, true);
				this.incrStackInSlot(1, empty);
				
				if (this.decrStackSize(0, 1) == null)
				{
					this.markDirty();
				}
			}
		}
	}
	
	//スロットの空コンテナを満たす
	private void fillContainer()
	{
		ItemStack in = this.getStackInSlot(0);
		ItemStack current = this.getStackInSlot(1);
		FluidStack fluid = this.productTank.getFluid();
		
		if (in != null && fluid != null && FluidContainerRegistry.isEmptyContainer(in))
		{
			ItemStack ret = FluidContainerRegistry.fillFluidContainer(fluid, in);
			
			boolean flag1 = ret != null;
			boolean flag2 = false;
			if (current == null) flag2 = true;
			else if (this.isItemStackable(ret, current)) flag2 = true;
			
			if (flag1 && flag2)
			{
				this.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getContainerCapacity(ret), true);
				this.incrStackInSlot(1, ret);
				if (this.decrStackSize(0, 1) == null)
				{
					this.markDirty();
				}
			}
		}
	}
	
	private void insertFluidInHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		int ext = this.extractAmount()/MAX_COOLTIME;
		
		if (tile != null)
		{
			if (tile instanceof IFluidHandler)
			{
				FluidStack current = this.productTank.getFluid();
				IFluidHandler tank = (IFluidHandler) tile;
				if (current != null)
				{
					FluidStack drn = new FluidStack(current.getFluid(), ext);
					FluidStack drn2 = tank.drain(ForgeDirection.DOWN, drn, false);
					int fillAmount = 0;
					if (drn2 != null)
					{
						int f1 = this.productTank.getCapacity() - current.amount;
						int f2 = Math.min(f1, drn2.amount);
						fillAmount = Math.min(f2, ext);
					}
					
					if (fillAmount > 0)
					{
						tank.drain(ForgeDirection.DOWN, fillAmount, true);
						this.productTank.fill(drn2, true);
						return;
					}
					
				}
				else
				{
					FluidTankInfo[] info = tank.getTankInfo(ForgeDirection.DOWN);
					if (info == null) return; 
					FluidStack drn = null;
					int fillAmount = 0;
					for (int i = 0 ; i < info.length ; i++)
					{
						if (info[i].fluid != null)
						{
							drn = info[i].fluid.copy();
							fillAmount = Math.min(drn.amount, ext);
							break;
						}
					}
					
					if (drn != null && fillAmount > 0)
					{
						tank.drain(ForgeDirection.DOWN, fillAmount, true);
						this.productTank.fill(new FluidStack(drn.getFluid(), fillAmount), true);
						return;
					}
				}
			}
			
		}
	}
	
	private void insertItemInHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
		
		if (tile instanceof IInventory)
		{
			IInventory inv = (IInventory) tile;
			ItemStack current = this.getStackInSlot(0);
			
			if (tile instanceof ISidedInventory)
			{
				ISidedInventory sided = (ISidedInventory) tile;
				int[] accessible = sided.getAccessibleSlotsFromSide(0);
				ItemStack ret = null;
				int slot = 0;
				
				for (int i : accessible)
				{
					if (sided.getStackInSlot(i) == null) continue;
					
					ItemStack item = sided.getStackInSlot(i);
					if (!this.isItemValidForSlot(0, item)) continue;
					
					if (current == null
							|| this.isItemStackable(new ItemStack(item.getItem(), 1, item.getItemDamage()), current))
					{
						ret = new ItemStack(item.getItem(), 1, item.getItemDamage());
						slot = i;
						break;
					}
				}
				
				if (ret != null)
				{
					sided.decrStackSize(slot, 1);
					this.incrStackInSlot(0, ret);
					sided.markDirty();
					this.markDirty();
					return;
				}
			}
			else
			{
				ItemStack ret = null;
				int slot = 0;
				
				for (int i = 0 ; i < inv.getSizeInventory() ; i++)
				{
					if (inv.getStackInSlot(i) == null) continue;
					
					ItemStack item = inv.getStackInSlot(i);
					if (!this.isItemValidForSlot(0, item)) continue;
					
					if (current == null
							|| this.isItemStackable(new ItemStack(item.getItem(), 1, item.getItemDamage()), current))
					{
						ret = new ItemStack(item.getItem(), 1, item.getItemDamage());
						slot = i;
						break;
					}
				}
				
				if (ret != null)
				{
					inv.decrStackSize(slot, 1);
					this.incrStackInSlot(0, ret);
					inv.markDirty();
					this.markDirty();
					return;
				}
			}
		}
	}
	
	private void insertFromBlock()
	{
		if (this.mode == 0) return;
		
		Block block = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
		if (block instanceof IFluidBlock)
		{
			IFluidBlock target = (IFluidBlock) block;
			FluidStack get = target.drain(worldObj, xCoord, yCoord + 1, zCoord, false);
			if (this.productTank.fill(get, false) == get.amount)
			{
				target.drain(worldObj, xCoord, yCoord + 1, zCoord, true);
				this.productTank.fill(get, true);
				return;
			}
		}
		else if (block instanceof BlockLiquid)
		{
			Fluid target = FluidRegistry.lookupFluidForBlock(block);
			if (target != null)
			{
				FluidStack get = new FluidStack(target, FluidContainerRegistry.BUCKET_VOLUME);
				if (this.productTank.fill(get, false) == get.amount)
				{
					worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
					this.productTank.fill(get, true);
					return;
				}
			}
		}
		else if (block instanceof BlockCauldron)
		{
			if (worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord) == 3)
			{
				FluidStack get = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
				if (this.productTank.fill(get, false) == get.amount)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, 0, 3);
					this.productTank.fill(get, true);
					return;
				}
			}
		}
	}
	
	private void extractFluidFromHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		int ext = this.extractAmount()/MAX_COOLTIME;
		
		if (tile != null)
		{
			if (tile instanceof IFluidHandler)
			{
				IFluidHandler tank = (IFluidHandler) tile;
				FluidStack current = this.productTank.getFluid();
				if (current != null)
				{
					FluidStack drn = new FluidStack(current.getFluid(), ext);
					int fill = tank.fill(ForgeDirection.UP, drn, false);
					FluidStack drn2 = new FluidStack(current.getFluid(), fill);
					
					if (fill > 0)
					{
						tank.fill(ForgeDirection.UP, drn2, true);
						this.productTank.drain(fill, true);
						return;
					}
					
				}
			}
			
		}
	}
	
	private void extractItemFromHopper()
	{
		TileEntity tile = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if (tile instanceof IInventory)
		{
			IInventory inv = (IInventory) tile;
			ItemStack current = this.getStackInSlot(1);
			if (current == null) return;
			
			if (tile instanceof ISidedInventory)
			{
				ISidedInventory sided = (ISidedInventory) tile;
				int[] accessible = sided.getAccessibleSlotsFromSide(1);
				ItemStack ret = null;
				int slot = 0;
				
				for (int i : accessible)
				{
					ItemStack item = sided.getStackInSlot(i);
					if (!sided.isItemValidForSlot(i, current)) continue;
					
					if (item == null
							|| this.isItemStackable(new ItemStack(current.getItem(), 1, current.getItemDamage()), item))
					{
						ret = new ItemStack(current.getItem(), 1, current.getItemDamage());
						slot = i;
						break;
					}
				}
				
				if (ret != null)
				{
					if (sided.getStackInSlot(slot) == null)
					{
						sided.setInventorySlotContents(slot, ret);
					}
					else
					{
						sided.getStackInSlot(slot).stackSize++;
					}
					this.decrStackSize(1, 1);
					sided.markDirty();
					this.markDirty();
				}
			}
			else
			{
				ItemStack ret = null;
				int slot = 0;
				
				for (int i = 0 ; i < inv.getSizeInventory() ; i++)
				{
					ItemStack item = inv.getStackInSlot(i);
					if (!inv.isItemValidForSlot(i, current)) continue;
					
					if (item == null
							|| this.isItemStackable(new ItemStack(current.getItem(), 1, current.getItemDamage()), item))
					{
						ret = new ItemStack(current.getItem(), 1, current.getItemDamage());
						slot = i;
						break;
					}
				}
				
				if (ret != null)
				{
					if (inv.getStackInSlot(slot) == null)
					{
						inv.setInventorySlotContents(slot, ret);
					}
					else
					{
						inv.getStackInSlot(slot).stackSize++;
					}
					this.decrStackSize(1, 1);
					inv.markDirty();
					this.markDirty();
				}
			}
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
	
	private static boolean isItemStackable(ItemStack target, ItemStack current)
	{
		if (target == null || current == null) return false;
		
		if (target.getItem() == current.getItem() && target.getItemDamage() == current.getItemDamage())
		{
			return (current.stackSize + target.stackSize) <= current.getMaxStackSize();
		}
		
		return false;
	}
	
	private void incrStackInSlot(int i, ItemStack input)
	{
		if (i < this.getSizeInventory() && input != null &&  this.itemstacks[i] != null)
		{
			if (this.itemstacks[i].getItem() == input.getItem() 
					&& this.itemstacks[i].getItemDamage() == input.getItemDamage())
			{
				this.itemstacks[i].stackSize += input.stackSize;
				if (this.itemstacks[i].stackSize > this.getInventoryStackLimit())
				{
					this.itemstacks[i].stackSize = this.getInventoryStackLimit();
				}
			}
		}
		else
		{
			this.setInventorySlotContents(i, input);
		}
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
	
	/* ISidedInventory */
	
	protected int[] slotsTop = {0};
	protected int[] slotsBottom = {1};
	protected int[] slotsSides = {0,1};
 
	public ItemStack[] itemstacks = new ItemStack[getSizeInventory()];
	
	@Override
	public int getSizeInventory() {
		return 2;
	}
	
	@Override
	public ItemStack getStackInSlot(int par1) {
		return par1 < this.getSizeInventory() ? this.itemstacks[par1] : null;
	}
	
	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (par1 < this.getSizeInventory() && this.itemstacks[par1] != null)
		{
			ItemStack itemstack;
 
			if (this.itemstacks[par1].stackSize <= par2)
			{
				itemstack = this.itemstacks[par1];
				this.itemstacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.itemstacks[par1].splitStack(par2);
 
				if (this.itemstacks[par1].stackSize == 0)
				{
					this.itemstacks[par1] = null;
				}
 
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (par1 < this.getSizeInventory() && this.itemstacks[par1] != null)
		{
			ItemStack itemstack = this.itemstacks[par1];
			this.itemstacks[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		
		if (par1 > this.getSizeInventory()) par1 = 0;
		
		this.itemstacks[par1] = par2ItemStack;
 
		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	@Override
	public String getInventoryName()
	{
		return "FluidHopper";
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openInventory() {}
 
	@Override
	public void closeInventory() {}
	
	@Override
	public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
		return (par1 == 1) ? false : (par2ItemStack != null && FluidContainerRegistry.isContainer(par2ItemStack) ? true : false);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}
	
	@Override
	public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
		return this.isItemValidForSlot(par1, par2ItemStack);
	}
	
	@Override
	public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
		return par1 == 1;
	}

}
