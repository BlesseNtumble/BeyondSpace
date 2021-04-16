package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.GasGeneratorTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockAdvancedTile;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc.IBlockShiftDesc;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class GasGenerator extends BlockAdvancedTile implements IBlockShiftDesc {
    
	private IIcon iconMachineSide;
	private IIcon iconEnergyOutput;
	private IIcon[] iconFront;

	public GasGenerator(String assetName) {
		super(GCBlocks.machine);
		setHardness(1.0F);
		setStepSound(soundTypeMetal);
		setBlockName(assetName);
		setCreativeTab(BeyondSpace.bsTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.iconMachineSide = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_blank");
		this.iconEnergyOutput = par1IconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_output");
		this.iconFront = new IIcon[9];
		for(int i = 0; i < this.iconFront.length; i++){
			this.iconFront[i] = par1IconRegister.registerIcon(ModInfo.MODID + ":" + "gasGenerator/gasGenerator_" + i);
		}
	}

	@Override
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ) {
		entityPlayer.openGui(BeyondSpace.instance, -1, world, x, y, z);
		return true;
	}

	@Override
	public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ) {
		int change = 0;

		// Re-orient the block
		switch (par1World.getBlockMetadata(x, y, z)) {
		case 0:
			change = 3;
			break;
		case 3:
			change = 1;
			break;
		case 1:
			change = 2;
			break;
		case 2:
			change = 0;
			break;
		}

		TileEntity te = par1World.getTileEntity(x, y, z);
		if (te instanceof TileBaseUniversalElectrical) {
			((TileBaseUniversalElectrical) te).updateFacing();
		}

		par1World.setBlockMetadataWithNotify(x, y, z, change, 3);
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new GasGeneratorTileEntity();
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		if (side == metadata + 3) {
			return this.iconEnergyOutput;
		}
		if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2) {
			int gasLevel = 0;
	        TileEntity tile = world.getTileEntity(x, y, z);
	        if(tile instanceof GasGeneratorTileEntity){
	        gasLevel = ((GasGeneratorTileEntity)tile).gasLevel;
			return this.iconFront[gasLevel];
		}
	  }
		return this.iconMachineSide;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if (side == metadata + 3){
            return this.iconEnergyOutput;
        }
		if(metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2){
			return this.iconFront[8];
		}
        return this.iconMachineSide;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		final int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int change = 0;

		switch (angle) {
		case 0:
			change = 3;
			break;
		case 1:
			change = 1;
			break;
		case 2:
			change = 2;
			break;
		case 3:
			change = 0;
			break;
		}

		world.setBlockMetadataWithNotify(x, y, z, change, 3);
	}

	@Override
	public String getShiftDescription(int meta) {
		return GCCoreUtil.translate(this.getUnlocalizedName() + ".desc");
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}

}