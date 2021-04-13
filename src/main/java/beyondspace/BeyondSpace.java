package beyondspace;

import beyondspace.proxy.CommonProxy;
import beyondspace.proxy.network.GAGuiHandler;
import beyondspace.utils.BSConfig;
import beyondspace.utils.RegistrationsList;
import beyondspace.utils.space.GASpaceUtilities;
import beyondspace.world.generation.GAWorldGeneration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

@Mod(modid=ModInfo.MODID, name=ModInfo.NAME, version=ModInfo.VERSION, guiFactory=ModInfo.MODID + ".gui.GUIFactory", dependencies="required-after:GalacticraftCore; required-after:GalacticraftMars; required-after:GalaxySpace;", useMetadata = true)
public class BeyondSpace {
	@Instance(ModInfo.MODID)
	public static BeyondSpace instance;

    @SidedProxy(clientSide = ModInfo.MODID + ".proxy.ClientProxy", serverSide = ModInfo.MODID + ".proxy.CommonProxy")
    public static CommonProxy proxy;
	
	public GAWorldGeneration generator = new GAWorldGeneration();
	public static Configuration config;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	FMLCommonHandler.instance().bus().register(BeyondSpace.instance);
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	BSConfig.syncConfig();
        GameRegistry.registerWorldGenerator(generator, 0);
    	proxy.initializeAndRegisterHandlers();
    	proxy.preInit();
    }
    
	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(ModInfo.MODID)) {
			BSConfig.syncConfig();
		}
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GAGuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    	proxy.registerRenderThings();
    	proxy.RegisterKeyBinds();
		GASpaceUtilities.init();
    }
    
    public static CreativeTabs gaTab = new CreativeTabs(ModInfo.MODID) {
		@Override
		public Item getTabIconItem() {
			return RegistrationsList.handRocket;
		}
	};
    
    public static CreativeTabs upTab = new CreativeTabs(ModInfo.MODID + ".upgrades") {
		@Override
		public Item getTabIconItem() {
			return RegistrationsList.armorUpgrade;
		}
	};
}
