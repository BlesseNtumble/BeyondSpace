package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import cpw.mods.fml.client.FMLClientHandler;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class LightningrodTopRender extends TileEntitySpecialRenderer {

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/LightningRod.obj"));
	public static final ResourceLocation texture = new ResourceLocation(GalacticraftCore.ASSET_PREFIX, "textures/blocks/machine.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(x + 0.5, y - 1, z + 0.5);
		GL11.glScalef(0.4F, 0.4F, 0.4F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		model.renderPart("Top");
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}