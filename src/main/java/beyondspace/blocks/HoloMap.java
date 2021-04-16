package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.HoloMapTileEntity;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class HoloMap extends Block implements ITileEntityProvider {

	public IIcon top;
	public IIcon back;
	public IIcon down;
	
	public HoloMap() {
		super(Material.iron);
		this.setBlockName("HoloMap");
		this.setBlockTextureName(ModInfo.MODID + ":HoloMap");
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setHardness(5.0F);
		this.setHarvestLevel("pickaxe", 1);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeMetal);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.down = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
		this.back = iconRegister.registerIcon(ModInfo.MODID + ":HoloMap");
		this.top = iconRegister.registerIcon(ModInfo.MODID + ":HoloMapTop");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0) {
			return down;
		}else if(side == 1) {
			return top;
		}else{
			return back;
		}
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