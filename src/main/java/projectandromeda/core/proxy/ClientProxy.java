package projectandromeda.core.proxy;

import java.util.List;

import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.wrappers.ModelTransformWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import projectandromeda.ProjectAndromeda;
import projectandromeda.core.events.PAClientEventsHandler;
import projectandromeda.core.registers.blocks.PABlocks;
import projectandromeda.systems.ArterosSystem.arteros_e.blocks.ArterosEBlocks;

public class ClientProxy extends CommonProxy {

	public static Minecraft mc = FMLClientHandler.instance().getClient();
	
	@Override
    public void preload() {
		
		this.register_event(new PAClientEventsHandler());
		
		registerEntityRenderers();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
    public void load() {}
	
	@Override
    public void postload() {
		
	}
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelBakeEvent(ModelBakeEvent event) {
		
    }
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void loadTextures(TextureStitchEvent.Pre event) {
		
	}
	
	@Override
    public void registerRender()
    {	
		//BLOCKS META
    	for (ArterosEBlocks.EnumArterosEBlocks blockBasic : ArterosEBlocks.EnumArterosEBlocks.values())        
    		ClientUtil.registerBlockJson(ProjectAndromeda.TEXTURE_PREFIX, PABlocks.ARTEROS_E_BLOCKS, blockBasic.getMeta(), blockBasic.getName());
    	
    	/*if(GCCoreUtil.isDeobfuscated()) 
			GSUtils.addBlockMetadataJsonFiles(GSBlocks.DUNGEON_BLOCKS, name, DungeonBlocks.BASIC_TYPE.getName(), "");
    	*/
    	
    	//--------------------------- BLOCKS -----------------------------------
    	//ClientUtil.registerBlockJson(GalaxySpace.TEXTURE_PREFIX, GSBlocks.ASSEMBLER);	
    	
    	// -------------------------- ITEMS ------------------------------------
    /*	int i = 0;
    	for(String basic : ItemBasicGS.names)		
    		ClientUtil.registerItemJson(GalaxySpace.TEXTURE_PREFIX, GSItems.BASIC, i++, "basic/" + basic);
    			*/
    	
    	if(GCCoreUtil.isDeobfuscated()) {
			//GSUtils.addItemJsonFiles(GSItems.INGOTS, "ingots/", "uranium");		
			//GSUtils.addItemMetadataJsonFiles(GSItems.BASIC, ItemBasicGS.names, "basic/");
		}
    }
	
	@Override
    public void registerVariants()
    {
		addVariant(PABlocks.ARTEROS_E_BLOCKS.getRegistryName().getResourcePath(), "", "e0", "e1", "e2");
    }
	
	public static void registerEntityRenderers()
    {
		
    }
	
	/////////////////////////////////////////////SYSTEM////////////////////////////////////////////////////////////
	
	
	private static void addVariant(String name, String folder, String... variants)
    {
		Item itemBlockVariants = Item.REGISTRY.getObject(new ResourceLocation(ProjectAndromeda.MODID, name));
		ResourceLocation[] variants0 = new ResourceLocation[variants.length];
		for (int i = 0; i < variants.length; ++i) {
			variants0[i] = new ResourceLocation(ProjectAndromeda.MODID + ":" + folder + variants[i]);
		}
		ModelBakery.registerItemVariants(itemBlockVariants, variants0);        
    }
	
	public void registerTexture(Pre event, String texture) {
		event.getMap().registerSprite(new ResourceLocation(ProjectAndromeda.TEXTURE_PREFIX + texture));
	}
	
	private void replaceModelDefault(ModelBakeEvent event, String resLoc, String objLoc, List<String> visibleGroups, Class<? extends ModelTransformWrapper> clazz, IModelState parentState, String... variants)
    {
        ClientUtil.replaceModel(ProjectAndromeda.ASSET_PREFIX, event, resLoc, objLoc, visibleGroups, clazz, parentState, variants);
    }
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		ProjectAndromeda.proxy.registerVariants();
	}  
}
