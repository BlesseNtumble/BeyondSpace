package beyondspace.items.modules;

import beyondspace.utils.RegistrationsList;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import net.minecraft.item.ItemStack;

public class Protection extends ItemModule {

	@Override
	public String getName() {
		return "protection";
	}

	@Override
	public ItemStack getIcon() {
		
		return new ItemStack(RegistrationsList.armorUpgrade, 1, 1);
	}

	@Override
	public int getEquipmentSlot() {
		return -1;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { getIcon() };
	}
	
	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] {};
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}

	@Override
	public int getDischargeCount() { return 0; }
}
