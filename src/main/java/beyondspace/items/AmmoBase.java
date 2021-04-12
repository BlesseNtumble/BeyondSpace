package beyondspace.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class AmmoBase extends Item {

	/** Capacity of holder */
	public int capacity;
	
	public AmmoBase(int capacity, CreativeTabs tab, String name, String texture) {
		this.maxStackSize = 1;
		this.setCapacity(capacity);
		this.setCreativeTab(tab);
		this.setFull3D();
		this.setUnlocalizedName(name);
		/** Modified */
		this.setTextureName(texture);
	}
	
	/**
	 * Specifies capacity of the holder
	 * */
	public Item setCapacity(int capacity) {
		this.capacity = capacity;
		this.setMaxDamage(capacity);
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("Ammo", this.capacity);
		}
		list.add(EnumChatFormatting.BOLD.YELLOW + "Ammo: " + stack.stackTagCompound.getInteger("Ammo"));
	}
}
