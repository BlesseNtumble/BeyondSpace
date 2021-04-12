package beyondspace.blocks.tileentity;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import micdoodle8.mods.galacticraft.api.item.IItemOxygenSupply;
import micdoodle8.mods.galacticraft.core.Constants;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygen;
import micdoodle8.mods.galacticraft.core.util.FluidUtil;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.asteroids.AsteroidsModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class UltimateOxygenModuleTileEntity extends TileEntityOxygen implements IPacketReceiver, ISidedInventory, IFluidHandler
{
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
    public int scaledOxygenLevel;
    private int lastScaledOxygenLevel;
    public static final int OUTPUT_PER_TICK = 4000;
    public static final int OXYGEN_CAPACITY = 280000;
    private ItemStack[] containingItems = new ItemStack[1];

    public UltimateOxygenModuleTileEntity()
    {
        super(OXYGEN_CAPACITY, 40);
        this.storage.setCapacity(0);
        this.storage.setMaxExtract(0);
    }

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
	    	ItemStack oxygenItemStack = this.getStackInSlot(0);
	    	if (oxygenItemStack != null && oxygenItemStack.getItem() instanceof IItemOxygenSupply)
	    	{
	    		IItemOxygenSupply oxygenItem = (IItemOxygenSupply) oxygenItemStack.getItem();
	    		float oxygenDraw = Math.min(this.oxygenPerTick * 2.5F, this.maxOxygen - this.storedOxygen);
	    		this.storedOxygen += oxygenItem.discharge(oxygenItemStack, oxygenDraw);
	    		if (this.storedOxygen > this.maxOxygen) this.storedOxygen = this.maxOxygen;
	    	}
        }
    	
        super.updateEntity();

        this.scaledOxygenLevel = this.getScaledOxygenLevel(16);

        if (this.scaledOxygenLevel != this.lastScaledOxygenLevel)
        {
            this.worldObj.func_147479_m(this.xCoord, this.yCoord, this.zCoord);
        }

        this.lastScaledOxygenLevel = this.scaledOxygenLevel;

        this.produceOxygen(ForgeDirection.getOrientation((this.getBlockMetadata() + 2) ^ 1));

        this.lastScaledOxygenLevel = this.scaledOxygenLevel;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        final NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
        this.containingItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final int var5 = var4.getByte("Slot") & 255;

            if (var5 < this.containingItems.length)
            {
                this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
}

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);

        final NBTTagList list = new NBTTagList();

        for (int var3 = 0; var3 < this.containingItems.length; ++var3)
        {
            if (this.containingItems[var3] != null)
            {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.containingItems[var3].writeToNBT(var4);
                list.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", list);
    }

    @Override
    public EnumSet<ForgeDirection> getElectricalInputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public EnumSet<ForgeDirection> getElectricalOutputDirections()
    {
        return EnumSet.noneOf(ForgeDirection.class);
    }

    @Override
    public boolean shouldPullEnergy()
    {
        return false;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return false;
    }

    @Override
    public ForgeDirection getElectricInputDirection()
    {
        return null;
    }

    @Override
    public ItemStack getBatteryInSlot()
    {
        return null;
    }

    @Override
    public boolean shouldUseOxygen()
    {
        return false;
    }

    @Override
    public float getOxygenProvide(ForgeDirection direction)
    {
        return this.getOxygenOutputDirection() == direction ? Math.min(UltimateOxygenModuleTileEntity.OUTPUT_PER_TICK, this.getOxygenStored()) : 0.0F;
    }

    @Override
    public EnumSet<ForgeDirection> getOxygenInputDirections()
    {
        return EnumSet.of(ForgeDirection.getOrientation(this.getBlockMetadata() + 2));
    }

    @Override
    public EnumSet<ForgeDirection> getOxygenOutputDirections()
    {
        return EnumSet.of(ForgeDirection.getOrientation((this.getBlockMetadata() + 2) ^ 1));
    }
    
    public ForgeDirection getOxygenOutputDirection()
    {
        return ForgeDirection.getOrientation((this.getBlockMetadata() + 2) ^ 1);
    }
    
    @Override
    public int getSizeInventory()
    {
        return this.containingItems.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.containingItems[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.containingItems[par1] != null)
        {
            ItemStack var3;

            if (this.containingItems[par1].stackSize <= par2)
            {
                var3 = this.containingItems[par1];
                this.containingItems[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.containingItems[par1].splitStack(par2);

                if (this.containingItems[par1].stackSize == 0)
                {
                    this.containingItems[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.containingItems[par1] != null)
        {
            final ItemStack var2 = this.containingItems[par1];
            this.containingItems[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.containingItems[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.OxygenModule.name");
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {
    }

    @Override
    public void closeInventory()
    {
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack)
    {
        return slotID == 0 && itemstack!=null && itemstack.getItem() instanceof IItemOxygenSupply;
    }
    
    //ISidedInventory
    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return new int[] { 0 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack itemstack, int side)
    {
        if (slotID ==0 && this.isItemValidForSlot(slotID, itemstack))
        {
           	return itemstack.getItemDamage() < itemstack.getItem().getMaxDamage();
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int side)
    {
        if (slotID ==0 && itemstack != null)
        {
    		return FluidUtil.isEmptyContainer(itemstack);
        }
        return false;
    }
    
    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (from.ordinal() == this.getBlockMetadata() + 2 && GalacticraftCore.isPlanetsLoaded)
        {
            return fluid != null && fluid.getName().equals(AsteroidsModule.fluidLiquidOxygen.getName());
        }

        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        int used = 0;

        if (resource != null && this.canFill(from, resource.getFluid()))
        {
            used = (int) (this.receiveOxygen(resource.amount / Constants.LOX_GAS_RATIO, doFill) * Constants.LOX_GAS_RATIO);
        }

        return used;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        FluidTankInfo[] tankInfo = new FluidTankInfo[] {};
        int metaside = this.getBlockMetadata() + 2;
        int side = from.ordinal();

        if (metaside == side && GalacticraftCore.isPlanetsLoaded)
        {
            tankInfo = new FluidTankInfo[] { new FluidTankInfo(new FluidStack(AsteroidsModule.fluidLiquidOxygen, (int) (this.getOxygenStored() * Constants.LOX_GAS_RATIO)), (int) (OXYGEN_CAPACITY * Constants.LOX_GAS_RATIO)) };
        }
        return tankInfo;
    }
}