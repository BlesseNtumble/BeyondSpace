package projectandromeda.core.registers.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import projectandromeda.core.registers.blocks.BasicE.EnumBasicE;

public class ItemBasicE extends ItemBlock {

	public ItemBasicE(Block block) {
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
		String names = EnumBasicE.values()[stack.getItemDamage()].getName();
		return getBlock().getUnlocalizedName() + "." + names;
	}

}
