package beyondspace.proxy.network;

import beyondspace.items.WeaponBase;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ClientTickHandler {
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		World world = player.worldObj;
		ItemStack is = player.getCurrentEquippedItem();
		if ((is != null) && ((is.getItem() instanceof WeaponBase)) && ((player != Minecraft.getMinecraft().renderViewEntity) || (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0))) {
			if (player.getItemInUseCount() <= 0) {
				player.clearItemInUse();
				player.setItemInUse(is, Integer.MAX_VALUE);
			}
		}
	}
	
	private boolean hasSynced = false;

	private void gameTick_Start() {
	}

	private void gameTick_End() {
	}

	private boolean checkForTKMove(ItemStack stack) {
		return false;
	}

	private void renderTick_Start() {
	}

	private void renderTick_End() {
	}

	private void localServerTick_End() {
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null) {
			} else {
				gameTick_Start();
			}
		} else if (event.phase == TickEvent.Phase.END) {
			GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
			if (guiscreen != null) {
			} else {
				gameTick_End();
			}
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			renderTick_Start();
		} else if (event.phase == TickEvent.Phase.END) {
			renderTick_End();
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
		}
	}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			localServerTick_End();
		}
	}

	public boolean getHasSynced() {
		return this.hasSynced;
	}
}
