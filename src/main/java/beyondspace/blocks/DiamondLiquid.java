package beyondspace.blocks;

import beyondspace.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class DiamondLiquid extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public DiamondLiquid(Fluid fluid, Material material) {
        super(fluid, material);
        this.setBlockName("DiamondLiquid");
        //this.setCreativeTab(GAMain.gaTab);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(ModInfo.MODID + ":DiamondStill");
        flowingIcon = register.registerIcon(ModInfo.MODID + ":DiamondFlowing");
    }

    public void onBlockAdded(World world, int x, int y, int z) {
    	if (!world.isRemote) {
    		if (world.provider.dimensionId == 0) {
    			world.setBlockToAir(x, y, z);
    			if (world.rand.nextInt(4) == 0) world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(Items.diamond, 1)));
    		}
    	}
    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return true;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return true;
        }
        return super.displaceIfPossible(world, x, y, z);
    }
}