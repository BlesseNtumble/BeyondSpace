package beyondspace.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFire extends EntityThrowable {

    public EntityPlayer exploder;
	
	public EntityFire(World world, EntityLivingBase entity) {
		super(world, entity);
		this.exploder = (EntityPlayer) entity;
	}

	public EntityFire(World world) {
		super(world);
	}
	
	public EntityFire(World world, double x, double y, double z) {
		super(world, x, y, z);
    }
	
	@Override
	public void onImpact(MovingObjectPosition mop) {
		if(mop.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY)){
			mop.entityHit.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 0.5F);
			mop.entityHit.setFire(5);
		}
	}
	@Override
	public void onUpdate(){
		super.onUpdate();
		
		this.motionY /= getGravityVelocity();

		if (this.isInWater() || this.isWet()) {
			this.setDead();
		}
	}
	
	@Override
	public float getGravityVelocity(){
        return 0.5F;
    }
	
	@Override
	public float func_70182_d() {
        return 3.5F;
    }

	@Override
    public float func_70183_g() {
        return 0.0F;
    }
}
