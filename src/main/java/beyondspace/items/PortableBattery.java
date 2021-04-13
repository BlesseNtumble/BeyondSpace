package beyondspace.items;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.utils.BSUtilities;
import beyondspace.utils.RegistrationsList;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class PortableBattery extends ItemElectricBase {
	
	public static IIcon[] textures = new IIcon[6];
		
    public PortableBattery(String assetName){
        this.setUnlocalizedName(assetName);
        this.setCreativeTab(BeyondSpace.gaTab);
        this.setTextureName(ModInfo.MODID + ":" + assetName);
    }
    
	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return 100000.0F;
	}
	
	public static NBTTagCompound getCompound(ItemStack stack){
		if (stack.getTagCompound() == null) 
		stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}
	
	public static boolean verifyExistance(ItemStack stack, String tag) {
		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null)
			return false;
		else
			return stack.getTagCompound().hasKey(tag);
	}
	
	public static short getShort(ItemStack stack, String tag, short defaultExpected) {
		return verifyExistance(stack, tag) ? stack.getTagCompound().getShort(tag) : defaultExpected;
	}
	
	public static ItemStack setShort(ItemStack stack, String tag, short s)
	{
		NBTTagCompound compound = getCompound(stack);
		compound.setShort(tag, s);
		stack.setTagCompound(compound);
		return stack;
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
    	NBTTagCompound compound = getCompound(stack);
        if (player.isSneaking()) {
            int mode = getShort(stack, "Mode", (short) 0);
            setShort(stack, "Mode", (short) ((short) mode == 1 ? mode = 0 : mode+1));
            if (!world.isRemote)
                player.addChatComponentMessage(new ChatComponentTranslation(StatCollector.translateToLocal("beyondspace.charger") + ": " + StatCollector.translateToLocal("beyondspace.charger.mode") + mode));
        }
        return stack;
    }
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for (int i = 0; i < 6; i++) textures[i] = iconRegister.registerIcon(ModInfo.MODID + ":PortableCharger" + i);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta < 20 && meta > 0) return textures[4];
		return textures[(100 - meta) / 20];
	}
}