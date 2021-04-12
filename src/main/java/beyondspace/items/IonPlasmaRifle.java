package beyondspace.items;

import beyondspace.ModInfo;
import beyondspace.asjlib.ASJUtilities;
import beyondspace.entity.IonPlasmaBurstEntity;
import beyondspace.utils.RegistrationsList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class IonPlasmaRifle extends WeaponBase {

	public IonPlasmaRifle() {
		super(8, 512, false, (AmmoBase) RegistrationsList.plasmaAmmo, "IonPlasmaRifle", 40, 80);
		this.setTextureName(ModInfo.MODID + ":" + "plasmaRifle");
	}

	@Override
	public void spawnProjectile(World world, EntityPlayer player) {
		MovingObjectPosition mop = ASJUtilities.getSelectedBlock(player, 1.0F, 256.0D, true);
		if (mop != null) {
			world.spawnEntityInWorld(new IonPlasmaBurstEntity(world, mop.blockX, mop.blockY, mop.blockZ, player));
		}
	}	
}
