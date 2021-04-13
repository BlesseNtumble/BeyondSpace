package beyondspace.asjlib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * Small utility lib to help with some tricks. Feel free to use it in your mods.
 * @author AlexSocol
 * */
public class ASJUtilities {
	
	/**
	 * Returns the name of the block
	 * @param block The block to get name from
	 */
	public static String getBlockName(Block block) {
		return block.getUnlocalizedName().substring(5);
	}
	
	/**
	 * Returns the name of the item
	 * @param item The item to get name from
	 */
	public static String getItemName(Item item) {
		return item.getUnlocalizedName().substring(5);
	}
	
	/**
	 * Returns String ID of the mod this block/item is registered in
	 * @param stack ItemStack with block/item for analysis
	 * */
	public static String getModId(ItemStack stack) {
		GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());
	    return id == null || id.modId.equals("") ? "minecraft" : id.modId;
	}
	
	/**
	 * Sends entity to dimension without portal frames
	 * @param entity The entity to send
	 * @param dimTo ID of the dimension the entity should be sent to
	 * */
	public static void sendToDimensionWithoutPortal1(Entity entity, int dimTo, double x, double y, double z) {
		if (entity instanceof EntityPlayerMP) {
			System.out.println("Coords: " + x + ' ' + y + ' ' + z);
			EntityPlayerMP player = (EntityPlayerMP) entity;
	        WorldServer worldTo = player.mcServer.worldServerForDimension(dimTo);
	        player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dimTo, new FreeTeleporter(worldTo, x, y, z));
		}
	}
	
	/**
	 * Determines whether entity will die from next hit
	 * */
	public static boolean willEntityDie(LivingHurtEvent event) {
		float amount = event.ammount;
		DamageSource source = event.source;
		EntityLivingBase living = event.entityLiving;
		if (!source.isUnblockable()) {
			int armor = 25 - living.getTotalArmorValue();
			amount = amount * armor / 25.0F;
		}
		if (living.isPotionActive(Potion.resistance)) {
			int resistance = 25 - (living.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			amount = amount * resistance / 25.0F;
		}
		return Math.ceil(amount) >= Math.floor(living.getHealth());
	}
	
	/**
	 * Returns the number of the slot with item matching to item passed in
	 * @param item The item to compare
	 * @param inventory The inventory to scan
	 */
	public static int getSlotWithItem(Item item, IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            if (inventory.getStackInSlot(i) != null && inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }
	
	/**
	 * Removes itemstack from inventory
	 * @param inventory Inventory
	 * @param stack ItemStack to remove
	 * @return If the stack was removed
	 */
	public static boolean consumeItemStack(IInventory inventory, ItemStack stack) {
	   if(getAmount(inventory, stack) >= stack.stackSize) {
	      for (int i = 0; i < inventory.getSizeInventory(); i++) {
	         if(isItemStackEqual(inventory.getStackInSlot(i), stack)){
	            int amount = Math.min(stack.stackSize, inventory.getStackInSlot(i).stackSize);
	            if(amount > 0) {
	               inventory.decrStackSize(i, amount);
	               stack.stackSize -= amount;
	            }
	            if(stack.stackSize <= 0) {
	               return true;
	            }
	         }
	      }
	   }
	   return false;
	}

	/**
	 * Returns the amount of item from itemstack in inventory
	 * @param inventory Inventory
	 * @param stack Stack to compare item
	 * @return Amount
	 */
	public static int getAmount(IInventory inventory, ItemStack stack) {
	   int amount = 0;
	   for (int i = 0; i < inventory.getSizeInventory(); i++) {
	      if(isItemStackEqual(inventory.getStackInSlot(i), stack)) {
	         amount += inventory.getStackInSlot(i).stackSize;
	      }
	   }
	   return amount;
	}
	
	/**
	 * Checks if two itemstacks has same ID and metadata
	 * @param stack1 First itemstack
	 * @param stack2 Second itemstack
	 */
	public static boolean isItemStackEqual(ItemStack stack1, ItemStack stack2) {
		return (stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage());
	}
	
	/**
	 * Removes itemstack with NBT from inventory 
	 * @param inventory Inventory
	 * @param stack ItemStack to remove
	 * @return If the stack was removed
	 */
	public static boolean consumeItemStackNBT(IInventory inventory, ItemStack stack) {
	   if(getAmountNBT(inventory, stack) >= stack.stackSize) {
	      for (int i = 0; i < inventory.getSizeInventory(); i++) {
	         if(isItemStackEqualNBT(inventory.getStackInSlot(i), stack)){
	            int amount = Math.min(stack.stackSize, inventory.getStackInSlot(i).stackSize);
	            if(amount > 0) {
	            	inventory.decrStackSize(i, amount);
	            	stack.stackSize -= amount;
	            }
	            if(stack.stackSize <= 0) {
	               return true;
	            }
	         }
	      }
	   }
	   return false;
	}

	/**
	 * Returns the amount of items in stack with NBT from inventory
	 * @param inventory Inventory
	 * @param stack Stack to compare item
	 * @return Amount
	 */
	public static int getAmountNBT(IInventory inventory, ItemStack stack) {
	   int amount = 0;
	   for (int i = 0; i < inventory.getSizeInventory(); i++) {
	      if(isItemStackEqualNBT(inventory.getStackInSlot(i), stack)) {
	         amount += inventory.getStackInSlot(i).stackSize;
	      }
	   }
	   return amount;
	}
	
	/**
	 * Checks if two itemstacks has same ID, metadata and NBT
	 * @param stack1 First itemstack
	 * @param stack2 Second itemstack
	 */
	public static boolean isItemStackEqualNBT(ItemStack stack1, ItemStack stack2) {
	   if(stack1 != null && stack2 != null && stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage()) {
	      if(!stack1.hasTagCompound() && !stack2.hasTagCompound()) {
	         return true;
	      } else if(stack1.hasTagCompound() != stack2.hasTagCompound()) {
	         return false;
	      } else if(stack1.stackTagCompound.equals(stack2.stackTagCompound)) {
	         return true;
	      }
	   }
	   return false;
	}
	
	/**
	 * Changes itemstack's item
	 * @param stack Stack to change its item
	 * @param item Item to set in {@link stack}
	 * */
	public static ItemStack changeStackItem(ItemStack stack, Item item) {
		ItemStack newStack = new ItemStack(item, stack.stackSize, stack.getItemDamage());
		newStack.stackTagCompound = stack.stackTagCompound;
		return newStack;
	}
	
	/**
	 * Removes <b>block</b> from GameRegistry
	 * */
	public static void unregisterBlock(Block block) {
		
	}
	
	/**
	 * Removes <b>item</b> from GameData
	 * */
	/*public static void unregisterItem(Item item) {
		try {
			{ // Unregistering ID
				ObjectIntIdentityMap underlyingIntegerMap = (ObjectIntIdentityMap) ReflectionHelper.findField(RegistryNamespaced.class, "underlyingIntegerMap").get(GameData.getItemRegistry());
				IdentityHashMap field_148749_a = (IdentityHashMap) ReflectionHelper.findField(IdentityHashMap.class, "field_148749_a").get(underlyingIntegerMap);
				List field_148748_b = (List) ReflectionHelper.findField(ObjectIntIdentityMap.class, "field_148748_b").get(underlyingIntegerMap);
				if (field_148749_a.containsKey(item) && field_148749_a.containsValue(Integer.valueOf(Item.getIdFromItem(item)))) field_148749_a.remove(item); else throw new NullPointerException("IdentityHashMap doesn't contains " + item.toString());
				if (field_148748_b.contains(item)) field_148748_b.set(Item.getIdFromItem(item), (Object)null); else throw new NullPointerException("List doesn't contains " + item.toString());
			}
			
			{ // Unregistering Item
				Map registryObjects = (Map) ReflectionHelper.findField(RegistryNamespaced.class, "field_148758_b").get(GameData.getItemRegistry());
				String name = GameData.getItemRegistry().getNameForObject(item);
				if (registryObjects.containsKey(name) && registryObjects.containsValue(item)) registryObjects.replace(name, item, (Object)null); else throw new NullPointerException("Map doesn't contains " + item.toString());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Removes recipe of <b>itemstack</b>
	 * @author Code by yope_fried, inspired by pigalot, provided by Develance on forum.mcmodding.ru
	 * */
	public static void RemoveRecipe(ItemStack resultItem) {
		ItemStack recipeResult = null;
		ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
		
		for (int scan = 0; scan < recipes.size(); scan++) {
			IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
			if (tmpRecipe instanceof ShapedRecipes) {
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}

			if (tmpRecipe instanceof ShapelessRecipes) {
				ShapelessRecipes recipe = (ShapelessRecipes) tmpRecipe;
				recipeResult = recipe.getRecipeOutput();
			}

			if (ItemStack.areItemStacksEqual(resultItem, recipeResult)) {
				System.out.println("[ASJLib] Removed Recipe: " + recipes.get(scan) + " -> " + recipeResult);
				recipes.remove(scan);
			}
		}
	}
	
	/**
	 * Returns MOP with block and entity
	 * @param entity_base Entiy to calculate vector from
	 * @param fasc Just put 1.0F there
	 * @param dist Max distance for use
	 * @param interact Can player interact with blocks (not sure)
	 */
	public static MovingObjectPosition getMouseOver(EntityLivingBase entity_base, float fasc, double dist, boolean interact) {
        if (entity_base == null || entity_base.worldObj == null) {
        	return null;
        }
        
        Entity pointedEntity = null;
        double d0 = dist;
        double d1 = d0;
        Vec3 vec3 = Vec3.createVectorHelper(entity_base.posX, (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? entity_base.posY : entity_base.posY + entity_base.getEyeHeight()), entity_base.posZ);
        Vec3 vec31 = entity_base.getLook(fasc);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
        Vec3 vec33 = null;
        MovingObjectPosition objectMouseOver = rayTrace(entity_base, dist, fasc);

        if (objectMouseOver != null) {
            d1 = objectMouseOver.hitVec.distanceTo(vec3);
        }

        float f1 = 1.0F;
        List list = entity_base.worldObj.getEntitiesWithinAABBExcludingEntity(entity_base, entity_base.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity)list.get(i);

            if (entity.canBeCollidedWith() || interact) {
                float f2 = entity.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3)) {
                    if (0.0D < d2 || d2 == 0.0D) {
                        pointedEntity = entity;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                        if (entity == entity_base.ridingEntity && !entity.canRiderInteract()) {
                            if (d2 == 0.0D) {
                                pointedEntity = entity;
                                vec33 = movingobjectposition.hitVec;
                            }
                        } else {
                            pointedEntity = entity;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
        }

        if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
            return new MovingObjectPosition(pointedEntity, vec33);
        }
        return null;
    }
	
	/**
	 * Raytracer for 'getMouseOver' method.<br>
	 * Don't use it. Use {@link #getMouseOver(EntityLivingBase, float, double, boolean) getMouseOver} instead
	 */
	private static MovingObjectPosition rayTrace(EntityLivingBase entity, double dist, float fasc) {
        Vec3 vec3 = Vec3.createVectorHelper(entity.posX, (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT ? entity.posY : entity.posY + entity.getEyeHeight()), entity.posZ);
        Vec3 vec31 = entity.getLook(fasc);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
        return entity.worldObj.func_147447_a(vec3, vec32, false, false, true);
    }
	
	/**
	 * Returns MOP with only blocks.
	 * @param player Player to calculate vector from
	 * @param fasc Just put 1.0F there
	 * @param dist Max distance for use
	 * @param interact Can player interact with blocks (not sure)
	 */
	public static MovingObjectPosition getSelectedBlock(EntityPlayer player, float fasc, double dist, boolean interact) {
		Vec3 vec3 = player.getPosition(fasc);
		vec3.yCoord += player.getEyeHeight();
		Vec3 vec31 = player.getLook(fasc);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
		MovingObjectPosition movingobjectposition = player.worldObj.rayTraceBlocks(vec3, vec32, interact);
		return movingobjectposition;
	}
	
	/**
	 * Registers new entity
	 * @param entityClass Entity's class file
	 * @param name The name of this entity
	 */
	public static void registerEntity(Class<? extends Entity> entityClass, String name, Object instance){
		int ID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, name, ID);
		EntityRegistry.registerModEntity(entityClass, name, ID, instance, 128, 1, true);
	}
	
	/**
	 * Registers new entity with egg
	 * @param entityClass Entity's class file
	 * @param name The name of this entity
	 * @param backColor Background egg color
	 * @param frontColor The color of dots
	 */
	public static void registerEntityEgg(Class<? extends Entity> entityClass, String name, int backColor, int frontColor, Object instance){
		int ID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(entityClass, name, ID);
		EntityRegistry.registerModEntity(entityClass, name, ID, instance, 128, 1, true);
		EntityList.entityEggs.put(Integer.valueOf(ID), new EntityList.EntityEggInfo(ID, backColor, frontColor));
	}

	/**
	 * Changes the biome at given coordinates, currently really buggy
	 * @param world This World
	 * @param x -Coordinate
	 * @param z -Coordinate
	 * @param boime The biome to set at this location
	 * */
	public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
		if (biome == null) {
			return;
		}
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		byte[] array = chunk.getBiomeArray();
		array[((z & 0xF) << 4 | x & 0xF)] = ((byte) (biome.biomeID & 0xFF));
		chunk.setBiomeArray(array);
	}
	
	/**
	 * Determines whether this potion is bad or not
	 * */
	public static boolean isBadPotion(Potion effect) {
		Field isBadEffect = ReflectionHelper.findField(Potion.class, new String[] { "isBadEffect", "field_76418_K" });
		try {
			return ((effect != null) && (isBadEffect.getBoolean(effect)));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @return String which tolds you to hold shift-key
	 * */
	public static String holdShift() {
		return (StatCollector.translateToLocal("tooltip.hold") + EnumChatFormatting.WHITE + "SHIFT" + EnumChatFormatting.GRAY + StatCollector.translateToLocal("tooltip.shift"));
	}
	
	/**
	 * @return String which tolds you to hold control-key
	 * */
	public static String holdCtrl() {
		return (StatCollector.translateToLocal("tooltip.hold") + EnumChatFormatting.WHITE + "CTRL" + EnumChatFormatting.GRAY + StatCollector.translateToLocal("tooltip.ctrl"));
	}
}