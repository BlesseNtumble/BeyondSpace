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
				world.spawnEntityInWorld(new RedLightningEntity(world, player.posX, player.posY, player.posZ));
		    }
		}
		return stack;
    }
}
