package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import beyondspace.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderItemToyRocket implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack is, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack is, Object... data) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5F, 0.0F, 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(RenderToyRocketTile.texture);
		GL11.glCallList(ClientProxy.displayList[0]);
		GL11.glPopMatrix();
	}
}
