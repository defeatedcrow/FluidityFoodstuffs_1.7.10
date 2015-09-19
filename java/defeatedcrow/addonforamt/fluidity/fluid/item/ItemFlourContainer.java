package defeatedcrow.addonforamt.fluidity.fluid.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.api.event.*;
import defeatedcrow.addonforamt.fluidity.common.FluidityCore;

public class ItemFlourContainer extends Item implements IFuelHandler{
	
	private static String[] sackName = new String[] {"flour", "salt", "sugar", "milk", "wheat", "rice", "seed", "water", "lava", "milk"};
	
	@SideOnly(Side.CLIENT)
    private IIcon iconItemType[];
	
	public ItemFlourContainer() {
		super();
		this.setContainerItem(Items.paper);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (movingobjectposition == null)
        {
            
        }
        else
        {
        	FlourSackCrickEvent event = new FlourSackCrickEvent(player, item, world, movingobjectposition);
        	if (MinecraftForge.EVENT_BUS.post(event))
            {
                return item;
            }

        	if (event.getResult() == Event.Result.ALLOW)
            {
                if (player.capabilities.isCreativeMode)
                {
                    return item;
                }

                if (--item.stackSize <= 0)
                {
                    return event.result;
                }

                if (!player.inventory.addItemStackToInventory(event.result))
                {
                    player.dropPlayerItemWithRandomChoice(event.result, false);
                }

                return item;
            }
        }
        
        if (item != null && item.getItemDamage() == 3){
			player.setItemInUse(item, this.getMaxItemUseDuration(item));
		}
		return super.onItemRightClick(item, world, player);
    }
	
	/* -- 牛乳のみ飲食効果がある -- */
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		if (stack != null){
			if (stack.getItemDamage() == 3 || stack.getItemDamage() == 9)
			return EnumAction.drink;
		}
		return EnumAction.none;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 16;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		player.clearActivePotions();
		if (!player.capabilities.isCreativeMode) {
			--stack.stackSize;
			if (!player.worldObj.isRemote) {
				ItemStack ret = new ItemStack(Items.paper, 1, 0);
				EntityItem drop = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, ret);
				player.worldObj.spawnEntityInWorld(drop);
			}
		}
		return stack;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
    {
        int j = MathHelper.clamp_int(par1, 0, 9);
        return this.iconItemType[j];
    }

	@Override
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int meta = par1ItemStack.getItemDamage();
		int i = MathHelper.clamp_int(meta, 0, 8);
		return meta < 9 ? super.getUnlocalizedName() + "_" + sackName[i] : super.getUnlocalizedName() + "_" + meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
		par3List.add(new ItemStack(this, 1, 3));
		par3List.add(new ItemStack(this, 1, 4));
		par3List.add(new ItemStack(this, 1, 5));
		par3List.add(new ItemStack(this, 1, 6));
		par3List.add(new ItemStack(this, 1, 7));
		par3List.add(new ItemStack(this, 1, 8));
		if (FluidityCore.ffmMilk != null){
			par3List.add(new ItemStack(this, 1, 9));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.iconItemType = new IIcon[10];

        for (int i = 0; i < 10; ++i)
        {
        	this.iconItemType[i] = par1IconRegister.registerIcon("fluiditydc:sack_" + sackName[i]);
        }
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel != null && fuel.getItemDamage() == 8)
		return 2400;
		
		return 0;
	}

}
