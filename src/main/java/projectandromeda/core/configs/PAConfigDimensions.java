package projectandromeda.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;

public class PAConfigDimensions
{
    public static boolean loaded;

    static Configuration config;

    public PAConfigDimensions(File file)
    {
        if (!PAConfigDimensions.loaded)
        {
        	PAConfigDimensions.config = new Configuration(file);
        	PAConfigDimensions.syncConfig(true);
        }
    }
    
    // DIMENSIONS
    public static int dimensionIDArteros_E;
    
    //HOME
    //public static boolean enableArteros_E;
  

    
    
    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try
        {
            Property prop;

            if (!config.isChild)
            {
                if (load)
                {
                    config.load();
                }
            }

                                
            prop = config.get(Constants.CONFIG_CATEGORY_DIMENSIONS, "dimensionIDArteros_E", -100);
            prop.setComment("Dimension ID for Arteros_E");
            prop.setLanguageKey("gc.configgui.dimensionIDArteros_E").setRequiresMcRestart(true);
            dimensionIDArteros_E = prop.getInt();
            propOrder.add(prop.getName());
            
         
            // Space Stations -----------------------------------------------------------------
            
         
            //----------------------------------------------------------------------------------
            /*
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableArteros_E", true);
            prop.setComment("Enable/Disable Arteros_E");
            prop.setLanguageKey("gc.configgui.enableArteros_E").setRequiresMcRestart(true);
            enableArteros_E = prop.getBoolean(true);
            propOrder.add(prop.getName());*/
        
                        
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Planets) has a problem loading it's config");
        }
    }  

}
