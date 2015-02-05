package defeatedcrow.addonforamt.fluidity.fluid.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import defeatedcrow.addonforamt.fluidity.api.event.*;

public class ItemFlourBucket extends ItemBucket {
	
	private final String TEX;
	private final Block FLUID;

	public ItemFlourBucket(Block block, String name) {
		super(block);
		this.setContainerItem(Items.bucket);
		this.TEX = name;
		this.FLUID = block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("fluiditydc:bucket_" + TEX);
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
        	FlourBucketCrickEvent event = new FlourBucketCrickEvent(player, item, world, movingobjectposition);
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
	
	@Override
	public boolean tryPlaceContainedLiquid(World world, int x, int y, int z)
    {
        if (this.FLUID == null || this.FLUID == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = world.getBlock(x, y, z).getMaterial();
            boolean flag = !material.isSolid();

            if (!world.isAirBlock(x, y, z) && !flag)
            {
                return false;
            }
            else
            {
                world.setBlock(x, y, z, this.FLUID, 0, 3);

                return true;
            }
        }
    }

}
