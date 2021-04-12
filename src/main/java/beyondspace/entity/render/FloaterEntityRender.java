package beyondspace.entity.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.entity.FloaterEntity;
import beyondspace.entity.model.FloaterModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class FloaterEntityRender extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID + ":" + "textures/entity/Floater.png");

	public FloaterEntityRender() {
		super(new FloaterModel(), 0.5F);
	}

	protected ResourceLocation getEntityTexture(FloaterEntity p_110775_1_) {
		return texture;
	}

	protected void preRenderCallback(FloaterEntity p_77041_1_, float p_77041_2_) {
		GL11.glScalef(7F, 7F, 7F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
		this.preRenderCallback((FloaterEntity) p_77041_1_, p_77041_2_);
	}

	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((FloaterEntity) p_110775_1_);
	}
}