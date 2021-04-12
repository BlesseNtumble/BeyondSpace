package beyondspace.items.modules;

import beyondspace.utils.RegistrationsList;
import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolarPanel extends ItemModule {

	@Override
	public String getName() {
		return "solarpanel";
	}

	@Override
	public ItemStack getIcon() {
		
		return new ItemStack(RegistrationsList.armorUpgrade, 1, 0);
	}

	@Override
	public int getEquipmentSlot() {
		return 0;
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
	
	@Override
	public void onUpdate(World world, EntityPlayer player, ItemStack itemStack, boolean enable) 
	{
		if(!world.isRemote && world.provider.isDaytime() && !world.isRaining()) {
			for(int i = 0; i < 4; i++)
			{
				if(player.inventory.armorItemInSlot(i) == null) continue;
				
				if(player.inventory.armorItemInSlot(i).getItem() instanceof IItemElectric)
				{
					ItemStack stack = player.inventory.armorItemInSlot(i);
					IItemElectric armor = (IItemElectric)stack.getItem();
					if(armor.getElectricityStored(stack) >= armor.getMaxElectricityStored(stack)) continue;

					if(player.ticksExisted % 2 == 0)
						armor.recharge(stack, 10F, true);
					break;
				}
			}			
		}
	}

}
