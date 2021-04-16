package beyondspace.proxy;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.event.EventReactions;
import beyondspace.event.MessageActions;
import beyondspace.proxy.network.CommonEventHandler;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public RegistrationsList regs;
	public static SimpleNetworkWrapper network;
	
	public CommonProxy() {
		regs = new RegistrationsList();
	}
	
	public void registerRenderThings() {}
	public void RegisterKeyBinds() {}
	
	public int getBlockRender(Block id){
        return -1;
    }
    
	public void preInit() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.MODID);
		network.registerMessage(MessageActions.Handler.class, MessageActions.class, 0, Side.SERVER);
		regs.construct();
    	regs.RegisterBlocks();
    	regs.RegisterFluids();
    	regs.RegisterItems();
    	regs.RegisterArmor();
    	regs.RegisterTileEntities();
    	regs.RegisterEntities();
    	regs.RegisterRecipes();
	}
	
    public void initializeAndRegisterHandlers() {
    	FMLCommonHandler.instance().bus().register(BeyondSpace.instance);
    	MinecraftForge.EVENT_BUS.register(new CommonEventHandler());
		FMLCommonHandler.instance().bus().register(new CommonEventHandler());
    }

	public void init() {
		regs.constructSpace();
		regs.RegisterPlanets();
    	regs.RegisterMoons();
    	GalacticraftCore.planetOverworld.setBodyIcon(new ResourceLocation("beyondspace:textures/gui/celestialbodies/earth.png"));
		FMLCommonHandler.instance().bus().register(new EventReactions());
	}
	
	public void postInit() {
		regs.RegisterRecipesPost();
	}
}
