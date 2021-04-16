package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.LaserTurretTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class LaserTurret extends Block implements ITileEntityProvider {

	public LaserTurret() {
		super(Material.iron);
		this.setBlockName("LaserTurret");
        this.setBlockTextureName(ModInfo.MODID + ":LaserTurret");
        this.setCreativeTab(BeyondSpace.bsTab);
        this.setHardness(1.0F);
        this.setHarvestLevel("pickaxe", 2);
        this.setResistance(18.0F);
        this.setStepSound(soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new LaserTurretTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			player.addChatComponentMessage(new ChatComponentText("Energy: " + ((LaserTurretTileEntity)world.getTileEntity(x, y, z)).energy));
		}
        return false;
    }
}