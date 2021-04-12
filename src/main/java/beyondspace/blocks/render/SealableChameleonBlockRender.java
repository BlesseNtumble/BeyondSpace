package beyondspace.blocks.render;

import org.lwjgl.opengl.GL11;

import beyondspace.blocks.tileentity.SealableChameleonBlockTileEntity;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class SealableChameleonBlockRender extends TileEntitySpecialRenderer {

    public double renderMinX = 0.0, renderMaxX = 1.0, renderMinY = 0.0, renderMaxY = 1.0, renderMinZ = 0.0, renderMaxZ = 1.0;
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float meta) {
		renderTE((SealableChameleonBlockTileEntity) te, x, y, z, meta);
	}
	
	private void renderTE(SealableChameleonBlockTileEntity te, double x, double y, double z, float meta) { // RenderBlocks 
		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;
		
		int l = te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord).colorMultiplier(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
		float red = (float)(l >> 16 & 255) / 255.0F;
        float green = (float)(l >> 8 & 255) / 255.0F;
        float blue = (float)(l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float f4 = (red * 30.0F + green * 70.0F) / 100.0F;
            float f5 = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = f3;
            green = f4;
            blue = f5;
        }
        
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        float f7 = f4 * red;
        float f8 = f4 * green;
        float f9 = f4 * blue;
        float f10 = f3;
        float f11 = f5;
        float f12 = f6;
        float f13 = f3;
        float f14 = f5;
        float f15 = f6;
        float f16 = f3;
        float f17 = f5;
        float f18 = f6;

        if (te.getBlock() != Blocks.grass) {
            f10 = f3 * red;
            f11 = f5 * red;
            f12 = f6 * red;
            f13 = f3 * green;
            f14 = f5 * green;
            f15 = f6 * green;
            f16 = f3 * blue;
            f17 = f5 * blue;
            f18 = f6 * blue;
        }
        
		IIcon side0 = te.getBlock().getIcon(0, te.getMeta());
		IIcon side1 = te.getBlock().getIcon(1, te.getMeta());
		IIcon side2 = te.getBlock().getIcon(2, te.getMeta());
		IIcon side3 = te.getBlock().getIcon(3, te.getMeta());
		IIcon side4 = te.getBlock().getIcon(4, te.getMeta());
		IIcon side5 = te.getBlock().getIcon(5, te.getMeta());
		
		
        tessellator.startDrawingQuads();

		// Render Y negative
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord, te.yCoord - 1, te.zCoord).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord - 1, te.zCoord));
			tessellator.setColorOpaque_F(f10, f13, f16);
			
			double d3 = (double)side0.getInterpolatedU(0.0D);
	        double d4 = (double)side0.getInterpolatedU(16.0D);
	        double d5 = (double)side0.getInterpolatedV(0.0D);
	        double d6 = (double)side0.getInterpolatedV(16.0D);
	        
	        tessellator.addVertexWithUV(x, y, z + 1, d3, d6);
            tessellator.addVertexWithUV(x, y, z, d3, d5);
            tessellator.addVertexWithUV(x + 1, y, z, d4, d5);
            tessellator.addVertexWithUV(x + 1, y, z + 1, d4, d6);
		}
		
		// Render Y positive 
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord, te.yCoord + 1, te.zCoord).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord + 1, te.zCoord));
			tessellator.setColorOpaque_F(f7, f8, f9);
			
			double d3 = (double)side1.getInterpolatedU(0.0D);
	        double d4 = (double)side1.getInterpolatedU(16.0D);
	        double d5 = (double)side1.getInterpolatedV(0.0D);
	        double d6 = (double)side1.getInterpolatedV(16.0D);

	        tessellator.addVertexWithUV(x + 1, y + 1, z + 1, d4, d6);
            tessellator.addVertexWithUV(x + 1, y + 1, z, d4, d5);
            tessellator.addVertexWithUV(x, y + 1, z, d3, d5);
            tessellator.addVertexWithUV(x, y + 1, z + 1, d3, d6);
		}
		
		// Render Z negative 
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord - 1).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord - 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
			
			double d3 = (double)side2.getInterpolatedU(0.0D);
	        double d4 = (double)side2.getInterpolatedU(16.0D);
	        double d5 = (double)side2.getInterpolatedV(0.0D);
	        double d6 = (double)side2.getInterpolatedV(16.0D);
	        
	        tessellator.addVertexWithUV(x, y + 1, z, d4, d5);
            tessellator.addVertexWithUV(x + 1, y + 1, z, d3, d5);
            tessellator.addVertexWithUV(x + 1, y, z, d3, d6);
            tessellator.addVertexWithUV(x, y, z, d4, d6);
		}
		
		// Render Z positive 
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord, te.yCoord, te.zCoord + 1).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord + 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
            
			double d3 = (double)side3.getInterpolatedU(0.0D);
	        double d4 = (double)side3.getInterpolatedU(16.0D);
	        double d5 = (double)side3.getInterpolatedV(0.0D);
	        double d6 = (double)side3.getInterpolatedV(16.0D);
	        
	        tessellator.addVertexWithUV(x, y + 1, z + 1, d3, d5);
            tessellator.addVertexWithUV(x, y, z + 1, d3, d6);
            tessellator.addVertexWithUV(x + 1, y, z + 1, d4, d6);
            tessellator.addVertexWithUV(x + 1, y + 1, z + 1, d4, d5);
		}
		
		// Render X negative 
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord + 1, te.yCoord, te.zCoord).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord + 1, te.yCoord, te.zCoord));
			tessellator.setColorOpaque_F(f12, f15, f18);
            
			double d3 = (double)side4.getInterpolatedU(0.0D);
	        double d4 = (double)side4.getInterpolatedU(16.0D);
	        double d5 = (double)side4.getInterpolatedV(0.0D);
	        double d6 = (double)side4.getInterpolatedV(16.0D);
	        
	        tessellator.addVertexWithUV(x, y + 1, z + 1, d4, d5);
            tessellator.addVertexWithUV(x, y + 1, z, d3, d5);
            tessellator.addVertexWithUV(x, y, z, d3, d6);
            tessellator.addVertexWithUV(x, y, z + 1, d4, d6);
		}
		
		// Render X positive 
		{
			tessellator.setBrightness(te.getWorldObj().getBlock(te.xCoord + 1, te.yCoord, te.zCoord).getMixedBrightnessForBlock(te.getWorldObj(), te.xCoord + 1, te.yCoord, te.zCoord));
			tessellator.setColorOpaque_F(f12, f15, f18);
            
			double d3 = (double)side5.getInterpolatedU(0.0D);
	        double d4 = (double)side5.getInterpolatedU(16.0D);
	        double d5 = (double)side5.getInterpolatedV(0.0D);
	        double d6 = (double)side5.getInterpolatedV(16.0D);
	        
	        tessellator.addVertexWithUV(x + 1, y, z + 1, d3, d6);
            tessellator.addVertexWithUV(x + 1, y, z, d4, d6);
            tessellator.addVertexWithUV(x + 1, y + 1, z, d4, d5);
            tessellator.addVertexWithUV(x + 1, y + 1, z + 1, d3, d5);
		}

        tessellator.draw();
		GL11.glPopMatrix();
	}
}
