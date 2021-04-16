package beyondspace.proxy.network;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import beyondspace.items.AdvancedOxygenEPPTank;
import beyondspace.utils.RegistrationsList;
import beyondspace.world.dimension.Jupiter.CloudRendererJupiter;
import beyondspace.world.dimension.Jupiter.SkyProviderJupiter;
import beyondspace.world.dimension.JupiterNew.WorldProviderJupiterNew;
import beyondspace.world.dimension.Neptune.CloudRendererNeptune;
import beyondspace.world.dimension.Neptune.SkyProviderNeptune;
import beyondspace.world.dimension.NeptuneNew.WorldProviderNeptuneNew;
import beyondspace.world.dimension.Saturn.CloudRendererSaturn;
import beyondspace.world.dimension.Saturn.SkyProviderSaturn;
import beyondspace.world.dimension.SaturnNew.WorldProviderSaturnNew;
import beyondspace.world.dimension.SaturnRings.SkyProviderSaturnRings;
import beyondspace.world.dimension.SaturnRings.WorldProviderSaturnRings;
import beyondspace.world.dimension.Space.SkyProviderSpace;
import beyondspace.world.dimension.Space.WorldProviderSpace;
import beyondspace.world.dimension.Uranus.CloudRendererUranus;
import beyondspace.world.dimension.Uranus.SkyProviderUranus;
import beyondspace.world.dimension.UranusNew.WorldProviderUranusNew;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import galaxyspace.core.client.models.ModelOxygenTank;
import galaxyspace.core.handler.GSColorRingClient;
import galaxyspace.core.util.GSAttributePlayer;
import micdoodle8.mods.galacticraft.api.event.client.CelestialBodyRenderEvent;
import micdoodle8.mods.galacticraft.api.galaxies.CelestialBody;
import micdoodle8.mods.galacticraft.api.galaxies.Moon;
import micdoodle8.mods.galacticraft.api.galaxies.Planet;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import micdoodle8.mods.galacticraft.api.inventory.IInventoryGC;
import micdoodle8.mods.galacticraft.core.client.CloudRenderer;
import micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerHandler.EnumModelPacket;
import micdoodle8.mods.galacticraft.core.items.ItemOxygenTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class ClientEventHandler {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		WorldClient world = minecraft.theWorld;

		if (world != null) {
			/**JUPITER*/
			if (world.provider instanceof WorldProviderJupiterNew) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderJupiter());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRendererJupiter());
				}
			}
			/**SATURN*/
			if (world.provider instanceof WorldProviderSaturnNew) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderSaturn());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRendererSaturn());
				}
			}
			/**RINGS*/
			if (world.provider instanceof WorldProviderSaturnRings) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderSaturnRings());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRenderer());
				}
			}
			/**URANUS*/
			if (world.provider instanceof WorldProviderUranusNew) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderUranus());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRendererUranus());
				}
			}
			/**NEPTUNE*/
			if (world.provider instanceof WorldProviderNeptuneNew) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderNeptune());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRendererNeptune());
				}
			}
			/**SPACE*/
			if (world.provider instanceof WorldProviderSpace) {
				if (world.provider.getSkyRenderer() == null) {
					world.provider.setSkyRenderer(new SkyProviderSpace());
				}

				if (world.provider.getCloudRenderer() == null) {
					world.provider.setCloudRenderer(new CloudRenderer());
				}
			}
		}
	}
    
	/**Render Loop around Saturn*/
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	public void onRingRender(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent) {
		if (renderEvent.celestialBody.equals(RegistrationsList.saturnRings)) {
			drawAsteroidRings(renderEvent, RegistrationsList.saturnRings);
		}
	}
	
	  @SubscribeEvent
	  @SideOnly(Side.CLIENT)
	  public void onRender(RenderPlayerEvent.Specials.Post event) {
	    GSColorRingClient.onPostRender(event);
	    EntityPlayer player = event.entityPlayer;
	    if (player != null) {
	      ModelOxygenTank tank = new ModelOxygenTank();
	      GL11.glPushMatrix();
	      GL11.glDisable(3042);
	      if (player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).getBaseValue() > 0.0D) {
	        double tex = player.getEntityAttribute(GSAttributePlayer.OXTANKS_LEFT).getBaseValue() + 1.0D;
	        (Minecraft.getMinecraft()).renderEngine.bindTexture(new ResourceLocation("beyondspace" + ":textures/model/EPPOxTank.png"));
	        tank.renderLeft((Entity)event.entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	      } 
	      if (player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).getBaseValue() > 0.0D) {
	        double tex = player.getEntityAttribute(GSAttributePlayer.OXTANKS_RIGHT).getBaseValue() + 1.0D;
	        (Minecraft.getMinecraft()).renderEngine.bindTexture(new ResourceLocation("beyondspace" + ":textures/model/EPPOxTank.png"));
	        tank.renderRight((Entity)event.entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
	      } 
	      GL11.glEnable(3042);
	      GL11.glPopMatrix();
	    } 
	  }
	
    protected void drawAsteroidRings(CelestialBodyRenderEvent.CelestialRingRenderEvent.Pre renderEvent, CelestialBody aroundBody) {
        Vector3f mapPos = renderEvent.parentOffset;
        
        float xOffset = (float) mapPos.x;
        float yOffset = (float) mapPos.y;
        
        if (FMLClientHandler.instance().getClient().currentScreen instanceof GuiCelestialSelection)
          GL11.glColor4f(0.7F, 0.5F, 0.3F, 0.5F);
        else
          GL11.glColor4f(0.3F, 0.1F, 0.1F, 1.0F);
        renderEvent.setCanceled(true);
        GL11.glBegin(GL11.GL_LINE_LOOP);

		final float theta = (float) (2 * Math.PI / 90);
		final float cos = (float) Math.cos(theta);
		final float sin = (float) Math.sin(theta);

		float min = 0;
		float max = 0;

		if (aroundBody instanceof Planet) {
			min = 72.F;
			max = 78.F;
		} else if (aroundBody instanceof Moon) {
			max = 0.67F;
			min = 0.27F;
		}

		float x = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
		float y = 0;

		// outer ring
		float temp;
		for (int i = 0; i < 90; i++) {
			GL11.glVertex2f(x + xOffset, y + yOffset);

			temp = x;
			x = cos * x - sin * y;
			y = sin * temp + cos * y;
		}

		GL11.glEnd();

		// inner ring
		GL11.glBegin(GL11.GL_LINE_LOOP);

		x = min
				* renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
		y = 0;

		for (int i = 0; i < 90; i++) {
			GL11.glVertex2f(x + xOffset, y + yOffset);

			temp = x;
			x = cos * x - sin * y;
			y = sin * temp + cos * y;
		}

		GL11.glEnd();

		// inner red area
		GL11.glColor4f(0.7F, 0.5F, 0.3F, 0.3F);
		GL11.glBegin(GL11.GL_QUADS);

		x = min * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
		y = 0;
		float x2 = max * renderEvent.celestialBody.getRelativeDistanceFromCenter().unScaledDistance;
		float y2 = 0;

		for (int i = 0; i < 90; i++) {
			GL11.glVertex2f(x2 + xOffset, y2 + yOffset);
			GL11.glVertex2f(x + xOffset, y + yOffset);

			temp = x;
			x = cos * x - sin * y;
			y = sin * temp + cos * y;
			temp = x2;
			x2 = cos * x2 - sin * y2;
			y2 = sin * temp + cos * y2;

			GL11.glVertex2f(x + xOffset, y + yOffset);
			GL11.glVertex2f(x2 + xOffset, y2 + yOffset);
		}

		GL11.glEnd();
     }
}
