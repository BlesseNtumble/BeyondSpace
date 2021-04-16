package beyondspace.items;

import java.util.List;
import java.util.Random;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.api.item.IItemSpaceFood;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class DehydratedFood extends ItemFood implements IItemSpaceFood {

	public static final String[] dehydrated = new String[] {
	"BeefCooked", "BeefRaw", "Cheese", "ChickenCooked", "ChickenRaw", "FishCooked", "FishRaw", "MushroomStew", "PorkchopCooked", "PorkchopRaw", "PotatoBaked", "PumpkinPie",};
	
	@SideOnly(Side.CLIENT)
	private IIcon[] texture;
	public static Random rand = new Random();
	
	public DehydratedFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
			this.setCreativeTab(BeyondSpace.bsTab);
			this.setHasSubtypes(true);
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
    
    public int getHealAmount(ItemStack par1ItemStack)
    {
        switch (par1ItemStack.getItemDamage())
        {
        case 15:
        case 1:
            return 4;
        case 4:
            return 4;
        case 6:
            return 4;
        case 9:
            return 4;
        default:
            return 8;
        }
    }

    public float getSaturationModifier(ItemStack par1ItemStack)
    {
        switch (par1ItemStack.getItemDamage())
        {
        case 1:
            return 0.3F;
        case 4:
            return 0.3F;
        case 6:
            return 0.3F;
        case 9:
            return 0.3F;
        default:
            return 0.7F;
        }
    }
    
    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
            par3EntityPlayer.getFoodStats().addStats(this.getHealAmount(par1ItemStack), this.getSaturationModifier(par1ItemStack));
            if (!par2World.isRemote)
            {
                par3EntityPlayer.entityDropItem(new ItemStack(GCItems.canister, 1, 0), 0.0F);
            }

        return super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
    }
}