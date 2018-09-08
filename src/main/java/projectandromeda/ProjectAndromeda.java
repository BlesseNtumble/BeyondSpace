package projectandromeda;

import org.apache.logging.log4j.Level;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLRelaunchLog;
import projectandromeda.core.events.PAEventsHandler;
import projectandromeda.core.proxy.CommonProxy;
import projectandromeda.systems.ArterosSystem.ArterosBodies;

@Mod(
		   modid = ProjectAndromeda.MODID,
		   version = ProjectAndromeda.VERSION,
		   dependencies = Constants.DEPENDENCIES_FORGE + "required-after:galacticraftcore; required-after:galacticraftplanets; required-after:asmodeuscore@[0.0.1,); after:galaxyspace;",
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
    	proxy.preload();
    	proxy.register_event(new PAEventsHandler());
    	ArterosBodies.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {  
    	proxy.load(); 
    	proxy.registerRender();
    	
    	ArterosBodies.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postload();
    }
    
    public static void info(String message)
	{ 
		FMLRelaunchLog.log("Galaxy Space", Level.INFO, message);
	}
    
    public static void debug(String message)
   	{ 
   		if(debug) FMLRelaunchLog.log("[DEBUG] Galaxy Space", Level.INFO, message);
   	}  
}
