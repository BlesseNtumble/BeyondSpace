package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.ToyRocketTileEntity;
import beyondspace.proxy.ClientProxy;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderToyRocketTile extends TileEntitySpecialRenderer {

	public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/model/ToyRocket.png");

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		render((ToyRocketTileEntity)tile, x, y, z, f);
	}

	private void render(ToyRocketTileEntity tile, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glTranslatef(0.5F, 0.0F, 0.5F);
		bindTexture(texture);
		GL11.glCallList(ClientProxy.displayList[0]);
		GL11.glPopMatrix();
	}
}
