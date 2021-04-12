package beyondspace.blocks.tileentity;

import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.IThrottledPacket;

@Optional.InterfaceList({
    @Optional.Interface(iface = "vazkii.botania.api.mana.IManaReceiver", modid = "Botania"),
    @Optional.Interface(iface = "vazkii.botania.api.mana.IThrottledPacket", modid = "Botania")
})
public class ManaToFuelWrapperTileEntity extends TileEntity implements IManaReceiver, IThrottledPacket {
	
	public static final double conversionRate = 0.1;
	public double fuel;
	public static final double maxFuel = 5000.0;
	public int mana;
	public static final int maxMana = (int) (maxFuel/conversionRate);
	public int speed;
	
	public ManaToFuelWrapperTileEntity() {
		this.fuel = 0;
		this.mana = 0;
		this.speed = 1;
	}
	
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			for (int i = 0; i < this.speed * 20; i++)
			if (this.mana > 0 && this.fuel < this.maxFuel) {
				this.fuel += conversionRate;
				--this.mana;
			}
			if (this.fuel > this.maxFuel) this.fuel = this.maxFuel;
			if (this.mana > this.maxMana) this.mana = this.maxMana;
		}
	}

	@Optional.Method(modid = "Botania")
	@Override
	public int getCurrentMana() {
		return this.mana;
	}

	@Optional.Method(modid = "Botania")
	@Override
	public boolean isFull() {
		return this.getCurrentMana() >= this.maxMana;
	}

	@Optional.Method(modid = "Botania")
	@Override
	public void recieveMana(int mana) {
		this.mana = Math.max(0, Math.min(getCurrentMana() + mana, this.maxMana));
	    this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord));
	    markDispatchable();
	}

	@Optional.Method(modid = "Botania")
	@Override
	public boolean canRecieveManaFromBursts() {
		return true;
	}

	@Optional.Method(modid = "Botania")
	@Override
	public void markDispatchable() {
		return;
	}
}
