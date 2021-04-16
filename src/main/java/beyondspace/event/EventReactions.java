package beyondspace.event;

import beyondspace.proxy.ClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;

public class EventReactions {

	boolean leftclick, leftclick_last = false;
	int tick_leftclick = 1;

	boolean shift, shift_last = false;
	int tick_shift = 1;

	boolean reload, repair, highjump, speed, invis, nightvis, sensor;
	
	@SubscribeEvent
	public void actionBinds(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.side == Side.CLIENT) {
			// Attack
			if (Minecraft.getMinecraft().gameSettings.keyBindAttack.getIsKeyPressed()) {
				PacketSelector.actionLeftClickTickClient(event.player, tick_leftclick);
				tick_leftclick += 1;
				leftclick = true;
				if (!leftclick_last && leftclick) PacketSelector.actionLeftClickClient(event.player, leftclick); // press down
				leftclick_last = leftclick;
			} else {
				tick_leftclick = 1;
				leftclick = false;
				leftclick_last = leftclick;
			}
			
			// Sneak
			if (Minecraft.getMinecraft().gameSettings.keyBindSneak.getIsKeyPressed()) {
				PacketSelector.actionShiftTickClient(event.player, tick_shift);
				tick_shift += 1;
				shift = true;
				if (!shift_last && shift) PacketSelector.actionShiftClient(event.player, shift); // press down
				shift_last = shift;
			} else {
				tick_shift = 1;
				shift = false;
				if (shift_last && !shift) PacketSelector.actionShiftClient(event.player, shift); // unpress down
				shift_last = shift;
			}
			
			// Reload
			if (ClientProxy.keyReload.isPressed() && !reload) {
				reload = true;
				PacketSelector.actionReloadClient(event.player);
			} else if (reload) {
				reload = false;
			}
			
			// Repair
			if (ClientProxy.keyRepair.isPressed() && !repair) {
				repair = true;
				PacketSelector.actionRepairClient(event.player);
			} else if (repair) {
				repair = false;
			}
			
			// Highjump
			if (ClientProxy.keyHighjump.isPressed() && !highjump) {
				highjump = true;
				PacketSelector.actionSwitchClient(event.player, MessageActions.MessageIDs.HIGHTJUMP.ordinal());
			} else if (highjump) {
				highjump = false;
			}
			
			// Speed
			if (ClientProxy.keySpeed.isPressed() && !speed) {
				speed = true;
				PacketSelector.actionSwitchClient(event.player, MessageActions.MessageIDs.SPEED.ordinal());
			} else if (speed) {
				speed = false;
			}
			
			// Invis
			if (ClientProxy.keyInvis.isPressed() && !invis) {
				invis = true;
				PacketSelector.actionSwitchClient(event.player, MessageActions.MessageIDs.INVIS.ordinal());
			} else if (invis) {
				invis = false;
			}
			
			// Nightvis
			if (ClientProxy.keyNightvis.isPressed() && !nightvis) {
				nightvis = true;
				PacketSelector.actionSwitchClient(event.player, MessageActions.MessageIDs.NIGHTVIS.ordinal());
			} else if (nightvis) {
				nightvis = false;
			}
			
			// Sensor
			if (ClientProxy.keySensor.isPressed() && !sensor) {
				sensor = true;
				PacketSelector.actionSwitchClient(event.player, MessageActions.MessageIDs.SENSOR.ordinal());
			} else if (sensor) {
				sensor = false;
			}
		}
	}
}
