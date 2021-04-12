package beyondspace.blocks.tileentity;

import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

@Optional.InterfaceList({
    @Optional.Interface(iface = "thaumcraft.api.aspects.IAspectContainer", modid = "Thaumcraft"),
    @Optional.Interface(iface = "thaumcraft.api.aspects.IEssentiaTransport", modid = "Thaumcraft")
})
public class VisToFuelWrapperTileEntity extends TileEntity implements IEssentiaTransport, IAspectContainer {
	
	public static final double conversionRate = 20;
	public double fuel;
	public static final double maxFuel = 5000.0;
	public int vis;
	public static final int maxVis = (int) (maxFuel/conversionRate);
	public int speed;
	
	public VisToFuelWrapperTileEntity() {
		this.fuel = 0;
		this.vis = 0;
		this.speed = 1;
	}
	
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			for (int i = 0; i < this.speed * 20; i++)
			if (this.vis > 0 && this.fuel < this.maxFuel) {
				this.fuel += conversionRate;
				--this.vis;
			}
			if (this.fuel > this.maxFuel) this.fuel = this.maxFuel;
			if (this.vis > this.maxVis) this.vis = this.maxVis;
			if (this.worldObj.getWorldTime() % 10 == 0 && this.vis < this.maxVis) fillMe();
		}
	}

	void fillMe() {
		for (int i = 0; i < 6; i ++) {
			TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.VALID_DIRECTIONS[i]);
			if (te != null) {
				//System.out.println("Taking from " + ForgeDirection.VALID_DIRECTIONS[i].toString());
				IEssentiaTransport ic = (IEssentiaTransport) te;
				if (!ic.canOutputTo(ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[i]))) {
					return;
				}
	
				if (ic.getSuctionAmount(ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[i])) < getSuctionAmount(ForgeDirection.VALID_DIRECTIONS[i])) {
					addToContainer(Aspect.ENERGY, ic.takeEssentia(Aspect.ENERGY, 1, ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[i])));
				}
			}
		}
	}
	
	
	@Optional.Method(modid = "Thaumcraft")
	@Override
	public AspectList getAspects() {
		AspectList al = new AspectList();
		if (this.vis > 0) al.add(Aspect.ENERGY, this.vis);
		return al;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public void setAspects(AspectList aspects) { }

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return tag == Aspect.ENERGY;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int addToContainer(Aspect tag, int amount) {
		if (amount == 0 || this.vis >= this.maxVis || !this.doesContainerAccept(tag)) return 0;
		int added = Math.min(amount, this.maxVis - this.vis);
		this.vis += added;
		amount -= added;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		markDirty();
		return amount;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return tag == Aspect.ENERGY && this.vis >= amount;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean doesContainerContain(AspectList list) {
		for (Aspect tag : list.getAspects()) {
			if ((this.vis >= list.getAmount(tag)) && (tag == Aspect.ENERGY)) return true;
		}
		return false;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean isConnectable(ForgeDirection face) {
		return true;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public void setSuction(Aspect aspect, int amount) { }

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return Aspect.ENERGY;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int getSuctionAmount(ForgeDirection face) {
		return this.vis < this.maxVis ? 64 : 0;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return 0;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
		return amount - addToContainer(aspect, amount);
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return Aspect.ENERGY;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.vis;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public int getMinimumSuction() {
		return Integer.MAX_VALUE;
	}

	@Optional.Method(modid = "Thaumcraft")
	@Override
	public boolean renderExtendedTube() {
		return false;
	}
}
