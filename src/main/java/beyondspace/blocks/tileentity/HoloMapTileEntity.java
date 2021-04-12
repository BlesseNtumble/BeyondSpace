package beyondspace.blocks.tileentity;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.tileentity.TileEntity;

public class HoloMapTileEntity extends TileEntity {
	
	public SolarSystem system_render = GalacticraftCore.solarSystemSol;
	@Override
	public void updateEntity() {

		if(!this.getWorldObj().isRemote) {
	        if(this.getWorldObj().provider instanceof IGalacticraftWorldProvider)
	        {        	
	        	CelestialBody body = ((IGalacticraftWorldProvider)this.getWorldObj().provider).getCelestialBody();
	        
	        	if(body instanceof Planet)
	        		system_render = ((Planet)body).getParentSolarSystem();
	        	else if(body instanceof IChildBody)
	        		system_render = ((IChildBody)body).getParentPlanet().getParentSolarSystem();
	        }
		}
		/*Planet planet = GalacticraftCore.planetOverworld;
		double angle = planet.getPhaseShift() * (180 / Math.PI) +
		(double) (Minecraft.getMinecraft().theWorld.getWorldTime()) % (1200.0 * planet.getRelativeOrbitTime()) * 360.0 / (1200.0 * planet.getRelativeOrbitTime());
		
		angle /= 57;
		double x = Math.cos(-angle) * planet.getRelativeDistanceFromCenter().scaledDistance;
		double z = Math.sin(-angle) * planet.getRelativeDistanceFromCenter().scaledDistance;
		
		this.worldObj.spawnParticle("crit", this.xCoord + x + 0.5, this.yCoord + 1.5, this.zCoord + z + 0.5, 0, 0, 0);*/
	}
}
