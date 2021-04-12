package beyondspace.items;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class RepairKit extends Item {
	
	public RepairKit() {
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setFull3D();
		this.setMaxDamage(2048);
		this.maxStackSize = 1;
		this.setTextureName(ModInfo.MODID + ":RepairKit");
		this.setUnlocalizedName("RepairKit");
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.BOLD.YELLOW + "Bits left: " + (2048 - stack.getItemDamage()));
		if (!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
}
