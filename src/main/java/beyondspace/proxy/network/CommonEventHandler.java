package beyondspace.proxy.network;

import java.util.Iterator;
import java.util.List;

import beyondspace.entity.RedLightningEntity;
import beyondspace.items.HighPressureResistantModularArmor;
import beyondspace.utils.BSConfig;
import beyondspace.utils.BSUtilities;
import beyondspace.utils.RegistrationsList;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import galaxyspace.core.registers.potions.GSPotions;
import galaxyspace.systems.SolarSystem.planets.overworld.items.armor.ItemSpaceArmors;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import micdoodle8.mods.galacticraft.core.entities.EntityMeteor;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class CommonEventHandler {
	
	ItemStack chestLastTick = null;
	
	@SubscribeEvent
	public void onEntityFall(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;

			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound()) player.getCurrentArmor(0).stackTagCompound = new NBTTagCompound();
			
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shighjump) &&  player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.highjump)) {
				event.distance -= 4;
			}
			
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.antigrav)) {
				if (event.distance >= 4 && isEnoughDurability(player.getCurrentArmor(0), event.distance)) {
					event.setCanceled(true);
					hurtArmor(player.getCurrentArmor(0), player,  event.distance);
				}
				
			}
			
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shockwave) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shockactive)) {
				if (event.distance >= 4 && isEnoughDurability(player.getCurrentArmor(0), event.distance)) {
					event.setCanceled(true);
					player.getCurrentArmor(0).stackTagCompound.setBoolean(HighPressureResistantModularArmor.shockactive, false);
					hurtArmor(player.getCurrentArmor(0), player, event.distance);
					shockwaveThem(player);
				}
			}
		}
	}
	
	public void shockwaveThem(EntityPlayer player) {
		double power = player.getCurrentArmor(0).stackTagCompound.getInteger(HighPressureResistantModularArmor.shocktick) / 4.0D;
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(player.posX, player.posY, player.posZ, player.posX + 1, player.posY + 1, player.posZ + 1).expand(power, power, power);
		power /= 10.0D;
		List list = player.worldObj.getEntitiesWithinAABB(EntityLiving.class, aabb);
		Iterator iterator = list.iterator();
		EntityLiving shocked = null;
		while (iterator.hasNext()) {
			shocked = (EntityLiving)iterator.next();
			
			shocked.motionX += 1 / (shocked.posX - player.posX + 0.000000000000000000000000000000000000000000001F) * power;
			shocked.motionY += power * 1.0D;
			shocked.motionZ += 1 / (shocked.posZ - player.posZ + 0.000000000000000000000000000000000000000000001F) * power;
		}
	}

	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;

			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound()) player.getCurrentArmor(0).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.shighjump) && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.highjump) && isEnoughDurability(player.getCurrentArmor(0), 1)) {
				dischargeStack(player.getCurrentArmor(0), 100);
				player.motionY += 0.5D;
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;

			//if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).hasTagCompound()) player.getCurrentArmor(2).stackTagCompound = new NBTTagCompound();
			
			double x = 1.0D;
			
			for(int i = 0; i < 4; i++)
				if (player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemSpaceArmors && player.getCurrentArmor(i).hasTagCompound() && player.getCurrentArmor(i).stackTagCompound.getBoolean("protection")) {
					if (isEnoughDurability(player.getCurrentArmor(i), 3)) {
						x += 1.0D;
						hurtArmor(player.getCurrentArmor(i), player, 3);
					}
				}
			
			event.ammount /= x;
			/*
			if ((player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.explosion) && (event.source.isExplosion()))) {
				if (isEnoughDurability(player.getCurrentArmor(2), 5)) {
					event.setCanceled(true);
					hurtArmor(player.getCurrentArmor(2), player, 5);
				}
			}
			
			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.fire) && (event.source.isFireDamage())) {
				if (isEnoughDurability(player.getCurrentArmor(2), 1)) {
					event.setCanceled(true);
					if (player.isBurning()) player.extinguish();
					hurtArmor(player.getCurrentArmor(2), player, 0.2F);
				}
			}*/
		}
	}
	
	@SubscribeEvent
	public void onStruckByLightning(EntityStruckByLightningEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound()) player.getCurrentArmor(0).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == RegistrationsList.highPressureResistantModularArmorLegs && !player.getCurrentArmor(1).hasTagCompound()) player.getCurrentArmor(1).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).hasTagCompound()) player.getCurrentArmor(2).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && !player.getCurrentArmor(3).hasTagCompound()) player.getCurrentArmor(3).stackTagCompound = new NBTTagCompound();
			
			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.tesla)) {
				if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric)) ((HighPressureResistantModularArmor) player.getCurrentArmor(0).getItem()).setElectricity(player.getCurrentArmor(0), ((HighPressureResistantModularArmor) player.getCurrentArmor(0).getItem()).getMaxElectricityStored(player.getCurrentArmor(0)));
				if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == RegistrationsList.highPressureResistantModularArmorLegs && !player.getCurrentArmor(1).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric)) ((HighPressureResistantModularArmor) player.getCurrentArmor(1).getItem()).setElectricity(player.getCurrentArmor(1), ((HighPressureResistantModularArmor) player.getCurrentArmor(1).getItem()).getMaxElectricityStored(player.getCurrentArmor(1)));
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric)) ((HighPressureResistantModularArmor) player.getCurrentArmor(2).getItem()).setElectricity(player.getCurrentArmor(2), ((HighPressureResistantModularArmor) player.getCurrentArmor(2).getItem()).getMaxElectricityStored(player.getCurrentArmor(2)));
				if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && !player.getCurrentArmor(3).stackTagCompound.getBoolean(HighPressureResistantModularArmor.electric)) ((HighPressureResistantModularArmor) player.getCurrentArmor(3).getItem()).setElectricity(player.getCurrentArmor(3), ((HighPressureResistantModularArmor) player.getCurrentArmor(3).getItem()).getMaxElectricityStored(player.getCurrentArmor(3)));
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onBreakSpeed(BreakSpeed event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;

			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).hasTagCompound()) player.getCurrentArmor(2).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.fastdig)) {
				event.newSpeed = event.originalSpeed * 4;
			}
		}
	}
	
	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {
		ItemStack result = attemptFill(event.world, event.entityPlayer, event.target);
		
		if (result != null) {
			event.result = result;
			event.setResult(Result.ALLOW);
		}
	}

	private ItemStack attemptFill(World world, EntityPlayer player, MovingObjectPosition p) {
		Block block = world.getBlock(p.blockX, p.blockY, p.blockZ);
		if (block == RegistrationsList.diamondLiquid || block == RegistrationsList.H2ONH3CH4Liquid || block == RegistrationsList.HHeLiquid) {
			return new ItemStack(Items.bucket);
		}
		return null;
	}
	
    @SubscribeEvent
    public void onPlayerPickupItem(ItemPickupEvent event){
    	EntityPlayer player = event.player;
		if (player.dimension == BSConfig.saturn) {
			if (event.pickedUp.getEntityItem().isItemEqual(new ItemStack(Items.diamond))) {
				player.addStat(RegistrationsList.diamond_rain, 1);
			}
		}
    }

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		/*// Do space stuff
		if (Switches.spaceDim) if (!event.entity.worldObj.isRemote && event.entity.dimension == GAConfig.space) GASpaceUtilities.doStuffInSpaceDim(event.entity);
		// Launch to space!!!
		if (Switches.spaceDim) if (!event.entity.worldObj.isRemote && event.entity.dimension != GAConfig.space && (event.entity.worldObj.provider instanceof WorldProviderSpace || event.entity.worldObj.provider instanceof WorldProviderSurface) && event.entity.posY > 500) {
			Vec3 pos = GASpaceUtilities.getBodyPositionFromDimID(event.entity.dimension);
			ASJUtilities.sendToDimensionWithoutPortal(event.entity, GAConfig.space, pos.xCoord, pos.yCoord, pos.zCoord);
			//if (event.entity.worldObj instanceof WorldServer) WorldUtil.transferEntityToDimension(event.entity, GAConfig.space, (WorldServer) event.entity.worldObj, false, null);
		}
		*/
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			//if (!player.capabilities.isFlying) player.motionY = -50;
			
			// Just in case (avoiding NPE)
			if (player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && !player.getCurrentArmor(0).hasTagCompound()) player.getCurrentArmor(0).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == RegistrationsList.highPressureResistantModularArmorLegs && !player.getCurrentArmor(1).hasTagCompound()) player.getCurrentArmor(1).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).hasTagCompound()) player.getCurrentArmor(2).stackTagCompound = new NBTTagCompound();
			if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead && !player.getCurrentArmor(3).hasTagCompound()) player.getCurrentArmor(3).stackTagCompound = new NBTTagCompound();
			
			chargeItems(player);
			
			if (!player.capabilities.isCreativeMode) {
				// Antiradiation
				if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.antirad)) {
					player.removePotionEffect(GSPotions.radiation.id);
				}
				// Flight stuff
				if (chestLastTick != player.getCurrentArmor(2)) checkArmor(player); {
					if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.jetpack)) {
						if (player.capabilities.isFlying) {
							if (((HighPressureResistantModularArmor) player.getCurrentArmor(2).getItem()).getElectricityStored(player.getCurrentArmor(2)) - 1 > 0) {
								dischargeStack(player.getCurrentArmor(2), 1);
							} else {
								player.capabilities.isFlying = false;
								player.capabilities.allowFlying = false;
							}
						}
					}
				}	
			}
			chestLastTick = player.getCurrentArmor(2);	
			
			// Frostwalking stuff
			if(player.isSneaking() && (player.getCurrentArmor(0) != null) && (player.getCurrentArmor(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot && player.getCurrentArmor(0).stackTagCompound.getBoolean(HighPressureResistantModularArmor.frostwalk))){ 
				if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
				} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
				} 
	
				if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
				} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX) + 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
				} 
	
				if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1) == Blocks.water) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1, Blocks.ice); 
				} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1) == Blocks.lava) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) + 1, Blocks.obsidian); 
				} 
	
				if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.water) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.ice); 
				} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ)) == Blocks.lava) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX) - 1, MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ), Blocks.obsidian); 
				} 
	
				if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1) == Blocks.water) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1, Blocks.ice); 
				} else if (player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1) == Blocks.lava) { 
					player.worldObj.setBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY) - 1, MathHelper.floor_double(player.posZ) - 1, Blocks.obsidian); 
				} 
			}
			
			// Diamond Rain on Saturn
			if (player.dimension == BSConfig.saturn && BSConfig.isSaturnDiamondRain && player.worldObj.rand.nextInt(1000) == 0) {
				if (!player.worldObj.isRemote) player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX + player.worldObj.rand.nextInt(24) - 12, 255, player.posZ + player.worldObj.rand.nextInt(24) - 12, new ItemStack(Items.diamond)));
			}

			//Meteors in Overworld
			if (player.dimension == 0 && BSConfig.isOverworldMeteors && player.worldObj.rand.nextInt(20*60*60*24) == 0) {
				if (!player.worldObj.isRemote) player.worldObj.spawnEntityInWorld(new EntityMeteor(player.worldObj, player.posX + player.worldObj.rand.nextInt(24) - 12, 255, player.posZ + player.worldObj.rand.nextInt(24) - 12, 0, -5, 0, 5));
			}
			
			// Craft armor set
			if(player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == RegistrationsList.highPressureResistantModularArmorHead &&
			   player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody &&
			   player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == RegistrationsList.highPressureResistantModularArmorLegs &&
			   player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == RegistrationsList.highPressureResistantModularArmorBoot){
			   player.addStat(RegistrationsList.indestructible, 1);
			}
			
			// Storm on Gas Giants
			if (player.dimension == BSConfig.jupiter || player.dimension == BSConfig.saturn || player.dimension == BSConfig.uranus || player.dimension == BSConfig.neptune) {
				if (player.worldObj.rand.nextInt(20) == 0) {
					int c = player.worldObj.rand.nextInt(1);

					int x = (int) (player.posX + player.worldObj.rand.nextInt(64) - 32);
					int z = (int) (player.posZ + player.worldObj.rand.nextInt(64) - 32);
					double y = player.worldObj.getTopSolidOrLiquidBlock(x, z);
					
					for (int i = 0; i < c; i++) {
						//player.worldObj.spawnEntityInWorld(new EntityLightningBolt(player.worldObj, player.posX + (double) player.worldObj.rand.nextInt(64) - 32, 160, player.posZ + (double) player.worldObj.rand.nextInt(64) - 32));
						player.worldObj.spawnEntityInWorld(new RedLightningEntity(player.worldObj, x, y, z));
					}
				}
			}
		}
	}
	
	/** Uses energy from portable charger to charge items */
	private void chargeItems(EntityPlayer player) {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() == RegistrationsList.portableBattery) {
				if (((ItemElectricBase)player.inventory.getStackInSlot(i).getItem()).getElectricityStored((player.inventory.getStackInSlot(i))) == 0) continue;
				for (int j = 0; j < player.inventory.getSizeInventory(); j++) {
					if (player.inventory.getStackInSlot(j) != null && player.inventory.getStackInSlot(j).getItem() != RegistrationsList.portableBattery) {
						BSUtilities.rechargeItemWithItem(player.inventory.getStackInSlot(j), 200, player.inventory.getStackInSlot(i), false);
					}
				}
			}
		}
	}
	
	/** Cheks players armor to remove flight ability if necessarily */
	public void checkArmor(EntityPlayer player) {
		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && !player.getCurrentArmor(2).hasTagCompound()) player.getCurrentArmor(2).stackTagCompound = new NBTTagCompound();
		if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == RegistrationsList.highPressureResistantModularArmorBody && player.getCurrentArmor(2).stackTagCompound.getBoolean(HighPressureResistantModularArmor.jetpack) && isEnoughDurability(player.getCurrentArmor(2), 1)) {
			player.capabilities.allowFlying = true;
		} else if ((player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() != RegistrationsList.highPressureResistantModularArmorBody) || (player.getCurrentArmor(2) == null)) {
			player.capabilities.allowFlying = false;
			player.capabilities.isFlying = false;
		}
	}
	
	/**
	 * @param stack Itemstack to damage
	 * @param amount Amount of damage
	 * @return Can this itemstack take the damage without breaking
	 * */
	public static boolean isEnoughDurability(ItemStack stack, float amount) {
		if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		if (stack.getItem() instanceof IItemElectric) {
			return ((IItemElectric)stack.getItem()).getElectricityStored(stack) - (amount * 100) > 0;
		} else {
			if ((int) amount < 1) amount++;
			return stack.getItemDamage() + amount < stack.getMaxDamage();
		}
	}
	
	/**
	 * Actually damages itemstack depending on it's electricity state
	 * @param stack Itemstack to damage
	 * @param amount Amount of damage
	 * */
	public static void hurtArmor(ItemStack stack, EntityPlayer player, float amount) {
		if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		if (stack.getItem() instanceof IItemElectric) {
			dischargeStack(stack, amount * 100);
		} else {
			if ((int) amount < 1) amount++;
			stack.damageItem((int) amount, player);
		}
	}
	
	/**
	 * Will discharge itemstack by the ammount
	 * @param stack Itemstack to discharge
	 * @param amount Amount of electricity to take
	 * */
	public static void dischargeStack(ItemStack stack, float amount) {
		((IItemElectric)stack.getItem()).discharge(stack, amount, true);
	}
	
	/**
	 * Will recharge itemstack by the ammount
	 * @param stack Itemstack to recharge
	 * @param amount Amount of electricity to provide
	 * */
	public static void rechargeStack(ItemStack stack, float amount) {
		((IItemElectric)stack.getItem()).recharge(stack, amount, true);
	}
}
