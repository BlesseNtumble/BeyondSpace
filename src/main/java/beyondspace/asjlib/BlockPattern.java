package beyondspace.asjlib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPattern extends Block {

	private boolean isOpaque;
	
    public BlockPattern(String modid, Material material, String name, CreativeTabs tab, float hardness, String harvTool, int harvLvl, float resistance, Block.SoundType sound, boolean isOpaque) {
    	super(material);
        this.setBlockName(name);
        this.setBlockTextureName(modid + ":" + name);
        this.setCreativeTab(tab);
        this.setHardness(hardness);
        this.setHarvestLevel(harvTool, harvLvl);
        this.setResistance(resistance);
        this.setStepSound(sound);
        this.isOpaque = isOpaque;
    }

    @Override
    public boolean isOpaqueCube() {
        return isOpaque;
    }
}