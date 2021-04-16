package beyondspace.items;

import java.util.List;

import beyondspace.BeyondSpace;
import beyondspace.asjlib.ASJUtilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import scala.Int;

public class WeaponBase extends Item {
	/** Capacity of holder */
	private int capacity;
	/** Does this gun fires with flames */
	private boolean flames;
	/** Required holder */
	private AmmoBase holder;
	/** Time of reloading, in ticks (1/20 s)*/
	private int reloadSpeed;
	/** Delay between shots, in ticks (1/20 s) */
	private int speed;
	
	protected WeaponBase(int capacity, int durability, boolean flames, AmmoBase holder, String name, int reloadSpeed, int speed) {
		this.maxStackSize = 1;
		this.setCapacity(capacity);
		this.setCreativeTab(BeyondSpace.bsTab);
		this.setDurability(durability);
		this.setFlames(flames);
		this.setFull3D();
		this.setHolderType(holder);
		this.setReloadSpeed(reloadSpeed);
		this.setSpeed(speed);
		this.setUnlocalizedName(name);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow;
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		if (!stack.hasTagCompound()) this.addNBT(stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bol) {
		if (!world.isRemote) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (!stack.hasTagCompound()) addNBT(stack);
				// Fire
				if (stack.stackTagCompound.getBoolean("SetFired")) {
					stack.stackTagCompound.setInteger("GoDown", this.speed);
					stack.stackTagCompound.setBoolean("SetFired", false);
				}
				
				if (stack.stackTagCompound.getInteger("GoDown") > 0) {
					stack.stackTagCompound.setInteger("GoDown", stack.stackTagCompound.getInteger("GoDown") - 1);
				}
				
				if (stack.stackTagCompound.getInteger("GoDown") <= 0) {
					stack.stackTagCompound.setBoolean("CanFire", true);
				}
				
				// Flames
				if (stack.stackTagCompound.getInteger("Flames") > 0) {
					stack.stackTagCompound.setInteger("Flames", stack.stackTagCompound.getInteger("Flames") - 1);
				}
				
				// Reload
				if (stack.stackTagCompound.getBoolean("StartReloading")) {
					stack.stackTagCompound.setInteger("Ejecting", 0);
					stack.stackTagCompound.setBoolean("Switched", false);
					stack.stackTagCompound.setBoolean("StartReloading", false);
					stack.stackTagCompound.setBoolean("Reloading", true);
				}
				
				if (!stack.stackTagCompound.getBoolean("Switched") && stack.stackTagCompound.getBoolean("Reloading") && stack.stackTagCompound.getInteger("Ejecting") < this.reloadSpeed / 2) {
					stack.stackTagCompound.setInteger("Ejecting", stack.stackTagCompound.getInteger("Ejecting") + 1);
				}
				
				if (!stack.stackTagCompound.getBoolean("Switched") && stack.stackTagCompound.getBoolean("Reloading") && stack.stackTagCompound.getInteger("Ejecting") >= this.reloadSpeed / 2) {						
					stack.stackTagCompound.setBoolean("Switched", true);
				}
				
				if (stack.stackTagCompound.getBoolean("Switched") && stack.stackTagCompound.getBoolean("Reloading") && stack.stackTagCompound.getInteger("Ejecting") > 0) {
					stack.stackTagCompound.setInteger("Ejecting", stack.stackTagCompound.getInteger("Ejecting") - 1);
				}
			
				if (stack.stackTagCompound.getBoolean("Switched") && stack.stackTagCompound.getBoolean("Reloading") && stack.stackTagCompound.getInteger("Ejecting") <= 0) {
					stack.stackTagCompound.setBoolean("Reloading", false);
					stack.stackTagCompound.setBoolean("CanFire", true);
				}
			}
		}
	}
	
	/**
	 * Fires from the gun
	 * @param stack Stack with the gun
	 * @param world Current world
	 * @param player Shooting player
	 */
	public void fire(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote){ 

        	// Checking if there is no ammo
            if (stack.stackTagCompound.getInteger("Bullets") <= 0) {
            	player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "No ammo! | ��� ��������!"));
            }
            
            // Shooting projectile
            if (stack.stackTagCompound.getBoolean("CanFire") && stack.stackTagCompound.getInteger("Bullets") > 0) {
	            stack.stackTagCompound.setBoolean("SetFired", true);
				stack.stackTagCompound.setBoolean("CanFire", false);
				if (!player.capabilities.isCreativeMode) {
					stack.stackTagCompound.setInteger("Bullets", stack.stackTagCompound.getInteger("Bullets") - 1);
				}
		        stack.damageItem(world.rand.nextInt(3), player);
		        this.spawnProjectile(world, player);
		        if (this.flames) stack.stackTagCompound.setInteger("Flames", 2);
            }
		}
	}
	
	public void spawnProjectile(World world, EntityPlayer player) {}
	
	/**
	 * Reloads the gun. Consumes appropriate holder.
	 * @param stack Stack with the gun
	 * @param player Player with this gun
	 */
	public void reload(EntityPlayer player, ItemStack stack) {
		if (player.inventory.hasItem(this.holder) && stack.stackTagCompound.getInteger("Bullets") < this.capacity) {
    		int slot = ASJUtilities.getSlotWithItem(this.holder, player.inventory);
    		int need = this.capacity- stack.stackTagCompound.getInteger("Bullets");
    		ItemStack ammo = player.inventory.getStackInSlot(slot);
    		int have = (ammo.getMaxDamage() - ammo.getItemDamage());
    		if (have > 0) {
	    		if (have >= need) {
	    			stack.stackTagCompound.setInteger("Bullets", stack.stackTagCompound.getInteger("Bullets") + need);
	    			System.out.println("Inserted " + need + " bullets into gun");
	    			ammo.stackTagCompound.setInteger("Ammo", ammo.getMaxDamage() - ammo.getItemDamage() - need);
	    			ammo.damageItem(need, player);
	    			if (ammo.stackTagCompound.getInteger("Ammo") == 0) player.inventory.setInventorySlotContents(slot, null);
	    		} else if (have < need) {
	    			stack.stackTagCompound.setInteger("Bullets", stack.stackTagCompound.getInteger("Bullets") + have);
	    			player.inventory.setInventorySlotContents(slot, null);
	    		}
    		} else {
    			player.inventory.setInventorySlotContents(slot, null);
    		}
    		
    		stack.stackTagCompound.setBoolean("StartReloading", true);
			stack.stackTagCompound.setBoolean("CanFire", false);
    	}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, Int.MaxValue());
		return stack;
	}
	
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		player.setItemInUse(stack, 1);
    }
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        return true;
    }

	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	/**
	 * Specifies capacity of the holder
	 */
	private void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Specifies durability of the gun
	 */
	private void setDurability(int durability) {
		this.setMaxDamage(durability);
	}

	/**
	 * Specifies if the gun is shooting with flames
	 */
	private void setFlames(boolean flames) {
		this.flames = flames;
	}

	/**
	 * Specifies required holder type
	 */
	private void setHolderType(AmmoBase holder) {
		this.holder = holder;
	}

	/**
	 * Specifies reload speed
	 */
	private void setReloadSpeed(int reloadSpeed) {
		this.reloadSpeed = reloadSpeed;
	}

	/**
	 * Specifies delay between shots
	 */
	private void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Adds needed NBT data to the gun
	 */
	private void addNBT(ItemStack stack) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("CanFire", true);
		stack.stackTagCompound.setInteger("Bullets", this.capacity);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		if (!stack.hasTagCompound()) addNBT(stack);
		list.add(EnumChatFormatting.BOLD.GRAY + StatCollector.translateToLocal("item.weaponbase.firespeed") +" "+ (1200 / this.speed) + StatCollector.translateToLocal("item.weaponbase.timesperminute"));
		list.add(EnumChatFormatting.BOLD.GRAY + StatCollector.translateToLocal("item.weaponbase.requires") +" "+ StatCollector.translateToLocal("item." + ASJUtilities.getItemName(this.holder) + ".name"));
		if (stack.stackTagCompound.getInteger("Bullets") > 0) {
            list.add(EnumChatFormatting.BOLD.GRAY + StatCollector.translateToLocal("item.weaponbase.ammo") +" "+ stack.stackTagCompound.getInteger("Bullets") + "/" + this.capacity);
		} else {
			list.add(EnumChatFormatting.BOLD.DARK_RED + StatCollector.translateToLocal("item.weaponbase.noammo"));
		}
		int durabilityLeft = stack.getMaxDamage() - stack.getItemDamage();
		list.add(
				(durabilityLeft >= (stack.getMaxDamage() / 2) || (durabilityLeft == 250) ? EnumChatFormatting.BOLD.GRAY :
				durabilityLeft < (stack.getMaxDamage() / 2) && durabilityLeft >= (stack.getMaxDamage() / 4) ? EnumChatFormatting.BOLD.YELLOW :
				durabilityLeft < (stack.getMaxDamage() / 4) && durabilityLeft >= (stack.getMaxDamage() / 10) ? EnumChatFormatting.BOLD.GOLD :
				durabilityLeft < (stack.getMaxDamage() / 10) && durabilityLeft >= (stack.getMaxDamage() / 20) ? EnumChatFormatting.BOLD.RED :
				durabilityLeft < (stack.getMaxDamage() / 20) && durabilityLeft >= (stack.getMaxDamage() / 50) ? EnumChatFormatting.BOLD.DARK_RED : EnumChatFormatting.BOLD.BLACK)
				+ StatCollector.translateToLocal("item.weaponbase.approxshootsleft") +" "+ durabilityLeft);
	}
}
