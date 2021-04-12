package beyondspace.items.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class StandartScaledTileItemRender implements IItemRenderer {

	public TileEntitySpecialRenderer render;
	private TileEntity tile;
	
	public StandartScaledTileItemRender(TileEntitySpecialRenderer render, TileEntity tile) {
		this.tile = tile;
		this.render = render;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case ENTITY:
			return true;
		case EQUIPPED:
			return true;
		case EQUIPPED_FIRST_PERSON:
			return true;
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case ENTITY:
			this.render.renderTileEntityAt(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
			break;
		case EQUIPPED:
			this.render.renderTileEntityAt(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
			break;
		case EQUIPPED_FIRST_PERSON:
			this.render.renderTileEntityAt(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
			break;
		case INVENTORY:
			this.render.renderTileEntityAt(this.tile, 0.0D, 0.0D, 0.0D, 0.0F);
			break;
		default: 
			}}}
