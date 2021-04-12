package beyondspace.blocks.tileentity;

import java.util.EnumSet;

import cpw.mods.fml.relauncher.Side;
import micdoodle8.mods.galacticraft.api.tile.IDisableableMachine;
import micdoodle8.mods.galacticraft.api.transmission.NetworkType;
import micdoodle8.mods.galacticraft.api.transmission.tile.IConnector;
import micdoodle8.mods.galacticraft.api.world.IAtmosphericGas;
import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectricalSource;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.items.ItemAtmosphericValve;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class GasGeneratorTileEntity extends TileBaseUniversalElectricalSource implements IPacketReceiver, IDisableableMachine, IInventory, ISidedInventory, IConnector, IFluidHandler
{
    @NetworkedField(targetSide = Side.CLIENT)
    public boolean disabled = false;
    @NetworkedField(targetSide = Side.CLIENT)
    public int disableCooldown = 0;
    private ItemStack[] containingItems = new ItemStack[1];
    public static float min_gen = 30.0F;
    public static float max_gen = 200.0F;
    @NetworkedField(targetSide = Side.CLIENT)
    public int gasLevel;
    public int lastGasLevel;
    public int natureGasProducts = -1;
    @NetworkedField(targetSide = Side.CLIENT)
    public FluidTank gas = new FluidTank(2000);
    @NetworkedField(targetSide = Side.CLIENT)
    public int gasType = -1;
    
	public enum NatureGas {
		NATURE_GAS(0, "atmosphericgases", "xxyyzz");

		int index;
		String gas;
		String liquid;

		NatureGas(int id, String fluidname, String outputname) {
			this.index = id;
			this.gas = new String(fluidname);
			this.liquid = new String(outputname);
		}
	}

    public GasGeneratorTileEntity() {
    	storage.setCapacity(30000);
    	storage.setMaxExtract(max_gen - min_gen);
        storage.setMaxReceive(max_gen - min_gen);
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        if (!this.worldObj.isRemote){
            this.recharge(this.containingItems[0]);
            if (this.disableCooldown > 0){
                this.disableCooldown--;
            }
            if (!this.getDisabled(0)){
            	FluidStack gas = this.gas.getFluid();
                if (gas == null || gas.amount <= 0) this.gasType = -1;
                else this.gasType = this.getIdFromName(gas.getFluid().getName());
                
            	if(this.natureGasProducts == -1){
            		this.natureGasProducts = this.getNatureGasProducts();
            	}
            	ItemStack atmosphericValve = this.containingItems[0];
            	if(atmosphericValve != null && atmosphericValve.getItem() instanceof ItemAtmosphericValve && this.natureGasProducts > 0){
            		if(this.gasType == -1 || (this.gasType == NatureGas.NATURE_GAS.index && this.gas.getFluidAmount() <= this.gas.getCapacity())){
            			Block blockUp = this.worldObj.getBlock(xCoord, yCoord + 1, zCoord);
            			if(blockUp != null && blockUp.getMaterial() == Material.air && blockUp!=GCBlocks.breatheableAir && blockUp!=GCBlocks.brightBreatheableAir){
            				FluidStack natureGas = FluidRegistry.getFluidStack(NatureGas.NATURE_GAS.gas, getNatureGasProducts());
            				this.gas.fill(natureGas, true);
            				this.gasType = NatureGas.NATURE_GAS.index;
            				generate();
            			}
            		}
            	} else{
            		generate();
            		this.gas.drain(1, true);
            	}
            }
       }
        this.gasLevel = (int) Math.floor((this.gas.getFluidAmount() + 49) * 8 / this.gas.getCapacity());
        if (this.gasLevel != this.lastGasLevel){
            this.worldObj.func_147479_m(this.xCoord, this.yCoord, this.zCoord);
        }    
        this.lastGasLevel = this.gasLevel;
        this.produce();
        }

    private void generate(){
    	if(this.storage.getEnergyStoredGC() + 5 <= this.getMaxEnergyStoredGC() && this.gas.getFluidAmount() > 0 && this.gas.getFluidAmount() < 500){
    		storage.setEnergyStored(this.storage.getEnergyStoredGC() + 5);
    	}
    	if(this.storage.getEnergyStoredGC() + min_gen <= this.getMaxEnergyStoredGC() && this.gas.getFluidAmount() >= 500 && this.gas.getFluidAmount() < 800){
			storage.setEnergyStored(this.storage.getEnergyStoredGC() + min_gen);
		}
    	if(this.storage.getEnergyStoredGC() + 70 <= this.getMaxEnergyStoredGC() && this.gas.getFluidAmount() >= 800 && this.gas.getFluidAmount() < 1000){
    		storage.setEnergyStored(this.storage.getEnergyStoredGC() + 70);
    	}
		if(this.storage.getEnergyStoredGC() + max_gen <= this.getMaxEnergyStoredGC() && this.gas.getFluidAmount() >= 1000 && this.gas.getFluidAmount() <= this.gas.getCapacity()){
			storage.setEnergyStored(this.storage.getEnergyStoredGC() + max_gen);
		}
    }
    public int getIdFromName(String gasname)
    {
        for (NatureGas type : NatureGas.values())
        {
            if (type.gas.equals(gasname))
            {
                return type.index;
            }
        }

        return -1;
    }

    
    public int getNatureGasProducts() {
		WorldProvider provider = this.worldObj.provider;
		int result = 0;
       /* if(provider instanceof WorldProviderMars){ result = 2; return result;}
		if(provider instanceof WorldProviderJupiter){ result = 5; return result;}*/
		if(provider instanceof IGalacticraftWorldProvider)
		{
			if(((IGalacticraftWorldProvider)provider).isGasPresent(IAtmosphericGas.HYDROGEN))				
				return 5;
			if(((IGalacticraftWorldProvider)provider).isGasPresent(IAtmosphericGas.METHANE))				
				return 3;
			if(((IGalacticraftWorldProvider)provider).isGasPresent(IAtmosphericGas.ARGON))				
				return 1;
		}
		return 0;
	}
    
    public int getScaledGasLevel(int i){
        return this.gas.getFluid() != null ? this.gas.getFluid().amount * i / this.gas.getCapacity() : 0;
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
        
        if (nbt.hasKey("gas")){
            this.gas.readFromNBT(nbt.getCompoundTag("gas"));
        }

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

		if (this.gas.getFluid() != null){
            nbt.setTag("gas", this.gas.writeToNBT(new NBTTagCompound()));
        }
		
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
        return GCCoreUtil.translate("tile.GasGenerator.name");
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
		return slotID == 0 && itemstack.getItem() instanceof ItemAtmosphericValve;
	}

	@Override
	public boolean canConnect(ForgeDirection direction, NetworkType type) {
		if (direction == null || direction.equals(ForgeDirection.UNKNOWN) || type != NetworkType.POWER) {
			return false;
		}

		return direction == this.getElectricalOutputDirectionMain();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int used = 0;

        if (resource != null && this.canFill(from, resource.getFluid()))
        {
            int type = this.getIdFromName(FluidRegistry.getFluidName(resource));

            if (this.gasType == -1 || (this.gasType == type && this.gas.getFluidAmount() < this.gas.getCapacity()))
            {
                if (type > 0)
                {
	            	float conversion = 2F * Constants.LOX_GAS_RATIO;
	                FluidStack fluidToFill = new FluidStack(resource.getFluid(), (int) (resource.amount * conversion));
	            	used = MathHelper.ceiling_float_int(this.gas.fill(fluidToFill, doFill) / conversion);
                }
                else
                	used = this.gas.fill(resource, doFill);
            }
        }

        return used;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] tankInfo = new FluidTankInfo[] {};
        int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();

        if (metaside == side){
            tankInfo = new FluidTankInfo[] { new FluidTankInfo(this.gas) };
        }
        return tankInfo;
	}
}