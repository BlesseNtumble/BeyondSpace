package beyondspace.utils.space;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;

public class ContainerSolarSystem {
	public double minX, minZ, maxX, maxZ, posX, posZ;
	public SolarSystem system;
	public Vector<ContainerPlanet> planets;
	
	public ContainerSolarSystem(SolarSystem system, double px, double pz, double x, double z, double X, double Z) {
		System.out.print("Constructing Solar System Container For " + system.getLocalizedName() + '\n');
		this.minX = x;
		this.minZ = z;
		this.maxX = X;
		this.maxZ = Z;
		this.posX = px;
		this.posZ = pz;
		this.system = system;
		planets = new Vector<ContainerPlanet>();
		this.addPlanets();
	}
	
	private void addPlanets() {
		System.out.print("Trying to add planets to container for " + system.getLocalizedName() + '\n');
		List<Planet> list = GalaxyRegistry.getPlanetsForSolarSystem(this.system);
		if (list.isEmpty()) {
			System.out.print("Adding failed: no planets detected. Skipping " + system.getLocalizedName() + '\n');
			return;
		}
		System.out.print("Adding..." + '\n');
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Planet planet = (Planet)iterator.next();
			
			double posX = this.posX + Math.cos(planet.getPhaseShift()) * (planet.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance);
			double posZ = this.posZ + Math.sin(planet.getPhaseShift()) * (planet.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance);

    		double minX = posX - (planet.getRelativeSize() * BSConfig.PlanetSize);
    		double minZ = posZ - (planet.getRelativeSize() * BSConfig.PlanetSize);
    		double maxX = posX + (planet.getRelativeSize() * BSConfig.PlanetSize);
    		double maxZ = posZ + (planet.getRelativeSize() * BSConfig.PlanetSize);

    		planets.add(new ContainerPlanet(planet, posX, posZ, minX, minZ, maxX, maxZ));
   		}
	} 
}
