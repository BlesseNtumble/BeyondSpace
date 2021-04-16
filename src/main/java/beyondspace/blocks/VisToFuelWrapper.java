package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.VisToFuelWrapperTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class VisToFuelWrapper extends Block implements ITileEntityProvider {

	public VisToFuelWrapper() {
		super(Material.iron);
		this.setBlockName("VisToFuelWrapper");
        this.setBlockTextureName(ModInfo.MODID + ":VisToFuelWrapper");
        this.setCreativeTab(BeyondSpace.bsTab);
        this.setHardness(5.0F);
        this.setHarvestLevel("pickaxe", 1);
        this.setResistance(25.0F);
        this.setStepSound(soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new VisToFuelWrapperTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Fuel: " + (((double)((int)((((VisToFuelWrapperTileEntity)world.getTileEntity(x, y, z)).fuel) * 10))/10))));
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "Vis: " + ((VisToFuelWrapperTileEntity)world.getTileEntity(x, y, z)).vis));
		}
		return true;
    }
}
