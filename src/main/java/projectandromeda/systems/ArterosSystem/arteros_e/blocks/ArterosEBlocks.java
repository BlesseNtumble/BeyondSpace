package projectandromeda.systems.ArterosSystem.arteros_e.blocks;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.core.blocks.ISortableBlock;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArterosEBlocks extends Block implements ISortableBlock {
	
	public static final PropertyEnum<EnumArterosEBlocks> BASIC = PropertyEnum.create("type", EnumArterosEBlocks.class);
			
	public ArterosEBlocks(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		EnumArterosEBlocks type = ((EnumArterosEBlocks) state.getValue(BASIC));
		switch(type)
		{
			case SURFACE:
			case SUBSURFACE:
				return SoundType.GROUND;
			default:
				return SoundType.STONE;
		}

	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> list) {		
		for (EnumArterosEBlocks blockBasic : EnumArterosEBlocks.values())        
            list.add(new ItemStack(this, 1, blockBasic.getMeta()));        
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
	}
	
	@Override
	public EnumSortCategoryBlock getCategory(int meta) {
		return EnumSortCategoryBlock.GENERAL;
	}
		
	//////////////////////////////////////////////////////
	
	public enum EnumArterosEBlocks implements IStringSerializable {
		SURFACE(0, "e0"),
		SUBSURFACE(1, "e1"),
		STONE(2, "e2");
		
		private final int meta;
		private final String name;
		
		private EnumArterosEBlocks(int p_meta, String p_name) {
			meta = p_meta;
			name = p_name;
		}
		
		public int getMeta() {
			return meta;
		}
		
		public static EnumArterosEBlocks valueByMeta(int meta) {
			return values()[meta];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BASIC, EnumArterosEBlocks.valueByMeta(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumArterosEBlocks)state.getValue(BASIC)).getMeta();
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BASIC);
	}

}
