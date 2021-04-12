package beyondspace.items;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class AdvancedOxygenTank extends ItemOxygenTank
	{
    public AdvancedOxygenTank(int tier, String assetName)
    {
        super(tier, assetName);
        this.setMaxStackSize(1);
        this.setMaxDamage(tier * 1080);
        this.setUnlocalizedName(assetName);
        this.setTextureName(ModInfo.MODID + ":" + assetName);
        this.setNoRepair();
        
    }
  
    @Override
    public CreativeTabs getCreativeTab()
    {
        return BeyondSpace.gaTab;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b)
    {
        par2List.add(GCCoreUtil.translate("gui.tank.oxygenRemaining") + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()));
    }
}