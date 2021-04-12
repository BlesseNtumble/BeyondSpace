package beyondspace.items;

import beyondspace.BeyondSpace;
import beyondspace.asjlib.ASJUtilities;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class PlasmaHammer extends Item {

	public PlasmaHammer() {
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setUnlocalizedName("PlasmaHammer");
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		MovingObjectPosition mop = ASJUtilities.getSelectedBlock(player, 1.0F, 4.5D, false);
		if (mop == null)
			return super.onBlockStartBreak(stack, x, y, z, player);
		
		int sideHit = mop.sideHit;

		// we successfully destroyed a block. time to do AOE!
		int xRange = 1;
		int yRange = 1;
		int zRange = 0;
		switch (sideHit) {
		case 0:
		case 1:
			yRange = 0;
			zRange = 1;
			break;
		case 2:
		case 3:
			xRange = 1;
			zRange = 0;
			break;
		case 4:
		case 5:
			xRange = 0;
			zRange = 1;
			break;
		}

		for (int xPos = x - xRange; xPos <= x + xRange; xPos++)
			for (int yPos = y - yRange; yPos <= y + yRange; yPos++)
				for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
					// don't break the originally already broken block, duh
					if (xPos == x && yPos == y && zPos == z)
						continue;

					if (!super.onBlockStartBreak(stack, xPos, yPos, zPos, player))
						breakExtraBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, x, y, z);
				}

		return super.onBlockStartBreak(stack, x, y, z, player);
	}

	protected void breakExtraBlock(World world, int x, int y, int z, int sidehit, EntityPlayer playerEntity, int refX,
			int refY, int refZ) {
		// prevent calling that stuff for air blocks, could lead to unexpected
		// behaviour since it fires events
		if (world.isAirBlock(x, y, z))
			return;

		// what?
		if (!(playerEntity instanceof EntityPlayerMP))
			return;

		EntityPlayerMP player = (EntityPlayerMP) playerEntity;

		Block block = world.getBlock(x, y, z);
		if (block.getBlockHardness(world, x, y, z) == -1.0F) return;
		int meta = world.getBlockMetadata(x, y, z);

		// send the blockbreak event
		BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, player.theItemInWorldManager.getGameType(),
				player, x, y, z);
		if (event.isCanceled())
			return;

		if (player.capabilities.isCreativeMode) {
			block.onBlockHarvested(world, x, y, z, meta, player);
			if (block.removedByPlayer(world, player, x, y, z, false))
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);

			// send update to client
			if (!world.isRemote) {
				player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
			}
			return;
		}

		// callback to the tool the player uses. Called on both sides. This
		// damages the tool n stuff.
		player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);

		// server sided handling
		if (!world.isRemote) {
			// serverside we reproduce ItemInWorldManager.tryHarvestBlock

			// ItemInWorldManager.removeBlock
			block.onBlockHarvested(world, x, y, z, meta, player);

			if (block.removedByPlayer(world, player, x, y, z, true)) // boolean
																		// is if
																		// block
																		// can
																		// be
																		// harvested,
																		// checked
																		// above
			{
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
				block.harvestBlock(world, player, x, y, z, meta);
				block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
			}

			// always send block update to client
			player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
		}
		// client sided handling
		else {
			// PlayerControllerMP pcmp =
			// Minecraft.getMinecraft().playerController;
			// clientside we do a "this clock has been clicked on long enough to
			// be broken" call. This should not send any new packets
			// the code above, executed on the server, sends a block-updates
			// that give us the correct state of the block we destroy.

			// following code can be found in
			// PlayerControllerMP.onPlayerDestroyBlock
			world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
			if (block.removedByPlayer(world, player, x, y, z, true)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			}
			// callback to the tool
			ItemStack itemstack = player.getCurrentEquippedItem();
			if (itemstack != null) {
				itemstack.func_150999_a(world, block, x, y, z, player);

				if (itemstack.stackSize == 0) {
					player.destroyCurrentEquippedItem();
				}
			}

			// send an update to the server, so we get an update back
			// This fixes very fast tools sometimes resulting in ghost blocks,
			// but causes a bit more network traffic. Should be fine in theory
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(
					new C07PacketPlayerDigging(2, x, y, z, Minecraft.getMinecraft().objectMouseOver.sideHit));
		}
	}
}