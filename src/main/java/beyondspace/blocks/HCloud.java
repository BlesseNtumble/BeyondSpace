package beyondspace.blocks;

import java.util.Random;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HCloud extends Block {
	
	public HCloud() {
		super(Material.ice);
		this.setBlockName("HCloud");
		this.setBlockTextureName(ModInfo.MODID + ":HCloud");
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setLightOpacity(0);
		this.setHardness(4.0F);
		this.setStepSound(soundTypeCloth);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int par2, int par3, int par4) {
		return null;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_)
    {
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
    	Block block = world.getBlock(x, y, z);

        if (this == RegistrationsList.HCloud)
        {
            if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
            {
                return true;
            }

            if (block == this)
            {
                return false;
            }
        }

        return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
    
    }
}
