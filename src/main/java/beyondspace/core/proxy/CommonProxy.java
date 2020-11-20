package beyondspace.core.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preload(FMLPreInitializationEvent event) {}
	public void load(FMLInitializationEvent event) {}
	public void postload(FMLPostInitializationEvent event) {}
	public void registerItemRenderer(Item item, int meta, String id) {}
	public void registerVariants() {}
	public void registerRender() {}
	public void registerTexture(Pre event, String texture) {}
	public void register_event(Object obj)
	{
    	MinecraftForge.EVENT_BUS.register(obj);
	}
}
