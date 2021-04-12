package beyondspace.utils.space;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;

public class ContainerPlanet {
	
	public double minX, minZ, maxX, maxZ, posX, posZ;
	public Planet planet;
	public Vector<ContainerMoon> moons;
	
	public ContainerPlanet(Planet planet, double px, double pz, double x, double z, double X, double Z) {
		System.out.print("Constructing Planet Container For " + planet.getLocalizedName() + '\n');
		this.minX = x;
		this.minZ = z;
		this.maxX = X;
		this.maxZ = Z;
		this.posX = px;
		this.posZ = pz;
		this.planet = planet;
		moons = new Vector<ContainerMoon>();
		this.addMoons();
	}
	
	private void addMoons() {
		System.out.print("Trying to add moons to container for " + planet.getLocalizedName() + '\n');
		List list = GalaxyRegistry.getMoonsForPlanet(planet);
		if (list.isEmpty()) {
			System.out.print("Adding failed: no moons detected. Skipping " + planet.getLocalizedName() + '\n');
			return;
		}
		System.out.print("Adding..." + '\n');
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Moon moon = (Moon)iterator.next();
			
			double posX = this.posX + Math.cos(moon.getPhaseShift()) * (moon.getRelativeDistanceFromCenter().scaledDistance * BSConfig.PlanetMoonDistance / GalacticraftCore.moonMoon.getRelativeDistanceFromCenter().scaledDistance);
			double posZ = this.posZ + Math.sin(moon.getPhaseShift()) * (moon.getRelativeDistanceFromCenter().scaledDistance * BSConfig.PlanetMoonDistance / GalacticraftCore.moonMoon.getRelativeDistanceFromCenter().scaledDistance);

    		double minX = posX - (moon.getRelativeSize() * BSConfig.MoonSize);
    		double minZ = posZ - (moon.getRelativeSize() * BSConfig.MoonSize);
    		double maxX = posX + (moon.getRelativeSize() * BSConfig.MoonSize);
    		double maxZ = posZ + (moon.getRelativeSize() * BSConfig.MoonSize);

    		moons.add(new ContainerMoon(moon, posX, posZ, minX, minZ, maxX, maxZ));
   		}
	} 
}
