package beyondspace.utils.space;

import micdoodle8.mods.galacticraft.api.galaxies.Moon;

public class ContainerMoon {
	
	public double minX, minZ, maxX, maxZ, posX, posZ;
	public Moon moon;
	
	public ContainerMoon(Moon moon, double px, double pz, double x, double z, double X, double Z) {
		System.out.print("Constructing Moon Container For " + moon.getLocalizedName() + '\n');
		this.minX = x;
		this.minZ = z;
		this.maxX = X;
		this.maxZ = Z;
		this.posX = px;
		this.posZ = pz;
		this.moon = moon;
	}
}
