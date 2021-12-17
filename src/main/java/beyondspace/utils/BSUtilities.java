package beyondspace.utils;

import java.lang.reflect.Method;

import cofh.api.energy.IEnergyContainerItem;
import mekanism.api.energy.EnergizedItemManager;
import mekanism.api.energy.IEnergizedItem;
import micdoodle8.mods.galacticraft.api.item.ElectricItemHelper;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.util.VersionUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BSUtilities {
	
	public static boolean hasBukkit() {
		return isClassExists("org.bukkit.Bukkit");
	}
	
	private static boolean isClassExists(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException event) {
			return false;
		}
	}
	
	/** Converts gravity from m/s^2 to galacticraft gravity units
	 * @param Si gravity acceleration (ex: Earth 9.8)
	 * @return GGU equivalent */
	public static float calculateGravity(float Si) {
		return ((9.81F - Si) * (0.085F / 9.81F));
	}
	
	/** Universal charging method to charge one stack using power from another
	 * @param target ItemStack to charge
	 * @param amount Amount of electricity to transfer
	 * @param bat ItemStack to take energy from
	 * @param simulate If true just returns possible amount of energy transfered
	 * @return Either transfered energy or simulated amount */
	public static float rechargeItemWithItem(ItemStack target, float amount, ItemStack bat, boolean simulate) {
        if (target != null) {
            Item item = target.getItem();
            float maxExtractSave = 200;
            float energyToCharge = Math.min(((ItemElectricBase)bat.getItem()).getEnergyStored(bat), Math.min(maxExtractSave, amount));

            if (item instanceof IItemElectric) {
            	return ((IItemElectric)bat.getItem()).discharge(bat, ElectricItemHelper.chargeItem(target, energyToCharge), !simulate);
            }
            
            else if (EnergyConfigHandler.isRFAPILoaded() && item instanceof IEnergyContainerItem) {
            	return ((IItemElectric)bat.getItem()).discharge(bat, ((IEnergyContainerItem)item).receiveEnergy(target, (int) (energyToCharge * EnergyConfigHandler.TO_RF_RATIO), false) / EnergyConfigHandler.TO_RF_RATIO, !simulate);
            }
            else if (EnergyConfigHandler.isMekanismLoaded() && item instanceof IEnergizedItem && ((IEnergizedItem) item).canReceive(target)) {
            	return ((IItemElectric)bat.getItem()).discharge(bat, (float) EnergizedItemManager.charge(target, energyToCharge * EnergyConfigHandler.TO_MEKANISM_RATIO) / EnergyConfigHandler.TO_MEKANISM_RATIO, !simulate);
            }
            
            else if (EnergyConfigHandler.isIndustrialCraft2Loaded()) {
                try {
                    Class<?> itemElectricIC2 = Class.forName("ic2.api.item.ISpecialElectricItem");
                    Class<?> itemElectricIC2B = Class.forName("ic2.api.item.IElectricItem");
                    Class<?> itemManagerIC2 = Class.forName("ic2.api.item.IElectricItemManager");
                    if (itemElectricIC2.isInstance(item)) {
                        //Implement by reflection:
                        //float energy = (float) ((ISpecialElectricItem)item).getManager(itemStack).charge(itemStack, energyToCharge * EnergyConfigHandler.TO_IC2_RATIO, 4, false, false) * EnergyConfigHandler.IC2_RATIO;
                        Object IC2item = itemElectricIC2.cast(item);
                        Method getMan = itemElectricIC2.getMethod("getManager", ItemStack.class);
                        Object IC2manager = getMan.invoke(IC2item, target);
                        double result;
                        if (VersionUtil.mcVersion1_7_2) {
                            Method methodCharge = itemManagerIC2.getMethod("charge", ItemStack.class, int.class, int.class, boolean.class, boolean.class);
                            result = (Integer) methodCharge.invoke(IC2manager, target, (int) (energyToCharge * EnergyConfigHandler.TO_IC2_RATIO), 2, false, false);
                        } else {
                            Method methodCharge = itemManagerIC2.getMethod("charge", ItemStack.class, double.class, int.class, boolean.class, boolean.class);
                            result = (Double) methodCharge.invoke(IC2manager, target, (double) (energyToCharge * EnergyConfigHandler.TO_IC2_RATIO), 2, false, false);
                        }
                        float energy = (float) result / EnergyConfigHandler.TO_IC2_RATIO;
                        return ((IItemElectric)bat.getItem()).discharge(bat, energy, !simulate);
                    }
                    else if (itemElectricIC2B.isInstance(item))
                    {
                        Class<?> electricItemIC2 = Class.forName("ic2.api.item.ElectricItem");
                        Object IC2manager = electricItemIC2.getField("manager").get(null);
                        double result;
                        if (VersionUtil.mcVersion1_7_2)
                        {
                            Method methodCharge = itemManagerIC2.getMethod("charge", ItemStack.class, int.class, int.class, boolean.class, boolean.class);
                            result = (Integer) methodCharge.invoke(IC2manager, target, (int) (energyToCharge * EnergyConfigHandler.TO_IC2_RATIO), 2, false, false);
                        }
                        else
                        {
                            Method methodCharge = itemManagerIC2.getMethod("charge", ItemStack.class, double.class, int.class, boolean.class, boolean.class);
                            result = (Double) methodCharge.invoke(IC2manager, target, (double) (energyToCharge * EnergyConfigHandler.TO_IC2_RATIO), 2, false, false);
                        }
                        float energy = (float) result / EnergyConfigHandler.TO_IC2_RATIO;
                        return ((IItemElectric)bat.getItem()).discharge(bat, energy, !simulate);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
		return 0;
    }
}