package beyondspace.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import beyondspace.BeyondSpace;
import beyondspace.ModInfo;
import beyondspace.proxy.network.CommonEventHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.Result;
import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyDisplayHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class PlasmaOmnitool extends ItemTool implements IItemElectric {

	private static final Set someVanillaStuff = Sets.newHashSet(new Block[] {	/* Pickaxe -> */ Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail,
																				/*	 Axe -> */ Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin,
																				/* 	 Spade -> */ Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});
	public static IIcon[] textures = new IIcon[2];
	public float transferMax = 200.0F;

	public PlasmaOmnitool() {
		super(0.0F, ToolMaterial.WOOD, someVanillaStuff);
		this.setCreativeTab(BeyondSpace.gaTab);
		this.setMaxDamage(100000);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setUnlocalizedName("PlasmaOmnitool");
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		textures[0] = iconRegister.registerIcon(ModInfo.MODID + ":PlasmaOmnitoolOff");
		textures[1] = iconRegister.registerIcon(ModInfo.MODID + ":PlasmaOmnitool");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return meta < 100000 ? textures[1] : textures[0];
	}
	
	/**
	 * Get efficiency on material
	 */
	@Override
	public float func_150893_a(ItemStack stack, Block block) {
		return getElectricityStored(stack) > 0 ? 20.0F : 0;
	}
	
	/**
	 * Is tool effective against block
	 */
	@Override
	public boolean func_150897_b(Block block) {
		return true;
	}
	
	/**
	 * Damage against entity 
	 */
	public float getDamageAgainstEntity(ItemStack stack) {
		return getElectricityStored(stack) > 0 ? 10.0F : 0;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return false;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)  {
		if (getElectricityStored(stack) > 0) {
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), this.getDamageAgainstEntity(stack));
			CommonEventHandler.dischargeStack(stack, 10.0F);
			return true;
		}
        return false;
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase destroyer) {
		CommonEventHandler.dischargeStack(stack, 10.0F);
		return true;
	}
	
	@Override
	public String getToolMaterialName() {
		return "PlasmaWeapon";
	}
	
	/**
	 * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
	 */
	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 12.0D, 0));
		return multimap;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass) {
		return 5;
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		return getElectricityStored(stack) > 0 ? 25.0F : 0;
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return ImmutableSet.of("pickaxe", "axe", "sword", "shovel", "hammer", "hoe");
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
		if (getElectricityStored(stack) > 0) {
			if (!entity.worldObj.isRemote) {
				if (entity instanceof IShearable) {
					IShearable target = (IShearable) entity;
					if (target.isShearable(stack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ)) {
						ArrayList<ItemStack> drops = target.onSheared(stack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
		
						Random rand = new Random();
						for (ItemStack stack1 : drops) {
							EntityItem ent = entity.entityDropItem(stack1, 1.0F);
							ent.motionY += rand.nextFloat() * 0.05F;
							ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
							ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
						}
						CommonEventHandler.dischargeStack(stack, 10.0F);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			Block block = player.worldObj.getBlock(x, y, z);
			if (block instanceof IShearable) {
				IShearable target = (IShearable) block;
				if (target.isShearable(stack, player.worldObj, x, y, z)) {
					ArrayList<ItemStack> drops = target.onSheared(stack, player.worldObj, x, y, z, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack));
					Random rand = new Random();
	
					for (ItemStack stack1 : drops) {
						float f = 0.7F;
						double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
						double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
						double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
						EntityItem entityitem = new EntityItem(player.worldObj, (double) x + d, (double) y + d1, (double) z + d2, stack1);
						entityitem.delayBeforeCanPickup = 10;
						player.worldObj.spawnEntityInWorld(entityitem);
					}
	
					CommonEventHandler.dischargeStack(stack, 10.0F);
					player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(block)], 1);
				}
			}
		}
		return false;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (getElectricityStored(stack) > 0) {
			boolean flag = true;
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, flag);
	
			if (movingobjectposition != null) {
				FillBucketEvent event = new FillBucketEvent(player, stack, world, movingobjectposition);
				if (MinecraftForge.EVENT_BUS.post(event)) {
					CommonEventHandler.dischargeStack(stack, 100.0F);
					return stack;
				}
	
				if (event.getResult() == Event.Result.ALLOW) {
					return stack;
				}
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					int k = movingobjectposition.blockZ;
	
					if (!world.canMineBlock(player, i, j, k)) {
						return stack;
					}
	
					if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
						return stack;
					}
	
					Material material = world.getBlock(i, j, k).getMaterial();
					int l = world.getBlockMetadata(i, j, k);
					
					if ((material == Material.water || material == Material.lava) && l == 0) {
						world.setBlockToAir(i, j, k);
						CommonEventHandler.dischargeStack(stack, 100.0F);
						return stack;
					}
	
				}
			}
		}
		return stack;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (getElectricityStored(stack) > 0) {
			if (!player.canPlayerEdit(x, y, z, side, stack)) {
				return false;
			} else {
				UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
				if (MinecraftForge.EVENT_BUS.post(event)) {
					return false;
				}
	
				if (event.getResult() == Result.ALLOW) {
					CommonEventHandler.dischargeStack(stack, 10.0F);
					return true;
				}
	
				Block block = world.getBlock(x, y, z);
	
				if (side != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt)) {
					Block block1 = Blocks.farmland;
					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
	
					if (world.isRemote) {
						return true;
					} else {
						world.setBlock(x, y, z, block1);
						CommonEventHandler.dischargeStack(stack, 10.0F);
						return true;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean what) {
		if (!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		String color = "";
		float joules = getElectricityStored(stack);
		if (joules <= getMaxElectricityStored(stack) / 3.0F) {
			color += EnumChatFormatting.DARK_RED;
		} else if (joules > getMaxElectricityStored(stack) * 2.0F / 3.0F) {
			color += EnumChatFormatting.DARK_GREEN;
		} else {
			color += EnumChatFormatting.GOLD;
		}
		list.add(color + EnergyDisplayHelper.getEnergyDisplayS(joules) + "/" + EnergyDisplayHelper.getEnergyDisplayS(getMaxElectricityStored(stack)));
		if (getElectricityStored(stack) > 0) list.add(EnumChatFormatting.BLUE + "+10 " + StatCollector.translateToLocal("attribute.name.generic.attackDamage"));
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		setElectricity(stack, 0.0F);
	}
	
	@Override
	public float recharge(ItemStack stack, float energy, boolean doRecharge) {
		float rejectedElectricity = Math.max(getElectricityStored(stack) + energy - getMaxElectricityStored(stack), 0.0F);
		float energyToReceive = energy - rejectedElectricity;
		
		if (energyToReceive > this.transferMax) {
			rejectedElectricity += energyToReceive - this.transferMax;
			energyToReceive = this.transferMax;
		}
		
		if (doRecharge) {
			setElectricity(stack, getElectricityStored(stack) + energyToReceive);
		}
		
		return energyToReceive;
	}

	@Override
	public float discharge(ItemStack stack, float energy, boolean doDischarge) {
		float energyToTransfer = Math.min(getElectricityStored(stack), energy);
		if (doDischarge) {
			setElectricity(stack, getElectricityStored(stack) - energyToTransfer);
		}
		return energyToTransfer;
	}

	@Override
	public float getElectricityStored(ItemStack stack) {
		float energyStored = 0.0F;
		if (stack.getTagCompound().hasKey("electricity")) {
			NBTBase obj = stack.getTagCompound().getTag("electricity");
			if ((obj instanceof NBTTagDouble)) {
				energyStored = ((NBTTagDouble) obj).func_150288_h();
			} else if ((obj instanceof NBTTagFloat)) {
				energyStored = ((NBTTagFloat) obj).func_150288_h();
			}
		}
		
		stack.setItemDamage((int)(stack.getMaxDamage() - (energyStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
		return energyStored;
	}

	@Override
	public float getMaxElectricityStored(ItemStack theItem) {
		return this.getMaxDamage();
	}

	@Override
	public void setElectricity(ItemStack stack, float joules) {
		float electricityStored = Math.max(Math.min(joules, getMaxElectricityStored(stack)), 0.0F);
		stack.getTagCompound().setFloat("electricity", electricityStored);
		
		stack.setItemDamage((int)(stack.getMaxDamage() - (electricityStored / getMaxElectricityStored(stack) * stack.getMaxDamage())));
	}

	@Override
	public float getTransfer(ItemStack stack) {
		return Math.min(this.transferMax , getMaxElectricityStored(stack) - getElectricityStored(stack));
	}

	@Override
	public int getTierGC(ItemStack itemStack) {
		return 1;
	}
}