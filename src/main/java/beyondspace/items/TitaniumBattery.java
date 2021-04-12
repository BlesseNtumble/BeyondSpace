package beyondspace.items;

import beyondspace.ModInfo;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TitaniumBattery extends ItemElectricBase
{
    public TitaniumBattery(String assetName, CreativeTabs tab)
    {
        super();
        this.setUnlocalizedName(assetName);
        this.setCreativeTab(tab);
        this.setTextureName(ModInfo.MODID + ":" + assetName);
    }

    @Override
    public float getMaxElectricityStored(ItemStack itemStack)
    {
        return 80000;
    }
}
