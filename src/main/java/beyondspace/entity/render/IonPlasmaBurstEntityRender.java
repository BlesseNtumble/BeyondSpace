package beyondspace.entity.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.entity.IonPlasmaBurstEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class IonPlasmaBurstEntityRender extends Render {

	private static final Random RANDOM = new Random(432L);
	
	public static final IModelCustom sphere = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/IonPlasmaSphere.obj"));
	public static final IModelCustom ringIn = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/IonPlasmaRingIn.obj"));
	public static final IModelCustom ringMid = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/IonPlasmaRingMid.obj"));
	public static final IModelCustom ringOut = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/IonPlasmaRingOut.obj"));
	
    public IonPlasmaBurstEntityRender() {
        shadowSize = 0.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(ModInfo.MODID + ":textures/entity/IonPlasma.png");
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
        IonPlasmaBurstEntity burst = (IonPlasmaBurstEntity) entity;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        renderEquippedItems(burst, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F);
        
// Sphere White
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        if (burst.burstprogress <= 25) {
			GL11.glScalef(187.5F, 187.5F, 187.5F);
		} else {
			GL11.glScalef(burst.burstprogress * 7.5F, burst.burstprogress * 7.5F, burst.burstprogress * 7.5F);
		}
        GL11.glCullFace(GL11.GL_BACK);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_FRONT);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
        
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.5F - (float) 1 / 200.0F));
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ModInfo.MODID + ":textures/entity/IonPlasme.png"));
        
// Sphere Outer
        GL11.glPushMatrix();
        if (burst.burstprogress <= 25) {
			GL11.glScalef(200, 200, 200);
		} else {
			GL11.glScalef(burst.burstprogress * 8, burst.burstprogress * 8, burst.burstprogress * 8);
		}
		GL11.glRotatef(burst.burstprogress * 2, 1.0F, 1.0F, 1.0F);
        sphere.renderAll();        
        GL11.glPopMatrix();

// Sphere Inner
        GL11.glPushMatrix();
        if (burst.burstprogress <= 25) {
			GL11.glScalef(175, 175, 175);
		} else {
			GL11.glScalef(burst.burstprogress * 7, burst.burstprogress * 7, burst.burstprogress * 7);
		}
		GL11.glRotatef(-burst.burstprogress * 2, 1.0F, 1.0F, 1.0F);
        sphere.renderAll();        
        GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glColor4f(0.5F, 0.5F, 0.5F, (1.5F - (float) 1 / 200.0F));
        bindEntityTexture(entity);

// Sphere In
        GL11.glPushMatrix();
        if (burst.burstprogress <= 25) {
			GL11.glScalef(181.25F, 181.25F, 181.25F);
		} else {
			GL11.glScalef(burst.burstprogress * 7.25F, burst.burstprogress * 7.25F, burst.burstprogress * 7.25F);
		}
		GL11.glRotatef(burst.burstprogress * 2, 1.0F, 1.0F, 1.0F);
        GL11.glCullFace(GL11.GL_BACK);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_FRONT);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glPopMatrix();

// Sphere Out
        GL11.glPushMatrix();
        if (burst.burstprogress <= 25) {
			GL11.glScalef(193.75F, 193.75F, 193.75F);
		} else {
			GL11.glScalef(burst.burstprogress * 7.75F, burst.burstprogress * 7.75F, burst.burstprogress * 7.75F);
		}
		GL11.glRotatef(-burst.burstprogress * 2, 1.0F, 1.0F, 1.0F);
        GL11.glCullFace(GL11.GL_BACK);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_FRONT);
        sphere.renderAll();
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (1.5F - (float) 1 / 200.0F));
        
// Rings
        GL11.glPushMatrix();
        if (burst.burstprogress <= 25) {
        	GL11.glScalef(burst.burstprogress * 4, burst.burstprogress * 4, burst.burstprogress * 4);
        } else {
			GL11.glScalef((float)burst.burstprogress * 8.0F * ((float)burst.burstprogress / 50.0F), (float)burst.burstprogress * 8.0F * ((float)burst.burstprogress / 50.0F), (float)burst.burstprogress * 8.0F * ((float)burst.burstprogress / 50.0F));
		}
        ringIn.renderAll();
        ringMid.renderAll();
        ringOut.renderAll();        
        GL11.glPopMatrix();
 
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }
    
	public void renderEquippedItems(IonPlasmaBurstEntity burst, float f0) {
		Tessellator tessellator = Tessellator.instance;

		if (burst.burstprogress > 0) {
			RenderHelper.disableStandardItemLighting();
			float f1 = ((float) burst.burstprogress + f0) / 200.0F;
			float f2 = 0.0F;

			if (f1 > 0.8F) {
				f2 = (f1 - 0.8F) / 0.2F;
			}

			Random random = new Random(432L);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDepthMask(false);
			GL11.glPushMatrix();

			for (int i = 0; (float) i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i) {
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
				tessellator.startDrawing(6);
				float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
				float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
				tessellator.setColorRGBA_I(16777215, (int) (255.0F * (1.0F - f2)));
				tessellator.addVertex(0.0D, 0.0D, 0.0D);
				tessellator.setColorRGBA_I(16711935, 0);
				tessellator.addVertex(-0.866D * (double) f4, (double) f3, (double) (-0.5F * f4));
				tessellator.addVertex(0.866D * (double) f4, (double) f3, (double) (-0.5F * f4));
				tessellator.addVertex(0.0D, (double) f3, (double) (1.0F * f4));
				tessellator.addVertex(-0.866D * (double) f4, (double) f3, (double) (-0.5F * f4));
				tessellator.draw();
			}

			GL11.glPopMatrix();
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glShadeModel(GL11.GL_FLAT);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}
}