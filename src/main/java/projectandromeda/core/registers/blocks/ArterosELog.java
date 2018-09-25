package projectandromeda.core.registers.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import projectandromeda.core.utils.PACreativeTabs;

public class ArterosELog extends BlockRotatedPillar {
	
	public static final PropertyEnum<ArterosELog.EnumAxis> LOG_AXIS = PropertyEnum.<ArterosELog.EnumAxis>create("axis", ArterosELog.EnumAxis.class);

	public ArterosELog(String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(PACreativeTabs.PABlocksTab);
		setHardness(2.0F);
		setSoundType(SoundType.WOOD);
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		int i = 4;
		int j = 5;

		if (worldIn.isAreaLoaded(pos.add(-5, -5, -5), pos.add(5, 5, 5))) {
			for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
				IBlockState iblockstate = worldIn.getBlockState(blockpos);

				if (iblockstate.getBlock().isLeaves(iblockstate, worldIn, blockpos)) {
					iblockstate.getBlock().beginLeavesDecay(iblockstate, worldIn, blockpos);
				}
			}
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getStateFromMeta(meta).withProperty(LOG_AXIS, ArterosELog.EnumAxis.fromFacingAxis(facing.getAxis()));
	}
	
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:

			switch ((ArterosELog.EnumAxis) state.getValue(LOG_AXIS)) {
			case X:
				return state.withProperty(LOG_AXIS, ArterosELog.EnumAxis.Z);
			case Z:
				return state.withProperty(LOG_AXIS, ArterosELog.EnumAxis.X);
			default:
				return state;
			}

		default:
			return state;
		}
	}

	@Override
	public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	public static enum EnumAxis implements IStringSerializable {
		X("x"),
		Y("y"),
		Z("z"),
		NONE("none");

		private final String name;

		private EnumAxis(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public static EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
			switch (axis) {
			case X:
				return X;
			case Y:
				return Y;
			case Z:
				return Z;
			default:
				return NONE;
			}
		}

		public String getName() {
			return this.name;
		}
	}
}