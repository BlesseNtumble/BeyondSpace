package beyondspace.items.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class NuclearRodRender implements IItemRenderer {

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/NuclearRod.obj"));
	public static final ResourceLocation[] textures = new ResourceLocation[8]; //new ResourceLocation(ModInfo.MODID, "textures/items/NuclearRod/NuclearRod_0.png");
	
	public NuclearRodRender(){
		for(int i = 0; i < textures.length; i++){
			textures[i] = new ResourceLocation(ModInfo.MODID, "textures/items/NuclearRod/NuclearRod_" + i + ".png");
		}
	}
	@Override
	public boolean handleRenderType(ItemStack is, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (type == ItemRenderType.EQUIPPED){
			GL11.glRotated(135, 1, 0, 0);
			GL11.glRotated(-30, 0, 0, 1);
			GL11.glTranslated(0.6, 0.25, -1);
			GL11.glScaled(1.5, 1.5, 1.5);
		} else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslated(0, 1, 0.75);
			GL11.glRotated(-20, 1, 0, 1);
			GL11.glScaled(1.5, 1.5, 1.5);
		} else if (type == ItemRenderType.INVENTORY) {
			GL11.glScaled(2, 2, 2);
			GL11.glRotated(45, 0, 1, 0);
		}
		int index = (int)Math.floor((stack.getItemDamage() * 7) / stack.getMaxDamage());
		index = index > 0 ? textures.length - 1 : index < 0 ? 0 : index;
		Minecraft.getMinecraft().renderEngine.bindTexture(textures[index]);
		model.renderAll();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
