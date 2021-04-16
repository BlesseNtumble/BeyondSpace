package beyondspace.items.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.utils.RegistrationsList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class PlasmaGunRender implements IItemRenderer {
  public ResourceLocation rifle_model;
  
  public ResourceLocation rifle_texture;
  
  public IModelCustom rifle_modelr;
  
  
  public PlasmaGunRender(Item paramItem) {
    if (paramItem == RegistrationsList.ionPlasmaRifle) {
      this.rifle_model = new ResourceLocation(ModInfo.MODID + ":model/SuperSpaceIonPlasmaRifle.obj");
      this.rifle_texture = new ResourceLocation(ModInfo.MODID + ":textures/items/SuperSpaceIonPlasmaRifle.png");
      this.rifle_modelr = AdvancedModelLoader.loadModel(this.rifle_model);
    }
  }
  
  public boolean handleRenderType(ItemStack paramItemStack, IItemRenderer.ItemRenderType paramItemRenderType) {
    return true;
  }
  
  public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, IItemRenderer.ItemRendererHelper paramItemRendererHelper) {
    return (paramItemRenderType == IItemRenderer.ItemRenderType.ENTITY && paramItemRendererHelper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION);
  }
  
  public void renderItem(IItemRenderer.ItemRenderType paramItemRenderType, ItemStack paramItemStack, Object... paramVarArgs) {
    GL11.glPushMatrix();
    if (paramItemStack != null) {
      Item item = paramItemStack.getItem();
      if (item == RegistrationsList.ionPlasmaRifle) {
        float f;
        (Minecraft.getMinecraft()).renderEngine.bindTexture(this.rifle_texture);
        switch (RenderEnum.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[paramItemRenderType.ordinal()]) {
          case 1:
            f = 1.5F;
            EntityPlayer player1 = (EntityPlayer) paramVarArgs[1];
            GL11.glScalef((float) 1.75, (float)1.75, (float)1.75);
            if(player1.isUsingItem()) {
            GL11.glTranslatef(-0.1F, 0.4F, 0.0F);
            GL11.glRotatef(0.0F, 6.0F, 1.0F, 1.0F);
            GL11.glRotatef(95.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, -0.65F);
            }else{
            GL11.glTranslatef(0.3F, 0.4F, 0.0F);
            GL11.glRotatef(0.0F, 6.0F, 1.0F, 1.0F);
            GL11.glRotatef(95.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }
            break;
          case 2:
            f = 1.5F;
            GL11.glScalef(f, f, f);
            GL11.glTranslatef(0.1F, 0.4F, -0.05F);
            GL11.glRotatef(58.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(85.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            break;
          case 3:
            f = 11.0F;
            GL11.glScalef(f, f, f);
            GL11.glTranslatef(6.5F / f, 10.0F / f, 0.0F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(85.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            break;
          case 4:
            f = 2.0F;
            GL11.glScalef(f, f, f);
            GL11.glTranslatef(0.0F, 0.7F, 0.0F);
            break;
        } 
        this.rifle_modelr.renderAll();
      } 
    } 
    GL11.glPopMatrix();
  }

}
