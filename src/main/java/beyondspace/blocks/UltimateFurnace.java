package beyondspace.blocks;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.UltimateFurnaceTileEntity;
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

public class UltimateFurnace extends BlockTileGC implements IBlockShiftDesc
{
    public static final int ULTIMATE_FURNACE_METADATA = 0;

    private IIcon furnaceIcon;
    private IIcon iconFurnaceSide;
    private IIcon iconFurnace;
    private IIcon iconFurnaceInput;

    public UltimateFurnace(String modid, Material material, String name, CreativeTabs tab, float hardness, String harvTool, int harvLvl, float resistance, Block.SoundType sound, boolean isOpaque)
    {
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
      this.furnaceIcon = iconRegister.registerIcon(ModInfo.MODID + ":" + "ultimateFurnaceIcon");
      this.iconFurnaceSide = iconRegister.registerIcon(ModInfo.MODID + ":" + "ultimateFurnaceSide");                    
      this.iconFurnace = iconRegister.registerIcon(ModInfo.MODID + ":" + "ultimateFurnace");
      this.iconFurnaceInput = iconRegister.registerIcon(ModInfo.MODID + ":" + "ultimateFurnaceInput");
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        int type = metadata & 4;
        int metaside = (metadata & 3) + 2;
        return this.getIcon(side, metadata);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        int metaside = (metadata & 3) + 2;

        if (side == 0 || side == 1)
        {
            return this.furnaceIcon;
        }
        if ((metadata & 4) == 0)
        {       	
        if (side == metaside)
        {
          return this.iconFurnaceInput;
        }
        else if (metaside == 2 && side == 4 || metaside == 3 && side == 5 || metaside == 4 && side == 3 || metaside == 5 && side == 2)
        {
           return this.iconFurnace;
        }
        }
        return this.iconFurnaceSide;
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

        world.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
    }

    @Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        int original = metadata & 3;
        int change = 0;

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

        TileEntity te = par1World.getTileEntity(x, y, z);
        if (te instanceof TileBaseUniversalElectrical)
        {
            ((TileBaseUniversalElectrical) te).updateFacing();
        }

        par1World.setBlockMetadataWithNotify(x, y, z, (metadata & 12) + change, 3);
        return true;
    }

    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!par1World.isRemote)
        {
            par5EntityPlayer.openGui(BeyondSpace.instance, -1, par1World, x, y, z);
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
       return new UltimateFurnaceTileEntity();           
    }

    public ItemStack getUltimateFurnace()
    {
        return new ItemStack(this, 1, 0);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(this.getUltimateFurnace());
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata & 12;
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
        int tier = (meta >= 8 ? 2 : 1);
        switch (meta & 4)
        {
        case 0:
            return GCCoreUtil.translate("tile.ultimateFurnace.description");
        }
        return "";
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }
}