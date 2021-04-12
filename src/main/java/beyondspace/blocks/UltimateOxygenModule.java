package beyondspace.blocks;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.UltimateOxygenModuleTileEntity;
import micdoodle8.mods.galacticraft.core.blocks.BlockTileGC;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc.IBlockShiftDesc;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class UltimateOxygenModule extends BlockTileGC implements IBlockShiftDesc {

	private IIcon oxygenModuleInput;
    private IIcon oxygenModuleOutput;
    private IIcon[] oxygenModule;
    
	public UltimateOxygenModule(String modid, Material material, String name, CreativeTabs tab, float hardness, String harvTool, int harvLvl, float resistance, Block.SoundType sound, boolean isOpaque) {
		super(material);
        this.setBlockName(name);
        this.setBlockTextureName(modid + ":" + name);
        this.setCreativeTab(tab);
        this.setHardness(hardness);
        this.setHarvestLevel(harvTool, harvLvl);
        this.setResistance(resistance);
        this.setStepSound(sound);		
	}

	@Override
	public int getRenderType() {
		return BeyondSpace.proxy.getBlockRender(this);
	}
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(ModInfo.MODID + ":ultimateFurnaceIcon");
        this.oxygenModuleInput = iconRegister.registerIcon(ModInfo.MODID + ":oxygenModuleInput");
        this.oxygenModuleOutput = iconRegister.registerIcon(ModInfo.MODID + ":oxygenModuleOutput");
        
        this.oxygenModule = new IIcon[17];

        for (int i = 0; i < this.oxygenModule.length; i++)
        {
            this.oxygenModule[i] = iconRegister.registerIcon(ModInfo.MODID + ":oxygen/oxygenStorageModule_" + i);
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata >= 0)
        {
            metadata -= 0;

            if (side == 0 || side == 1)
            {
                return this.blockIcon;
            }
            if (side == metadata + 2)
            {
                return this.oxygenModuleInput;
            }
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.oxygenModuleOutput;
            }

            int oxygenLevel = 0;
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof UltimateOxygenModuleTileEntity)
            {
                oxygenLevel = Math.min(((UltimateOxygenModuleTileEntity) tile).scaledOxygenLevel, 16);
            }

            return this.oxygenModule[oxygenLevel];
        }

        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0 || side == 1)
        {
            return this.blockIcon;
        }
        if (metadata >= 0)
        {
            metadata -= 0;
            if (side == 0 || side == 1)
            {
                return this.blockIcon;
            }
            if (side == metadata + 2)
            {
                return this.oxygenModuleInput;
            }
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.oxygenModuleOutput;
            }

            return this.oxygenModule[16];
        }

        return this.blockIcon;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        int angle = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = 0;

        switch (angle)
        {
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

        if (metadata >= 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0 + change, 3);
        }
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata;

        int change = 0;

        if (metadata >= 0)
        {
            original -= 0;
        }
            TileEntity te = par1World.getTileEntity(x, y, z);
            if (te instanceof TileBaseUniversalElectrical)
            {
                ((TileBaseUniversalElectrical) te).updateFacing();
            }
        // Re-orient the block
        switch (original)
        {
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

        if (metadata >= 0)
        {
            change += 0;
        }
      
        par1World.setBlockMetadataWithNotify(x, y, z, change, 3);
        return true;
    }

    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!par1World.isRemote)
        {
            par5EntityPlayer.openGui(BeyondSpace.instance, -1, par1World, x, y, z);
            return true;
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new UltimateOxygenModuleTileEntity();
    }
    public ItemStack getOxygenStorageModule()
    {
        return new ItemStack(this, 1, 0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(this.getOxygenStorageModule());
    }

    @Override
    public int damageDropped(int metadata)
    {
        if (metadata >= 0)
        {
            return 0;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        int metadata = this.getDamageValue(world, x, y, z);

        return new ItemStack(this, 1, metadata);
    }

    @Override
    public String getShiftDescription(int meta)
    {
        switch (meta)
        {
        case 0:
            return GCCoreUtil.translate("tile.oxygenModule.description");
        }
        return "";
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }
}
