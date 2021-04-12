package beyondspace.utils.space;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.galaxies.Star;

public class ContainerGalaxy {

	public Vector<ContainerSolarSystem> systems;
	
	public ContainerGalaxy() {
		System.out.print("Constructing Galaxy Container\n");
		systems = new Vector<ContainerSolarSystem>();
		initGalaxy();
	}
	
	/** Initializing the whole galaxy <br> (solar systems, stars, planets, moons, their position, boundaries, etc) */
	private void initGalaxy() {
		System.out.print("Trying to initialize Galaxy Container\n");
		Collection<SolarSystem> list = GalaxyRegistry.getRegisteredSolarSystems().values();
		if (list.isEmpty()) {
			System.out.print("Initialization failed: no solar systems detected\n");
			return;
		}
		System.out.print("Initializing...\n");
		Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
        	Object system = iterator.next();
        	if (system instanceof SolarSystem) {
        		Star star = ((SolarSystem)system).getMainStar();
        		double posX = ((SolarSystem) system).getMapPosition().x * BSConfig.StarStarDistance;
        		double posZ = ((SolarSystem) system).getMapPosition().y * BSConfig.StarStarDistance;
        		
        		double minX = posX - (star.getRelativeSize() * BSConfig.StarSize);
        		double minZ = posZ - (star.getRelativeSize() * BSConfig.StarSize);
        		double maxX = posX + (star.getRelativeSize() * BSConfig.StarSize);
        		double maxZ = posZ + (star.getRelativeSize() * BSConfig.StarSize);

        		systems.add(new ContainerSolarSystem((SolarSystem) system, posX, posZ, minX, minZ, maxX, maxZ));
        	}
        }
	}
}