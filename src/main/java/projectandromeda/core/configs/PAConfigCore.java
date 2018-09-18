package projectandromeda.core.configs;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;

import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.util.GCLog;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;

public class PAConfigCore
{
    public static boolean loaded;

    static Configuration config;

    public PAConfigCore(File file)
    {
        if (!PAConfigCore.loaded)
        {
        	PAConfigCore.config = new Configuration(file);
        	PAConfigCore.syncConfig(true);
        }
    }

    public static boolean enableCheckVersion;
   
    public static boolean enableDebug;
    
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

            prop = config.get(Constants.CONFIG_CATEGORY_GENERAL, "enableCheckVersion", true);
            prop.setComment("Enable/Disable Check Version.");
            prop.setLanguageKey("gc.configgui.enableCheckVersion").setRequiresMcRestart(true);;
            enableCheckVersion = prop.getBoolean(true);
            propOrder.add(prop.getName()); 
         
            prop = config.get("development", "enableDebug", false);
            prop.setComment("Enable/Disable Debug mode");
            prop.setLanguageKey("gc.configgui.enableDebug").setRequiresMcRestart(true);
            enableDebug = prop.getBoolean(false);
            propOrder.add(prop.getName());
            
            config.setCategoryPropertyOrder(CATEGORY_GENERAL, propOrder);

            if (config.hasChanged())
            {
                config.save();
            }
        }
        catch (final Exception e)
        {
            FMLLog.log(Level.ERROR, e, "GalaxySpace (Core) has a problem loading it's config");
        }
    }
    
    private static int parseKeyValue(String key)
    {
    	if (key.equals("KEY_A"))
    	{
    		return Keyboard.KEY_A;
    	}
    	else if (key.equals("KEY_B"))
    	{
    		return Keyboard.KEY_B;
    	}
    	else if (key.equals("KEY_C"))
    	{
    		return Keyboard.KEY_C;
    	}
    	else if (key.equals("KEY_D"))
    	{
    		return Keyboard.KEY_D;
    	}
    	else if (key.equals("KEY_E"))
    	{
    		return Keyboard.KEY_E;
    	}
    	else if (key.equals("KEY_F"))
    	{
    		return Keyboard.KEY_F;
    	}
    	else if (key.equals("KEY_G"))
    	{
    		return Keyboard.KEY_G;
    	}
    	else if (key.equals("KEY_H"))
    	{
    		return Keyboard.KEY_H;
    	}
    	else if (key.equals("KEY_I"))
    	{
    		return Keyboard.KEY_I;
    	}
    	else if (key.equals("KEY_J"))
    	{
    		return Keyboard.KEY_J;
    	}
    	else if (key.equals("KEY_K"))
    	{
    		return Keyboard.KEY_K;
    	}
    	else if (key.equals("KEY_L"))
    	{
    		return Keyboard.KEY_L;
    	}
    	else if (key.equals("KEY_M"))
    	{
    		return Keyboard.KEY_M;
    	}
    	else if (key.equals("KEY_N"))
    	{
    		return Keyboard.KEY_N;
    	}
    	else if (key.equals("KEY_O"))
    	{
    		return Keyboard.KEY_O;
    	}
    	else if (key.equals("KEY_P"))
    	{
    		return Keyboard.KEY_P;
    	}
    	else if (key.equals("KEY_Q"))
    	{
    		return Keyboard.KEY_Q;
    	}
    	else if (key.equals("KEY_R"))
    	{
    		return Keyboard.KEY_R;
    	}
    	else if (key.equals("KEY_S"))
    	{
    		return Keyboard.KEY_S;
    	}
    	else if (key.equals("KEY_T"))
    	{
    		return Keyboard.KEY_T;
    	}
    	else if (key.equals("KEY_U"))
    	{
    		return Keyboard.KEY_U;
    	}
    	else if (key.equals("KEY_V"))
    	{
    		return Keyboard.KEY_V;
    	}
    	else if (key.equals("KEY_W"))
    	{
    		return Keyboard.KEY_W;
    	}
    	else if (key.equals("KEY_X"))
    	{
    		return Keyboard.KEY_X;
    	}
    	else if (key.equals("KEY_Y"))
    	{
    		return Keyboard.KEY_Y;
    	}
    	else if (key.equals("KEY_Z"))
    	{
    		return Keyboard.KEY_Z;
    	}
    	else if (key.equals("KEY_1"))
    	{
    		return Keyboard.KEY_1;
    	}
    	else if (key.equals("KEY_2"))
    	{
    		return Keyboard.KEY_2;
    	}
    	else if (key.equals("KEY_3"))
    	{
    		return Keyboard.KEY_3;
    	}
    	else if (key.equals("KEY_4"))
    	{
    		return Keyboard.KEY_4;
    	}
    	else if (key.equals("KEY_5"))
    	{
    		return Keyboard.KEY_5;
    	}
    	else if (key.equals("KEY_6"))
    	{
    		return Keyboard.KEY_6;
    	}
    	else if (key.equals("KEY_7"))
    	{
    		return Keyboard.KEY_7;
    	}
    	else if (key.equals("KEY_8"))
    	{
    		return Keyboard.KEY_8;
    	}
    	else if (key.equals("KEY_9"))
    	{
    		return Keyboard.KEY_9;
    	}
    	else if (key.equals("KEY_0"))
    	{
    		return Keyboard.KEY_0;
    	}
    	
    	GCLog.severe("Failed to parse keyboard key: " + key + "... Use values A-Z or 0-9" );
    	
    	return 0;
    }
}
