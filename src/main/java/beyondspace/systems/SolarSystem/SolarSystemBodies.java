package beyondspace.systems.SolarSystem;

import asmodeuscore.core.astronomy.BodiesData;
import asmodeuscore.core.astronomy.BodiesRegistry;
import asmodeuscore.core.astronomy.BodiesRegistry.Galaxies;
import asmodeuscore.core.astronomy.SpaceData;
import asmodeuscore.core.prefab.celestialbody.Comet;
import asmodeuscore.core.prefab.celestialbody.ExMoon;
import asmodeuscore.core.prefab.celestialbody.ExPlanet;
import beyondspace.BeyondSpace;
import beyondspace.core.util.GADimensions;
import beyondspace.systems.SolarSystem.moons.saturnRings.dimensions.TeleportTypeSaturnRings;
import beyondspace.systems.SolarSystem.moons.saturnRings.dimensions.WorldProviderSaturnRings;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody.ScalableDistance;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.util.ResourceLocation;

public class SolarSystemBodies {

	public static SolarSystem testSystem;
	public static Planet planetJupiter = GalacticraftCore.planetJupiter;
	public static Planet planetSaturn = GalacticraftCore.planetSaturn;
	public static Planet planetUranus = GalacticraftCore.planetUranus;
	public static Planet planetNeptune = GalacticraftCore.planetNeptune;
	
	public static ExPlanet planetTest;
	public static ExMoon saturnRings;
	
    public static Comet testComet;
	
	public static void preInit()
	{
		Galaxies testGalaxy = BodiesRegistry.registerGalaxy("testGalaxy", new ResourceLocation("galaxyspace:textures/gui/celestialbodies/deedee.png"));
		testSystem = BodiesRegistry.registerSolarSystem(BeyondSpace.ASSET_PREFIX, "test_system", testGalaxy, new Vector3(0.0F, 0.0F, 0.0F), "star_test", 3.0F);
		GalaxyRegistry.registerSolarSystem(testSystem);
		/*
		planetTest = new ExPlanet("test").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		planetTest.setClassPlanet(ClassBody.ASTEROID);
		planetTest.setPhaseShift((float) (Math.PI / 4));
		planetTest.setRelativeDistanceFromCenter(new ScalableDistance(7.5F, 7.5F));
		planetTest.setRelativeOrbitTime(12F);
		planetTest.setRingColorRGB(0.0F, 0.4F, 0.9F);
		planetTest.setBodyIcon(new ResourceLocation(GalaxyAdditions.MODID, "textures/gui/celestialbodies/test.png"));
		planetTest.setAtmosphericPressure(15);
		planetTest.setDayLenght(125000L);
		planetTest.setOrbitEccentricity(0.3F, 0.2F);
		planetTest.setOrbitOffset(450.0F, 0.0F);
		planetTest.setRingColorRGB(0.0F, 1.0F, 0.0F);
		*/
		
		
		planetTest = BodiesRegistry.registerExPlanet(testSystem, "test", BeyondSpace.MODID, 0.1F);
		BodiesRegistry.setOrbitData(planetTest, (float)Math.PI / 4, 1.0F, 15F, 10.3F, 5.0F, 5F, 0.0F);
		BodiesRegistry.setPlanetData(planetTest, 5, 20000L, BodiesRegistry.calculateGravity(9.3F), true);
		GalaxyRegistry.registerPlanet(planetTest);
		
		saturnRings = BodiesRegistry.registerExMoon(galaxyspace.systems.SolarSystem.SolarSystemBodies.planetSaturn, "saturn_rings", BeyondSpace.MODID, 10F);
		BodiesRegistry.setOrbitData(saturnRings, (float) Math.PI, 1.0F, 120F);
		BodiesRegistry.setAtmosphere(saturnRings, false, false, false, -5, 0.0F, 0.0F);
		BodiesRegistry.setPlanetData(saturnRings, 0, 0, 0, true);
		//BodiesHelper.setProviderData(saturnRings, WorldProviderSaturnRings.class, -1337, 5, ACBiome.ACSpace);
		GalaxyRegistry.registerMoon(saturnRings);
		
		testComet = new Comet("test").setParentSolarSystem(GalacticraftCore.solarSystemSol);
		testComet.setRelativeDistanceFromCenter(new ScalableDistance(5.0F, 5.0F));
		testComet.setRelativeOrbitTime(320F);
		testComet.setBodyIcon(new ResourceLocation("textures/items/apple.png"));
		SpaceData.registerCelestialBody(testComet);
		
		BodiesData data = new BodiesData(BodiesRegistry.registerType("comet"), BodiesRegistry.registerClass("space_station"));   	
		BodiesRegistry.registerBodyData(testComet, data);
		
		//data = new BodiesData(null, 0.068F, 0, 0, true);   	
		//BodiesHelper.registerBody(saturnRings, data, true);
		

		GalacticraftRegistry.registerTeleportType(WorldProviderSaturnRings.class, new TeleportTypeSaturnRings());
	}
	
	public static void postInit()
	{
		GADimensions.SATURN_RINGS = WorldUtil.getDimensionTypeById(-1337);
	}
}
