package beyondspace.entity;

import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RedLightningEntity extends EntityLightningBolt {
	
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;

    private final Random random = new Random();
    
	private float damage;

	public RedLightningEntity(World world) {
		super(world, 0, 0, 0);
	}
	
    public RedLightningEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setSize(0.8F, 1.8F);
        this.setLocationAndAngles(x, y, z, 0.0F, 0.0F);      
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    protected void entityInit() {}

    protected void readEntityFromNBT(NBTTagCompound nbt) {}

    protected void writeEntityToNBT(NBTTagCompound nbt) {}
}