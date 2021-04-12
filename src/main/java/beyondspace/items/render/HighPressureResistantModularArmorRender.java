package beyondspace.items.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public  class HighPressureResistantModularArmorRender extends AdvancedArmorModel {

	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/HighPressureResistantModularArmor.obj"));
	public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/armor/HighPressureResistantModularArmor.png");
	private final int partType;

	/**armorType: 0 - head, 1 - body and arms, 2 - legs, 3 - feet.**/
	public HighPressureResistantModularArmorRender(int armorType) {
		partType = armorType;
	}

	@Override
	public void pre() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void post() {
		GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void partHead() {
		if (partType == 0) {
			if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
			GL11.glTranslatef(0F, -1.85F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("G");
		}
	}

	@Override
	public void partBody() {
		if (partType == 1) {
			if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
			GL11.glTranslatef(0F, -1.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("Gru");
		}
	}

	@Override
	public void partRightArm() {
		if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
		if (partType == 1) {
			GL11.glTranslatef(0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("P2");
		}
	}

	@Override
	public void partLeftArm() {
		if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
		if (partType == 1) {
			GL11.glTranslatef(-0.375F, -1.6F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("L2");
		}
	}

	@Override
	public void partRightLeg() {
		if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
		if (partType == 2) {
			GL11.glTranslatef(0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("P3");
		}
	}

	@Override
	public void partLeftLeg() {
		if (Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.invisibility) != null) return;
		if (partType == 2) {
			GL11.glTranslatef(-0.125F, -0.75F, 0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			model.renderPart("L3");
		}
	}
}