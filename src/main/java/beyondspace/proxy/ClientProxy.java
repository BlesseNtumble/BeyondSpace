package beyondspace.proxy;

import org.lwjgl.input.Keyboard;

import beyondspace.Switches;
import beyondspace.blocks.SulfurTorch;
import beyondspace.blocks.render.BlockRendererSulfurTorch;
import beyondspace.blocks.render.HoloMapRender;
import beyondspace.blocks.render.LightningrodMidRender;
import beyondspace.blocks.render.LightningrodTopRender;
import beyondspace.blocks.render.NuclearReactorRender;
import beyondspace.blocks.render.SealableChameleonBlockRender;
import beyondspace.blocks.tileentity.HoloMapTileEntity;
import beyondspace.blocks.tileentity.LightningRodMidTileEntity;
import beyondspace.blocks.tileentity.LightningRodTopTileEntity;
import beyondspace.blocks.tileentity.NuclearReactorTileEntity;
import beyondspace.blocks.tileentity.SealableChameleonBlockTileEntity;
import beyondspace.entity.EntityFire;
import beyondspace.entity.FloaterEntity;
import beyondspace.entity.IonPlasmaBurstEntity;
import beyondspace.entity.RedLightningEntity;
import beyondspace.entity.render.FloaterEntityRender;
import beyondspace.entity.render.IonPlasmaBurstEntityRender;
import beyondspace.entity.render.RedLightningEntityRender;
import beyondspace.entity.render.RenderFire;
import beyondspace.items.render.NuclearRodEmptyRender;
import beyondspace.items.render.NuclearRodRender;
import beyondspace.items.render.StandartScaledTileItemRender;
import beyondspace.proxy.network.ClientEventHandler;
import beyondspace.proxy.network.ClientTickHandler;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import micdoodle8.mods.galacticraft.core.client.render.block.BlockRendererMachine;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	
	
	public static int blockOxygenModuleRenderID;
	public static int blockUltimateFurnaceRenderID;
	public static int blockWarpControleMonitorRenderID;
	public static int sulfurTorchRenderID;
	@Override
	public void registerRenderThings() {
		// Block
		ClientRegistry.bindTileEntitySpecialRenderer(HoloMapTileEntity.class, new HoloMapRender());
		if (Switches.parseObj) ClientRegistry.bindTileEntitySpecialRenderer(NuclearReactorTileEntity.class, new NuclearReactorRender());
		ClientRegistry.bindTileEntitySpecialRenderer(SealableChameleonBlockTileEntity.class, new SealableChameleonBlockRender());
		if (Switches.parseObj) ClientRegistry.bindTileEntitySpecialRenderer(LightningRodMidTileEntity.class, new LightningrodMidRender());
		if (Switches.parseObj) ClientRegistry.bindTileEntitySpecialRenderer(LightningRodTopTileEntity.class, new LightningrodTopRender());
		if (Switches.parseObj) MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegistrationsList.lightningrodMid), new StandartScaledTileItemRender(new LightningrodMidRender(), new LightningRodMidTileEntity()));
		if (Switches.parseObj) MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegistrationsList.lightningrodTop), new StandartScaledTileItemRender(new LightningrodTopRender(), new LightningRodTopTileEntity()));
		//if (Switches.parseObj) MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(RegistrationsList.nuclearReactor ), new StandartScaledTileItemRender(new NuclearReactorRender (), new NuclearReactorTileEntity ()));
		
		sulfurTorchRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockRendererSulfurTorch(sulfurTorchRenderID));
        blockOxygenModuleRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockRendererMachine(blockOxygenModuleRenderID));
        blockUltimateFurnaceRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockRendererMachine(blockUltimateFurnaceRenderID));
        blockWarpControleMonitorRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockRendererMachine(blockWarpControleMonitorRenderID));
		
		// Entity
        if (Switches.parseObj) RenderingRegistry.registerEntityRenderingHandler(IonPlasmaBurstEntity.class, new IonPlasmaBurstEntityRender());
		RenderingRegistry.registerEntityRenderingHandler(EntityFire.class, new RenderFire());
		
		// Mob
		RenderingRegistry.registerEntityRenderingHandler(FloaterEntity.class, new FloaterEntityRender());
		RenderingRegistry.registerEntityRenderingHandler(RedLightningEntity.class, new RedLightningEntityRender());
		
	//	MinecraftForgeClient.registerItemRenderer(RegistrationsList.superSpaceIonPlasmaRifle, new SuperSpaceIonPlasmaRifleRender());
	//	MinecraftForgeClient.registerItemRenderer(RegistrationsList.plasmaAmmo, new IonPlasmaAmmoRender());
		if (Switches.parseObj) MinecraftForgeClient.registerItemRenderer(RegistrationsList.nuclearRod, new NuclearRodRender());
		if (Switches.parseObj) MinecraftForgeClient.registerItemRenderer(RegistrationsList.nuclearRodEmpty, new NuclearRodEmptyRender());
		
	}
	
	
	
	public final static KeyBinding keyReload = new KeyBinding("key.reload.desc", Keyboard.KEY_R, "key.categories.gameplay");
    public final static KeyBinding keyRepair = new KeyBinding("key.repair.desc", Keyboard.KEY_K, "key.categories.gameplay");
    public final static KeyBinding keyHighjump = new KeyBinding("key.highjump.desc", Keyboard.KEY_H, "key.categories.gameplay");
    public final static KeyBinding keySpeed = new KeyBinding("key.speed.desc", Keyboard.KEY_P, "key.categories.gameplay");
    public final static KeyBinding keyInvis = new KeyBinding("key.invis.desc", Keyboard.KEY_I, "key.categories.gameplay");
    public final static KeyBinding keyNightvis = new KeyBinding("key.nightvis.desc", Keyboard.KEY_N, "key.categories.gameplay");
    public final static KeyBinding keySensor = new KeyBinding("key.sensor.desc", Keyboard.KEY_O, "key.categories.gameplay");
    
    @Override
	public int getBlockRender(Block id) {
		if (id instanceof SulfurTorch || id == RegistrationsList.sulfurTorch) {
			return this.sulfurTorchRenderID;
		}
		if (id == RegistrationsList.ultimateOxygenModule) {
			return this.blockOxygenModuleRenderID;
		}
		if (id == RegistrationsList.ultimateFurnace) {
			return this.blockUltimateFurnaceRenderID;
		}
		return -1;
	}
    
	public void RegisterKeyBinds() {
		ClientRegistry.registerKeyBinding(keyReload);
		ClientRegistry.registerKeyBinding(keyRepair);
		ClientRegistry.registerKeyBinding(keyHighjump);
		ClientRegistry.registerKeyBinding(keySpeed);
		ClientRegistry.registerKeyBinding(keyInvis);
		ClientRegistry.registerKeyBinding(keyNightvis);
		ClientRegistry.registerKeyBinding(keySensor);
	}
	
	public void initializeAndRegisterHandlers() {
		super.initializeAndRegisterHandlers();
		ClientEventHandler clientEventHandler = new ClientEventHandler();
		FMLCommonHandler.instance().bus().register(clientEventHandler);
        MinecraftForge.EVENT_BUS.register(clientEventHandler);
        FMLCommonHandler.instance().bus().register(new ClientTickHandler());
	}
}
