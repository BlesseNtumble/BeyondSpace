package projectandromeda;

import java.io.File;

import org.apache.logging.log4j.Level;

import asmodeuscore.api.IBodies;
import asmodeuscore.api.IBodiesHandler;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.CreativeTabGC;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import projectandromeda.core.configs.PAConfigCore;
import projectandromeda.core.configs.PAConfigDimensions;
import projectandromeda.core.events.PAEventsHandler;
import projectandromeda.core.proxy.CommonProxy;
import projectandromeda.core.registers.blocks.PABlocks;
import projectandromeda.core.utils.PACreativeTabs;

@Mod(
		   modid = ProjectAndromeda.MODID,
		   version = ProjectAndromeda.VERSION,
		   dependencies = Constants.DEPENDENCIES_FORGE + "required-after:galacticraftcore; required-after:galacticraftplanets; required-after:asmodeuscore@[0.0.8,); after:galaxyspace@[2.0.1,);",
		   acceptedMinecraftVersions = Constants.MCVERSION,
		   name = ProjectAndromeda.NAME
		)

public class ProjectAndromeda {

	public static final int major_version = 0;
	public static final int minor_version = 0;
	public static final int build_version = 1;
	
	public static final String NAME = "ProjectAndromeda";
	public static final String MODID = "projectandromeda";
    public static final String VERSION = major_version + "." + minor_version + "." + build_version;
    public static final String ASSET_PREFIX = MODID;
    public static final String TEXTURE_PREFIX = ASSET_PREFIX + ":";

    public static boolean debug;
    
    //---------------------------------------------
    
    @Instance(ProjectAndromeda.MODID)
    public static ProjectAndromeda instance;
    
    @SidedProxy(clientSide="projectandromeda.core.proxy.ClientProxy", serverSide="projectandromeda.core.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {      	
    	new PAConfigCore(new File(event.getModConfigurationDirectory(), "ProjectAndromeda/core.conf"));
    	new PAConfigDimensions(new File(event.getModConfigurationDirectory(), "ProjectAndromeda/dimensions.conf"));
    	
    	PABlocks.initialize();
    	
    	proxy.preload();
    	proxy.register_event(new PAEventsHandler());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {  
    	proxy.load(); 
    	proxy.registerRender();

    	PACreativeTabs.PABlocksTab = new CreativeTabGC(CreativeTabs.getNextID(), "andromeda_blocks", new ItemStack(PABlocks.ARTEROS_E_BLOCKS), null);
    	    
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postload();

    	//NetworkRegistry.INSTANCE.registerGuiHandler(instance, new PAGuiHandler());
    
    }
    
    public static void info(String message)
	{ 
		FMLRelaunchLog.log("P:Andromeda", Level.INFO, message);
	}
    
    public static void debug(String message)
   	{ 
   		if(debug) FMLRelaunchLog.log("[DEBUG] P:Andromeda", Level.INFO, message);
   	}  
    
    @EventBusSubscriber(modid = MODID)
    public static class RegistrationHandler
    {
    	@SubscribeEvent
    	public static void registerModels(ModelRegistryEvent event) {
    		proxy.registerVariants();

    	}    
    	
    	@SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
        {
            /*if (GCCoreUtil.getEffectiveSide() == Side.CLIENT)
            {
                GSSounds.registerSounds(event.getRegistry());
            }*/
        }
    	
    	@SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
    		//GSBlocks.oreDictRegistration();
    		//GSItems.oreDictRegistration();
        }
    }

}
