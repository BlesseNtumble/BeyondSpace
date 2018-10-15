package projectandromeda.systems.ArterosSystem.arteros_e.world.gen;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenArteros_e_trees extends WorldGenAbstractTree {
	
	private static final IBlockState DEFAULT_TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
	private static final IBlockState DEFAULT_LEAF = Blocks.LEAVES.getDefaultState() .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK) .withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;
	/** True if this tree should grow Vines. */
	private final boolean vinesGrow;
	/** The metadata value of the wood to use in tree generation. */
	private final IBlockState metaWood;
	/** The metadata value of the leaves to use in tree generation. */
	private final IBlockState metaLeaves;

	public WorldGenArteros_e_trees(boolean p_i2027_1_) {
		this(p_i2027_1_, 4, DEFAULT_TRUNK, DEFAULT_LEAF, false);
	}

	public WorldGenArteros_e_trees(boolean notify, int minTreeHeightIn, IBlockState woodMeta, IBlockState leavesMeta, boolean growVines) {
		super(notify);
		minTreeHeight = minTreeHeightIn;
		metaWood = woodMeta;
		metaLeaves = leavesMeta;
		vinesGrow = growVines;
	}
	
	public void placeLeavesHead(World worldIn, BlockPos leavesPosHead, IBlockState leavesMeta) {
		if(worldIn != null) {
			setBlockAndNotifyAdequately(worldIn, leavesPosHead.north(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosHead.south(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosHead.west(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosHead.east(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosHead.up(2), metaLeaves);
		}
	}
	public void placeLeavesFloor(World worldIn, BlockPos leavesPosFloor, IBlockState leavesMeta) {
		if(worldIn != null) {
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.north(2), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.south(2), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.west(2), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.east(2), metaLeaves);
			//
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.north().up(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.south().up(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.west().up(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.east().up(), metaLeaves);
			//
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.north().west(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.south().west(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.north().east(), metaLeaves);
			setBlockAndNotifyAdequately(worldIn, leavesPosFloor.south().east(), metaLeaves);
		}
	}
	
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int woodHeight = minTreeHeight + rand.nextInt(4);
		
		if(position.getY() >= 1 && position.getY() + woodHeight + 1 <= worldIn.getHeight()) {
			// woods
			for(int h = 0; h < woodHeight; h++) {
				setBlockAndNotifyAdequately(worldIn, position.up(h), metaWood);
			}
			BlockPos leavesPosHead;
			if(woodHeight == minTreeHeight) {
				leavesPosHead = position.up(minTreeHeight);
				placeLeavesHead(worldIn, leavesPosHead, metaLeaves);
				placeLeavesFloor(worldIn, position, metaLeaves);
			} else if(woodHeight > minTreeHeight) {
				leavesPosHead = position.up(woodHeight);
				placeLeavesHead(worldIn, leavesPosHead, metaLeaves);
				placeLeavesFloor(worldIn, position, metaLeaves);
			}
			return true;
		}
		
		return true;
	}
}