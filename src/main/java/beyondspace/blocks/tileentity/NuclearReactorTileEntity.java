package beyondspace.blocks.tileentity;

import java.util.EnumSet;

import beyondspace.asjlib.ASJUtilities;
import beyondspace.utils.RegistrationsList;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;

public class NuclearReactorTileEntity extends TileBaseUniversalElectricalSource implements IConnector {
	
	/** Nuclear rod slots */
	public ItemStack[] slot = new ItemStack[4];
	/** Is automated */
	public boolean auto;
	/** Energy per tick */
	public static final int perTick = 128;
	
	public NuclearReactorTileEntity() {
		auto = false;
		slot[0] = slot[1] = slot[2] = slot[3] = null;
		this.storage.setCapacity(perTick * RegistrationsList.nuclearRod.getMaxDamage() / 2);
		this.storage.setEnergyStored(0);
		this.storage.setMaxExtract(perTick * slot.length);
		this.storage.setMaxReceive(0);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		//if (!this.worldObj.isRemote) {
			for (int i = 0; i < slot.length; i++) { // For each slot
				if (slot[i] != null) {				// If full
													// If internal buffer isn't full and rod has uranium in it
					if (this.storage.getEnergyStoredGC() + perTick <= this.storage.getCapacityGC() && slot[i].getItem() == RegistrationsList.nuclearRod && slot[i].getItemDamage() < slot[i].getMaxDamage()) {
						this.storage.setEnergyStored(this.storage.getEnergyStoredGC() + perTick);
						slot[i].setItemDamage(slot[i].getItemDamage() + 1);
					}
													// If rod got completely depleted
					if (slot[i].getItem() == RegistrationsList.nuclearRod && slot[i].getItemDamage() >= slot[i].getMaxDamage()) {
						if (auto) {					// If process is automated
							placeRodToInv(i);		// Removes depleted rod
							takeRodFromInv(i);		// And gets new one
						}							// Or jyst leaves all as is
						else slot[i] = new ItemStack(RegistrationsList.nuclearRodEmpty, 1);
					}								// If process is automated
					if (slot[i].getItem() == RegistrationsList.nuclearRodEmpty && !auto) {
						placeRodToInv(i);			// Removes depleted rod
						takeRodFromInv(i);			// And gets new one
					}
				} else /*if (auto)*/ {
					takeRodFromInv(i);				// If slot empty and process is automated - trying to fill slot
				}
			}
		//}
		this.produce(); 							// And finally produce energy
		this.produce(false);
	}
	
	/**
	 * Removes depleted rod from slot to nearby {@link IInventory} <br> Or drops it out into world
	 * @param side side to out
	 * @param depleted Is the rod already depleted */
	private void placeRodToInv(int side) {
		int x = side == 3 ? 1 : side == 2 ? -1 : 0;
		int z = side == 1 ? 1 : side == 0 ? -1 : 0;
		TileEntity tile = this.worldObj.getTileEntity(this.xCoord + x, this.yCoord, this.zCoord + z);
		if (tile != null && tile instanceof IInventory) {
			IInventory inv = (IInventory) tile;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				if (inv.getStackInSlot(i) == null) {
					inv.setInventorySlotContents(i, new ItemStack(RegistrationsList.nuclearRodEmpty, 1, 0));
					slot[side] = null;
					return;
				}
			}
		}
		
		slot[side] = null;
		EntityItem rod = new EntityItem(this.worldObj, this.xCoord + x, this.yCoord, this.zCoord + z, new ItemStack(RegistrationsList.nuclearRodEmpty, 1, 0));
		rod.setVelocity(0, 0, 0);
		this.worldObj.spawnEntityInWorld(rod);
		
	}
	
	/** Tries to get <i>full</i> rod from nearby {@link IInventory} */
	private void takeRodFromInv(int side) {
		int x = side == 3 ? 1 : side == 2 ? -1 : 0;
		int z = side == 1 ? 1 : side == 0 ? -1 : 0;
		
		TileEntity tile = this.worldObj.getTileEntity(this.xCoord + x, this.yCoord, this.zCoord + z);
		if (tile != null && tile instanceof IInventory) {
			IInventory inv = (IInventory) tile;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == RegistrationsList.nuclearRod) {
					slot[side] = new ItemStack(RegistrationsList.nuclearRod, 1, inv.getStackInSlot(i).getItemDamage());
					inv.setInventorySlotContents(i, null);
					return;
				}
			}
		}
	}
	
	/** Allows to operate with slots by hands */
	public void handOperateSlots(EntityPlayer player, int side) {
		if (player.isSneaking()) { // Remove slot contents
			if (player.getCurrentEquippedItem() == null && slot[side] != null) {
				player.inventory.addItemStackToInventory(slot[side]);
				slot[side] = null;
			}
		} else {
			if (player.getCurrentEquippedItem() == null) { // Debug message
				if (slot[side] != null) {
					if (!this.worldObj.isRemote) player.addChatMessage(new ChatComponentText("SERVER: Slot-" + side + " contains " + slot[side].stackSize + 'x' + ASJUtilities.getItemName(slot[side].getItem()) + ':' + slot[side].getItemDamage()));
					if ( this.worldObj.isRemote) player.addChatMessage(new ChatComponentText("CLIENT: Slot-" + side + " contains " + slot[side].stackSize + 'x' + ASJUtilities.getItemName(slot[side].getItem()) + ':' + slot[side].getItemDamage()));
				} else {
					if (!this.worldObj.isRemote) player.addChatMessage(new ChatComponentText("SERVER: Slot-" + side + " is empty"));
					if ( this.worldObj.isRemote) player.addChatMessage(new ChatComponentText("CLIENT: Slot-" + side + " is empty"));
				}
			}
			
			// Fill slot
			if (slot[side] == null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == RegistrationsList.nuclearRod) {
				slot[side] = new ItemStack(RegistrationsList.nuclearRod, 1, player.getCurrentEquippedItem().getItemDamage());
				player.getCurrentEquippedItem().stackSize--;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.storage.setEnergyStored(nbt.getInteger("energy"));
		this.storage.setCapacity(perTick * RegistrationsList.nuclearRod.getMaxDamage() / 2);
		
		NBTTagList items = nbt.getTagList("Items", 10);

        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound stack = items.getCompoundTagAt(i);
            int slot = stack.getByte("Slot") & 255;

            if (slot < this.slot.length) {
                this.slot[slot] = ItemStack.loadItemStackFromNBT(stack);
            }
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("energy", this.storage.getEnergyStoredGC());
		nbt.setFloat("maxEnergy", perTick * RegistrationsList.nuclearRod.getMaxDamage() / 2);
		
		NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.slot.length; ++i) {
            if (this.slot[i] != null) {
                NBTTagCompound stack = new NBTTagCompound();
                stack.setByte("Slot", (byte) i);
                this.slot[i].writeToNBT(stack);
                items.appendTag(stack);
            }
        }
        nbt.setTag("Items", items);
	}

	@Override
	public EnumSet<ForgeDirection> getElectricalInputDirections() {
		return EnumSet.noneOf(ForgeDirection.class);
	}

	@Override
	public EnumSet<ForgeDirection> getElectricalOutputDirections() {
		return EnumSet.of(ForgeDirection.UP);
	}
	
	@Override
    public ForgeDirection getElectricalOutputDirectionMain() {
        return ForgeDirection.UP;
    }
	
	@Override
	public boolean canConnect(ForgeDirection direction, NetworkType type) {
		return direction != null && direction == this.getElectricalOutputDirectionMain() && type == NetworkType.POWER;
	}
}