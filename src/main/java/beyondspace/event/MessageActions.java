package beyondspace.event;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageActions implements IMessage {

	public static enum MessageIDs {
		FIRE,
		FORWARD,
		BACKWARD,
		LEFT,
		RIGHT,
		RELOAD,
		REPAIR,
		SNEAK,
		SNEAKTICK,
		HIGHTJUMP,
		SPEED,
		INVIS,
		NIGHTVIS,
		SENSOR;
	};
	
	public byte action;
	public int ticks;
	public boolean state;

	public MessageActions() { }

	public MessageActions(byte action, boolean state, int ticks) {
		this.action = action;
		this.state = state;
		this.ticks = ticks;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(action);
		buf.writeBoolean(state);
		buf.writeInt(ticks);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		action = buf.readByte();
		state = buf.readBoolean();
		ticks = buf.readInt();
	}
	
	// ----------------------------------------------------------------------------------
	public static class Handler implements IMessageHandler<MessageActions, IMessage> {

		@Override
		public IMessage onMessage(MessageActions packet, MessageContext message) {
			EntityPlayerMP player = message.getServerHandler().playerEntity;

			if (packet.action == MessageIDs.FIRE	 .ordinal())	PacketAcceptor.onFire(player);
			if (packet.action == MessageIDs.RELOAD	 .ordinal())	PacketAcceptor.onReload(player);
			if (packet.action == MessageIDs.REPAIR	 .ordinal())	PacketAcceptor.onRepair(player);
			if (packet.action == MessageIDs.SNEAK	 .ordinal())	PacketAcceptor.onSneak(player, packet.state);
			if (packet.action == MessageIDs.SNEAKTICK.ordinal())	PacketAcceptor.onSneakTick(player, packet.ticks);
			
			if (packet.action == MessageIDs.HIGHTJUMP.ordinal())	PacketAcceptor.onSwitch(player, MessageIDs.HIGHTJUMP.ordinal());
			if (packet.action == MessageIDs.SPEED	 .ordinal())	PacketAcceptor.onSwitch(player, MessageIDs.SPEED	.ordinal());
			if (packet.action == MessageIDs.INVIS	 .ordinal())	PacketAcceptor.onSwitch(player, MessageIDs.INVIS	.ordinal());
			if (packet.action == MessageIDs.NIGHTVIS .ordinal())	PacketAcceptor.onSwitch(player, MessageIDs.NIGHTVIS	.ordinal());
			if (packet.action == MessageIDs.SENSOR	 .ordinal())	PacketAcceptor.onSwitch(player, MessageIDs.SENSOR	.ordinal());
			
			
			return null;
		}

	}
	// ----------------------------------------------------------------------------------
}