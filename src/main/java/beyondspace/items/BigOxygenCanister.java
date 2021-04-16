package beyondspace.items;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.GalaxySpace;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BigOxygenCanister extends Item implements IItemOxygenSupply {
  private final int ox_storage;
  
  private final String name;
  
  public BigOxygenCanister(String assetName, int storage) {
    this.name = assetName;
    this.setMaxDamage(1000);
    this.setMaxStackSize(1);
    this.setNoRepair();
    this.setUnlocalizedName(this.name);
    this.setContainerItem(GCItems.oilCanister);
    this.setTextureName(ModInfo.MODID + ":" + assetName);
    this.ox_storage = storage / 1000;
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack par1ItemStack, EntityPlayer player, List par2List, boolean b) {
    if (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() > 0)
    	par2List.add(GCCoreUtil.translate("gui.tank.oxygenRemaining") + ": " + ((par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()) * this.ox_storage)); 
  }
  
  @Override
  public CreativeTabs getCreativeTab() {
    return BeyondSpace.bsTab;
  }
  
  @Override
  public ItemStack getContainerItem(ItemStack itemstack) {
    if (super.getContainerItem(itemstack) == null)
      return null; 
    return itemstack;
  }
  
  public float recharge(ItemStack itemStack) {
    return this.ox_storage;
  }
  
  public float discharge(ItemStack itemStack, float amount) {
    if (itemStack.getItemDamage() < itemStack.getMaxDamage()) {
      itemStack.setItemDamage(itemStack.getItemDamage() + 1);
      return this.ox_storage;
    } 
    return 0.0F;
  }
  
  public int getOxygenStored(ItemStack par1ItemStack) {
    return par1ItemStack.getMaxDamage();
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item item, CreativeTabs tab, List par3List) {
    par3List.add(new ItemStack(item, 1, 0));
    par3List.add(new ItemStack(item, 1, 1000));
  }
}
