package beyondspace.event;

import beyondspace.items.HighPressureResistantModularArmor;
import beyondspace.items.WeaponBase;
import beyondspace.utils.RegistrationsList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

/** ActionsList */
public class PacketAcceptor {

	public static void onSneak(EntityPlayer player, boolean state) {
		if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shockwave)) {
			player.getCurrentArmor(0).stackTagCompound.setBoolean(HighPressureResistantModularArmor.shockactive, state);
			player.getCurrentArmor(0).stackTagCompound.setInteger(HighPressureResistantModularArmor.shocktick, 0);
		}
	}

	public static void onSneakTick(EntityPlayer player, int ticks) {
		if (player.worldObj.isAirBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ))) {
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shockwave) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shockactive)) {
				player.getCurrentArmor(0).stackTagCompound.setInteger(HighPressureResistantModularArmor.shocktick, ticks);
				player.motionY -= 5;
			}
		}
	}
	
	public static void onFire(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			((WeaponBase) stack.getItem()).fire(stack, player.worldObj, player);
		}
	}
	
	public static void onReload(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			((WeaponBase) stack.getItem()).reload(player, stack);
		}
	}
	
	public static void onRepair(EntityPlayer player) {
		ItemStack stack = player.getCurrentEquippedItem();
		if (stack != null && stack.getItem() instanceof WeaponBase) {
			((WeaponBase) stack.getItem()).repair(player, stack);
		}
	}

	public static void onSwitch(EntityPlayerMP player, int ID) {
		ItemStack boot = player.getCurrentArmor(0);
		ItemStack legs = player.getCurrentArmor(1);
		ItemStack body = player.getCurrentArmor(2);
		ItemStack helm = player.getCurrentArmor(3);
		
		if (ID == MessageActions.MessageIDs.HIGHTJUMP.ordinal()	&& boot != null && boot.getItem() instanceof HighPressureResistantModularArmor && boot.stackTagCompound.getBoolean(HighPressureResistantModularArmor.highjump)) {
			boot.stackTagCompound.setBoolean(HighPressureResistantModularArmor.shighjump, !boot.stackTagCompound.getBoolean(HighPressureResistantModularArmor.shighjump	));
		}
		
		if (ID == MessageActions.MessageIDs.SPEED.ordinal()		&& legs != null && legs.getItem() instanceof HighPressureResistantModularArmor && legs.stackTagCompound.getBoolean(HighPressureResistantModularArmor.speed	 )) {
			legs.stackTagCompound.setBoolean(HighPressureResistantModularArmor.sspeed	, !legs.stackTagCompound.getBoolean(HighPressureResistantModularArmor.sspeed	));
		}
		
		if (ID == MessageActions.MessageIDs.INVIS.ordinal()		&& body != null && body.getItem() instanceof HighPressureResistantModularArmor && body.stackTagCompound.getBoolean(HighPressureResistantModularArmor.invis	 )) {
			body.stackTagCompound.setBoolean(HighPressureResistantModularArmor.sinvis	, !body.stackTagCompound.getBoolean(HighPressureResistantModularArmor.sinvis	));
		}
		
		if (ID == MessageActions.MessageIDs.NIGHTVIS.ordinal()	&& helm != null && helm.getItem() instanceof HighPressureResistantModularArmor && helm.stackTagCompound.getBoolean(HighPressureResistantModularArmor.nightvis)) {
			helm.stackTagCompound.setBoolean(HighPressureResistantModularArmor.snightvis, !helm.stackTagCompound.getBoolean(HighPressureResistantModularArmor.snightvis	));
		}
		
		if (ID == MessageActions.MessageIDs.SENSOR.ordinal()	&& helm != null && helm.getItem() instanceof HighPressureResistantModularArmor && helm.stackTagCompound.getBoolean(HighPressureResistantModularArmor.sensor	 )) {
			helm.stackTagCompound.setBoolean(HighPressureResistantModularArmor.ssensor	, !helm.stackTagCompound.getBoolean(HighPressureResistantModularArmor.ssensor	));
		}
	}
}