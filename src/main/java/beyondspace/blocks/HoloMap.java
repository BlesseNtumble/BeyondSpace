package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.HoloMapTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class HoloMap extends Block implements ITileEntityProvider {

	public HoloMap() {
		super(Material.iron);
		this.setBlockName("HoloMap");
		this.setBlockTextureName(ModInfo.MODID + ":HoloMap");
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setHardness(5.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeMetal);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new HoloMapTileEntity();
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
		  
	public boolean renderAsNormalBlock() {
		return false;
	}
}