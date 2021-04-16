package beyondspace.utils.space;

import beyondspace.Switches;
import beyondspace.asjlib.ASJUtilities;
import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.Star;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;

/** Utility class with methods used in Space dimension and for related mechanics */
public class BSSpaceUtilities {
	
	/** Star damage source */
	public static final DamageSource star = new DamageSource("Star").setDamageBypassesArmor().setDamageIsAbsolute();
	/** Container for the galaxy */
	public static ContainerGalaxy galaxy;
	
	/** Main function for space dimension operations */
	public static void doStuffInSpaceDim(Entity entity) {
		EntityLivingBase living = entity instanceof EntityLivingBase ? (EntityLivingBase) entity : null;
		EntityPlayer 	 player = living instanceof EntityPlayer	 ? (EntityPlayer)	  living : null;
		
		// Kill entities within the star
		if (getStarAt(entity.posX, entity.posZ) != null) {
			if (player != null) {
				if (player.capabilities.isCreativeMode) return;
				if (player.capabilities.disableDamage)  player.capabilities.disableDamage = false;
			}
			if (entity instanceof EntityLivingBase) ((EntityLivingBase) entity).attackEntityFrom(star, Float.MAX_VALUE);
			else entity.setDead();
		}
		
		// Landing on Planets/Moons
	    CelestialBody body = getLandableBodyAt(entity.posX, entity.posZ);
		if (body != null && body.getWorldProvider() != null && entity.posY < 5) {
			ASJUtilities.sendToDimensionWithoutPortal1(entity, body.getDimensionID(), 0.5, 255.5, 0.5);
			entity.setLocationAndAngles(0.5, 255.5, 0.5, 0, 0);
			if (!entity.worldObj.isRemote) WorldUtil.transferEntityToDimension(entity, body.getDimensionID(), (WorldServer) entity.worldObj, false, null);
		}
	}

	/** Create Galaxy Container */
	public static void init() {
		if (Switches.spaceDim) galaxy = new ContainerGalaxy();
	}
	
	/** @return Celestial Body at given coordinates */
	public static CelestialBody getBodyAt(double x, double z) {
		for (int i = 0; i < galaxy.systems.size(); i++) {
			for (int j = 0; j < galaxy.systems.get(i).planets.size(); j++) {
				for (int k = 0; k < galaxy.systems.get(i).planets.get(j).moons.size(); k++) {
					if (x >= galaxy.systems.get(i).planets.get(j).moons.get(k).minX
					 && x <= galaxy.systems.get(i).planets.get(j).moons.get(k).maxX
					 && z >= galaxy.systems.get(i).planets.get(j).moons.get(k).minZ
					 && z <= galaxy.systems.get(i).planets.get(j).moons.get(k).maxZ)
					 return  galaxy.systems.get(i).planets.get(j).moons.get(k).moon;
				}
				if (x >= galaxy.systems.get(i).planets.get(j).minX
				 && x <= galaxy.systems.get(i).planets.get(j).maxX
				 && z >= galaxy.systems.get(i).planets.get(j).minZ
				 && z <= galaxy.systems.get(i).planets.get(j).maxZ)
				 return  galaxy.systems.get(i).planets.get(j).planet;
			}
			if (x >= galaxy.systems.get(i).minX
			 && x <= galaxy.systems.get(i).maxX
			 && z >= galaxy.systems.get(i).minZ
			 && z <= galaxy.systems.get(i).maxZ)
			 return  galaxy.systems.get(i).system.getMainStar();
		}
		return null;
	}
	
	/** @returns Star on given coordinates or null */
	public static Star getStarAt(double x, double z) {
		for (int i = 0; i < galaxy.systems.size(); i++) {
			if (galaxy.systems.get(i).minX <= x && x <= galaxy.systems.get(i).maxX && galaxy.systems.get(i).minZ <= z && z <= galaxy.systems.get(i).maxZ) return galaxy.systems.get(i).system.getMainStar();
		}
		return null;
	}
	
	/** @returns Planet/Moon on given coordinates or null */
	public static CelestialBody getLandableBodyAt(double x, double z) {
		CelestialBody body = getBodyAt(x, z);
		if (body != null && (body instanceof Planet || body instanceof Moon)) return body;
		return null;
	}

	/** @return Coordinates of Planet/Moon with conforming ID in space dimension <br> or coordinates of the Earth */
	public static Vec3 getBodyPositionFromDimID(int id) {
		for (int i = 0; i < galaxy.systems.size(); i++) {
			for (int j = 0; j < galaxy.systems.get(i).planets.size(); j++) {
				for (int k = 0; k < galaxy.systems.get(i).planets.get(j).moons.size(); k++) {
					if (galaxy.systems.get(i).planets.get(j).moons.get(k).moon.getWorldProvider() != null && galaxy.systems.get(i).planets.get(j).moons.get(k).moon.getDimensionID() == id) {
						ContainerMoon moon = galaxy.systems.get(i).planets.get(j).moons.get(k);
						return Vec3.createVectorHelper(moon.posX, 64, moon.posZ);
					}
				}
				if (galaxy.systems.get(i).planets.get(j).planet.getWorldProvider() != null && galaxy.systems.get(i).planets.get(j).planet.getDimensionID() == id) {
					ContainerPlanet planet = galaxy.systems.get(i).planets.get(j);
					return Vec3.createVectorHelper(planet.posX, 64, planet.posZ);
				}
			}
		}
		
		return Vec3.createVectorHelper(65536.5, 64.5, 0.5);
	}
	
	/** @returns Earth's coordinates in the Space dimension */
	public static ChunkCoordinates getEarthPosition() {
		return new ChunkCoordinates(MathHelper.floor_double(Math.cos(GalacticraftCore.planetOverworld.getPhaseShift()) * (GalacticraftCore.planetOverworld.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance)), 64, MathHelper.floor_double(Math.sin(GalacticraftCore.planetOverworld.getPhaseShift()) * (GalacticraftCore.planetOverworld.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance)));
	}
}
