package beyondspace.items;

import beyondspace.ModInfo;
import beyondspace.entity.EntityFire;
import beyondspace.utils.RegistrationsList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class FlameThrower extends WeaponBase {

	public FlameThrower() {
		super(250, 16000, false, (AmmoBase) RegistrationsList.fuelTank, "FlameThrower", 60, 5);
		this.setTextureName(ModInfo.MODID + ":" + "flameThrower");
	}

	@Override
	public void spawnProjectile(World world, EntityPlayer player) {
		//if(!world.isRemote){
		world.spawnEntityInWorld(new EntityFire(world, player));
		//}
	}
}