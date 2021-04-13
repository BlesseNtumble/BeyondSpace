package beyondspace.items;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.asjlib.ASJUtilities;
import beyondspace.proxy.network.CommonEventHandler;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class PlasmaHammer extends ItemTool implements IItemElectric {

	private static final Set someVanillaStuff = Sets.newHashSet(new Block[] {	/* Pickaxe -> */ Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail,
			/*	 Axe -> */ Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin,
			/* 	 Spade -> */ Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	
	public static IIcon[] textures = new IIcon[2];
	public float transferMax = 200.0F;
	
	public PlasmaHammer() {
		super(0.0F, ToolMaterial.WOOD, someVanillaStuff);
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setUnlocalizedName("PlasmaHammer");
		this.setMaxDamage(100000);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		textures[0] = iconRegister.registerIcon(ModInfo.MODID + ":PlasmaHammerOff");
		textures[1] = iconRegister.registerIcon(ModInfo.MODID + ":PlasmaHammer");
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

					if (!super.onBlockStartBreak(stack, xPos, yPos, zPos, player) && !player.isSneaking()) {
						CommonEventHandler.dischargeStack(stack, 50.0F);
						breakExtraBlock(player.worldObj, xPos, yPos, zPos, sideHit, player, x, y, z);
					}else{
						CommonEventHandler.dischargeStack(stack, 10.0F);
					}
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
			if (block.removedByPlayer(world, player, x, y, z, false)) {
				block.onBlockDestroyedByPlayer(world, x, y, z, meta); }

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

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean what) {
		if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		String color = "";
		float joules = getElectricityStored(stack);
		if (joules <= getMaxElectricityStored(stack) / 3.0F) {
			color += EnumChatFormatting.DARK_RED;
		} else if (joules > getMaxElectricityStored(stack) * 2.0F / 3.0F) {
			color += EnumChatFormatting.DARK_GREEN;
		} else {
			color += EnumChatFormatting.GOLD;
		}
		list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS(getMaxElectricityStored(stack)));
		if (getElectricityStored(stack) > 0) list.add(EnumChatFormatting.BLUE + "+10 " + StatCollector.translateToLocal("attribute.name.generic.attackDamage"));
	}

	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		setElectricity(stack, 0.0F);
	}
	
	@Override
	public float recharge(ItemStack stack, float energy, boolean doRecharge) {
		float rejectedElectricity = Math.max(getElectricityStored(stack) + energy - getMaxElectricityStored(stack), 0.0F);
		float energyToReceive = energy - rejectedElectricity;
		
		if (energyToReceive > this.transferMax) {
			rejectedElectricity += energyToReceive - this.transferMax;
			energyToReceive = this.transferMax;
		}
		
		if (doRecharge) {
			setElectricity(stack, getElectricityStored(stack) + energyToReceive);
		}
		
		return energyToReceive;
	}

	@Override
	public float discharge(ItemStack stack, float energy, boolean doDischarge) {
		float energyToTransfer = Math.min(getElectricityStored(stack), energy);
		if (doDischarge) {
			setElectricity(stack, getElectricityStored(stack) - energyToTransfer);
		}
		return energyToTransfer;
	}

	@Override
	public float getElectricityStored(ItemStack stack) {
		float energyStored = 0.0F;
		if (stack.getTagCompound().hasKey("electricity")) {
			NBTBase obj = stack.getTagCompound().getTag("electricity");
			if ((obj instanceof NBTTagDouble)) {
				energyStored = ((NBTTagDouble) obj).func_150288_h();
			} else if ((obj instanceof NBTTagFloat)) {
				energyStored = ((NBTTagFloat) obj).func_150288_h();
			}
		}
		
		stack.setItemDamage((int)(stack.getMaxDamage() - (energyStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
		return energyStored;
	}

	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return this.getMaxDamage();
	}

	@Override
	public void setElectricity(ItemStack stack, float joules) {
		float electricityStored = Math.max(Math.min(joules, getMaxElectricityStored(stack)), 0.0F);
		stack.getTagCompound().setFloat("electricity", electricityStored);
		
		stack.setItemDamage((int)(stack.getMaxDamage() - (electricityStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
	}

	@Override
	public float getTransfer(ItemStack stack) {
		return Math.min(this.transferMax , getMaxElectricityStored(stack) - getElectricityStored(stack));
	}

	@Override
	public int getTierGC(ItemStack itemStack) {
		return 1;
	}
}
