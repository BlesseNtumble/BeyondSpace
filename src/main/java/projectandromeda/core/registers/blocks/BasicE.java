package projectandromeda.core.registers.blocks;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BasicE extends Block implements ISortableBlock {
	
	public static final PropertyEnum<EnumBasicE> BASIC = PropertyEnum.create("e_basic", EnumBasicE.class);
	
	public enum EnumBasicE implements IStringSerializable {
		surface(0, "e0"),
		subsurface(1, "e1"),
		stone(2, "e2");
		
		private final int meta;
		private final String name;
		
		private EnumBasicE(int p_meta, String p_name) {
			meta = p_meta;
			name = p_name;
		}
		
		public int getMeta() {
			return meta;
		}
		
		public static EnumBasicE valueByMeta(int meta) {
			return values()[meta];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
	public BasicE(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(GalacticraftCore.galacticraftBlocksTab);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> list) {
		for(int i = 0; i < EnumBasicE.values().length; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BASIC, EnumBasicE.valueByMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBasicE)state.getValue(BASIC)).getMeta();
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC);
	}

	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
}
