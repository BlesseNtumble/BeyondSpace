package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.LightningRodBaseTileEntity;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc.IBlockShiftDesc;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LightningRodBase extends BlockTileGC implements IBlockShiftDesc {

	public IIcon top;
	public IIcon[] front;
	public IIcon out;
	public IIcon back;
	public IIcon down;
	
	public LightningRodBase() {
		super(GCBlocks.machine);
		this.setBlockName("LightningRodBase");
        this.setBlockTextureName(ModInfo.MODID + ":LightningRodBase");
        this.setCreativeTab(BeyondSpace.bsTab);
        this.setHardness(3F);
        this.setHarvestLevel("pickaxe", 2);
        this.setResistance(8F);
        this.setStepSound(soundTypeMetal);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.down = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
		this.back = iconRegister.registerIcon(ModInfo.MODID + ":LightningRodBase");
		this.top = iconRegister.registerIcon(ModInfo.MODID + ":LightningRodTop");
		this.out = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_output"); 
        
		this.front = new IIcon[17];

		for (int i = 0; i < this.front.length; i++) {
			this.front[i] = iconRegister.registerIcon(ModInfo.MODID + ":energy/energyStorage_" + i);
		}
	}

	/*@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);

		if (metadata >= 0) {
			metadata -= 0;

			if (side == 0) {
				return this.down;
			}
			if(side == 1){
				return this.top;
			}
			if (side == metadata + 2) {
				return this.side;
			} else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()) {
				return this.side;
			}

			int level = 0;
			TileEntity tile = world.getTileEntity(x, y, z);
			if (tile instanceof LightningRodBaseTileEntity) {
				level = Math.min(((LightningRodBaseTileEntity) tile).energyLevel, 16);
			}

			return this.front[level];
		}

		return super.getIcon(world, x, y, z, side);
	}*/

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (side == 1){
            return this.top;
        }
		if(side == 0){
			return this.down;
		}

        if (metadata >= 0){
            metadata -= 0;
            if (side == metadata + 2){
                return this.down;
            }
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()) {
                return this.out;
            }
            else if (metadata == 0 && side == 5 || metadata == 3 && side == 3 || metadata == 1 && side == 4 || metadata == 2 && side == 2){
                return this.back;
            }
        }
        int level = 0;
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof LightningRodBaseTileEntity){
        level = ((LightningRodBaseTileEntity)tile).energyLevel;
        }
        return this.front[level];
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if (side == 1){
            return this.top;
        }
		if(side == 0){
			return this.down;
		}
        if (metadata >= 0){
            metadata -= 0;
            if (side == metadata + 2){
                return this.down;
            }
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()){
                return this.out;
            }
            else if (metadata == 0 && side == 5 || metadata == 3 && side == 3 || metadata == 1 && side == 4 || metadata == 2 && side == 2){
                return this.back;
            }
        }
        return this.front[16];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new LightningRodBaseTileEntity();
	}

	@Override
	public String getShiftDescription(int meta) {
		return StatCollector.translateToLocal("desc.lightningRod.name");
	}

	@Override
	public boolean showDescription(int meta) {
		return true;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack){
        int metadata = world.getBlockMetadata(x, y, z);

        int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle){
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
        world.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ){
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 3;
        int change = 0;

        // Re-orient the block
        switch (original){
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
        if (te instanceof TileBaseUniversalElectrical){
            ((TileBaseUniversalElectrical) te).updateFacing();
        }
        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
        return true;
    }

    @Override
    public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (!world.isRemote){
        	if (!player.isSneaking()) {
        		player.openGui(BeyondSpace.instance, -1, world, x, y, z);
        	} else {
        		player.addChatComponentMessage(new ChatComponentText("Energy Stored: " + ((LightningRodBaseTileEntity)world.getTileEntity(x, y, z)).storage.getEnergyStoredGC()));
        	}
        }
        return true;
    }
}