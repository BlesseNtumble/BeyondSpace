package beyondspace.proxy.network;

import beyondspace.blocks.container.AdvancedRefineryContainer;
import beyondspace.blocks.container.GasGeneratorContainer;
import beyondspace.blocks.container.LightningRodBaseContainer;
import beyondspace.blocks.container.OxygenModuleContainer;
import beyondspace.blocks.container.UltimateFurnaceContainer;
import beyondspace.blocks.gui.AdvancedRefineryGui;
import beyondspace.blocks.gui.GasGeneratorGui;
import beyondspace.blocks.gui.LightningRodBaseGui;
import beyondspace.blocks.gui.OxygenModuleGui;
import beyondspace.blocks.gui.UltimateFurnaceGui;
import beyondspace.blocks.tileentity.AdvancedRefineryTileEntity;
import beyondspace.blocks.tileentity.GasGeneratorTileEntity;
import beyondspace.blocks.tileentity.LightningRodBaseTileEntity;
import beyondspace.blocks.tileentity.UltimateFurnaceTileEntity;
import beyondspace.blocks.tileentity.UltimateOxygenModuleTileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.entities.player.GCPlayerStats;
import micdoodle8.mods.galacticraft.core.util.PlayerUtil;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BSGuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		EntityPlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player, false);

		if (playerBase == null) {
			player.addChatMessage(new ChatComponentText("Bug! - player instance null server-side."));
			return null;
		}
		GCPlayerStats stats = GCPlayerStats.get(playerBase);

		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof UltimateOxygenModuleTileEntity) {
				return new OxygenModuleContainer(player.inventory, (UltimateOxygenModuleTileEntity) tile);
			} else if (tile instanceof UltimateFurnaceTileEntity) {
				return new UltimateFurnaceContainer(player.inventory, (UltimateFurnaceTileEntity) tile);
			} else if (tile instanceof LightningRodBaseTileEntity) {
				return new LightningRodBaseContainer(player.inventory, (LightningRodBaseTileEntity) tile);
			} else if(tile instanceof AdvancedRefineryTileEntity) {
				return new AdvancedRefineryContainer(player.inventory, (AdvancedRefineryTileEntity) tile);
			} else if(tile instanceof GasGeneratorTileEntity) {
				return new GasGeneratorContainer(player.inventory, (GasGeneratorTileEntity) tile);
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			return this.getClientGuiElement(ID, player, world, new Vector3(x, y, z));
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	private Object getClientGuiElement(int ID, EntityPlayer player, World world, Vector3 position) {
		EntityClientPlayerMP playerClient = PlayerUtil.getPlayerBaseClientFromPlayer(player, false);

		TileEntity tile = world.getTileEntity(position.intX(), position.intY(), position.intZ());

		if (tile != null) {
			if (tile instanceof UltimateOxygenModuleTileEntity) {
				return new OxygenModuleGui(player.inventory, (UltimateOxygenModuleTileEntity) tile);
			} else if (tile instanceof UltimateFurnaceTileEntity) {
				return new UltimateFurnaceGui(player.inventory, (UltimateFurnaceTileEntity) tile);
			} else if (tile instanceof LightningRodBaseTileEntity) {
				return new LightningRodBaseGui(player.inventory, (LightningRodBaseTileEntity) tile);
			} else if(tile instanceof AdvancedRefineryTileEntity) {
				return new AdvancedRefineryGui(player.inventory, (AdvancedRefineryTileEntity) tile);
			} else if(tile instanceof GasGeneratorTileEntity) {
				return new GasGeneratorGui(player.inventory, (GasGeneratorTileEntity) tile);
			}
		}
		return null;
	}
}