package defeatedcrow.addonforamt.fluidity.fluid.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.api.event.*;

public class ItemFlourContainer extends Item{
	
	private static String[] sackName = new String[] {"flour", "salt", "sugar", "milk", "wheat", "rice", "seed", "water"};
	
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
            return item;
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
        	else
        	{
        		return super.onItemRightClick(item, world, player);
        	}
        }
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
    {
        int j = MathHelper.clamp_int(par1, 0, 7);
        return this.iconItemType[j];
    }

	@Override
	public int getMetadata(int par1) {
		return par1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 7);
		return super.getUnlocalizedName() + "_" + sackName[i];
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
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.iconItemType = new IIcon[8];

        for (int i = 0; i < 8; ++i)
        {
        	this.iconItemType[i] = par1IconRegister.registerIcon("fluiditydc:sack_" + sackName[i]);
        }
	}

}
