package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class H2ONH3CH4Ice extends Block {

	public H2ONH3CH4Ice() {
		super(Material.packedIce);
		this.setBlockName("H2ONH3CH4Ice");
        this.setBlockTextureName(ModInfo.MODID + ":H2ONH3CH4Ice");
        this.setCreativeTab(BeyondSpace.bsTab);
        this.setHardness(4.0F);
        this.setHarvestLevel("pickaxe", 2);
        this.setResistance(200.0F);
        this.slipperiness = 0.95F;
        this.setStepSound(soundTypeGlass);
	}
}