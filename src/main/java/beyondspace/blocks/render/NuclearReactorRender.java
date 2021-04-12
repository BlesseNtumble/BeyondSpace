package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.NuclearReactorTileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class NuclearReactorRender extends TileEntitySpecialRenderer {
	
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(ModInfo.MODID, "model/NuclearReactor.obj"));
	public static final ResourceLocation texture = new ResourceLocation(ModInfo.MODID, "textures/blocks/NuclearReactor.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
	    GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		model.renderPart("Base");
		model.renderPart("Rod");
		GL11.glEnable(GL11.GL_CULL_FACE);
	    GL11.glPopMatrix();
	    
	    {
	    	NuclearReactorTileEntity tile = (NuclearReactorTileEntity) te;
	    	
			for (int i = 0; i < 4; i++) if (tile.slot[i] != null) {
				double xMod = i == 0 || i == 1 ? 0.5 : i == 2 ? 0.275 : i == 3 ? 0.725 : 0;
				double zMod = i == 2 || i == 3 ? 0.5 : i == 0 ? 0.275 : i == 1 ? 0.725 : 0;
				GL11.glPushMatrix();
				GL11.glTranslated(x + xMod, y + 0.575, z + zMod);
				renderItemStack(tile.getWorldObj(), tile.slot[i]);
		    	GL11.glPopMatrix();
			}
	    }
	}
	
	public void renderItemStack(World world, ItemStack stack) {
		EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
		entityitem.hoverStart = 0.0F;
		GL11.glPushMatrix();
		RenderItem.renderInFrame = true;
		RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, -0.25D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		GL11.glPopMatrix();
	}
}
