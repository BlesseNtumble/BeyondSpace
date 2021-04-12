package beyondspace.blocks;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.blocks.tileentity.SealableChameleonBlockTileEntity;
import beyondspace.utils.RegistrationsList;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class SealableChameleonBlock extends Block implements ITileEntityProvider {

	public SealableChameleonBlock() {
		super(Material.iron);
		this.setBlockName("SealableChameleonBlock");
		this.setBlockTextureName(ModInfo.MODID + ":SealableChameleonBlock");
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setHardness(1.0F);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new SealableChameleonBlockTileEntity();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
				((SealableChameleonBlockTileEntity)world.getTileEntity(x, y, z)).setBlock(Block.getBlockFromItem(player.getCurrentEquippedItem().getItem()), player.getCurrentEquippedItem().getItemDamage());
			}
			if (player.getCurrentEquippedItem() == null) {
				((SealableChameleonBlockTileEntity)world.getTileEntity(x, y, z)).setBlock(RegistrationsList.chameleonBlock, 0);
			}
		} else {
			if (!world.isRemote) player.addChatComponentMessage(new ChatComponentText("Current block: " + StatCollector.translateToLocal(((SealableChameleonBlockTileEntity)world.getTileEntity(x, y, z)).getBlock().getUnlocalizedName() + ".name")));
		}
        return true;
    }
	
	@Override
	public int getRenderType() {
		return -1;
	}
}