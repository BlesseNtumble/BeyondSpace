package beyondspace.blocks.tileentity;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import micdoodle8.mods.galacticraft.core.blocks.GCBlocks;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.energy.tile.EnergyStorageTile;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseElectricBlockWithInventory;
import micdoodle8.mods.galacticraft.core.network.IPacketReceiver;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.miccore.Annotations.NetworkedField;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class UltimateFurnaceTileEntity extends TileBaseElectricBlockWithInventory implements ISidedInventory, IPacketReceiver
{
	
    public static int PROCESS_TIME_REQUIRED = 40;
    //1 sec - 20 tick;
    @NetworkedField(targetSide = Side.CLIENT)
    public int processTimeRequired = PROCESS_TIME_REQUIRED;

    @NetworkedField(targetSide = Side.CLIENT)
    public int processTicks = 0;
    private ItemStack[] containingItems = new ItemStack[5];
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

    private boolean initialised = false;

    public UltimateFurnaceTileEntity()
    {
        this(1);
    }
    public UltimateFurnaceTileEntity(int tier)
    {
        this.initialised = true;
        this.setUltimateFurnace();
    }
    
    private void setUltimateFurnace()
    {
        this.storage.setCapacity(50000);
        this.storage.setMaxExtract(75);
        this.setTierGC(2);   	
    }

    @Override
    public void updateEntity()
    {
        if (!this.initialised )
        {
            int metadata = this.getBlockMetadata();
            //for version update compatibility
            Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
            if (b == GCBlocks.machineBase)
            {
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, GCBlocks.machineTiered, 4, 2);
            }
            else if (metadata >= 8)
            {
            	this.setUltimateFurnace();
            }
            this.initialised = true;
        }

        super.updateEntity();

        if (!this.worldObj.isRemote)
        {
            if (this.canProcessFirst() || this.canProcessSecond())
            {
                if (this.hasEnoughEnergyToRun)
                {
                    //50% extra speed boost for Tier 2 machine if powered by Tier 2 power
                    if (this.tierGC == 2) this.processTimeRequired = this.PROCESS_TIME_REQUIRED / (1 + this.poweredByTierGC);

                    if (this.processTicks == 0)
                    {
                        this.processTicks = this.processTimeRequired;
                    }
                    else
                    {
                        if (--this.processTicks <= 0)
                        {
                            this.smeltItemFirst();
                            this.smeltItemSecond();
                            this.processTicks = this.canProcessFirst() ? this.processTimeRequired : 0;
                        }
                    }
                }
                else if (this.processTicks > 0 && this.processTicks < this.processTimeRequired)
                {
                    //Apply a "cooling down" process if the electric furnace runs out of energy while smelting
                    if (this.worldObj.rand.nextInt(4) == 0)
                    {
                        this.processTicks++;
                    }
                }
            }
            else
            {
                this.processTicks = 0;
            }
        }
    }

    public boolean canProcessFirst()
    {
        if (this.containingItems[1] == null || FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]) == null)
        {
            return false;
        }

        if (this.containingItems[2] != null)
        {
            if (!this.containingItems[2].isItemEqual(FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1])))
            {
                return false;
            }

            if (this.containingItems[2].stackSize + 1 > 64)
            {
                return false;
            }
        }

        return true;
    }
    
    public boolean canProcessSecond()
    {
        if (this.containingItems[3] == null || FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[3]) == null)
        {
            return false;
        }

        if (this.containingItems[4] != null)
        {
            if (!this.containingItems[4].isItemEqual(FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[3])))
            {
                return false;
            }

            if (this.containingItems[4].stackSize + 1 > 64)
            {
                return false;
            }
        }

        return true;
    }

    public void smeltItemFirst()
    {
        if (this.canProcessFirst())
        {
            ItemStack resultItemStack = FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[1]);

            if (this.containingItems[2] == null)
            {
                this.containingItems[2] = resultItemStack.copy();
                if (this.tierGC > 1)
                {
                    String nameSmelted = this.containingItems[1].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw") || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard")))
                        this.containingItems[2].stackSize += resultItemStack.stackSize;
                }
            }
            else if (this.containingItems[2].isItemEqual(resultItemStack))
            {
                this.containingItems[2].stackSize += resultItemStack.stackSize;
                if (this.tierGC > 1)
                {
                    String nameSmelted = this.containingItems[1].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw")  || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard")))
                        this.containingItems[2].stackSize += resultItemStack.stackSize;
                }
            }

            this.containingItems[1].stackSize--;

            if (this.containingItems[1].stackSize <= 0)
            {
                this.containingItems[1] = null;
            }
        }
    }
    
    
    public void smeltItemSecond()
    {
        if (this.canProcessSecond())
        {
            ItemStack resultItemStack = FurnaceRecipes.smelting().getSmeltingResult(this.containingItems[3]);

            if (this.containingItems[4] == null)
            {
                this.containingItems[4] = resultItemStack.copy();
                if (this.tierGC > 1)
                {
                    String nameSmelted = this.containingItems[3].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw") || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard")))
                        this.containingItems[4].stackSize += resultItemStack.stackSize;
                }
            }
            else if (this.containingItems[4].isItemEqual(resultItemStack))
            {
                this.containingItems[4].stackSize += resultItemStack.stackSize;
                if (this.tierGC > 1)
                {
                    String nameSmelted = this.containingItems[3].getUnlocalizedName().toLowerCase();
                    if (resultItemStack.getUnlocalizedName().toLowerCase().contains("ingot") && (nameSmelted.contains("ore") || nameSmelted.contains("raw")  || nameSmelted.contains("moon") || nameSmelted.contains("mars") || nameSmelted.contains("shard")))
                        this.containingItems[4].stackSize += resultItemStack.stackSize;
                }
            }

            this.containingItems[3].stackSize--;

            if (this.containingItems[3].stackSize <= 0)
            {
                this.containingItems[3] = null;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        if (this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        {
        	this.setUltimateFurnace();
        	this.initialised = true;
        }
        else
        	this.initialised = false;
        this.processTicks = par1NBTTagCompound.getInteger("smeltingTicks");
        this.containingItems = this.readStandardItemsFromNBT(par1NBTTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.tierGC == 1 && this.storage.getEnergyStoredGC() > EnergyStorageTile.STANDARD_CAPACITY)
        	this.storage.setEnergyStored(EnergyStorageTile.STANDARD_CAPACITY);
    	super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("smeltingTicks", this.processTicks);
        this.writeStandardItemsToNBT(par1NBTTagCompound);
    }

    @Override
    protected ItemStack[] getContainingItems()
    {
        return this.containingItems;
    }

    @Override
    public String getInventoryName()
    {
        return GCCoreUtil.translate("tile.UltimateFurnace.name");
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return true;
    }
    
    @Override
    public boolean isItemValidForSlot(int slotID, ItemStack itemStack)
    {
        if (itemStack == null) return false;
    	return slotID == 1 | slotID == 3 ? FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null : slotID == 0 && ItemElectricBase.isElectricItem(itemStack.getItem());
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return new int[] { 0, 1, 2, 3, 4 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(slotID, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack par2ItemStack, int par3)
    {
        return slotID == 2 && slotID == 4;
    }

    @Override
    public boolean shouldUseEnergy()
    {
        return this.canProcessFirst() || this.canProcessSecond();
    }
}