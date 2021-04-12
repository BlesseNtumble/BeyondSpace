package beyondspace.event;

import beyondspace.items.WeaponBase;
import beyondspace.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** ActionKeys */
public class PacketSelector {
	// --------------------------------------------------------------------------------------------------------------
	public static void actionLeftClickTickClient(EntityPlayer player, int tick) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.FIRE.ordinal(), true, tick));
		}
	}
	
	public static void actionLeftClickClient(EntityPlayer player, boolean buttonstate) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.FIRE.ordinal(), buttonstate, 0));
		}
	}

	// --------------------------------------------------------------------------------------------------------------
	public static void actionShiftTickClient(EntityPlayer player, int tick) {
		CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.SNEAKTICK.ordinal(), false, tick));
	}
	
	public static void actionShiftClient(EntityPlayer player, boolean buttonstate) {
		CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.SNEAK.ordinal(), buttonstate, 0));
	}
	
	// --------------------------------------------------------------------------------------------------------------
	public static void actionReloadClient(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.RELOAD.ordinal(), true, 0));
		}
	}
	
	// --------------------------------------------------------------------------------------------------------------
	public static void actionRepairClient(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			CommonProxy.network.sendToServer(new MessageActions((byte) MessageActions.MessageIDs.REPAIR.ordinal(), true, 0));
		}
	}

	public static void actionSwitchClient(EntityPlayer player, int ID) {
		CommonProxy.network.sendToServer(new MessageActions((byte) ID, true, 0));
	}
}