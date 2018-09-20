package projectandromeda.core.registers.blocks;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class PABlocks {
	
	public static Block e;
	
	public static void register() {
		e = new BasicE("e");
		GCBlocks.registerBlock(e, ItemBasicE.class);
	}
}
