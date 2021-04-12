package beyondspace.entity.render;

import org.lwjgl.opengl.GL11;

import beyondspace.entity.EntityFire;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFire extends Render
{
    private static final ResourceLocation meteorChunkTexture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/model/meteorChunk.png");

    private final IModelCustom meteorChunkModel = AdvancedModelLoader.loadModel(new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "models/meteorChunk.obj"));

    public RenderFire()
    {
        this.shadowSize = 0.1F;
    }

    protected ResourceLocation func_110779_a(EntityFire par1EntityArrow)
    {
        return RenderFire.meteorChunkTexture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110779_a((EntityFire) par1Entity);
    }

    public void renderMeteorChunk(EntityFire entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        final float var24 = entity.rotationPitch;
        final float var24b = entity.rotationYaw;
        GL11.glTranslatef((float) par2, (float) par4, (float) par6);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        GL11.glRotatef(var24b, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var24, 0.0F, 0.0F, 1.0F);
            this.bindTexture(RenderFire.meteorChunkTexture);
        this.meteorChunkModel.renderAll();

        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderMeteorChunk((EntityFire) par1Entity, par2, par4, par6, par8, par9);
    }
}
