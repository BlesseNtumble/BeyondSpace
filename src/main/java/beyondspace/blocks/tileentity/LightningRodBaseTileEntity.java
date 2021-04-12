package beyondspace.blocks.tileentity;

import java.util.EnumSet;
import java.util.List;

import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.relauncher.Side;
import galaxyspace.api.dimension.ILightning;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.core.energy.EnergyConfigHandler;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class LightningRodBaseTileEntity extends TileBaseUniversalElectricalSource implements IPacketReceiver, IDisableableMachine, IInventory, ISidedInventory, IConnector
{
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean disabled = false;
    @NetworkedField(targetSide = Side.CLIENT)
    public int disableCooldown = 0;
    private ItemStack[] containingItems = new ItemStack[1];
    public static float oneHit = 10000 * EnergyConfigHandler.RF_RATIO;
    @NetworkedField(targetSide = Side.CLIENT)
    public int energyLevel;
    public int lastEnergyLevel;
    
    public LightningRodBaseTileEntity() {
    	storage.setCapacity(5000000 * EnergyConfigHandler.RF_RATIO);
    	storage.setMaxExtract(oneHit);
        storage.setMaxReceive(oneHit);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if (!this.worldObj.isRemote){
            this.recharge(this.containingItems[0]);
            if (this.disableCooldown > 0){
                this.disableCooldown--;
            }
        }
        	if (!this.getDisabled(0)){

            	float multiplier = (this.worldObj.provider instanceof ILightning) ? 1.25F : 1.0F;
            	
            	if (this.getWorldObj().isAirBlock(xCoord, yCoord + 3, zCoord) && this.worldObj.getBlock(xCoord, yCoord + 1, zCoord) == RegistrationsList.lightningrodMid && this.worldObj.getBlock(xCoord, yCoord + 2, zCoord) == RegistrationsList.lightningrodTop ) {
    				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord - 16, this.yCoord + 1, this.zCoord - 16, this.xCoord + 16, this.yCoord + 8, this.zCoord + 16);
    				
    				List<EntityLightningBolt> list = this.worldObj.getEntitiesWithinAABB(EntityLightningBolt.class, aabb);
    				
    				for(EntityLightningBolt light : list)
    				{
    					light.setPosition(this.xCoord, this.yCoord + 2.5, this.zCoord);
    					if (!this.worldObj.isRemote){
	    					if (storage.getEnergyStoredGC() + (oneHit * multiplier * multiplier) <= this.getMaxEnergyStoredGC()) {
	    						storage.setEnergyStored(this.getEnergyStoredGC() + (oneHit * multiplier * multiplier));
	    					}
	    					else {
	    						this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
	    						this.worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
	    						this.worldObj.setBlockToAir(xCoord, yCoord + 2, zCoord);
	    						this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 5.0F, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
	    					}
    					}
    				}
    				/*
    				Iterator iterator = list.iterator();
    				EntityLightningBolt bolt = null;
    				
    				while (iterator.hasNext()) {
    					bolt = (EntityLightningBolt) iterator.next();
    					bolt.setPosition(this.xCoord + 0.5, this.yCoord + 2.5, this.zCoord + 0.5);
    					
    					if (storage.getEnergyStoredGC() + (oneHit * multiplier * multiplier) <= this.getMaxEnergyStoredGC()) {
    						storage.setEnergyStored(this.getEnergyStoredGC() + (oneHit * multiplier * multiplier));
    					} else {
    						this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    						this.worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
    						this.worldObj.setBlockToAir(xCoord, yCoord + 2, zCoord);
    						this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 5.0F, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
						}
					}*/
				}			
		}
        
        this.energyLevel = (int) Math.floor((this.getEnergyStoredGC() + 49) * 16 / this.getMaxEnergyStoredGC());
        if (this.energyLevel != this.lastEnergyLevel){
            this.worldObj.func_147479_m(this.xCoord, this.yCoord, this.zCoord);
        }    
        this.lastEnergyLevel = this.energyLevel;
        
        this.produce();
        }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.storage.setCapacity(nbt.getFloat("maxEnergy"));
        this.setDisabled(0, nbt.getBoolean("disabled"));
        this.disableCooldown = nbt.getInteger("disabledCooldown");

        final NBTTagList list = nbt.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < list.tagCount(); i++) {
            final NBTTagCompound nbt1 = list.getCompoundTagAt(i);
            final int id = nbt1.getByte("Slot") & 255;

            if (id < this.containingItems.length) {
                this.containingItems[id] = ItemStack.loadItemStackFromNBT(nbt1);
            }
        }
    }

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setFloat("maxEnergy", this.getMaxEnergyStoredGC());
		nbt.setInteger("disabledCooldown", this.disableCooldown);
		nbt.setBoolean("disabled", this.getDisabled(0));

		final NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.containingItems.length; i++) {
			if (this.containingItems[i] != null) {
				final NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				this.containingItems[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}

		nbt.setTag("Items", list);
	}

    @Override
	public EnumSet<ForgeDirection> getElectricalInputDirections() {
		return EnumSet.noneOf(ForgeDirection.class);
	}

	@Override
	public EnumSet<ForgeDirection> getElectricalOutputDirections() {
		int metadata = this.getBlockMetadata() & 3;
		return EnumSet.of(ForgeDirection.getOrientation((metadata + 2) ^ 1), ForgeDirection.UNKNOWN);
	}

    @Override
    public ForgeDirection getElectricalOutputDirectionMain() {
        int metadata = this.getBlockMetadata() & 3;

        return ForgeDirection.getOrientation((metadata + 2) ^ 1);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public String getInventoryName() {
        return GCCoreUtil.translate("tile.LightningRodBase.name");
    }

	@Override
	public void setDisabled(int index, boolean disabled) {
		if (this.disableCooldown == 0) {
			this.disabled = disabled;
			this.disableCooldown = 20;
		}
	}

	@Override
	public boolean getDisabled(int index) {
		return this.disabled;
	}

	public int getScaledElecticalLevel(int i) {
		return (int) Math.floor(this.getEnergyStoredGC() * i / this.getMaxEnergyStoredGC());
	}

	@Override
	public int getSizeInventory() {
		return this.containingItems.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.containingItems[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.containingItems[par1] != null) {
			ItemStack var3;

			if (this.containingItems[par1].stackSize <= par2) {
				var3 = this.containingItems[par1];
				this.containingItems[par1] = null;
				return var3;
			} else {
				var3 = this.containingItems[par1].splitStack(par2);

				if (this.containingItems[par1].stackSize == 0) {
					this.containingItems[par1] = null;
				}

				return var3;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.containingItems[par1] != null) {
			final ItemStack var2 = this.containingItems[par1];
			this.containingItems[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.containingItems[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int side) {
		return this.isItemValidForSlot(slotID, itemstack);
	}

	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int side) {
		return slotID == 0;
	}

	@Override
	public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
		return slotID == 0 && ItemElectricBase.isElectricItem(itemstack.getItem());
	}

	@Override
	public boolean canConnect(ForgeDirection direction, NetworkType type) {
		if (direction == null || direction.equals(ForgeDirection.UNKNOWN) || type != NetworkType.POWER) {
			return false;
		}

		return direction == this.getElectricalOutputDirectionMain();
	}
}