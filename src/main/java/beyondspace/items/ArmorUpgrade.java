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

public class ArmorUpgrade extends Item {
	/*
	public static enum UpgradeList {
		BLANK, // 0
		ANTIGRAV, GRAV, SHOCKWAVE, HIGHJUMP, KINETIC, STEPASSIST, FROSTWALK, // 1 - 7
		SPEED, // 8
		ANTIRAD, EXPLPROF, FASTDIGGING, FIREPROF, INVIS, JETPACK, MANAPROF, PROTECTION, TESLA, // 9 - 17
		AUTOFEED, DETOXICATOR, NIGHTVIS, REVEALING, SENSOR, SOLAR, WATERBR, // 18 - 24
		BATLV, BATMV, BATHV, MANADISC, RUNIC, VISDISC; // 25 - 30
	};*/
	
	public static final String[] subItems = new String[] {
			/*"Blank", // 0
			"Antigravitational", "Gravitational", "ShockWave", "Highjump", "Kinetic", "StepAssist", "Frostwalk", // 1 - 7
			"Speed", // 8
			"Antiradiational", "ExplosionProtection", "FastDigging", "FireProtection", "Invisibility", "Jetpack", "ManaProficiency", "Protection", "TeslaChain", // 9 - 17
			"AutoFeeder", "Detoxicator", "NightVision", "Revealing", "SensorGoggles", "SolarPanel", "WaterBreathing", // 18 - 24
			"BatteryLV", "BatteryMV", "BatteryHV", "ManaDiscount", "RunicShield", "VisDiscount" }; // 25 - 30*/
			
			"SolarPanel", 	// 0
			"Protection", 	// 1
			"Kinetic"		// 2
			
	};
	
	@SideOnly(Side.CLIENT)
	private IIcon[] texture;
	public static Random rand = new Random();
	
	public ArmorUpgrade() {
		this.setCreativeTab(BeyondSpace.upTab);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister){
		texture = new IIcon[subItems.length];
		for (int i = 0; i < subItems.length; i++){
			texture[i] = iconRegister.registerIcon(ModInfo.MODID + ":" + "Upgrade" + subItems[i]);
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
    	if (stack.getItemDamage() < subItems.length) {
        	return "item." + subItems[stack.getItemDamage()] + "Upgrade";
    	} else {
    		return "item." + subItems[0] + "Upgrade";
    	}
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < subItems.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
    	int damage = stack.getItemDamage();
    	String desc = subItems[damage];
    	list.add(StatCollector.translateToLocal("item.upgrade.desc." + desc));
    }
}
