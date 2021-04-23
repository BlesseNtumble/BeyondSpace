package beyondspace.items;

import java.util.List;
import java.util.Random;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class FloaterDrop extends Item {
	
	public static final String[] drops = new String[] { "FloaterGasBags", "FloaterHeart" };
	
	private IIcon[] texture = new IIcon[drops.length];
	public static Random rand = new Random();
	
	public FloaterDrop() {
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("FloaterItems");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for (int i = 0; i < drops.length; i++){
			texture[i] = iconRegister.registerIcon(ModInfo.MODID + ":" + drops[i]);
		}
	}

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
    	if (i < texture.length) {
        	return texture[i];
    	} else {
    		return texture[0];
    	}
    }

    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < drops.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public String getUnlocalizedName(ItemStack stack) {
    	if (stack.getItemDamage() < drops.length) {
        	return "item." + drops[stack.getItemDamage()];
    	} else {
    		return "item." + drops[0];
    	}
    }
}