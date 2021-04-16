package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry;
import micdoodle8.mods.galacticraft.api.galaxies.IChildBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.galaxies.SolarSystem;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class HoloMapRender extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);        
        
        Tessellator tes = Tessellator.instance;
        
        SolarSystem system_render = GalacticraftCore.solarSystemSol;

        if(te.getWorldObj() != null && te.getWorldObj().provider instanceof IGalacticraftWorldProvider)
        {        	
        	CelestialBody body = ((IGalacticraftWorldProvider)te.getWorldObj().provider).getCelestialBody();
        
        	if(body instanceof Planet)
        		system_render = ((Planet)body).getParentSolarSystem();
        	else if(body instanceof IChildBody)
        		system_render = ((IChildBody)body).getParentPlanet().getParentSolarSystem();
        }
        
        renderSystem(tes, system_render);
        
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	public void renderSystem(Tessellator tes, SolarSystem system) {

		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 1.125, 0.5);
		GL11.glScaled(0.25, 0.25, 0.25);
		double s = system.getMainStar().getRelativeSize();
		GL11.glScaled(s, s, s);
		Minecraft.getMinecraft().renderEngine.bindTexture(system.getMainStar().getBodyIcon());
		tes.startDrawingQuads();
		
		double x = 1.0D;
		tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, x);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, x, 0);
        tes.addVertexWithUV(0.5, -0.5, 0.5, x, x);
		
		tes.addVertexWithUV(0.5, 0.5, 0.5, x, x);
        tes.addVertexWithUV(0.5, 0.5, -0.5, x, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, x);
		
		tes.addVertexWithUV(-0.5, 0.5, -0.5, x, 0);
        tes.addVertexWithUV(0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(0.5, -0.5, -0.5, 0, x);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, x, x);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, 0, x);
        tes.addVertexWithUV(0.5, -0.5, 0.5, x, x);
        tes.addVertexWithUV(0.5, 0.5, 0.5, x, 0);
		
		tes.addVertexWithUV(-0.5, 0.5, 0.5, x, 0);
        tes.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
        tes.addVertexWithUV(-0.5, -0.5, -0.5, 0, x);
        tes.addVertexWithUV(-0.5, -0.5, 0.5, x, x);
		
		tes.addVertexWithUV(0.5, -0.5, 0.5, 0, x);
        tes.addVertexWithUV(0.5, -0.5, -0.5, x, x);
        tes.addVertexWithUV(0.5, 0.5, -0.5, x, 0);
        tes.addVertexWithUV(0.5, 0.5, 0.5, 0, 0);
		
		tes.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		//GL11.glTranslated(system.getMapPosition().x / 75.0, 0, system.getMapPosition().y / 75.0);
		int i = 0;
		for(Planet planet : GalaxyRegistry.getPlanetsForSolarSystem(system)) {		
		
			renderRing(planet, i++);
			renderPlanet(tes, planet);
		}
		GL11.glPopMatrix();
	}


	public void renderPlanet(Tessellator tes, Planet planet) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(planet.getBodyIcon());
				
		GL11.glColor3f(1,1,1);
		GL11.glTranslated(0.5, 1.125, 0.5);
		GL11.glRotated(planet.getPhaseShift() * (180 / Math.PI), 0, 1, 0);
		GL11.glRotated((double)(Minecraft.getMinecraft().theWorld.getWorldTime()) % (1200.0 * planet.getRelativeOrbitTime()) * 360.0 / (1200.0 * planet.getRelativeOrbitTime()), 0, 1, 0);
		GL11.glTranslated(planet.getRelativeDistanceFromCenter().scaledDistance, 0, 0);
		// TODO: spin timer		
		//GL11.glRotated((double)(Minecraft.getMinecraft().theWorld.getWorldTime() % 800) * 360.0 / 800.0, 0, 1, 0);
		GL11.glScaled(0.1, 0.1, 0.1);
		double s = planet.getRelativeSize();
		GL11.glScaled(s, s, s);
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
		GL11.glTranslated(0.5, 1.125, 0.5);
		GL11.glRotated(planet.getPhaseShift() * (180 / Math.PI), 0, 1, 0);
		GL11.glRotated((double)(Minecraft.getMinecraft().theWorld.getWorldTime() % (1200 * planet.getRelativeOrbitTime())) * 360.0 / (1200.0 * planet.getRelativeOrbitTime()), 0, 1, 0);
		GL11.glTranslated(planet.getRelativeDistanceFromCenter().scaledDistance, 0, 0);
		for(Moon moon : GalaxyRegistry.getMoonsForPlanet(planet))
			renderMoon(tes, moon);
		
		GL11.glPopMatrix();
	}

	public void renderRing(Planet planet, int count) {
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glTranslated(0.5, 1.125, 0.5);
		GL11.glRotated(90.0, 1.0, 0.0, 0.0);
		float color = count % 2 == 0 ? 0.9F : 0.5F;
		GL11.glColor4f(0, 0.4F, color, 0.4F);
		GL11.glScaled(planet.getRelativeDistanceFromCenter().scaledDistance, planet.getRelativeDistanceFromCenter().scaledDistance, 0);
		
		GL11.glBegin(GL11.GL_LINE_LOOP);
		for(int i = 0; i <= 360; i++) {
			double angle = 2 * Math.PI * i / 360;
			double x = Math.cos(angle);
			double y = Math.sin(angle);
			GL11.glVertex2d(x, y);
		}
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();		
	}

	public void renderMoon(Tessellator tes, Moon moon) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(moon.getBodyIcon());
		
		GL11.glRotated(moon.getPhaseShift() * (180 / Math.PI), 0, 1, 0);
		GL11.glRotated((double)(Minecraft.getMinecraft().theWorld.getWorldTime() % (12 * moon.getRelativeOrbitTime())) * 360.0 / (12.0 * moon.getRelativeOrbitTime()), 0, 1, 0);
		GL11.glTranslated(moon.getRelativeDistanceFromCenter().scaledDistance / 150, 0, 0);
// TODO: spin timer		GL11.glRotated((double)(Minecraft.getMinecraft().theWorld.getWorldTime() % 24000) * 360.0 / 24000.0, 0, 1, 0);
		GL11.glScaled(0.01, 0.01, 0.01);
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
