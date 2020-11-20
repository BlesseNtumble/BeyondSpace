package beyondspace.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.FMLLog;

public class BSConfigCore {
    public static boolean loaded;

    static Configuration config;

    public BSConfigCore(File file)
    {
        if (!loaded)
        {
        	config = new Configuration(file);
        	syncConfig(true);
        }
    }
    
   // public static boolean enableACentauriSystems;
 
    
    public static void syncConfig(boolean load)
    {
        List<String> propOrder = new ArrayList<String>();

        try
        {
            Property prop = null;

            if (!config.isChild)
            {
                if (load)
                {
                    config.load();
                }
            }
            /*
            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableACentauriSystems", true);
            prop.setComment("Enable/Disable Alpha Centauri and Proxima Systems.");
            prop.setLanguageKey("gc.configgui.enableACentauriSystems").setRequiresMcRestart(true);
            enableACentauriSystems = prop.getBoolean(true);
            propOrder.add(prop.getName());
            */
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxyAdditions has a problem loading it's config");
        }
    }
    
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_GENERAL)).getChildElements());
        list.addAll(new ConfigElement(config.getCategory(Constants.CONFIG_CATEGORY_DIMENSIONS)).getChildElements());
        return list;
    }
}
