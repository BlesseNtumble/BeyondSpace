package beyondspace.blocks.tileentity;

import beyondspace.utils.RegistrationsList;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class SealableChameleonBlockTileEntity extends TileEntity {
	
	private Block block;
	private int meta;
	
	public SealableChameleonBlockTileEntity() {
		this.setBlock(RegistrationsList.chameleonBlock, 0);
	}
	
	public void setBlock(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	public Block getBlock() {
		return this.block;
	}
	
	public int getMeta() {
		return this.meta;
	}
}
