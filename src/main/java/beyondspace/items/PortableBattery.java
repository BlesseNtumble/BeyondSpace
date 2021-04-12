package beyondspace.items;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

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