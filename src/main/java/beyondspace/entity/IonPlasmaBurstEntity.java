package beyondspace.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import beyondspace.ModInfo;
import beyondspace.asjlib.HarmlessExplosion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class IonPlasmaBurstEntity extends Entity {

	public int burstprogress;
	public float damage;
	public Random random = new Random();
    public EntityPlayer exploder;
	
	public IonPlasmaBurstEntity(World world) {
		super(world);
		this.setSize(1.0F, 1.0F);
	}

	public IonPlasmaBurstEntity(World world, double x, double y, double z, EntityPlayer exploder) {
        super(world);
		burstprogress = 0;
		damage = 0;
		this.exploder = exploder;
		this.setSize(1.0F, 1.0F);
		this.setLocationAndAngles(x, y, z, 0, 0);
    }
	
	@Override
	public float getBrightness(float f) {
		return 255.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float f) {
		return 255;
    }
	
	@Override
	public void onUpdate() {
        super.onUpdate();
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
//______Explosion______________________________________________________________________________________________________________________
	    if (burstprogress == 0) this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, ModInfo.MODID + ":ionblast", 5.0F, 1.0F);
	    burstprogress += 2;
        if (burstprogress == 50) new HarmlessExplosion(this.worldObj, exploder, this.posX, this.posY, this.posZ, 16.0F);
        if (burstprogress >= 50) burstprogress += 3;
        if (burstprogress >= 100) burstprogress += 4;
        if (burstprogress >= 200) this.setDead();
		
        if (burstprogress <= 50) {
	        AxisAlignedBB axisalignedbb = this.boundingBox.expand(16.0D, 16.0D, 16.0D);
			List list = this.worldObj.getEntitiesWithinAABB(Entity.class, axisalignedbb);
	
			if (list != null && !list.isEmpty()) {
				Iterator iterator = list.iterator();

				while (iterator.hasNext()) {
					Entity entity = (Entity) iterator.next();
					
					double distance = Math.sqrt(Math.pow((entity.posX - this.posX), 2) + Math.pow((entity.posY - this.posY), 2) + Math.pow((entity.posZ - this.posZ), 2));
					if (distance <= 16.0F) {
						if (entity instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) entity;
							if (player.capabilities.isCreativeMode || player.capabilities.disableDamage || entity == exploder) {
								continue;
							}
						}
							entity.motionX = (this.posX - entity.posX) / 10.0F;
							entity.motionY = (this.posY - entity.posY) / 10.0F;
							entity.motionZ = (this.posZ - entity.posZ) / 10.0F;
						
						if (burstprogress == 48) {
							for (int i = 0; i < 50; i++) {
								damage += ((float) (random.nextInt(8) + 1));
							}
							if (entity instanceof EntityPlayer) {
								if ((EntityPlayer) entity != exploder) {
									EntityPlayer player = (EntityPlayer) entity;
									player.attackEntityFrom(DamageSource.causePlayerDamage(exploder), damage);
								}
							}
							if (entity instanceof EntityLiving) {
								EntityLiving living = (EntityLiving) entity;
								living.attackEntityFrom(DamageSource.causePlayerDamage(exploder), damage);
							}
							damage = 0.0F;
						}
					}
				}
			}
		}
    }
	
	@Override
	protected void entityInit() { }
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double p_70112_1_) {
        double d1 = this.boundingBox.getAverageEdgeLength();
        d1 *= 512.0D * this.renderDistanceWeight;
        return p_70112_1_ < d1 * d1;
    }
}