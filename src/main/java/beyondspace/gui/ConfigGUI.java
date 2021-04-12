package beyondspace.gui;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class ConfigGUI extends GuiConfig {
	
	public ConfigGUI(GuiScreen screen) {
		super(screen, new ConfigElement(BeyondSpace.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), ModInfo.MODID, false, false, ModInfo.NAME + " config");
	}
}
