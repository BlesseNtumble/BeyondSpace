package beyondspace.world.dimension.Space;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import beyondspace.utils.BSConfig;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.IRenderHandler;

public class SkyProviderSpace extends IRenderHandler {

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		// Smooth centering
		double x_fix = -(mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * partialTicks);
		double y_fix = -(mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * partialTicks);
		double z_fix = -(mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * partialTicks);
		GL11.glTranslated(x_fix, y_fix, z_fix);

		// Lighting
		GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        // Starting rendering for all registered solar systems
        Iterator iterator = GalaxyRegistry.getRegisteredSolarSystems().values().iterator();
        while (iterator.hasNext()) {
        	Object system = iterator.next();
        	if (system instanceof SolarSystem) {
        		renderSystem((SolarSystem)system);
        	}
        }
        
        // Returning GL to original values
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	/** Render single solar system */
	public void renderSystem(SolarSystem system) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 1.5, 0.5);
		GL11.glTranslated(system.getMapPosition().x * BSConfig.StarStarDistance, 0, system.getMapPosition().y * BSConfig.StarStarDistance);
		double s = system.getMainStar().getRelativeSize() * BSConfig.StarSize;
		GL11.glScaled(s, s, s);
		GL11.glTranslated(0, -0.5, 0);
		Tessellator tes = Tessellator.instance;
		Minecraft.getMinecraft().renderEngine.bindTexture(system.getMainStar().getBodyIcon());
		tes.startDrawingQuads();
		
		tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
		
		tes.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(system.getMapPosition().x * BSConfig.StarStarDistance, 0, system.getMapPosition().y * BSConfig.StarStarDistance);
		renderAllPlanets(system);
		GL11.glPopMatrix();
	}
	
	public void renderAllPlanets(SolarSystem system) {
		Iterator iterator = GalaxyRegistry.getPlanetsForSolarSystem(system).iterator();
		while (iterator.hasNext()) {
			renderPlanet((Planet)iterator.next());
		}
	}

	public void renderPlanet(Planet planet) {
		GL11.glPushMatrix();
		Tessellator tes = Tessellator.instance;
		Minecraft.getMinecraft().renderEngine.bindTexture(planet.getBodyIcon());
				
		GL11.glTranslated(0.5, 1.5, 0.5);
		GL11.glRotated(planet.getPhaseShift() * (180.0 / Math.PI), 0, 1, 0);
		GL11.glTranslated(planet.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance, 0, 0);
		GL11.glRotated(-planet.getPhaseShift() * (180.0 / Math.PI), 0, 1, 0);
		double s = planet.getRelativeSize() * BSConfig.PlanetSize;
		GL11.glScaled(s, s, s);
		GL11.glTranslated(0, -0.5, 0);
		tes.startDrawingQuads();
		
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
		
		tes.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 1.5, 0.5);
		GL11.glRotated(planet.getPhaseShift() * (180.0 / Math.PI), 0, 1, 0);
		GL11.glTranslated(planet.getRelativeDistanceFromCenter().scaledDistance * BSConfig.StarPlanetDistance, 0, 0);
		renderAllMoons(planet);
		GL11.glPopMatrix();
	}

	public void renderAllMoons(Planet planet) {
		Iterator iterator = GalaxyRegistry.getMoonsForPlanet(planet).iterator();
		while (iterator.hasNext()) {
			renderMoon((Moon)iterator.next(), planet);
		}
	}

	public void renderMoon(Moon moon, Planet reverse) {
		GL11.glPushMatrix();
		Tessellator tes = Tessellator.instance;
		Minecraft.getMinecraft().renderEngine.bindTexture(moon.getBodyIcon());
		
		GL11.glRotated(moon.getPhaseShift() * (180.0 / Math.PI), 0, 1, 0);
		GL11.glTranslated(moon.getRelativeDistanceFromCenter().scaledDistance * BSConfig.PlanetMoonDistance / GalacticraftCore.moonMoon.getRelativeDistanceFromCenter().scaledDistance, 0, 0);
		GL11.glRotated(-reverse.getPhaseShift() * (180.0 / Math.PI), 0, 1, 0);
		double s = moon.getRelativeSize() * (1.0 / 0.2667) * BSConfig.MoonSize;
		GL11.glScaled(s, s, s);
		GL11.glTranslated(0, -0.5, 0);
		
		tes.startDrawingQuads();
		
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 1, 1);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, 0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 1, 0);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 1);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 1, 1);
		
		tes.addVertexWithUV(0.5, -0.5, 0.5, 0, 1);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 1, 1);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 1, 0);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
		
		tes.draw();
		GL11.glPopMatrix();
	}
}
