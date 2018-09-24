package projectandromeda.systems.ArterosSystem.arteros_e.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import projectandromeda.core.prefab.items.ItemBlockDesc;
import projectandromeda.systems.ArterosSystem.arteros_e.blocks.ArterosEBlocks.EnumArterosEBlocks;

public class ItemArterosEBlocks extends ItemBlockDesc {

	public ItemArterosEBlocks(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String names = EnumArterosEBlocks.values()[stack.getItemDamage()].getName();
		return getBlock().getUnlocalizedName() + "." + names;
	}

}
