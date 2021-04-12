package beyondspace.items;

import java.util.List;
import java.util.Random;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class DehydratedFood extends Item {
	
	public static final String[] dehydrated = new String[] {
	"BeefCooked", "BeefRaw", "Cheese", "ChickenCooked", "ChickenRaw", "FishCooked", "FishRaw", "MushroomStew", "PorkchopCooked", "PorkchopRaw", "PotatoBaked", "PumpkinPie",};
	
	@SideOnly(Side.CLIENT)
	private IIcon[] texture;
	public static Random rand = new Random();
	
	public DehydratedFood() {
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister){
		texture = new IIcon[dehydrated.length];
		for (int i = 0; i < dehydrated.length; i++){
			texture[i] = iconRegister.registerIcon(ModInfo.MODID + ":" + "dehydrated" + dehydrated[i]);
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

    public String getUnlocalizedName(ItemStack stack) {
    		return "item.DehydratedFood";    	
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < dehydrated.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    //22.5F
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	String desc = "";
    	int damage = stack.getItemDamage();
    	switch(damage) {
	    	case 0: desc = "BeefCooked.name"; break;
	    	case 1: desc = "BeefRaw.name"; break;
	    	case 2: desc = "Cheese.name"; break;
	    	case 3: desc = "ChickenCooked.name"; break;
	    	case 4: desc = "ChickenRaw.name"; break;
	    	case 5: desc = "FishCooked.name"; break;
	    	case 6: desc = "FishRaw.name"; break;
	    	case 7: desc = "MushroomStew.name"; break;
	    	case 8: desc = "PorkchopCooked.name"; break;
	    	case 9: desc = "PorkchopRaw.name"; break;
	    	case 10: desc = "PotatoBaked.name"; break;
	    	case 11: desc = "PumpkinPie.name"; break;
    	}
    	list.add(StatCollector.translateToLocal("DehydratedFood.desc." + desc));
    }
}