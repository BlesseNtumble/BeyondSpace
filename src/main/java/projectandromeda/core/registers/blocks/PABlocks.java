package projectandromeda.core.registers.blocks;

import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import projectandromeda.core.prefab.items.ItemBlockDesc;
import projectandromeda.systems.ArterosSystem.arteros_e.blocks.ArterosEBlocks;
import projectandromeda.systems.ArterosSystem.arteros_e.blocks.ArterosELog;
import projectandromeda.systems.ArterosSystem.arteros_e.items.ItemArterosEBlocks;

public class PABlocks {
	
	public static final Block ARTEROS_E_BLOCKS = new ArterosEBlocks("arteros_e_blocks").setHardness(3.0F);
	public static final Block ARTEROS_E_LOG = new ArterosELog("arteros_e_log");
	public static final Block ARTEROS_E_LEAVES = new ArterosELeaves("arteros_e_leaves");
	
	public static void initialize() 
	{
		registerBlock(ARTEROS_E_BLOCKS, ItemArterosEBlocks.class);
		registerBlock(ARTEROS_E_LOG, ItemBlockDesc.class);
		registerBlock(ARTEROS_E_LEAVES, ItemBlockDesc.class);
	}
	
	public static void oreDictRegistration() 
	{
		
	}
	
	public static void registerBlock(Block block, Class<? extends ItemBlock> itemClass)
    {
        GCBlocks.registerBlock(block, itemClass);
    }
}
