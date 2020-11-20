package beyondspace.systems.SolarSystem.moons.saturnRings.dimensions;

import micdoodle8.mods.galacticraft.core.Constants;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class SaturnRingsWorldData extends WorldSavedData {
	public static final String saveDataID = Constants.GCDATAFOLDER + "SaturnRingsData";
	public NBTTagCompound datacompound;

	public SaturnRingsWorldData(String s) {
		super(saveDataID);
		this.datacompound = new NBTTagCompound();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.datacompound = nbt.getCompoundTag("saturnrings");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("saturnrings", this.datacompound);
		return nbt;
	}
}