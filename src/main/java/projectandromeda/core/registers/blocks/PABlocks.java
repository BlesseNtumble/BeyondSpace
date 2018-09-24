package projectandromeda.core.registers.blocks;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import projectandromeda.core.prefab.items.ItemBlockDesc;
import projectandromeda.systems.ArterosSystem.arteros_e.blocks.ArterosEBlocks;
import projectandromeda.systems.ArterosSystem.arteros_e.items.ItemArterosEBlocks;

public class PABlocks {
	
	public static final Block ARTEROS_E_BLOCKS = new ArterosEBlocks("arteros_e_blocks").setHardness(3.0F);
	
	public static void initialize() 
	{
		registerBlock(ARTEROS_E_BLOCKS, ItemArterosEBlocks.class);
	}
	
	public static void oreDictRegistration() 
	{
		
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
