package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.ToyRocketTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ToyRocket extends Block implements ITileEntityProvider {

	public ToyRocket() {
		super(Material.iron);
		this.setBlockName("ToyRocket");
		this.setBlockTextureName("iron_block");
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setLightOpacity(0);
		this.setHardness(4.0F);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new ToyRocketTileEntity();
	}

	public int getRenderType() {
		return -1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(final World w, final int x, final int y, final int z, final EntityPlayer p, final int var6, final float var7, final float var8, final float var9) {
		if (p.isSneaking())
			return true;
		String sound = ModInfo.MODID + ":toyRocket";

		if (sound != null)
			w.playSoundAtEntity(p, sound, 0.125f, 1.0f);

		return true;
	}
}
