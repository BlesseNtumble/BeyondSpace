package beyondspace.items;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.entity.RedLightningEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class HandRocket extends Item {
	
	public HandRocket() {
        this.setCreativeTab(BeyondSpace.gaTab);
        this.setMaxStackSize(1);
        this.setTextureName(ModInfo.MODID + ":HandRocket");
        this.setUnlocalizedName("HandRocket");
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			if (!player.isSneaking()) {
				//ASJUtilities.sendToDimensionWithoutPortal(player, 0, player.posX, 228, player.posZ);
				//if (Switches.spaceDim) ASJUtilities.sendToDimensionWithoutPortal(player, GAConfig.space, player.posX, 228, player.posZ);
				/*TileEntity tile;
				tile = (GasGeneratorTileEntity)world.getTileEntity((int)player.posX, (int)player.posY, (int)player.posZ);
				if(tile instanceof GasGeneratorTileEntity){
					player.addChatComponentMessage(new ChatComponentText("fuck" + ((GasGeneratorTileEntity)tile).storage.getEnergyStoredGC()));
				}*/
				//TODO: FIX!!! >> world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
				//TODO: FIX!!! >> world.addWeatherEffect(new RedLightningEntity(world, player.posX, player.posY, player.posZ));
				//TODO: FIX!!! >> world.spawnEntityInWorld(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
				world.spawnEntityInWorld(new RedLightningEntity(world, player.posX, player.posY, player.posZ));
		    } else {
 				player.addChatComponentMessage(new ChatComponentText("Current dimension id: " + player.dimension));
 			}
		}
		return stack;
    }
}
