package beyondspace.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FlameEntity extends Entity {

    protected int particleAge;
    protected int particleMaxAge;
    protected float particleScale;
    protected float particleGravity;
    /** Particle alpha */
    protected float particleAlpha;
    /** The icon field from which the given particle pulls its texture. */
    protected IIcon particleIcon;
    public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;

    protected FlameEntity(World world, double x, double y, double z) {
        super(world);
        this.particleAlpha = 1.0F;
        this.setSize(0.2F, 0.2F);
        this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.lastTickPosX = x;
        this.lastTickPosY = y;
        this.lastTickPosZ = z;
        this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4; // (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleAge = 0;
    }

    public FlameEntity(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        this(world, x, y, z);
        this.motionX = motionX + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionY = motionY + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionZ = motionZ + (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        float f = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / (double)f1 * (double)f * 0.4000000059604645D;
        this.motionY = this.motionY / (double)f1 * (double)f * 0.4000000059604645D + 0.10000000149011612D;
        this.motionZ = this.motionZ / (double)f1 * (double)f * 0.4000000059604645D;
        // EntityFlameFX
        this.motionX = this.motionX * 0.009999999776482582D + motionX;
        this.motionY = this.motionY * 0.009999999776482582D + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582D + motionZ;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
    }

	@Override
	public void entityInit() { }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) { }
	
	public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.motionY -= 0.04D * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        
        /*this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;*/

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }
	
	public boolean canTriggerWalking() {
        return false;
    }
	
	public boolean canAttackWithItem() {
		return false;
    }
}