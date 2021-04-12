package beyondspace.world.dimension.SaturnRings;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class RingsSaveData extends WorldSavedData
{
    public static final String saveDataID = "GASaturnRingsData";
    public NBTTagCompound datacompound;

    public RingsSaveData(String s)
    {
        super(RingsSaveData.saveDataID);
        this.datacompound = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.datacompound = nbt.getCompoundTag("saturnRings");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("saturnRings", this.datacompound);
    }
}