package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.NuclearReactorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class NuclearReactor extends Block implements ITileEntityProvider {

	public NuclearReactor() {
		super(Material.iron);
		this.setBlockName("NuclearReactor");
        this.setBlockTextureName(ModInfo.MODID + ":NuclearReactor");
        this.setCreativeTab(BeyondSpace.bsTab);
        this.setHardness(5.0F);
        this.setHarvestLevel("pickaxe", 2);
        this.setResistance(256.0F);
        this.setStepSound(soundTypeMetal);
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new NuclearReactorTileEntity();
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (side > 1) ((NuclearReactorTileEntity) world.getTileEntity(x, y, z)).handOperateSlots(player, side - 2);
		if (!world.isRemote && side == 1) player.addChatMessage(new ChatComponentText("SERVER: Energy: " + ((NuclearReactorTileEntity)world.getTileEntity(x, y, z)).storage.getEnergyStoredGC()));
		if ( world.isRemote && side == 1) player.addChatMessage(new ChatComponentText("CLIENT: Energy: " + ((NuclearReactorTileEntity)world.getTileEntity(x, y, z)).storage.getEnergyStoredGC()));
		return false;
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		NuclearReactorTileEntity tile = ((NuclearReactorTileEntity) world.getTileEntity(x, y, z));
		for (int i = 0; i < tile.slot.length; i++) if (tile.slot[i] != null) {
			EntityItem stack = new EntityItem(world, x, y, z, new ItemStack(tile.slot[i].getItem(), tile.slot[i].stackSize, tile.slot[i].getItemDamage()));
			stack.setVelocity(0, 0, 0);
			world.spawnEntityInWorld(stack);
		}
    }
}