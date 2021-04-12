package beyondspace.blocks.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LaserTurretTileEntity extends TileEntity {
	
	public final int MAX_ENERGY = 50000, /*MAX_HEAT = 100,*/ ONE_SHOT = 500, DAMAGE = 20;
	public int cooldown, energy/*, heat*/;
	//boolean overheat;
	
	public LaserTurretTileEntity() {
		this.cooldown = 0;
		this.energy = 0;
	}
	
	@Override
	public void updateEntity() {
		this.refuel();
		if (cooldown > 0) --cooldown;
		if (!this.canShoot()) return;
		this.findTarget();
		//if (heat > 0) --heat;
	}
	
	public void refuel() {
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1, this.zCoord, this.xCoord + 1, this.yCoord + 2, this.zCoord + 1);
		List list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			EntityItem fuel = (EntityItem)iterator.next();
			if (fuel.getEntityItem().getItem() == Items.coal) {
				while (fuel.getEntityItem().stackSize > 0 && this.energy + 100 <= this.MAX_ENERGY) {
					this.energy += 100;
					--fuel.getEntityItem().stackSize;
				}
			}
		}
	}
	
	public void findTarget() {
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(32, 32, 32);
		List list = this.worldObj.getEntitiesWithinAABB(EntityMob.class, aabb);
		Iterator iterator = list.iterator();
		EntityMob target = null;
		
		while (iterator.hasNext()) {
			target = (EntityMob)iterator.next();
			if (this.canShoot()) this.shoot(target);
			else return;
		}
	}
	
	public void shoot(EntityLivingBase target) {
		this.cooldown = 20;
		//this.energy -= this.ONE_SHOT;
		double spawnX = this.xCoord + 0.5;
		double spawnY = this.yCoord + 1.5;
		double spawnZ = this.zCoord + 0.5;
		EntityArrow arrow = new EntityArrow(this.worldObj, spawnX, spawnY, spawnZ);
		
		double X = target.posX - spawnX;
		double Y = target.posY + 0.5 - spawnY;
		double Z = target.posZ - spawnZ;
		double length = 0.1; //Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2));
		
		arrow.motionX = X / length;
		arrow.motionY = Y / length;
		arrow.motionZ = Z / length;
		arrow.setDamage(DAMAGE);
		arrow.setKnockbackStrength(DAMAGE/10);
		this.worldObj.spawnEntityInWorld(arrow);
	}
	
	public boolean canShoot() {
		return this.energy - this.ONE_SHOT >= 0 && this.cooldown <= 0;
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("energy", this.energy);
		nbt.setInteger("cooldown", this.cooldown);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("energy");
		this.cooldown = nbt.getInteger("cooldown");
	}
}